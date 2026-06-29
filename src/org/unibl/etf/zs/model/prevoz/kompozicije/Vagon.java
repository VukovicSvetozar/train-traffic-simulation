package org.unibl.etf.zs.model.prevoz.kompozicije;

import java.util.Random;

import org.unibl.etf.zs.model.prevoz.PrevoznoSredstvo;

public abstract class Vagon extends PrevoznoSredstvo {

	private int duzina;
	private String oznaka;

	public Vagon() {
		odrediDuzinu();
		odrediOznaku();
	}

	private void odrediDuzinu() {
		Random rand = new Random();
		duzina = rand.nextInt(250) + 50;
	}

	private void odrediOznaku() {
		oznaka = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(3, 8);
	}

	public int getDuzina() {
		return duzina;
	}

	public void setDuzina(int duzina) {
		this.duzina = duzina;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	@Override
	public String toString() {
		StringBuffer poruka = new StringBuffer(this.getClass().getSimpleName() + "[" + oznaka + "]: ");
		if (smjerKretanja != null)
			poruka.append(smjerKretanja + " " + prikazPozicije());
		else
			poruka.append("u mirovanju.");
		return poruka.toString();
	}

}
