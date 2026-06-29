package org.unibl.etf.zs.model.prevoz;

import java.util.Map;
import java.util.HashMap;

import org.unibl.etf.zs.model.prevoz.kompozicije.Kompozicija;

public final class Dionica {

	private ImeSektora imeSektora;
	private int brzina;
	private Map<Kompozicija, Boolean> trenutneKompozicijeNaPruzi;
	private String imeTrenutnePolazneStanice;

	public Dionica(int id) {
		switch (id) {
		case 0:
			this.imeSektora = ImeSektora.ZAPADNI;
			break;
		case 1:
			this.imeSektora = ImeSektora.JUGOZAPADNI;
			break;
		case 2:
			this.imeSektora = ImeSektora.ISTOCNI;
			break;
		case 3:
			this.imeSektora = ImeSektora.JUGOISTOCNI;
			break;
		case 4:
			this.imeSektora = ImeSektora.SJEVERNI;
			break;
		case 5:
			this.imeSektora = ImeSektora.CENTRALNI;
			break;
		}
		this.brzina = 0;
		this.trenutneKompozicijeNaPruzi = new HashMap<>();
	}

	public ImeSektora getImeSektora() {
		return imeSektora;
	}

	public void setImeSektora(ImeSektora imeSektora) {
		this.imeSektora = imeSektora;
	}

	public int getBrzina() {
		return brzina;
	}

	public void setBrzina(int brzina) {
		this.brzina = brzina;
	}

	public Map<Kompozicija, Boolean> getTrenutneKompozicijeNaPruzi() {
		return trenutneKompozicijeNaPruzi;
	}

	public void setTrenutneKompozicijeNaPruzi(Map<Kompozicija, Boolean> trenutneKompozicijeNaPruzi) {
		this.trenutneKompozicijeNaPruzi = trenutneKompozicijeNaPruzi;
	}

	public String getImeTrenutnePolazneStanice() {
		return imeTrenutnePolazneStanice;
	}

	public void setImeTrenutnePolazneStanice(String imeTrenutnePolazneStanice) {
		this.imeTrenutnePolazneStanice = imeTrenutnePolazneStanice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imeSektora == null) ? 0 : imeSektora.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dionica other = (Dionica) obj;
		if (imeSektora == null) {
			if (other.imeSektora != null)
				return false;
		} else if (!imeSektora.equals(other.imeSektora))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sektor [imeSektora=" + imeSektora + ", brzina=" + brzina + ", trenutneKompozicijeNaPruzi="
				+ trenutneKompozicijeNaPruzi + ", imeTrenutnePolazneStanice=" + imeTrenutnePolazneStanice + "]";
	}

}
