package org.unibl.etf.zs.model.prevoz.kompozicije;

public final class LokomotivaManevarska extends Lokomotiva implements Manevarski {

	public LokomotivaManevarska() {
		ucitajSliku(PUTANJA_DO_SLIKE_MANEVARSKE_LOKOMOTIVE);
		postaviSlikuPrevoznogSredstvaNaMapu();
	}

}
