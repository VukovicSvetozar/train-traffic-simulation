package org.unibl.etf.zs.model.stanica;

import java.io.File;
import java.util.ArrayList;

import org.unibl.etf.zs.controller.MapaSaobracaja;
import org.unibl.etf.zs.model.mapa.Element;
import org.unibl.etf.zs.model.mapa.Teritorija;
import org.unibl.etf.zs.model.prevoz.kompozicije.Kompozicija;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica;
import org.unibl.etf.zs.model.saobracajnice.Saobracajnica.TipSaobracajnice;
import org.unibl.etf.zs.utility.PathFile;
import org.unibl.etf.zs.utility.PropertiesUtil;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class ZeljeznickaStanica extends Element implements PathFile {

	private int idZeleznickaStanica;
	private String imeZeleznice;

	private ArrayList<Saobracajnica> poljaNaZeleznickojStanici;
	private ArrayList<Kompozicija> trenutneKompozicjeNaZeljeznickojStanici;

	private Image slika;
	private ImageView pogledNaSliku;

	public ZeljeznickaStanica() {
	}

	public ZeljeznickaStanica(int idZeleznickaStanica) {
		this.idZeleznickaStanica = idZeleznickaStanica;
		poljaNaZeleznickojStanici = new ArrayList<>();
		trenutneKompozicjeNaZeljeznickojStanici = new ArrayList<>();
		kreirajPoljaNaZeleznickojStanici(idZeleznickaStanica);
	}

	private void kreirajPoljaNaZeleznickojStanici(int redniBrojZeleznickeStanice) {
		Saobracajnica element = null;
		switch (redniBrojZeleznickeStanice) {
		case 0:
			for (int i = 1; i < 3; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(27, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(27, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			for (int i = 1; i < 3; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(28, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(28, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			imeZeleznice = "A";
			ucitajSliku(PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_A);
			postaviElementNaMapu(27, 1);
			break;
		case 1:
			for (int i = 6; i < 8; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(5, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(5, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			for (int i = 6; i < 8; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(6, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(6, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			imeZeleznice = "B";
			ucitajSliku(PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_B);
			postaviElementNaMapu(5, 6);
			break;
		case 2:
			for (int i = 19; i < 21; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(12, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(12, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			for (int i = 19; i < 21; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(13, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(13, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			imeZeleznice = "C";
			ucitajSliku(PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_C);
			postaviElementNaMapu(12, 19);
			break;
		case 3:
			for (int i = 26; i < 28; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(1, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(1, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			for (int i = 26; i < 28; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(2, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(2, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			imeZeleznice = "D";
			ucitajSliku(PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_D);
			postaviElementNaMapu(1, 26);
			break;
		case 4:
			for (int i = 25; i < 27; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(25, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(25, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			for (int i = 25; i < 27; i++) {
				element = new Saobracajnica();
				element.postaviPozicijuElementu(26, i);
				element.setTipSaobracajnice(TipSaobracajnice.ZELJEZNICKA_STANICA);
				Teritorija.postaviElementNaMapu(26, i, element);
				poljaNaZeleznickojStanici.add(element);
			}
			imeZeleznice = "E";
			ucitajSliku(PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_E);
			postaviElementNaMapu(25, 25);
			break;

		}
	}

	private void ucitajSliku(String putanjaSlike) {
		slika = new Image(new File(putanjaSlike).toURI().toString());
		pogledNaSliku = new ImageView(slika);
		pogledNaSliku.setViewport(new Rectangle2D(0, 0, 44, 44));
	}

	private void postaviElementNaMapu(int x, int y) {
		int sirinaPoljaNaMapi = PropertiesUtil.vratiSvojstvo("VELICINA_POLJA", PUTANJA_DO_SVOJSTAVA_MAPE,
				Integer.class);
		postaviPozicijuElementu(x, y);
		pogledNaSliku.setTranslateX(sirinaPoljaNaMapi * y);
		pogledNaSliku.setTranslateY(sirinaPoljaNaMapi * x);
		pogledNaSliku.setVisible(true);
		Teritorija.postaviElementNaMapu(x, y, this);
		MapaSaobracaja.getMatricaTeritorije().getChildren().add(pogledNaSliku);
		prikazPodataka();
	}

	public void prikazPodataka() {
		StringBuffer poruka = kreirajPorukuZaPrikazPodataka();
		Tooltip info = new Tooltip(poruka.toString());
		info.setStyle("-fx-background-color:POWDERBLUE; -fx-text-fill: black; "
				+ "    -fx-background-radius: 10px; -fx-background-insets: 0; "
				+ "    -fx-padding: 0.8em 0.8em 0.8em 0.8em; "
				+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.5) , 10, 0.0 , 0 , 3 ); "
				+ "    -fx-font-weight: bold;" + "    -fx-font-style: italic;" + "    -fx-font-size: 0.85em;");
		Tooltip.install(pogledNaSliku, info);
	}

	private StringBuffer kreirajPorukuZaPrikazPodataka() {
		StringBuffer poruka = new StringBuffer();
		poruka.append("Zeljeznicka stanica: " + imeZeleznice + "\n");
		poruka.append("Pozicija: " + prikazPozicije() + "\n");
		if (trenutneKompozicjeNaZeljeznickojStanici.isEmpty()) {
			poruka.append("Trenutno nema kompozicija na stanici." + "\n");
		} else {
			poruka.append("Prisutne kompozicije:" + "\n");
			trenutneKompozicjeNaZeljeznickojStanici.forEach(kompozicija -> {
				poruka.append("\t" + kompozicija.prikazUStanici() + "\n");
			});
		}
		return poruka;
	}

	public int getIdZeleznickaStanica() {
		return idZeleznickaStanica;
	}

	public void setIdZeleznickaStanica(int idZeleznickaStanica) {
		this.idZeleznickaStanica = idZeleznickaStanica;
	}

	public String getImeZeleznice() {
		return imeZeleznice;
	}

	public void setImeZeleznice(String imeZeleznice) {
		this.imeZeleznice = imeZeleznice;
	}

	public ArrayList<Saobracajnica> getPoljaNaZeleznickojStanici() {
		return poljaNaZeleznickojStanici;
	}

	public void setPoljaNaZeleznickojStanici(ArrayList<Saobracajnica> poljaNaZeleznickojStanici) {
		this.poljaNaZeleznickojStanici = poljaNaZeleznickojStanici;
	}

	public ArrayList<Kompozicija> getTrenutneKompozicjeNaZeljeznickojStanici() {
		return trenutneKompozicjeNaZeljeznickojStanici;
	}

	public void setTrenutneKompozicjeNaZeljeznickojStanici(
			ArrayList<Kompozicija> trenutneKompozicjeNaZeljeznickojStanici) {
		this.trenutneKompozicjeNaZeljeznickojStanici = trenutneKompozicjeNaZeljeznickojStanici;
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

	@Override
	public String toString() {
		return "ZeljeznickaStanica [imeZeleznice=" + imeZeleznice + ", trenutneKompozicjeNaZeljeznickojStanici="
				+ trenutneKompozicjeNaZeljeznickojStanici + "]";
	}

}
