package org.unibl.etf.zs.model.prevoz.kompozicije;

public final class LokomotivaPutnicka extends Lokomotiva implements Putnicki {

	public LokomotivaPutnicka() {
		ucitajSliku(PUTANJA_DO_SLIKE_PUTNICKE_LOKOMOTIVE);
		postaviSlikuPrevoznogSredstvaNaMapu();
	}

}
