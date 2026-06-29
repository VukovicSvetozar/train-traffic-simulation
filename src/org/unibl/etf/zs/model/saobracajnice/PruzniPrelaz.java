package org.unibl.etf.zs.model.saobracajnice;

import java.util.ArrayList;

import org.unibl.etf.zs.model.mapa.Teritorija;
import org.unibl.etf.zs.model.prevoz.ImeSektora;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica.TipSaobracajnice;
import org.unibl.etf.zs.utility.PathFile;

public final class PruzniPrelaz implements PathFile {

	private int idPruzniPrelaz;
	private ArrayList<Saobracajnica> poljaNaPruznomPrelazu;
	private ImeSektora sektor;

	public PruzniPrelaz() {
	}

	public PruzniPrelaz(int idPruzniPrelaz) {
		this.idPruzniPrelaz = idPruzniPrelaz;
		kreirajPoljaNaPruznomPrelazu(idPruzniPrelaz);
	}

	private void kreirajPoljaNaPruznomPrelazu(int redniBrojPruznogPrelaza) {
		poljaNaPruznomPrelazu = new ArrayList<>();
		Saobracajnica element = null;
		switch (redniBrojPruznogPrelaza) {
		case 0:
			for (int i = 20; i < 22; i++) {
				element = new Saobracajnica(i, 2, TipSaobracajnice.PRUZNI_PRELAZ);
				Teritorija.postaviElementNaMapu(i, 2, element);
				poljaNaPruznomPrelazu.add(element);
			}
			sektor = ImeSektora.ZAPADNI;
			break;
		case 1:
			for (int i = 13; i < 15; i++) {
				element = new Saobracajnica(6, i, TipSaobracajnice.PRUZNI_PRELAZ);
				Teritorija.postaviElementNaMapu(6, i, element);
				poljaNaPruznomPrelazu.add(element);
			}
			sektor = ImeSektora.CENTRALNI;
			break;
		case 2:
			for (int i = 20; i < 22; i++) {
				element = new Saobracajnica(i, 26, TipSaobracajnice.PRUZNI_PRELAZ);
				Teritorija.postaviElementNaMapu(i, 26, element);
				poljaNaPruznomPrelazu.add(element);
			}
			sektor = ImeSektora.ISTOCNI;
			break;
		}
	}

	public int getIdPruzniPrelaz() {
		return idPruzniPrelaz;
	}

	public void setIdPruzniPrelaz(int idPruzniPrelaz) {
		this.idPruzniPrelaz = idPruzniPrelaz;
	}

	public ArrayList<Saobracajnica> getPoljaNaPruznomPrelazu() {
		return poljaNaPruznomPrelazu;
	}

	public void setPoljaNaPruznomPrelazu(ArrayList<Saobracajnica> poljaNaPruznomPrelazu) {
		this.poljaNaPruznomPrelazu = poljaNaPruznomPrelazu;
	}

	public ImeSektora getSektor() {
		return sektor;
	}

	public void setSektor(ImeSektora sektor) {
		this.sektor = sektor;
	}

}
