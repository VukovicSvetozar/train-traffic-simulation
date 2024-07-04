package org.unibl.etf.zs.model.prevoz.kompozicije;

import java.util.Random;

public final class VagonPutnickiSaLezajima extends VagonPutnicki {

	private int brojMjesta;

	public VagonPutnickiSaLezajima() {
		odrediBrojMjesta();
	}
	
	private void odrediBrojMjesta() {
		Random rand = new Random();
		brojMjesta = rand.nextInt(150) + 50;
	}

	public int getBrojMjesta() {
		return brojMjesta;
	}

	public void setBrojMjesta(int brojMjesta) {
		this.brojMjesta = brojMjesta;
	}
}
