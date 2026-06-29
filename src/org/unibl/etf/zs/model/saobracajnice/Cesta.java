package org.unibl.etf.zs.model.saobracajnice;

import java.util.ArrayList;

import org.unibl.etf.zs.model.mapa.Teritorija;
import org.unibl.etf.zs.model.prevoz.ImeSektora;
import org.unibl.etf.zs.model.prevoz.SmjerKretanja;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica.TipSaobracajnice;
import org.unibl.etf.zs.utility.PathFile;
import org.unibl.etf.zs.utility.PropertiesUtil;

public final class Cesta implements PathFile {

	private int idCesta;
	private int maksimalnaBrzina;
	private int brojVozilaNaCesti;
	private TrakaNaCesti[] traka;
	private ArrayList<Saobracajnica> poljaNaCesti;
	private ImeSektora sektor;

	public Cesta() {
	}

	public Cesta(int idCesta) {
		this.idCesta = idCesta;
		traka = new TrakaNaCesti[2];
		kreirajPoljaNaCesti(idCesta);
	}

	private void kreirajPoljaNaCesti(int redniBrojCeste) {
		poljaNaCesti = new ArrayList<>();
		Saobracajnica element = null;
		switch (redniBrojCeste) {
		case 0:
			for (int i = 0; i < 9; i++) {
				if (i != 2) {
					element = new Saobracajnica(20, i, TipSaobracajnice.CESTA);
					Teritorija.postaviElementNaMapu(20, i, element);
					poljaNaCesti.add(element);
				}
			}
			for (int i = 0; i < 9; i++) {
				if (i != 2) {
					element = new Saobracajnica(21, i, TipSaobracajnice.CESTA);
					Teritorija.postaviElementNaMapu(21, i, element);
					poljaNaCesti.add(element);
				}
			}
			for (int i = 22; i < 30; i++) {
				element = new Saobracajnica(i, 7, TipSaobracajnice.CESTA);
				Teritorija.postaviElementNaMapu(i, 7, element);
				poljaNaCesti.add(element);
			}
			for (int i = 22; i < 30; i++) {
				element = new Saobracajnica(i, 8, TipSaobracajnice.CESTA);
				Teritorija.postaviElementNaMapu(i, 8, element);
				poljaNaCesti.add(element);
			}
			sektor = ImeSektora.ZAPADNI;
			traka[0] = new TrakaNaCesti(21, 0, SmjerKretanja.ISTOK, idCesta);
			traka[1] = new TrakaNaCesti(29, 8, SmjerKretanja.SJEVER, idCesta);
			maksimalnaBrzina = PropertiesUtil.vratiSvojstvo("MAKSIMALNA_BRZINA_ZAPADNI_SEKTOR",
					PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class);
			brojVozilaNaCesti = PropertiesUtil.vratiSvojstvo("BROJ_VOZILA_ZAPADNI_SEKTOR",
					PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class);
			break;
		case 1:
			for (int i = 0; i < 30; i++) {
				if (i != 6) {
					element = new Saobracajnica(i, 13, TipSaobracajnice.CESTA);
					Teritorija.postaviElementNaMapu(i, 13, element);
					poljaNaCesti.add(element);
				}
			}
			for (int i = 0; i < 30; i++) {
				if (i != 6) {
					element = new Saobracajnica(i, 14, TipSaobracajnice.CESTA);
					Teritorija.postaviElementNaMapu(i, 14, element);
					poljaNaCesti.add(element);
				}
			}
			sektor = ImeSektora.CENTRALNI;
			traka[0] = new TrakaNaCesti(0, 13, SmjerKretanja.JUG, idCesta);
			traka[1] = new TrakaNaCesti(29, 14, SmjerKretanja.SJEVER, idCesta);
			maksimalnaBrzina = PropertiesUtil.vratiSvojstvo("MAKSIMALNA_BRZINA_CENTRALNI_SEKTOR",
					PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class);
			brojVozilaNaCesti = PropertiesUtil.vratiSvojstvo("BROJ_VOZILA_CENTRALNI_SEKTOR",
					PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class);
			break;
		case 2:
			for (int i = 21; i < 30; i++) {
				if (i != 26) {
					element = new Saobracajnica(20, i, TipSaobracajnice.CESTA);
					Teritorija.postaviElementNaMapu(20, i, element);
					poljaNaCesti.add(element);
				}
			}
			for (int i = 21; i < 30; i++) {
				if (i != 26) {
					element = new Saobracajnica(21, i, TipSaobracajnice.CESTA);
					Teritorija.postaviElementNaMapu(21, i, element);
					poljaNaCesti.add(element);
				}
			}
			for (int i = 22; i < 30; i++) {
				element = new Saobracajnica(i, 21, TipSaobracajnice.CESTA);
				Teritorija.postaviElementNaMapu(i, 21, element);
				poljaNaCesti.add(element);
			}
			for (int i = 22; i < 30; i++) {
				element = new Saobracajnica(i, 22, TipSaobracajnice.CESTA);
				Teritorija.postaviElementNaMapu(i, 22, element);
				poljaNaCesti.add(element);
			}
			sektor = ImeSektora.ISTOCNI;
			traka[0] = new TrakaNaCesti(20, 29, SmjerKretanja.ZAPAD, idCesta);
			traka[1] = new TrakaNaCesti(29, 22, SmjerKretanja.SJEVER, idCesta);
			maksimalnaBrzina = PropertiesUtil.vratiSvojstvo("MAKSIMALNA_BRZINA_ISTOCNI_SEKTOR",
					PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class);
			brojVozilaNaCesti = PropertiesUtil.vratiSvojstvo("BROJ_VOZILA_ISTOCNI_SEKTOR",
					PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class);
			break;
		}
	}

	public int getIdCesta() {
		return idCesta;
	}

	public void setIdCesta(int idCesta) {
		this.idCesta = idCesta;
	}

	public ArrayList<Saobracajnica> getPoljaNaCesti() {
		return poljaNaCesti;
	}

	public void setPoljaNaCesti(ArrayList<Saobracajnica> poljaNaCesti) {
		this.poljaNaCesti = poljaNaCesti;
	}

	public TrakaNaCesti[] getTraka() {
		return traka;
	}

	public void setTraka(TrakaNaCesti[] traka) {
		this.traka = traka;
	}

	public int getMaksimalnaBrzina() {
		return maksimalnaBrzina;
	}

	public void setMaksimalnaBrzina(int maksimalnaBrzina) {
		this.maksimalnaBrzina = maksimalnaBrzina;
	}

	public int getBrojVozilaNaCesti() {
		return brojVozilaNaCesti;
	}

	public void setBrojVozilaNaCesti(int brojVozilaNaCesti) {
		this.brojVozilaNaCesti = brojVozilaNaCesti;
	}

	public ImeSektora getSektor() {
		return sektor;
	}

	public void setSektor(ImeSektora sektor) {
		this.sektor = sektor;
	}

}
