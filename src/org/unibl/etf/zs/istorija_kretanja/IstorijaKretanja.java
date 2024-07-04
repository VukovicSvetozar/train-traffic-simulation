package org.unibl.etf.zs.istorija_kretanja;

import java.io.Serializable;

public final class IstorijaKretanja implements Serializable {

	private static final long serialVersionUID = 1891753726689917982L;

	private StringBuffer poruka;
	private String imeKompozicije;

	public IstorijaKretanja(String imeKompozicije) {
		poruka = new StringBuffer();
		this.imeKompozicije = imeKompozicije;
	}

	public StringBuffer getPoruka() {
		return poruka;
	}

	public void setPoruka(StringBuffer poruka) {
		this.poruka = poruka;
	}

	public String getImeKompozicije() {
		return imeKompozicije;
	}

	public void setImeKompozicije(String imeKompozicije) {
		this.imeKompozicije = imeKompozicije;
	}

	@Override
	public String toString() {
		return "Kompozicija: [" + imeKompozicije + "]";
	}

}
