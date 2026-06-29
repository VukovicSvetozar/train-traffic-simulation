package org.unibl.etf.zs.model.prevoz.vozila;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import org.unibl.etf.zs.model.prevoz.ImeSektora;
import org.unibl.etf.zs.model.prevoz.PrevoznoSredstvo;
import org.unibl.etf.zs.model.saobracajnice.TrakaNaCesti;
import org.unibl.etf.zs.simulation.KontrolerDrumskogSaobracaja;
import org.unibl.etf.zs.utility.FileLogger;

public abstract class Vozilo extends PrevoznoSredstvo implements Runnable {

	public enum StanjeVozila {
		PASIVNO, U_POKRETU, ZAVRSENO
	}

	private String idVozila;
	private StanjeVozila stanjeVozila;
	private TrakaNaCesti trakaVozila;

	private String marka;
	private String model;
	private String godinaProizvodnje;
	private int brzina;

	private boolean prosaoPruzniPrelaz;
	private ImeSektora drumskiSektor;

	public Vozilo() {
		odrediID();
		stanjeVozila = StanjeVozila.PASIVNO;
		prosaoPruzniPrelaz = false;
	}

	private void odrediID() {
		String id = "";
		Set<String> skupId = new HashSet<>();
		Random rand = new Random();
		for (Vozilo vozilo : KontrolerDrumskogSaobracaja.getListaVozila())
			skupId.add(vozilo.getIdVozila());
		do {
			int proizvoljanBroj = rand.nextInt(900) + 100;
			id = proizvoljanBroj + Long.toHexString(Double.doubleToLongBits(Math.random()));
		} while (!skupId.add(id));
		this.setIdVozila(id);
	}

	protected void unesiPodatkeOVozilu(String imePutanje) {
		Path putanja = null;
		try {
			putanja = Paths.get(imePutanje);
			long brojLinija = Files.lines(putanja).count();
			long slucajanRed = ThreadLocalRandom.current().nextLong(0, brojLinija);
			String linija = Files.readAllLines(putanja, StandardCharsets.UTF_8).get(Math.toIntExact(slucajanRed));
			this.marka = linija.split("-")[0].trim();
			this.model = linija.split("-")[1].trim();
			this.godinaProizvodnje = linija.split("-")[2].trim();
		} catch (IOException e) {
			FileLogger.log(Level.SEVERE, "Nije pronadjena putanja.", e);
		}
	}

	@Override
	public void run() {
		boolean aktivno = true;
		KontrolerDrumskogSaobracaja kontroler = new KontrolerDrumskogSaobracaja();

		while (aktivno) {
			switch (stanjeVozila) {
			case PASIVNO:
				kontroler.pokreniVozilo(this);
				break;
			case U_POKRETU:
				kontroler.pomjeriVozilo(this);
				break;
			case ZAVRSENO:
				aktivno = false;
				break;
			}
		}
	}

	public String getIdVozila() {
		return idVozila;
	}

	public void setIdVozila(String idVozila) {
		this.idVozila = idVozila;
	}

	public StanjeVozila getStanjeVozila() {
		return stanjeVozila;
	}

	public void setStanjeVozila(StanjeVozila stanjeVozila) {
		this.stanjeVozila = stanjeVozila;
	}

	public TrakaNaCesti getTrakaVozila() {
		return trakaVozila;
	}

	public void setTrakaVozila(TrakaNaCesti trakaVozila) {
		this.trakaVozila = trakaVozila;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public boolean isProsaoPruzniPrelaz() {
		return prosaoPruzniPrelaz;
	}

	public void setProsaoPruzniPrelaz(boolean prosaoPruzniPrelaz) {
		this.prosaoPruzniPrelaz = prosaoPruzniPrelaz;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getGodinaProizvodnje() {
		return godinaProizvodnje;
	}

	public void setGodinaProizvodnje(String godinaProizvodnje) {
		this.godinaProizvodnje = godinaProizvodnje;
	}

	public int getBrzina() {
		return brzina;
	}

	public void setBrzina(int brzina) {
		this.brzina = brzina;
	}

	public ImeSektora getDrumskiSektor() {
		return drumskiSektor;
	}

	public void setDrumskiSektor(ImeSektora drumskiSektor) {
		this.drumskiSektor = drumskiSektor;
	}

	@Override
	public String toString() {
		return "Vozilo [idVozila=" + idVozila + ", stanjeVozila=" + stanjeVozila + ", trakaVozila=" + trakaVozila + "]";
	}

}
