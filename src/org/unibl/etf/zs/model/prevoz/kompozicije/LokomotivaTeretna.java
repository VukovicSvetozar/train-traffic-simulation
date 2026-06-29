package org.unibl.etf.zs.model.prevoz.kompozicije;

public final class LokomotivaTeretna extends Lokomotiva implements Teretni {

	public LokomotivaTeretna() {
		ucitajSliku(PUTANJA_DO_SLIKE_TERETNE_LOKOMOTIVE);
		postaviSlikuPrevoznogSredstvaNaMapu();
	}

}
