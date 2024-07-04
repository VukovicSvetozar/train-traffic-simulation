package org.unibl.etf.zs.simulation;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.unibl.etf.zs.model.mapa.Polje;
import org.unibl.etf.zs.model.mapa.Teritorija;
import org.unibl.etf.zs.model.prevoz.SmjerKretanja;
import org.unibl.etf.zs.model.prevoz.vozila.Automobil;
import org.unibl.etf.zs.model.prevoz.vozila.Kamion;
import org.unibl.etf.zs.model.prevoz.vozila.Vozilo;
import org.unibl.etf.zs.model.prevoz.vozila.Vozilo.StanjeVozila;
import org.unibl.etf.zs.model.prevoz.ImeSektora;
import org.unibl.etf.zs.model.prevoz.Dionica;
import org.unibl.etf.zs.model.saobracajnice.Cesta;
import org.unibl.etf.zs.model.saobracajnice.PruzniPrelaz;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica;
import org.unibl.etf.zs.model.saobracajnice.TrakaNaCesti;
import org.unibl.etf.zs.utility.FileLogger;
import org.unibl.etf.zs.utility.FileWatcher;
import org.unibl.etf.zs.utility.PathFile;
import org.unibl.etf.zs.utility.PropertiesUtil;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class KontrolerDrumskogSaobracaja implements PathFile {

	private static int POCETAK_KREIRANJA_VOZILA;
	private static int INTERVAL_KREIRANJA_VOZILA;

	private static List<Vozilo> listaVozila;
	private static List<ImeSektora> listaSektoraZaVozila;

	private static ScheduledThreadPoolExecutor izvrsiteljKreiranjaVozila;

	public static void pokreniSimulacijuZaVozila() {
		listaVozila = Collections.synchronizedList(new ArrayList<Vozilo>());
		listaSektoraZaVozila = new ArrayList<ImeSektora>();
		ucitajSvojstva();
		popuniSektoreZaVozila();
		posmatrajKonfiguracioniDirektorijum();
		simulirajKreiranjeVozila();
	}

	private static void ucitajSvojstva() {
		POCETAK_KREIRANJA_VOZILA = PropertiesUtil.vratiSvojstvo("POCETAK_KREIRANJA_VOZILA",
				PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class);
		INTERVAL_KREIRANJA_VOZILA = PropertiesUtil.vratiSvojstvo("INTERVAL_KREIRANJA_VOZILA",
				PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class);
	}

	private static void popuniSektoreZaVozila() {
		for (Cesta cesta : Teritorija.getCeste())
			for (int i = 0; i < cesta.getBrojVozilaNaCesti(); i++)
				listaSektoraZaVozila.add(cesta.getSektor());
		Collections.shuffle(listaSektoraZaVozila);
	}

	private static void posmatrajKonfiguracioniDirektorijum() {
		FileWatcher direktorijumZaPosmatranje;
		try {
			direktorijumZaPosmatranje = new FileWatcher(Paths.get(PUTANJA_DO_DIREKTORIJUMA_ZA_SVOJSTVA), false);
			Thread radnaNit = new Thread(direktorijumZaPosmatranje::procesiranjeDogadjaja);
			radnaNit.setDaemon(true);
			radnaNit.start();
		} catch (Exception ex) {
			FileLogger.log(Level.SEVERE, "Navedena putanja nije odgovarajuca.", ex);
		}
	}

	private static void simulirajKreiranjeVozila() {
		Thread radnaNit = new Thread() {
			@Override
			public void run() {
				if (!listaSektoraZaVozila.isEmpty()) {
					kreirajVozilo();
				}
			}
		};
		radnaNit.setDaemon(true);
		izvrsiteljKreiranjaVozila = new ScheduledThreadPoolExecutor(10);
		izvrsiteljKreiranjaVozila.scheduleAtFixedRate(radnaNit, POCETAK_KREIRANJA_VOZILA, INTERVAL_KREIRANJA_VOZILA,
				TimeUnit.MILLISECONDS);

	}

	private static void kreirajVozilo() {
		Random rand = new Random();
		ImeSektora sektor = listaSektoraZaVozila.remove(0);
		Cesta cesta = Teritorija.vratiCestuNavedenogSektora(sektor);
		int slucajanIzborTrake = rand.nextInt(2);
		TrakaNaCesti traka = cesta.getTraka()[slucajanIzborTrake];
		int slucajanIzborTipaVozila = rand.nextInt(3);
		Vozilo vozilo = null;
		if (slucajanIzborTipaVozila > 0)
			vozilo = new Automobil();
		else
			vozilo = new Kamion();
		vozilo.setTrakaVozila(traka);
		vozilo.setDrumskiSektor(sektor);
		vozilo.setBrzina(rand.nextInt(cesta.getMaksimalnaBrzina() - 50) + 50);
		listaVozila.add(vozilo);
		new Thread(vozilo).start();
	}

	public void pokreniVozilo(Vozilo vozilo) {
		TrakaNaCesti traka = vozilo.getTrakaVozila();
		while (!Teritorija.slobodnoMjestoZaPrevoznoSredstvo(traka.getPocetnaPozicijaX(), traka.getPocetnaPozicijaY())) {
			synchronized (vozilo) {
				try {
					vozilo.wait();
				} catch (InterruptedException ex) {
					FileLogger.log(Level.SEVERE, "Trenutna nit je prekinuta.", ex);
				}
			}
		}
		vozilo.setStanjeVozila(StanjeVozila.U_POKRETU);
		vozilo.postaviPozicijuElementu(traka.getPocetnaPozicijaX(), traka.getPocetnaPozicijaY());
		vozilo.setSmjerKretanja(traka.getPocetniSmjer());
		int velicinaSlike = Teritorija.getVELICINA_POLJA();
		vozilo.getPogledNaSliku().setTranslateX(velicinaSlike * vozilo.getPozicijaY());
		vozilo.getPogledNaSliku().setTranslateY(velicinaSlike * vozilo.getPozicijaX());
		Teritorija.postaviElementNaMapu(vozilo.getPozicijaX(), vozilo.getPozicijaY(), vozilo);
	}

	public void pomjeriVozilo(Vozilo vozilo) {

		int velicinaSlike = Teritorija.getVELICINA_POLJA();

		int novoX = vozilo.getPozicijaX();
		int novoY = vozilo.getPozicijaY();
		int staroX = vozilo.getPozicijaX();
		int staroY = vozilo.getPozicijaY();

		switch (vozilo.getSmjerKretanja()) {

		case ISTOK:
			if (Teritorija.dozvoljenaSaobracajnicaZaVozilo(staroX + 1, staroY)) {
				novoX++;
				vozilo.setSmjerKretanja(SmjerKretanja.JUG);
			} else {
				novoY++;
			}
			Platform.runLater(() -> {
				vozilo.getPogledNaSliku().setViewport(new Rectangle2D(0, vozilo.getKoordinataSlikeY(),
						vozilo.getSirinaSlike(), vozilo.getVisinaSlike()));
			});
			break;
		case ZAPAD:
			if (Teritorija.dozvoljenaSaobracajnicaZaVozilo(staroX, staroY - 1)) {
				novoY--;
			} else if (!Teritorija.uGranicamaMape(staroX, staroY - 1)) {
				zavrsiKretanje(vozilo, staroX, staroY);
			} else {
				novoX++;
				vozilo.setSmjerKretanja(SmjerKretanja.JUG);
			}
			Platform.runLater(() -> {
				vozilo.getPogledNaSliku().setViewport(new Rectangle2D(velicinaSlike, vozilo.getKoordinataSlikeY(),
						vozilo.getSirinaSlike(), vozilo.getVisinaSlike()));
			});
			break;
		case SJEVER:
			if (Teritorija.dozvoljenaSaobracajnicaZaVozilo(staroX, staroY + 1)) {
				novoY++;
				vozilo.setSmjerKretanja(SmjerKretanja.ISTOK);
			} else if (Teritorija.dozvoljenaSaobracajnicaZaVozilo(staroX - 1, staroY)) {
				novoX--;
			} else if (!Teritorija.uGranicamaMape(staroX - 1, staroY)) {
				zavrsiKretanje(vozilo, staroX, staroY);
			} else {
				novoY--;
				vozilo.setSmjerKretanja(SmjerKretanja.ZAPAD);
			}
			Platform.runLater(() -> {
				vozilo.getPogledNaSliku().setViewport(new Rectangle2D(velicinaSlike * 2, vozilo.getKoordinataSlikeY(),
						vozilo.getSirinaSlike(), vozilo.getVisinaSlike()));
			});
			break;
		case JUG:
			novoX++;
			Platform.runLater(() -> {
				vozilo.getPogledNaSliku().setViewport(new Rectangle2D(velicinaSlike * 3, vozilo.getKoordinataSlikeY(),
						vozilo.getSirinaSlike(), vozilo.getVisinaSlike()));
			});
			break;
		}

		if (!StanjeVozila.ZAVRSENO.equals(vozilo.getStanjeVozila())) {
			if (Teritorija.uGranicamaMape(novoX, novoY)) {
				while (!Teritorija.slobodnoMjestoZaPrevoznoSredstvo(novoX, novoY)
						|| blokiranPruzniPrelaz(vozilo, novoX, novoY) || podNaponom(vozilo, novoX, novoY)) {
					synchronized (vozilo) {
						try {
							vozilo.wait();
						} catch (InterruptedException ex) {
							FileLogger.log(Level.SEVERE, "Trenutna nit je prekinuta.", ex);
						}
					}
				}
				vozilo.postaviPozicijuElementu(novoX, novoY);
				Teritorija.postaviElementNaMapu(novoX, novoY, vozilo);
				vozilo.getPogledNaSliku().setVisible(true);
				pomjeranjeSlike(vozilo, staroX, staroY);
				Teritorija.ukloniElementSaMape(staroX, staroY, vozilo);
			} else {
				zavrsiKretanje(vozilo, staroX, staroY);
			}

		}
	}

	private boolean blokiranPruzniPrelaz(Vozilo vozilo, int x, int y) {
		boolean blokiran = true;
		if (vozilo.isProsaoPruzniPrelaz()) {
			blokiran = false;
		} else if (poljeNijePruzniPrelaz(x, y)) {
			blokiran = false;
		} else if (slobodanSektor(vozilo)) {
			vozilo.setProsaoPruzniPrelaz(true);
			blokiran = false;
		} else {
			blokiran = true;
		}
		return blokiran;
	}

	private boolean podNaponom(Vozilo vozilo, int x, int y) {
		ImeSektora sektor = vozilo.getDrumskiSektor();
		PruzniPrelaz pruzniPrelaz = Teritorija.vratiPruzniPrelazNavedenogSektora(sektor);
		Saobracajnica saobracajnica = Saobracajnica.vratiSaobracajnicu(pruzniPrelaz.getPoljaNaPruznomPrelazu(), x, y);
		if (saobracajnica != null)
			return saobracajnica.isPodNaponom();
		else
			return false;
	}

	private void pomjeranjeSlike(Vozilo vozilo, int x, int y) {
		int velicinaSlike = Teritorija.getVELICINA_POLJA();
		int vrijemeProlaska = odrediVrijemeProlaska(vozilo);
		try {

			Path putanja = new Path();
			putanja.getElements()
					.add(new MoveTo(velicinaSlike * y + velicinaSlike / 2, velicinaSlike * x + velicinaSlike / 2));
			putanja.getElements().add(new LineTo(velicinaSlike * vozilo.getPozicijaY() + velicinaSlike / 2,
					velicinaSlike * vozilo.getPozicijaX() + velicinaSlike / 2));

			PathTransition pathTransitionB = new PathTransition();
			pathTransitionB.setDuration(Duration.millis(vrijemeProlaska));
			pathTransitionB.setNode(vozilo.getPogledNaSliku());
			pathTransitionB.setPath(putanja);
			Platform.runLater(() -> {
				pathTransitionB.play();
			});
			Thread.sleep(vrijemeProlaska);
			pathTransitionB.stop();

		} catch (InterruptedException e) {
			FileLogger.log(Level.SEVERE, "Uspavljivanje niti.", e);
		}
	}

	private boolean poljeNijePruzniPrelaz(int pozicijaX, int pozicijaY) {
		boolean nijePruzniPrelaz = true;
		Polje polje = Teritorija.vratiPolje(pozicijaX, pozicijaY);
		Saobracajnica element = polje.vratiSaobracajnicu();
		if (element != null && Saobracajnica.TipSaobracajnice.PRUZNI_PRELAZ.equals(element.getTipSaobracajnice()))
			nijePruzniPrelaz = false;
		return nijePruzniPrelaz;
	}

	private boolean slobodanSektor(Vozilo vozilo) {
		boolean jesteSlobodanSektor = false;
		for (Dionica sektor : Teritorija.getPruzniSektori()) {
			if (sektor.getImeSektora().equals(vozilo.getDrumskiSektor())) {
				if (sektor.getTrenutneKompozicijeNaPruzi().containsValue(true))
					jesteSlobodanSektor = false;
				else
					jesteSlobodanSektor = true;
				break;
			}
		}
		return jesteSlobodanSektor;
	}

	private int odrediVrijemeProlaska(Vozilo vozilo) {
		int vrijemeProlaska = 0;
		if (vozilo.getBrzina() > 250)
			vrijemeProlaska = 500;
		else if (vozilo.getBrzina() < 50)
			vrijemeProlaska = 2500;
		else
			vrijemeProlaska = 125_000 / vozilo.getBrzina();
		return vrijemeProlaska;
	}

	public void zavrsiKretanje(Vozilo vozilo, int x, int y) {
		vozilo.setStanjeVozila(StanjeVozila.ZAVRSENO);
		vozilo.postaviPozicijuElementu(-1, -1);
		vozilo.getPogledNaSliku().setVisible(false);
		Teritorija.ukloniElementSaMape(x, y, vozilo);
	}

	public static int getPOCETAK_KREIRANJA_VOZILA() {
		return POCETAK_KREIRANJA_VOZILA;
	}

	public static void setPOCETAK_KREIRANJA_VOZILA(int pOCETAK_KREIRANJA_VOZILA) {
		POCETAK_KREIRANJA_VOZILA = pOCETAK_KREIRANJA_VOZILA;
	}

	public static int getINTERVAL_KREIRANJA_VOZILA() {
		return INTERVAL_KREIRANJA_VOZILA;
	}

	public static void setINTERVAL_KREIRANJA_VOZILA(int iNTERVAL_KREIRANJA_VOZILA) {
		INTERVAL_KREIRANJA_VOZILA = iNTERVAL_KREIRANJA_VOZILA;
	}

	public static List<Vozilo> getListaVozila() {
		return listaVozila;
	}

	public static void setListaVozila(List<Vozilo> listaVozila) {
		KontrolerDrumskogSaobracaja.listaVozila = listaVozila;
	}

	public static List<ImeSektora> getListaSektoraZaVozila() {
		return listaSektoraZaVozila;
	}

	public static void setListaSektoraZaVozila(List<ImeSektora> listaSektoraZaVozila) {
		KontrolerDrumskogSaobracaja.listaSektoraZaVozila = listaSektoraZaVozila;
	}

	public static ScheduledThreadPoolExecutor getIzvrsiteljKreiranjaVozila() {
		return izvrsiteljKreiranjaVozila;
	}

	public static void setIzvrsiteljKreiranjaVozila(ScheduledThreadPoolExecutor izvrsiteljKreiranjaVozila) {
		KontrolerDrumskogSaobracaja.izvrsiteljKreiranjaVozila = izvrsiteljKreiranjaVozila;
	}

}
