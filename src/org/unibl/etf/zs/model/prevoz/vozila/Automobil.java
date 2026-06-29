package org.unibl.etf.zs.model.prevoz.vozila;

import java.util.Random;

public final class Automobil extends Vozilo {

	private int brojVrata;

	public Automobil() {
		super();
		odrediBrojVrata();
		ucitajSliku();
		postaviSlikuPrevoznogSredstvaNaMapu();
		unesiPodatkeOVozilu(PUTANJA_DO_PODATAKA_O_AUTOMOBILIMA);
	}

	private void odrediBrojVrata() {
		Random rand = new Random();
		brojVrata = rand.nextInt(5) + 2;
	}

	private void ucitajSliku() {
		Random rand = new Random();
		int slucajanBroj = rand.nextInt(3);
		switch (slucajanBroj) {
		case 0:
			ucitajSliku(PUTANJA_DO_SLIKE_AUTOMOBILA_1);
			break;
		case 1:
			ucitajSliku(PUTANJA_DO_SLIKE_AUTOMOBILA_2);
			break;
		case 2:
			ucitajSliku(PUTANJA_DO_SLIKE_AUTOMOBILA_3);
			break;
		default:
			break;
		}

	}

	public int getBrojVrata() {
		return brojVrata;
	}

	public void setBrojVrata(int brojVrata) {
		this.brojVrata = brojVrata;
	}

}
