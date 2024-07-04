package org.unibl.etf.zs.model.saobracajnice;

import org.unibl.etf.zs.model.prevoz.SmjerKretanja;

public final class TrakaNaCesti {

	private int pocetnaPozicijaX;
	private int pocetnaPozicijaY;
	private SmjerKretanja pocetniSmjer;
	private int idCesteKojojPripada;

	public TrakaNaCesti() {
		super();
	}

	public TrakaNaCesti(int pozicijaX, int pozicijaY, SmjerKretanja pocetniSmjer, int idCesteKojojPripada) {
		this.pocetnaPozicijaX = pozicijaX;
		this.pocetnaPozicijaY = pozicijaY;
		this.pocetniSmjer = pocetniSmjer;
		this.idCesteKojojPripada = idCesteKojojPripada;
	}

	public int getPocetnaPozicijaX() {
		return pocetnaPozicijaX;
	}

	public void setPocetnaPozicijaX(int pocetnaPozicijaX) {
		this.pocetnaPozicijaX = pocetnaPozicijaX;
	}

	public int getPocetnaPozicijaY() {
		return pocetnaPozicijaY;
	}

	public void setPocetnaPozicijaY(int pocetnaPozicijaY) {
		this.pocetnaPozicijaY = pocetnaPozicijaY;
	}

	public SmjerKretanja getPocetniSmjer() {
		return pocetniSmjer;
	}

	public void setPocetniSmjer(SmjerKretanja pocetniSmjer) {
		this.pocetniSmjer = pocetniSmjer;
	}

	public int getIdCesteKojojPripada() {
		return idCesteKojojPripada;
	}

	public void setIdCesteKojojPripada(int idCesteKojojPripada) {
		this.idCesteKojojPripada = idCesteKojojPripada;
	}

}
