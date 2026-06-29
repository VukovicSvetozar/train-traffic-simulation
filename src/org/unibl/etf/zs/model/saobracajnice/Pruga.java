package org.unibl.etf.zs.model.saobracajnice;

import java.util.ArrayList;

import org.unibl.etf.zs.model.mapa.Teritorija;
import org.unibl.etf.zs.model.prevoz.ImeSektora;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica.TipSaobracajnice;
import org.unibl.etf.zs.utility.PathFile;

public final class Pruga implements PathFile {

	private int idPruga;
	private ArrayList<Saobracajnica> poljaNaPruzi;
	private ImeSektora sektor;

	public Pruga() {
	}

	public Pruga(int idPruga) {
		this.idPruga = idPruga;
		kreirajPoljaNaPruzi(idPruga);
	}

	private void kreirajPoljaNaPruzi(int redniBrojPruge) {
		poljaNaPruzi = new ArrayList<>();
		Saobracajnica element = null;
		switch (redniBrojPruge) {
		case 0:
			for (int i = 6; i < 17; i++) {
				element = new Saobracajnica(i, 5, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(i, 5, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 2; i < 5; i++) {
				element = new Saobracajnica(16, i, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(16, i, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 17; i < 27; i++) {
				if (i != 20 && i != 21) {
					element = new Saobracajnica(i, 2, TipSaobracajnice.PRUGA);
					Teritorija.postaviElementNaMapu(i, 2, element);
					poljaNaPruzi.add(element);
				}
			}
			sektor = ImeSektora.ZAPADNI;
			break;
		case 1:
			element = new Saobracajnica(29, 2, TipSaobracajnice.PRUGA);
			Teritorija.postaviElementNaMapu(29, 2, element);
			poljaNaPruzi.add(element);
			sektor = ImeSektora.JUGOZAPADNI;
			break;
		case 2:
			for (int i = 8; i < 20; i++) {
				if (i != 13 && i != 14) {
					element = new Saobracajnica(6, i, TipSaobracajnice.PRUGA);
					Teritorija.postaviElementNaMapu(6, i, element);
					poljaNaPruzi.add(element);
				}
			}
			for (int i = 7; i < 12; i++) {
				element = new Saobracajnica(i, 19, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(i, 19, element);
				poljaNaPruzi.add(element);
			}
			sektor = ImeSektora.CENTRALNI;
			break;
		case 3:
			for (int i = 14; i < 19; i++) {
				element = new Saobracajnica(i, 20, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(i, 20, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 21; i < 27; i++) {
				element = new Saobracajnica(18, i, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(18, i, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 19; i < 25; i++) {
				if (i != 20 && i != 21) {
					element = new Saobracajnica(i, 26, TipSaobracajnice.PRUGA);
					Teritorija.postaviElementNaMapu(i, 26, element);
					poljaNaPruzi.add(element);
				}
			}
			sektor = ImeSektora.ISTOCNI;
			break;
		case 4:
			for (int i = 27; i < 30; i++) {
				element = new Saobracajnica(25, i, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(25, i, element);
				poljaNaPruzi.add(element);
			}
			sektor = ImeSektora.JUGOISTOCNI;
			break;
		case 5:
			for (int i = 21; i < 27; i++) {
				element = new Saobracajnica(12, i, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(12, i, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 9; i < 12; i++) {
				element = new Saobracajnica(i, 26, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(i, 26, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 27; i < 29; i++) {
				element = new Saobracajnica(9, i, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(9, i, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 5; i < 9; i++) {
				element = new Saobracajnica(i, 28, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(i, 28, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 23; i < 28; i++) {
				element = new Saobracajnica(5, i, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(5, i, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 3; i < 5; i++) {
				element = new Saobracajnica(i, 23, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(i, 23, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 1; i < 4; i++) {
				element = new Saobracajnica(i, 22, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(i, 22, element);
				poljaNaPruzi.add(element);
			}
			for (int i = 23; i < 26; i++) {
				element = new Saobracajnica(1, i, TipSaobracajnice.PRUGA);
				Teritorija.postaviElementNaMapu(1, i, element);
				poljaNaPruzi.add(element);
			}
			sektor = ImeSektora.SJEVERNI;
			break;
		}
	}

	public int getIdPruga() {
		return idPruga;
	}

	public void setIdPruga(int idPruga) {
		this.idPruga = idPruga;
	}

	public ArrayList<Saobracajnica> getPoljaNaPruzi() {
		return poljaNaPruzi;
	}

	public void setPoljaNaPruzi(ArrayList<Saobracajnica> poljaNaPruzi) {
		this.poljaNaPruzi = poljaNaPruzi;
	}

	public ImeSektora getSektor() {
		return sektor;
	}

	public void setSektor(ImeSektora sektor) {
		this.sektor = sektor;
	}

}
