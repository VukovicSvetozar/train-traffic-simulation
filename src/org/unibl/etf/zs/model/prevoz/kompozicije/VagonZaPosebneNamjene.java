package org.unibl.etf.zs.model.prevoz.kompozicije;

public final class VagonZaPosebneNamjene extends Vagon implements PosebneNamjene, Manevarski {

	public VagonZaPosebneNamjene() {
		ucitajSliku(PUTANJA_DO_SLIKE_VAGONA_ZA_POSEBNE_NAMJENE);
		postaviSlikuPrevoznogSredstvaNaMapu();
	}

}
