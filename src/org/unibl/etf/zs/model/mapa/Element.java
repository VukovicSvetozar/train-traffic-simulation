package org.unibl.etf.zs.model.mapa;

import org.unibl.etf.zs.utility.PathFile;

public abstract class Element implements PathFile {

	protected int pozicijaX;
	protected int pozicijaY;

	public Element() {
		pozicijaX = -1;
		pozicijaY = -1;
	}

	public void postaviPozicijuElementu(int pozicijaX, int pozicijaY) {
		this.pozicijaX = pozicijaX;
		this.pozicijaY = pozicijaY;
	}

	public String prikazPozicije() {
		return "[" + pozicijaX + ":" + pozicijaY + "]";
	}

	public int getPozicijaX() {
		return pozicijaX;
	}

	public void setPozicijaX(int pozicijaX) {
		this.pozicijaX = pozicijaX;
	}

	public int getPozicijaY() {
		return pozicijaY;
	}

	public void setPozicijaY(int pozicijaY) {
		this.pozicijaY = pozicijaY;
	}

}
