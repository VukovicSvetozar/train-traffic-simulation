package org.unibl.etf.zs.model.prevoz.kompozicije;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.unibl.etf.zs.istorija_kretanja.IstorijaKretanja;
import org.unibl.etf.zs.model.prevoz.Dionica;
import org.unibl.etf.zs.simulation.KontrolerZeljeznickogSaobracaja;
import org.unibl.etf.zs.utility.PathFile;
import org.unibl.etf.zs.utility.Serialization;

public final class Kompozicija extends Thread implements PathFile {

	public enum StanjeKompozicije {
		CEKANJE_NA_POLAZAK, U_POKRETU, CEKANJE_U_MEDJUSTANICI, ZAVRSENO
	}

	private String ime;
	private StanjeKompozicije stanjeKompozicije;

	private ArrayList<Lokomotiva> lokomotive;
	private ArrayList<Vagon> vagoni;

	private int maksimalnaBrzina;
	private int trenutnaBrzina;

	private String polaziste;
	private String odrediste;

	private IstorijaKretanja istorijaKretanja;
	private Dionica aktivniSektor;
	private int brojPotrebnihPrelazaka;

	public Kompozicija() {
	}

	public Kompozicija(ArrayList<Lokomotiva> lokomotive, ArrayList<Vagon> vagoni, String osnovniPodaci) {
		odrediIme();
		this.stanjeKompozicije = StanjeKompozicije.CEKANJE_NA_POLAZAK;
		this.lokomotive = lokomotive.stream().collect(Collectors.toCollection(ArrayList::new));
		this.vagoni = vagoni.stream().collect(Collectors.toCollection(ArrayList::new));
		this.maksimalnaBrzina = Integer.parseInt(osnovniPodaci.split("-")[1]);
		this.trenutnaBrzina = 0;
		this.lokomotive.stream().forEach(
				lokomotiva -> lokomotiva.setTipPogona(Lokomotiva.TipPogona.valueOf(osnovniPodaci.split("-")[2])));
		this.polaziste = osnovniPodaci.split("-")[3];
		this.odrediste = osnovniPodaci.split("-")[4];
		this.istorijaKretanja = new IstorijaKretanja(ime);
	}

	private void odrediIme() {
		String ime = "";
		Set<String> skupImena = new HashSet<>();
		Random rand = new Random();
		for (Kompozicija kompozicija : KontrolerZeljeznickogSaobracaja.getListaKompozicija())
			skupImena.add(kompozicija.getIme());
		do {
			int proizvoljanBroj = rand.nextInt(900) + 100;
			ime = proizvoljanBroj + Long.toHexString(Double.doubleToLongBits(Math.random())).substring(2, 6);
		} while (!skupImena.add(ime));
		this.setIme(ime);
	}

	@Override
	public void run() {

		KontrolerZeljeznickogSaobracaja kontroler = new KontrolerZeljeznickogSaobracaja();

		kontroler.kompozicijaUlaziNaStanicu(this);
		boolean aktivno = true;

		while (aktivno) {
			switch (stanjeKompozicije) {
			case CEKANJE_NA_POLAZAK:
				kontroler.pokreniKompoziciju(this);
				break;
			case U_POKRETU:
				kontroler.pomjeriKompoziciju(this);
				break;
			case CEKANJE_U_MEDJUSTANICI:
				kontroler.kompozicijaUMedjustanici(this);
				break;
			case ZAVRSENO:
				Serialization.sacuvajIstorijuKretanja(istorijaKretanja, PUTANJA_DO_DIREKTORIJUMA_ZA_SERIJALIZACJU);
				aktivno = false;
				break;
			}
		}
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public StanjeKompozicije getStanjeKompozicije() {
		return stanjeKompozicije;
	}

	public void setStanjeKompozicije(StanjeKompozicije stanjeKompozicije) {
		this.stanjeKompozicije = stanjeKompozicije;
	}

	public ArrayList<Lokomotiva> getLokomotive() {
		return lokomotive;
	}

	public void setLokomotive(ArrayList<Lokomotiva> lokomotive) {
		this.lokomotive = lokomotive;
	}

	public ArrayList<Vagon> getVagoni() {
		return vagoni;
	}

	public void setVagoni(ArrayList<Vagon> vagoni) {
		this.vagoni = vagoni;
	}

	public int getMaksimalnaBrzina() {
		return maksimalnaBrzina;
	}

	public void setMaksimalnaBrzina(int maksimalnaBrzina) {
		this.maksimalnaBrzina = maksimalnaBrzina;
	}

	public String getPolaziste() {
		return polaziste;
	}

	public void setPolaziste(String polaziste) {
		this.polaziste = polaziste;
	}

	public String getOdrediste() {
		return odrediste;
	}

	public void setOdrediste(String odrediste) {
		this.odrediste = odrediste;
	}

	public int getTrenutnaBrzina() {
		return trenutnaBrzina;
	}

	public void setTrenutnaBrzina(int trenutnaBrzina) {
		this.trenutnaBrzina = trenutnaBrzina;
	}

	public IstorijaKretanja getIstorijaKretanja() {
		return istorijaKretanja;
	}

	public void setIstorijaKretanja(IstorijaKretanja istorijaKretanja) {
		this.istorijaKretanja = istorijaKretanja;
	}

	public Dionica getAktivniSektor() {
		return aktivniSektor;
	}

	public void setAktivniSektor(Dionica aktivniSektor) {
		this.aktivniSektor = aktivniSektor;
	}

	public int getBrojPotrebnihPrelazaka() {
		return brojPotrebnihPrelazaka;
	}

	public void setBrojPotrebnihPrelazaka(int brojPotrebnihPrelazaka) {
		this.brojPotrebnihPrelazaka = brojPotrebnihPrelazaka;
	}

	@Override
	public String toString() {
		return "Kompozicija: " + ime;
	}

	public String prikazUStanici() {
		StringBuffer sb = new StringBuffer();
		if (StanjeKompozicije.ZAVRSENO.equals(stanjeKompozicije))
			sb.append("Kompozicija: " + ime + " [" + stanjeKompozicije + "]");
		else
			sb.append(
					"Kompozicija: " + ime + " [" + stanjeKompozicije + " ] (" + polaziste + " --> " + odrediste + ")");
		return sb.toString();
	}

	public String prikazIstorijeKretanja() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n______________________________\n");
		lokomotive.forEach(l -> sb.append("\t" + l + "\n"));
		vagoni.forEach(v -> sb.append("\t" + v + "\n"));
		sb.append("\n\n");
		return sb.toString();
	}

}
