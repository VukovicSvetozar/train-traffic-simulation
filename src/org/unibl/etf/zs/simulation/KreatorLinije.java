package org.unibl.etf.zs.simulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.unibl.etf.zs.utility.Dialog;
import org.unibl.etf.zs.utility.FileLogger;

import javafx.application.Platform;

import org.unibl.etf.zs.model.prevoz.PrevoznoSredstvo;
import org.unibl.etf.zs.model.prevoz.kompozicije.Kompozicija;
import org.unibl.etf.zs.model.prevoz.kompozicije.Lokomotiva;
import org.unibl.etf.zs.model.prevoz.kompozicije.LokomotivaManevarska;
import org.unibl.etf.zs.model.prevoz.kompozicije.LokomotivaPutnicka;
import org.unibl.etf.zs.model.prevoz.kompozicije.LokomotivaTeretna;
import org.unibl.etf.zs.model.prevoz.kompozicije.LokomotivaUniverzalna;
import org.unibl.etf.zs.model.prevoz.kompozicije.Manevarski;
import org.unibl.etf.zs.model.prevoz.kompozicije.PosebneNamjene;
import org.unibl.etf.zs.model.prevoz.kompozicije.Putnicki;
import org.unibl.etf.zs.model.prevoz.kompozicije.Teretni;
import org.unibl.etf.zs.model.prevoz.kompozicije.Vagon;
import org.unibl.etf.zs.model.prevoz.kompozicije.VagonPutnickiRestoran;
import org.unibl.etf.zs.model.prevoz.kompozicije.VagonPutnickiSaLezajima;
import org.unibl.etf.zs.model.prevoz.kompozicije.VagonPutnickiSaSjedistima;
import org.unibl.etf.zs.model.prevoz.kompozicije.VagonPutnickiZaSpavanje;
import org.unibl.etf.zs.model.prevoz.kompozicije.VagonTeretni;
import org.unibl.etf.zs.model.prevoz.kompozicije.VagonZaPosebneNamjene;
import org.unibl.etf.zs.model.prevoz.kompozicije.Lokomotiva.TipPogona;

public class KreatorLinije {

	private ArrayList<Lokomotiva> lokomotive;
	private ArrayList<Vagon> vagoni;
	private String osnovniPodaci;

	public KreatorLinije() {
		lokomotive = new ArrayList<>();
		vagoni = new ArrayList<>();
	}

	public void novaLinija(Path datoteka) {
		ucitajPodatke(datoteka);
		if (validnaKonfiguracija())
			kreirajNovuKompoziciju();
		else
			prikaziUpozorenje();
	}

	private void ucitajPodatke(Path datoteka) {
		try {
			List<String> linijeDatoteke = Files.readAllLines(datoteka);
			for (String linija : linijeDatoteke) {
				if (osnovniPodaci == null && linija.startsWith("O"))
					osnovniPodaci = linija;
				else {
					switch (linija) {
					case "LokomotivaManevarska":
						lokomotive.add(new LokomotivaManevarska());
						break;
					case "LokomotivaUniverzalna":
						lokomotive.add(new LokomotivaUniverzalna());
						break;
					case "LokomotivaTeretna":
						lokomotive.add(new LokomotivaTeretna());
						break;
					case "LokomotivaPutnicka":
						lokomotive.add(new LokomotivaPutnicka());
						break;
					case "VagonZaPosebneNamjene":
						vagoni.add(new VagonZaPosebneNamjene());
						break;
					case "VagonTeretni":
						vagoni.add(new VagonTeretni());
						break;
					case "VagonPutnickiRestoran":
						vagoni.add(new VagonPutnickiRestoran());
						break;
					case "VagonPutnickiSaLezajima":
						vagoni.add(new VagonPutnickiSaLezajima());
						break;
					case "VagonPutnickiSaSjedistima":
						vagoni.add(new VagonPutnickiSaSjedistima());
						break;
					case "VagonPutnickiZaSpavanje":
						vagoni.add(new VagonPutnickiZaSpavanje());
						break;
					default:
						break;
					}
				}
			}
		} catch (IOException ex) {
			FileLogger.log(Level.SEVERE, "Navedena putanja nije odgovarajuca.", ex);
		}
	}

	private boolean validnaKonfiguracija() {
		boolean validnaKonfiguracija = false;

		Predicate<PrevoznoSredstvo> manevarski = ps -> ps instanceof Manevarski;
		Predicate<PrevoznoSredstvo> posebneNamjene = ps -> ps instanceof PosebneNamjene;
		Predicate<PrevoznoSredstvo> putnicki = ps -> ps instanceof Putnicki;
		Predicate<PrevoznoSredstvo> teretni = ps -> ps instanceof Teretni;

		if (lokomotive.isEmpty())
			validnaKonfiguracija = false;
		else {

			List<PrevoznoSredstvo> elementiKompozicije = Stream.concat(lokomotive.stream(), vagoni.stream())
					.collect(Collectors.toList());

			validnaKonfiguracija = elementiKompozicije.stream().allMatch(manevarski)
					|| elementiKompozicije.stream().allMatch(posebneNamjene)
					|| elementiKompozicije.stream().allMatch(putnicki)
					|| elementiKompozicije.stream().allMatch(teretni);

			if (validnaKonfiguracija && osnovniPodaci != null) {
				String[] podaci = osnovniPodaci.split("-");
				if (podaci.length > 4)
					validnaKonfiguracija = podaci[1].chars().allMatch(Character::isDigit)
							&& Arrays.stream(TipPogona.values()).anyMatch(
									tipPogona -> tipPogona.name().equals(podaci[2]))
							&& podaci[3].matches("[A-G]{1}") && podaci[4].matches("[A-E]{1}")
							&& !podaci[3].equals(podaci[4]);
				else
					validnaKonfiguracija = false;
			} else
				validnaKonfiguracija = false;
		}

		return validnaKonfiguracija;
	}

	private void kreirajNovuKompoziciju() {
		Kompozicija kompozicija = new Kompozicija(lokomotive, vagoni, osnovniPodaci);
		KontrolerZeljeznickogSaobracaja.getListaKompozicija().add(kompozicija);
		kompozicija.start();
	}

	private void prikaziUpozorenje() {
		Thread th = new Thread() {
			@Override
			public void run() {
				String info = "Pokusajte unijeti ispravne vrijednosti!";
				Dialog.showInfoDialog("Info", "Pogresna konfiguracije kompozicije!", info);
			}
		};
		th.setDaemon(true);
		Platform.runLater(th);
	}

}
