package org.unibl.etf.zs.model.prevoz.kompozicije;

public final class LokomotivaUniverzalna extends Lokomotiva implements Putnicki, Teretni, PosebneNamjene {

	public LokomotivaUniverzalna() {
		ucitajSliku(PUTANJA_DO_SLIKE_UNIVERZALNE_LOKOMOTIVE);
		postaviSlikuPrevoznogSredstvaNaMapu();
	}

}
