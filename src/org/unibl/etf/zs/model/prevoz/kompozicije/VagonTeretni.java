package org.unibl.etf.zs.model.prevoz.kompozicije;

import java.util.Random;

public final class VagonTeretni extends Vagon implements Teretni {

	private int maksimalnaNosivost;

	public VagonTeretni() {
		odrediMaksimalnuNosivost();
		ucitajSliku(PUTANJA_DO_SLIKE_TERETNOG_VAGONA);
		postaviSlikuPrevoznogSredstvaNaMapu();
	}

	private void odrediMaksimalnuNosivost() {
		Random rand = new Random();
		maksimalnaNosivost = rand.nextInt(2500) + 500;
	}

	public int getMaksimalnaNosivost() {
		return maksimalnaNosivost;
	}

	public void setMaksimalnaNosivost(int maksimalnaNosivost) {
		this.maksimalnaNosivost = maksimalnaNosivost;
	}

}
