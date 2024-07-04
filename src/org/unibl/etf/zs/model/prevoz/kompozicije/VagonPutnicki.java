package org.unibl.etf.zs.model.prevoz.kompozicije;

public abstract class VagonPutnicki extends Vagon implements Putnicki {

	public VagonPutnicki() {
		ucitajSliku(PUTANJA_DO_SLIKE_PUTNICKOG_VAGONA);
		postaviSlikuPrevoznogSredstvaNaMapu();
	}

}
