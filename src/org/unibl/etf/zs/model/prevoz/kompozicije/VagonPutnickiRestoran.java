package org.unibl.etf.zs.model.prevoz.kompozicije;

import java.util.UUID;

public final class VagonPutnickiRestoran extends VagonPutnicki {

	private String opis;

	public VagonPutnickiRestoran() {
		odrediOpis();
	}

	private void odrediOpis() {
		opis = UUID.randomUUID().toString().replaceAll("-", "");
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

}
