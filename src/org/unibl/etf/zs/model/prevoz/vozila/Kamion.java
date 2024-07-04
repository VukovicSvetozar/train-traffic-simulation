package org.unibl.etf.zs.model.prevoz.vozila;

import java.util.Random;

public final class Kamion extends Vozilo {

	private int nosivost;

	public Kamion() {
		super();
		odrediNosivost();
		ucitajSliku(PUTANJA_DO_SLIKE_KAMIONA);
		postaviSlikuPrevoznogSredstvaNaMapu();
		unesiPodatkeOVozilu(PUTANJA_DO_PODATAKA_O_KAMIONIMA);
	}

	private void odrediNosivost() {
		Random rand = new Random();
		nosivost = rand.nextInt(9000) + 1000;
	}

	public int getNosivost() {
		return nosivost;
	}

	public void setNosivost(int nosivost) {
		this.nosivost = nosivost;
	}

}
