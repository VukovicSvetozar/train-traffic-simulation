package org.unibl.etf.zs.model.saobracajnice;

import java.util.ArrayList;

import org.unibl.etf.zs.model.mapa.Element;

public final class Saobracajnica extends Element {

	public enum TipSaobracajnice {
		CESTA, PRUZNI_PRELAZ, PRUGA, ZELJEZNICKA_STANICA
	};

	private TipSaobracajnice tipSaobracajnice;
	private boolean podNaponom;

	public Saobracajnica() {
	}

	public Saobracajnica(int pozicijaX, int pozicijaY, TipSaobracajnice tipSaobracajnice) {
		this.pozicijaX = pozicijaX;
		this.pozicijaY = pozicijaY;
		this.tipSaobracajnice = tipSaobracajnice;
		this.podNaponom = false;
	}

	public TipSaobracajnice getTipSaobracajnice() {
		return tipSaobracajnice;
	}

	public void setTipSaobracajnice(TipSaobracajnice tipSaobracajnice) {
		this.tipSaobracajnice = tipSaobracajnice;
	}

	public boolean isPodNaponom() {
		return podNaponom;
	}

	public void setPodNaponom(boolean podNaponom) {
		this.podNaponom = podNaponom;
	}

	public static Saobracajnica vratiSaobracajnicu(ArrayList<Saobracajnica> lista, int x, int y) {
		return lista.stream().filter(s -> s.getPozicijaX() == x && s.getPozicijaY() == y).findFirst().orElse(null);
	}

	public static void postaviNapon(ArrayList<Saobracajnica> lista, int x, int y, boolean napon) {
		Saobracajnica saobracajnica = vratiSaobracajnicu(lista, x, y);
		if (saobracajnica != null)
			saobracajnica.setPodNaponom(napon);
	}

	@Override
	public String toString() {
		return "Saobracajnica [prikazPozicije()=" + prikazPozicije() + "]";
	}

}
