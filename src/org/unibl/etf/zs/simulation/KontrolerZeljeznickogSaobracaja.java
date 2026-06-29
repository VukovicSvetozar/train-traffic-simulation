package org.unibl.etf.zs.simulation;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.unibl.etf.zs.model.mapa.Polje;
import org.unibl.etf.zs.model.mapa.Teritorija;
import org.unibl.etf.zs.model.prevoz.ImeSektora;
import org.unibl.etf.zs.model.prevoz.PrevoznoSredstvo;
import org.unibl.etf.zs.model.prevoz.Dionica;
import org.unibl.etf.zs.model.prevoz.SmjerKretanja;
import org.unibl.etf.zs.model.prevoz.kompozicije.Kompozicija;
import org.unibl.etf.zs.model.prevoz.kompozicije.Kompozicija.StanjeKompozicije;
import org.unibl.etf.zs.model.prevoz.vozila.Vozilo;
import org.unibl.etf.zs.model.saobracajnice.Pruga;
import org.unibl.etf.zs.model.saobracajnice.PruzniPrelaz;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica;
import org.unibl.etf.zs.model.stanica.ZeljeznickaStanica;
import org.unibl.etf.zs.utility.FileLogger;

import javafx.application.Platform;

import org.unibl.etf.zs.model.prevoz.kompozicije.Lokomotiva;
import org.unibl.etf.zs.model.prevoz.kompozicije.Vagon;

public class KontrolerZeljeznickogSaobracaja {

	private static List<Kompozicija> listaKompozicija = Collections.synchronizedList(new ArrayList<Kompozicija>());
	private boolean prolaziPruzniPrelaz = false;

	public void kompozicijaUlaziNaStanicu(Kompozicija kompozicija) {
		ZeljeznickaStanica zs = Teritorija.pronadjiStanicu(kompozicija.getPolaziste());
		if (zs != null) {
			zs.getTrenutneKompozicjeNaZeljeznickojStanici().add(kompozicija);
			zs.prikazPodataka();
			ispisiPorukuKadaJeUStanici(zs, kompozicija);
		}
	}

	public void pokreniKompoziciju(Kompozicija kompozicija) {
		Saobracajnica polaznaTacka = odrediPocetnuTackuISmjer(kompozicija);
		Dionica sektor = odrediAktivniSektor(kompozicija);
		while (zauzetaDeonica(kompozicija, polaznaTacka, sektor)) {
			synchronized (kompozicija) {
				try {
					kompozicija.wait();
				} catch (InterruptedException ex) {
					FileLogger.log(Level.SEVERE, "Trenutna nit je prekinuta.", ex);
				}
			}
		}
		kompozicija.setStanjeKompozicije(StanjeKompozicije.U_POKRETU);
		dodajKompozicijuUSektor(kompozicija, sektor);
		prilagodiBrzinuUSektoru(kompozicija, sektor);
		pomjeriSliku(kompozicija.getLokomotive().get(0), polaznaTacka.getPozicijaX(), polaznaTacka.getPozicijaY());
	}

	public void pomjeriKompoziciju(Kompozicija kompozicija) {
		izlazakIzMedjustanice(kompozicija);

		PrevoznoSredstvo elementKompozicije = kompozicija.getLokomotive().stream()
				.filter(l -> l.getSmjerKretanja() != null).findFirst().orElse(null);
		if (elementKompozicije == null)
			elementKompozicije = kompozicija.getVagoni().stream().filter(v -> v.getSmjerKretanja() != null).findFirst()
					.orElse(null);

		if (elementKompozicije == null) {
			ulazakUMedjustanicu(kompozicija);
		} else {
			List<SmjerKretanja> sviSmjerovi = new LinkedList<SmjerKretanja>(Arrays.asList(SmjerKretanja.values()));
			sviSmjerovi.remove(suprotanSmjer(elementKompozicije.getSmjerKretanja()));

			for (SmjerKretanja smjerKretanja : sviSmjerovi) {
				int pozicijaX = elementKompozicije.getPozicijaX();
				int pozicijaY = elementKompozicije.getPozicijaY();

				switch (smjerKretanja) {
				case ISTOK:
					pozicijaY++;
					break;
				case ZAPAD:
					pozicijaY--;
					break;
				case SJEVER:
					pozicijaX--;
					break;
				case JUG:
					pozicijaX++;
					break;
				}

				if (Teritorija.uGranicamaMape(pozicijaX, pozicijaY)) {
					Polje polje = Teritorija.vratiPolje(pozicijaX, pozicijaY);
					Saobracajnica element = polje.vratiSaobracajnicu();
					if (element != null) {
						if (Saobracajnica.TipSaobracajnice.ZELJEZNICKA_STANICA.equals(element.getTipSaobracajnice())) {
							predjiPolje(kompozicija, null, -1, -1);
							String novoPolaziste = Teritorija.vratiImeStanice(element);
							kompozicija.setPolaziste(novoPolaziste);
							break;
						} else if (Saobracajnica.TipSaobracajnice.PRUGA.equals(element.getTipSaobracajnice())) {
							predjiPolje(kompozicija, smjerKretanja, pozicijaX, pozicijaY);
							break;
						} else if (Saobracajnica.TipSaobracajnice.PRUZNI_PRELAZ.equals(element.getTipSaobracajnice())) {
							predjiPolje(kompozicija, smjerKretanja, pozicijaX, pozicijaY);
							prolaziPruzniPrelaz = true;
							break;
						}

					}
				}
			}
		}
	}

	public void kompozicijaUMedjustanici(Kompozicija kompozicija) {
		int vrijemeBoravkaUMedjustanici = 3000;
		try {
			Thread.sleep(vrijemeBoravkaUMedjustanici);
		} catch (InterruptedException ex) {
			FileLogger.log(Level.SEVERE, "Trenutna nit je prekinuta.", ex);
		}
		if (kompozicija.getPolaziste().equals(kompozicija.getOdrediste()))
			kompozicija.setStanjeKompozicije(StanjeKompozicije.ZAVRSENO);
		else
			kompozicija.setStanjeKompozicije(StanjeKompozicije.CEKANJE_NA_POLAZAK);
		prikaziPodatkeOZeljeznickojStanici(kompozicija);
	}

	private Saobracajnica odrediPocetnuTackuISmjer(Kompozicija kompozicija) {
		String polaziste = kompozicija.getPolaziste();
		String odrediste = kompozicija.getOdrediste();
		Saobracajnica pocetnaTacka = new Saobracajnica();
		Lokomotiva vodecaLokomotiva = kompozicija.getLokomotive().get(0);
		switch (polaziste) {
		case "A":
			pocetnaTacka.postaviPozicijuElementu(26, 2);
			vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.SJEVER);
			break;
		case "B":
			if ("A".equals(odrediste)) {
				pocetnaTacka.postaviPozicijuElementu(6, 5);
				vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.ZAPAD);
			} else {
				pocetnaTacka.postaviPozicijuElementu(6, 8);
				vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.ISTOK);
			}
			break;
		case "C":
			if ("D".equals(odrediste)) {
				pocetnaTacka.postaviPozicijuElementu(12, 21);
				vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.ISTOK);
			} else if ("E".equals(odrediste)) {
				pocetnaTacka.postaviPozicijuElementu(14, 20);
				vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.JUG);
			} else {
				pocetnaTacka.postaviPozicijuElementu(11, 19);
				vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.SJEVER);
			}
			break;
		case "D":
			pocetnaTacka.postaviPozicijuElementu(1, 25);
			vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.ZAPAD);
			break;
		case "E":
			pocetnaTacka.postaviPozicijuElementu(24, 26);
			vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.SJEVER);
			break;
		case "F":
			pocetnaTacka.postaviPozicijuElementu(29, 2);
			vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.SJEVER);
			break;
		case "G":
			pocetnaTacka.postaviPozicijuElementu(25, 29);
			vodecaLokomotiva.setSmjerKretanja(SmjerKretanja.ZAPAD);
			break;
		}
		return pocetnaTacka;
	}

	private Dionica odrediAktivniSektor(Kompozicija kompozicija) {
		String polaziste = kompozicija.getPolaziste();
		String odrediste = kompozicija.getOdrediste();
		ImeSektora oblastSektora = null;

		switch (polaziste) {
		case "A":
			oblastSektora = ImeSektora.ZAPADNI;
			break;
		case "B":
			if ("A".equals(odrediste))
				oblastSektora = ImeSektora.ZAPADNI;
			else
				oblastSektora = ImeSektora.CENTRALNI;
			break;
		case "C":
			if ("D".equals(odrediste))
				oblastSektora = ImeSektora.SJEVERNI;
			else if ("E".equals(odrediste))
				oblastSektora = ImeSektora.ISTOCNI;
			else
				oblastSektora = ImeSektora.CENTRALNI;
			break;
		case "D":
			oblastSektora = ImeSektora.SJEVERNI;
			break;
		case "E":
			oblastSektora = ImeSektora.ISTOCNI;
			break;
		case "F":
			oblastSektora = ImeSektora.JUGOZAPADNI;
			break;
		case "G":
			oblastSektora = ImeSektora.JUGOISTOCNI;
			break;
		}
		Dionica sektor = null;
		for (Dionica s : Teritorija.getPruzniSektori()) {
			if (oblastSektora.equals(s.getImeSektora())) {
				sektor = s;
				break;
			}
		}
		return sektor;
	}

	private boolean zauzetaDeonica(Kompozicija kompozicija, Saobracajnica polaznaTacka, Dionica sektor) {

		boolean zauzeto;
		if ((sektor.getTrenutneKompozicijeNaPruzi().isEmpty()
				|| kompozicija.getPolaziste().equals(sektor.getImeTrenutnePolazneStanice()))
				&& Teritorija.slobodnoMjestoZaPrevoznoSredstvo(polaznaTacka.getPozicijaX(),
						polaznaTacka.getPozicijaY()))
			zauzeto = false;
		else
			zauzeto = true;
		return zauzeto;
	}

	private void dodajKompozicijuUSektor(Kompozicija kompozicija, Dionica sektor) {
		int brojPotrebnihPrelazaka = kompozicija.getLokomotive().size() + kompozicija.getVagoni().size() + 2;
		kompozicija.setBrojPotrebnihPrelazaka(brojPotrebnihPrelazaka);
		sektor.getTrenutneKompozicijeNaPruzi().put(kompozicija, true);
		sektor.setImeTrenutnePolazneStanice(kompozicija.getPolaziste());
	}

	private void prilagodiBrzinuUSektoru(Kompozicija kompozicija, Dionica sektor) {
		if (sektor.getBrzina() == 0 || sektor.getBrzina() > kompozicija.getMaksimalnaBrzina()) {
			kompozicija.setTrenutnaBrzina(kompozicija.getMaksimalnaBrzina());
			sektor.setBrzina(kompozicija.getMaksimalnaBrzina());
		} else if (sektor.getBrzina() <= kompozicija.getMaksimalnaBrzina()) {
			kompozicija.setTrenutnaBrzina(sektor.getBrzina());
		}
	}

	private void predjiPolje(Kompozicija kompozicija, SmjerKretanja smjerKretanja, int pozicijaX, int pozicijaY) {
		ispisiPorukuKadaJeUPokretu(kompozicija);
		postaviPoljaPodNapon(kompozicija);
		if (prolaziPruzniPrelaz)
			predjiPruzniPrelaz(kompozicija);
//		pomjeriSlike(kompozicija);
		ukloniElementeSaMape(kompozicija);
		proslijediSmjerKrozKompoziciju(kompozicija, smjerKretanja, pozicijaX, pozicijaY);
		pomjeriSlike(kompozicija);
	}

	private void pomjeriSliku(PrevoznoSredstvo elementKompozicije, int pozicijaX, int pozicijaY) {
		Platform.runLater(() -> {
			elementKompozicije.postaviPozicijuElementu(pozicijaX, pozicijaY);
			if (elementKompozicije.getSmjerKretanja() != null)
				elementKompozicije.getPogledNaSliku().setVisible(true);
			else
				elementKompozicije.getPogledNaSliku().setVisible(false);
			elementKompozicije.getPogledNaSliku().setTranslateX(22 * elementKompozicije.getPozicijaY());
			elementKompozicije.getPogledNaSliku().setTranslateY(22 * elementKompozicije.getPozicijaX());
		});
		Teritorija.postaviElementNaMapu(elementKompozicije.getPozicijaX(), elementKompozicije.getPozicijaY(),
				elementKompozicije);
	}

	private void izlazakIzMedjustanice(Kompozicija kompozicija) {
		boolean sveLokomotiveNapustileStanicu = kompozicija.getLokomotive().stream()
				.allMatch(l -> l.getSmjerKretanja() != null);
		boolean sviVagoniNapustiliStanicu = kompozicija.getVagoni().stream()
				.allMatch(v -> v.getSmjerKretanja() != null);
		if (kompozicija.getAktivniSektor() == null)
			kompozicija.setAktivniSektor(odrediAktivniSektor(kompozicija));
		if (sveLokomotiveNapustileStanicu && sviVagoniNapustiliStanicu)
			kompozicijaNapustaStanicu(kompozicija);
	}

	private void kompozicijaNapustaStanicu(Kompozicija kompozicija) {
		ZeljeznickaStanica zs = Teritorija.pronadjiStanicu(kompozicija.getPolaziste());
		if (zs != null) {
			zs.getTrenutneKompozicjeNaZeljeznickojStanici().remove(kompozicija);
			zs.prikazPodataka();
		}
	}

	private void ulazakUMedjustanicu(Kompozicija kompozicija) {
		osobodiKompozicijuIzSektora(kompozicija);
		kompozicija.setAktivniSektor(odrediAktivniSektor(kompozicija));
		kompozicija.setTrenutnaBrzina(0);
		kompozicijaUlaziNaStanicu(kompozicija);
		kompozicija.setStanjeKompozicije(StanjeKompozicije.CEKANJE_U_MEDJUSTANICI);
	}

	private void osobodiKompozicijuIzSektora(Kompozicija kompozicija) {
		Dionica sektor = kompozicija.getAktivniSektor();
		sektor.getTrenutneKompozicijeNaPruzi().remove(kompozicija);
		if (sektor.getTrenutneKompozicijeNaPruzi().isEmpty()) {
			sektor.setBrzina(0);
			sektor.setImeTrenutnePolazneStanice(null);
		}
		for (Kompozicija k : getListaKompozicija()) {
			synchronized (k) {
				k.notify();
			}
		}
	}

	private SmjerKretanja suprotanSmjer(SmjerKretanja smjerKretanja) {
		SmjerKretanja suprotanSmjer = null;
		switch (smjerKretanja) {
		case ISTOK:
			suprotanSmjer = SmjerKretanja.ZAPAD;
			break;
		case ZAPAD:
			suprotanSmjer = SmjerKretanja.ISTOK;
			break;
		case SJEVER:
			suprotanSmjer = SmjerKretanja.JUG;
			break;
		case JUG:
			suprotanSmjer = SmjerKretanja.SJEVER;
			break;
		}
		return suprotanSmjer;
	}

	private void ispisiPorukuKadaJeUStanici(ZeljeznickaStanica zs, Kompozicija kompozicija) {
		kompozicija.getIstorijaKretanja().getPoruka()
				.append("Stanica " + zs.getImeZeleznice() + "  ( vrijeme: "
						+ LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "  brzina: "
						+ kompozicija.getTrenutnaBrzina() + " km/h )" + kompozicija.prikazIstorijeKretanja());
	}

	private void ispisiPorukuKadaJeUPokretu(Kompozicija kompozicija) {
		kompozicija.getIstorijaKretanja().getPoruka()
				.append("U pokretu " + "  ( vrijeme: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "  brzina: "
						+ kompozicija.getTrenutnaBrzina() + " km/h )" + kompozicija.prikazIstorijeKretanja());
	}

	private void proslijediSmjerKrozKompoziciju(Kompozicija kompozicija, SmjerKretanja smjerKretanja, int pozicijaX,
			int pozicijaY) {
		int tempX, tempY;
		SmjerKretanja privrememniSmjer;

		for (Lokomotiva lokomotiva : kompozicija.getLokomotive()) {
			privrememniSmjer = lokomotiva.getSmjerKretanja();
			tempX = lokomotiva.getPozicijaX();
			tempY = lokomotiva.getPozicijaY();
			lokomotiva.setSmjerKretanja(smjerKretanja);
			lokomotiva.setPozicijaX(pozicijaX);
			lokomotiva.setPozicijaY(pozicijaY);
			smjerKretanja = privrememniSmjer;
			pozicijaX = tempX;
			pozicijaY = tempY;
		}
		for (Vagon vagon : kompozicija.getVagoni()) {
			privrememniSmjer = vagon.getSmjerKretanja();
			tempX = vagon.getPozicijaX();
			tempY = vagon.getPozicijaY();
			vagon.setSmjerKretanja(smjerKretanja);
			vagon.setPozicijaX(pozicijaX);
			vagon.setPozicijaY(pozicijaY);
			smjerKretanja = privrememniSmjer;
			pozicijaX = tempX;
			pozicijaY = tempY;
		}
	}

	private void pomjeriSlike(Kompozicija kompozicija) {
		kompozicija.getLokomotive().forEach(l -> pomjeriSliku(l, l.getPozicijaX(), l.getPozicijaY()));
		kompozicija.getVagoni().forEach(v -> pomjeriSliku(v, v.getPozicijaX(), v.getPozicijaY()));
		try {
			Thread.sleep(odrediVrijemeProlaska(kompozicija));
		} catch (InterruptedException ex) {
			FileLogger.log(Level.SEVERE, "Trenutna nit je prekinuta.", ex);
		}
	}

	private void ukloniElementeSaMape(Kompozicija kompozicija) {
		kompozicija.getLokomotive().forEach(l -> Teritorija.ukloniElementSaMape(l.getPozicijaX(), l.getPozicijaY(), l));
		kompozicija.getVagoni().forEach(v -> Teritorija.ukloniElementSaMape(v.getPozicijaX(), v.getPozicijaY(), v));
	}

	private void predjiPruzniPrelaz(Kompozicija kompozicija) {
		Dionica sektor = kompozicija.getAktivniSektor();
		int brojPrelazaka = kompozicija.getBrojPotrebnihPrelazaka();
		kompozicija.setBrojPotrebnihPrelazaka(brojPrelazaka - 1);
		if (kompozicija.getBrojPotrebnihPrelazaka() == 0) {
			prolaziPruzniPrelaz = false;
			sektor.getTrenutneKompozicijeNaPruzi().put(kompozicija, false);
			for (Vozilo vozilo : KontrolerDrumskogSaobracaja.getListaVozila()) {
				synchronized (vozilo) {
					vozilo.notify();
				}
			}
		}
	}

	private int odrediVrijemeProlaska(Kompozicija kompozicija) {
		int vrijemeProlaska = 0;
		if (kompozicija.getTrenutnaBrzina() > 250)
			vrijemeProlaska = 500;
		else if (kompozicija.getTrenutnaBrzina() < 50)
			vrijemeProlaska = 2500;
		else
			vrijemeProlaska = 125_000 / kompozicija.getTrenutnaBrzina();
		return vrijemeProlaska;
	}

	private void postaviPoljaPodNapon(Kompozicija kompozicija) {
		Lokomotiva vodecaLokomotiva = kompozicija.getLokomotive().get(0);
		if (Lokomotiva.TipPogona.ELEKTRICNI.equals(vodecaLokomotiva.getTipPogona())) {

			Dionica dionica = kompozicija.getAktivniSektor();
			ImeSektora imeSektora = dionica.getImeSektora();
			Pruga pruga = Teritorija.vratiPruguNavedenogSektora(imeSektora);
			PruzniPrelaz pruzniPrelaz = Teritorija.vratiPruzniPrelazNavedenogSektora(imeSektora);

			Set<Kompozicija> listaElektricnihKompozicijaNaDatojDionici = dionica.getTrenutneKompozicijeNaPruzi()
					.keySet();

			listaElektricnihKompozicijaNaDatojDionici = listaElektricnihKompozicijaNaDatojDionici.stream()
					.filter(k -> Lokomotiva.TipPogona.ELEKTRICNI.equals(k.getLokomotive().get(0).getTipPogona()))
					.collect(Collectors.toSet());

			pruga.getPoljaNaPruzi().forEach(saobracajnica -> {
				saobracajnica.setPodNaponom(false);
			});
			if (pruzniPrelaz != null)
				pruzniPrelaz.getPoljaNaPruznomPrelazu().forEach(saobracajnica -> {
					saobracajnica.setPodNaponom(false);
				});

			for (Kompozicija elektricnaKompozicija : listaElektricnihKompozicijaNaDatojDionici) {

				postaviNaponNaPolja(elektricnaKompozicija, pruzniPrelaz, pruga);
			}

			for (Vozilo vozilo : KontrolerDrumskogSaobracaja.getListaVozila()) {
				synchronized (vozilo) {
					vozilo.notify();
				}
			}
		}
	}

	private void postaviNaponNaPolja(Kompozicija elektricnaKompozicija, PruzniPrelaz pruzniPrelaz, Pruga pruga) {
		elektricnaKompozicija.getLokomotive().forEach(lokomotiva -> {
			Saobracajnica.postaviNapon(pruga.getPoljaNaPruzi(), lokomotiva.getPozicijaX(), lokomotiva.getPozicijaY(),
					true);
			if (pruzniPrelaz != null)
				Saobracajnica.postaviNapon(pruzniPrelaz.getPoljaNaPruznomPrelazu(), lokomotiva.getPozicijaX(),
						lokomotiva.getPozicijaY(), true);
		});
		elektricnaKompozicija.getVagoni().forEach(vagon -> {
			Saobracajnica.postaviNapon(pruga.getPoljaNaPruzi(), vagon.getPozicijaX(), vagon.getPozicijaY(), true);
			if (pruzniPrelaz != null)
				Saobracajnica.postaviNapon(pruzniPrelaz.getPoljaNaPruznomPrelazu(), vagon.getPozicijaX(),
						vagon.getPozicijaY(), true);
		});
		PrevoznoSredstvo prviElement = elektricnaKompozicija.getLokomotive().get(0);
		PrevoznoSredstvo poslednjiElement = null;
		if (!elektricnaKompozicija.getVagoni().isEmpty()) {
			poslednjiElement = elektricnaKompozicija.getVagoni().get(elektricnaKompozicija.getVagoni().size() - 1);
		} else
			poslednjiElement = elektricnaKompozicija.getLokomotive()
					.get(elektricnaKompozicija.getLokomotive().size() - 1);

		postaviNaponNaOkruzujucaPolja(pruga.getPoljaNaPruzi(), prviElement.getPozicijaX(), prviElement.getPozicijaY());
		if (pruzniPrelaz != null)
			postaviNaponNaOkruzujucaPolja(pruzniPrelaz.getPoljaNaPruznomPrelazu(), prviElement.getPozicijaX(),
					prviElement.getPozicijaY());

		if (prviElement != poslednjiElement) {
			postaviNaponNaOkruzujucaPolja(pruga.getPoljaNaPruzi(), poslednjiElement.getPozicijaX(),
					poslednjiElement.getPozicijaY());
			if (pruzniPrelaz != null)
				postaviNaponNaOkruzujucaPolja(pruzniPrelaz.getPoljaNaPruznomPrelazu(), poslednjiElement.getPozicijaX(),
						poslednjiElement.getPozicijaY());
		}
	}

	private void postaviNaponNaOkruzujucaPolja(ArrayList<Saobracajnica> listaSaobracajnica, int x, int y) {
		Saobracajnica.postaviNapon(listaSaobracajnica, x + 1, y, true);
		Saobracajnica.postaviNapon(listaSaobracajnica, x - 1, y, true);
		Saobracajnica.postaviNapon(listaSaobracajnica, x, y + 1, true);
		Saobracajnica.postaviNapon(listaSaobracajnica, x, y - 1, true);
	}

	public void prikaziPodatkeOZeljeznickojStanici(Kompozicija kompozicija) {
		ZeljeznickaStanica zs = Teritorija.pronadjiStanicu(kompozicija.getPolaziste());
		if (zs != null) {
			zs.prikazPodataka();
		}
	}

	public static List<Kompozicija> getListaKompozicija() {
		return listaKompozicija;
	}

	public static void setListaKompozicija(List<Kompozicija> listaKompozicija) {
		KontrolerZeljeznickogSaobracaja.listaKompozicija = listaKompozicija;
	}

	public boolean isProlaziPruzniPrelaz() {
		return prolaziPruzniPrelaz;
	}

	public void setProlaziPruzniPrelaz(boolean prolaziPruzniPrelaz) {
		this.prolaziPruzniPrelaz = prolaziPruzniPrelaz;
	}

}
