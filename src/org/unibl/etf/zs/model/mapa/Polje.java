package org.unibl.etf.zs.model.mapa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.unibl.etf.zs.model.saobracajnice.Saobracajnica;
import org.unibl.etf.zs.utility.FileLogger;

public final class Polje {

	private List<Element> elementi;

	public Polje() {
		super();
		this.elementi = Collections.synchronizedList(new ArrayList<Element>());
	}

	/**
	 * Provjera da li konkretno polje sadrzi odredjeni tip elementa.
	 * 
	 * @param tip - ime klase ciji tip zelimo da provjerimo da li je sadrzan u polju.
	 * @return true - u slucaju da polje kao el ement sadrzi prosledjeni tip.
	 */
	public boolean sadrziTip(Class<? extends Element> tip) {
		boolean sadrzi = false;
		try {
			for (Element element : elementi)
				if (tip.isInstance(element)) {
					sadrzi = true;
					break;
				}
		} catch (Exception e) {
			FileLogger.log(Level.SEVERE, "", e);
		}
		return sadrzi;
	}

	public Saobracajnica vratiSaobracajnicu() {
		Saobracajnica saobracajnica = null;
		for (Element element : elementi)
			if (element instanceof Saobracajnica) {
				saobracajnica = (Saobracajnica) element;
				break;
			}
		return saobracajnica;
	}

	public List<Element> getElementi() {
		return elementi;
	}

	public void setElementi(List<Element> elementi) {
		this.elementi = elementi;
	}

	@Override
	public String toString() {
		return "Polje [elementi=" + elementi + "]";
	}

}
