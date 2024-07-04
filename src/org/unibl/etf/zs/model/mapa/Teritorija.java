package org.unibl.etf.zs.model.mapa;

import java.util.Arrays;
import java.util.stream.Stream;

import org.unibl.etf.zs.model.prevoz.PrevoznoSredstvo;
import org.unibl.etf.zs.model.prevoz.Dionica;
import org.unibl.etf.zs.model.prevoz.kompozicije.Kompozicija;
import org.unibl.etf.zs.model.prevoz.vozila.Vozilo;
import org.unibl.etf.zs.model.prevoz.ImeSektora;
import org.unibl.etf.zs.model.saobracajnice.Cesta;
import org.unibl.etf.zs.model.saobracajnice.Pruga;
import org.unibl.etf.zs.model.saobracajnice.PruzniPrelaz;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica.TipSaobracajnice;
import org.unibl.etf.zs.model.stanica.ZeljeznickaStanica;
import org.unibl.etf.zs.simulation.KontrolerDrumskogSaobracaja;
import org.unibl.etf.zs.simulation.KontrolerZeljeznickogSaobracaja;
import org.unibl.etf.zs.utility.PathFile;
import org.unibl.etf.zs.utility.PropertiesUtil;

public final class Teritorija implements PathFile {

	private static int DIMENZIJA_MAPE;
	private static int VELICINA_POLJA;
	private static int BROJ_CESTI;
	private static int BROJ_PRUGA;
	private static int BROJ_PRUZNIH_PRELAZA;
	private static int BROJ_ZELJEZNICKIH_STANICA;
	private static int BROJ_SEKTORA;

	private static Polje[][] mapa;
	private static Cesta[] ceste;
	private static Pruga[] pruge;
	private static PruzniPrelaz[] pruzniPrelazi;
	private static ZeljeznickaStanica[] zeljeznickeStanice;
	private static Dionica[] pruzniSektori;

	public static void kreirajTeritoriju() {
		ucitajSvojstvaMape();
		kreirajMapu();
		kreirajCeste();
		kreirajPruge();
		kreirajPruznePrelaze();
		kreirajZeleznickeStanice();
		kreirajSektore();
	}

	private static void ucitajSvojstvaMape() {
		DIMENZIJA_MAPE = PropertiesUtil.vratiSvojstvo("DIMENZIJA_MAPE", PUTANJA_DO_SVOJSTAVA_MAPE, Integer.class);
		VELICINA_POLJA = PropertiesUtil.vratiSvojstvo("VELICINA_POLJA", PUTANJA_DO_SVOJSTAVA_MAPE, Integer.class);
		BROJ_CESTI = PropertiesUtil.vratiSvojstvo("BROJ_CESTI", PUTANJA_DO_SVOJSTAVA_MAPE, Integer.class);
		BROJ_PRUGA = PropertiesUtil.vratiSvojstvo("BROJ_PRUGA", PUTANJA_DO_SVOJSTAVA_MAPE, Integer.class);
		BROJ_PRUZNIH_PRELAZA = PropertiesUtil.vratiSvojstvo("BROJ_PRUZNIH_PRELAZA", PUTANJA_DO_SVOJSTAVA_MAPE,
				Integer.class);
		BROJ_ZELJEZNICKIH_STANICA = PropertiesUtil.vratiSvojstvo("BROJ_ZELJEZNICKIH_STANICA", PUTANJA_DO_SVOJSTAVA_MAPE,
				Integer.class);
		BROJ_SEKTORA = PropertiesUtil.vratiSvojstvo("BROJ_SEKTORA", PUTANJA_DO_SVOJSTAVA_MAPE, Integer.class);
	}

	private static void kreirajMapu() {
		mapa = new Polje[DIMENZIJA_MAPE][DIMENZIJA_MAPE];
		for (int i = 0; i < DIMENZIJA_MAPE; i++)
			for (int j = 0; j < DIMENZIJA_MAPE; j++)
				mapa[i][j] = new Polje();
	}

	private static void kreirajCeste() {
		ceste = new Cesta[BROJ_CESTI];
		for (int i = 0; i < BROJ_CESTI; i++)
			ceste[i] = new Cesta(i);
	}

	private static void kreirajPruge() {
		pruge = new Pruga[BROJ_PRUGA];
		for (int i = 0; i < BROJ_PRUGA; i++)
			pruge[i] = new Pruga(i);
	}

	private static void kreirajPruznePrelaze() {
		pruzniPrelazi = new PruzniPrelaz[BROJ_PRUZNIH_PRELAZA];
		for (int i = 0; i < BROJ_PRUZNIH_PRELAZA; i++)
			pruzniPrelazi[i] = new PruzniPrelaz(i);
	}

	private static void kreirajZeleznickeStanice() {
		zeljeznickeStanice = new ZeljeznickaStanica[BROJ_ZELJEZNICKIH_STANICA];
		for (int i = 0; i < BROJ_ZELJEZNICKIH_STANICA; i++)
			zeljeznickeStanice[i] = new ZeljeznickaStanica(i);
	}

	private static void kreirajSektore() {
		pruzniSektori = new Dionica[BROJ_SEKTORA];
		for (int i = 0; i < BROJ_SEKTORA; i++)
			pruzniSektori[i] = new Dionica(i);
	}

	public static Polje vratiPolje(int x, int y) {
		return mapa[x][y];
	}

	public static boolean uGranicamaMape(int x, int y) {
		if (0 <= x && x < DIMENZIJA_MAPE && 0 <= y && y < DIMENZIJA_MAPE)
			return true;
		else
			return false;
	}

	public static void postaviElementNaMapu(int x, int y, Element element) {
		if (uGranicamaMape(x, y))
			mapa[x][y].getElementi().add(element);
	}

	public static void ukloniElementSaMape(int x, int y, Element element) {
		boolean uklonio = false;
		if (uGranicamaMape(x, y))
			uklonio = mapa[x][y].getElementi().remove(element);
		if (uklonio) {
			for (Kompozicija kompozicija : KontrolerZeljeznickogSaobracaja.getListaKompozicija())
				synchronized (kompozicija) {
					kompozicija.notify();
				}
			for (Vozilo vozilo : KontrolerDrumskogSaobracaja.getListaVozila())
				synchronized (vozilo) {
					vozilo.notify();
				}
		}
	}

	public static ZeljeznickaStanica pronadjiStanicu(String imeStanice) {
		return Arrays.stream(zeljeznickeStanice).filter(z -> z.getImeZeleznice().equals(imeStanice)).findFirst()
				.orElse(null);
	}

	public static boolean slobodnoMjestoZaPrevoznoSredstvo(int x, int y) {
		Polje polje = vratiPolje(x, y);
		if (polje.sadrziTip(PrevoznoSredstvo.class))
			return false;
		else
			return true;
	}

	public static String vratiImeStanice(Saobracajnica saobracajnica) {
		Stream<ZeljeznickaStanica> stanice = Stream.of(zeljeznickeStanice);
		return stanice.filter(s -> s.getPoljaNaZeleznickojStanici().contains(saobracajnica))
				.map(st -> st.getImeZeleznice()).findAny().orElse(null);
	}

	public static Cesta vratiCestuNavedenogSektora(ImeSektora sektor) {
		Cesta cesta = null;
		for (Cesta c : ceste)
			if (sektor.equals(c.getSektor()))
				cesta = c;
		return cesta;
	}

	public static Pruga vratiPruguNavedenogSektora(ImeSektora sektor) {
		Pruga pruga = null;
		for (Pruga p : pruge)
			if (sektor.equals(p.getSektor()))
				pruga = p;
		return pruga;
	}

	public static PruzniPrelaz vratiPruzniPrelazNavedenogSektora(ImeSektora sektor) {
		PruzniPrelaz pruzniPrelaz = null;
		for (PruzniPrelaz p : pruzniPrelazi)
			if (sektor.equals(p.getSektor()))
				pruzniPrelaz = p;
		return pruzniPrelaz;
	}

	public static boolean dozvoljenaSaobracajnicaZaVozilo(int x, int y) {
		if (uGranicamaMape(x, y)) {
			Polje polje = vratiPolje(x, y);
			if (polje.sadrziTip(Saobracajnica.class) && (TipSaobracajnice.CESTA
					.equals(((Saobracajnica) polje.getElementi().get(0)).getTipSaobracajnice())
					|| TipSaobracajnice.PRUZNI_PRELAZ
							.equals(((Saobracajnica) polje.getElementi().get(0)).getTipSaobracajnice())))
				return true;
		}
		return false;
	}

	public static int getDIMENZIJA_MAPE() {
		return DIMENZIJA_MAPE;
	}

	public static void setDIMENZIJA_MAPE(int dIMENZIJA_MAPE) {
		DIMENZIJA_MAPE = dIMENZIJA_MAPE;
	}

	public static int getVELICINA_POLJA() {
		return VELICINA_POLJA;
	}

	public static void setVELICINA_POLJA(int vELICINA_POLJA) {
		VELICINA_POLJA = vELICINA_POLJA;
	}

	public static int getBROJ_CESTI() {
		return BROJ_CESTI;
	}

	public static void setBROJ_CESTI(int bROJ_CESTI) {
		BROJ_CESTI = bROJ_CESTI;
	}

	public static int getBROJ_PRUGA() {
		return BROJ_PRUGA;
	}

	public static void setBROJ_PRUGA(int bROJ_PRUGA) {
		BROJ_PRUGA = bROJ_PRUGA;
	}

	public static int getBROJ_PRUZNIH_PRELAZA() {
		return BROJ_PRUZNIH_PRELAZA;
	}

	public static void setBROJ_PRUZNIH_PRELAZA(int bROJ_PRUZNIH_PRELAZA) {
		BROJ_PRUZNIH_PRELAZA = bROJ_PRUZNIH_PRELAZA;
	}

	public static int getBROJ_ZELJEZNICKIH_STANICA() {
		return BROJ_ZELJEZNICKIH_STANICA;
	}

	public static void setBROJ_ZELJEZNICKIH_STANICA(int bROJ_ZELJEZNICKIH_STANICA) {
		BROJ_ZELJEZNICKIH_STANICA = bROJ_ZELJEZNICKIH_STANICA;
	}

	public static int getBROJ_SEKTORA() {
		return BROJ_SEKTORA;
	}

	public static void setBROJ_SEKTORA(int bROJ_SEKTORA) {
		BROJ_SEKTORA = bROJ_SEKTORA;
	}

	public static Polje[][] getMapa() {
		return mapa;
	}

	public static void setMapa(Polje[][] mapa) {
		Teritorija.mapa = mapa;
	}

	public static Cesta[] getCeste() {
		return ceste;
	}

	public static void setCeste(Cesta[] ceste) {
		Teritorija.ceste = ceste;
	}

	public static Pruga[] getPruge() {
		return pruge;
	}

	public static void setPruge(Pruga[] pruge) {
		Teritorija.pruge = pruge;
	}

	public static PruzniPrelaz[] getPruzniPrelazi() {
		return pruzniPrelazi;
	}

	public static void setPruzniPrelazi(PruzniPrelaz[] pruzniPrelazi) {
		Teritorija.pruzniPrelazi = pruzniPrelazi;
	}

	public static ZeljeznickaStanica[] getZeljeznickeStanice() {
		return zeljeznickeStanice;
	}

	public static void setZeljeznickeStanice(ZeljeznickaStanica[] zeljeznickeStanice) {
		Teritorija.zeljeznickeStanice = zeljeznickeStanice;
	}

	public static Dionica[] getPruzniSektori() {
		return pruzniSektori;
	}

	public static void setPruzniSektori(Dionica[] pruzniSektori) {
		Teritorija.pruzniSektori = pruzniSektori;
	}

}
