package org.unibl.etf.zs.model.prevoz.kompozicije;

import java.util.Random;

import org.unibl.etf.zs.model.prevoz.PrevoznoSredstvo;

public abstract class Lokomotiva extends PrevoznoSredstvo {

	public enum TipPogona {
		PARNI, DIZELSKI, ELEKTRICNI;
	}

	private TipPogona tipPogona;
	private int snaga;
	private String oznaka;

	public Lokomotiva() {
		odrediSnagu();
		odrediOznaku();
	}

	private void odrediSnagu() {
		Random rand = new Random();
		snaga = rand.nextInt(150) + 50;
	}

	private void odrediOznaku() {
		oznaka = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(3, 8);
	}

	public int getSnaga() {
		return snaga;
	}

	public void setSnaga(int snaga) {
		this.snaga = snaga;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public TipPogona getTipPogona() {
		return tipPogona;
	}

	public void setTipPogona(TipPogona tipPogona) {
		this.tipPogona = tipPogona;
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
