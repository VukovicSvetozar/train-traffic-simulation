package org.unibl.etf.zs.model.prevoz;

import java.io.File;

import org.unibl.etf.zs.controller.MapaSaobracaja;
import org.unibl.etf.zs.model.mapa.Element;
import org.unibl.etf.zs.model.mapa.Teritorija;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class PrevoznoSredstvo extends Element {

	protected SmjerKretanja smjerKretanja;

	protected Image slika;
	protected ImageView pogledNaSliku;
	protected int koordinataSlikeX;
	protected int koordinataSlikeY;
	protected int visinaSlike;
	protected int sirinaSlike;

	public PrevoznoSredstvo() {
		koordinataSlikeX = 0;
		koordinataSlikeY = 0;
		visinaSlike = Teritorija.getVELICINA_POLJA();
		sirinaSlike = Teritorija.getVELICINA_POLJA();
	}

	public void ucitajSliku(String putanjaSlike) {
		slika = new Image(new File(putanjaSlike).toURI().toString());
		pogledNaSliku = new ImageView(slika);
		pogledNaSliku.setVisible(false);
		pogledNaSliku.setViewport(new Rectangle2D(koordinataSlikeX, koordinataSlikeY, sirinaSlike, visinaSlike));
	}

	public void postaviSlikuPrevoznogSredstvaNaMapu() {
		Platform.runLater(() -> {
			MapaSaobracaja.getMatricaTeritorije().getChildren().add(pogledNaSliku);
		});
	}

	public SmjerKretanja getSmjerKretanja() {
		return smjerKretanja;
	}

	public void setSmjerKretanja(SmjerKretanja smjerKretanja) {
		this.smjerKretanja = smjerKretanja;
	}

	public Image getSlika() {
		return slika;
	}

	public void setSlika(Image slika) {
		this.slika = slika;
	}

	public ImageView getPogledNaSliku() {
		return pogledNaSliku;
	}

	public void setPogledNaSliku(ImageView pogledNaSliku) {
		this.pogledNaSliku = pogledNaSliku;
	}

	public int getKoordinataSlikeX() {
		return koordinataSlikeX;
	}

	public void setKoordinataSlikeX(int koordinataSlikeX) {
		this.koordinataSlikeX = koordinataSlikeX;
	}

	public int getKoordinataSlikeY() {
		return koordinataSlikeY;
	}

	public void setKoordinataSlikeY(int koordinataSlikeY) {
		this.koordinataSlikeY = koordinataSlikeY;
	}

	public int getVisinaSlike() {
		return visinaSlike;
	}

	public void setVisinaSlike(int visinaSlike) {
		this.visinaSlike = visinaSlike;
	}

	public int getSirinaSlike() {
		return sirinaSlike;
	}

	public void setSirinaSlike(int sirinaSlike) {
		this.sirinaSlike = sirinaSlike;
	}

}
