package org.unibl.etf.zs.utility;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import java.io.IOException;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;

import org.unibl.etf.zs.model.mapa.Teritorija;
import org.unibl.etf.zs.model.saobracajnice.Cesta;
import org.unibl.etf.zs.simulation.KontrolerDrumskogSaobracaja;
import org.unibl.etf.zs.simulation.KreatorLinije;

public class FileWatcher implements PathFile {

	private final WatchService posmatrac;
	private final Map<WatchKey, Path> kljucevi;
	private Path putanja;
	private boolean linija;

	public FileWatcher(Path putanja, boolean linija) throws IOException {
		this.putanja = putanja;
		this.linija = linija;
		this.posmatrac = FileSystems.getDefault().newWatchService();
		this.kljucevi = new HashMap<WatchKey, Path>();
		WatchKey kljuc = putanja.register(posmatrac, ENTRY_MODIFY);
		kljucevi.put(kljuc, putanja);
	}

	public void procesiranjeDogadjaja() {
		for (;;) {
			WatchKey kljuc;
			try {
				kljuc = posmatrac.take();
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				FileLogger.log(Level.SEVERE, "Nit je prekinuta.", ex);
				return;
			}
			Path putanjaKljuca = kljucevi.get(kljuc);
			if (putanjaKljuca == null) {
				FileLogger.log(Level.SEVERE, "WatchKey nije prepoznat.", null);
				continue;
			}

			for (WatchEvent<?> dogadjaj : kljuc.pollEvents()) {
				Kind<?> tipDogadjaja = dogadjaj.kind();
				if (tipDogadjaja == OVERFLOW)
					continue;
				Path imeDatoteke = putanja.resolve((Path) dogadjaj.context());
				if (linija && tipDogadjaja.equals(ENTRY_MODIFY) && imeDatoteke.toString().endsWith(".txt")) {
					kreirajLiniju(imeDatoteke);
				} else if (tipDogadjaja.equals(ENTRY_MODIFY) && imeDatoteke.toString().endsWith(".properties")) {
					promjenaSvojstava();
				}
			}

			boolean valid = kljuc.reset();
			if (!valid) {
				kljucevi.remove(kljuc);
				if (kljucevi.isEmpty())
					break;
			}
		}
	}

	private void kreirajLiniju(Path imeDatoteke) {
		KreatorLinije linija = new KreatorLinije();
		linija.novaLinija(imeDatoteke);
//		imeDatoteke.toFile().setWritable(false);
	}

	private void promjenaSvojstava() {
		for (Cesta cesta : Teritorija.getCeste()) {
			switch (cesta.getSektor()) {
			case ZAPADNI:
				cesta.setMaksimalnaBrzina(PropertiesUtil.vratiSvojstvo("MAKSIMALNA_BRZINA_ZAPADNI_SEKTOR",
						PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class));
				dodajNovaVozila(cesta, "BROJ_VOZILA_ZAPADNI_SEKTOR");
				break;
			case CENTRALNI:
				cesta.setMaksimalnaBrzina(PropertiesUtil.vratiSvojstvo("MAKSIMALNA_BRZINA_CENTRALNI_SEKTOR",
						PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class));
				dodajNovaVozila(cesta, "BROJ_VOZILA_CENTRALNI_SEKTOR");
				break;
			case ISTOCNI:
				cesta.setMaksimalnaBrzina(PropertiesUtil.vratiSvojstvo("MAKSIMALNA_BRZINA_ISTOCNI_SEKTOR",
						PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, Integer.class));
				dodajNovaVozila(cesta, "BROJ_VOZILA_ISTOCNI_SEKTOR");
				break;
			default:
				break;
			}
		}
	}

	private void dodajNovaVozila(Cesta cesta, String putanja) {
		int brojVozilaNaCestiStaraVrijednost = cesta.getBrojVozilaNaCesti();
		int brojVozilaNaCestiNovaVrijednost = PropertiesUtil.vratiSvojstvo(putanja, PUTANJA_DO_SVOJSTAVA_SAOBRACAJA,
				Integer.class);
		int razlika = brojVozilaNaCestiNovaVrijednost - brojVozilaNaCestiStaraVrijednost;
		if (razlika > 0) {
			for (int i = 0; i < razlika; i++)
				KontrolerDrumskogSaobracaja.getListaSektoraZaVozila().add(cesta.getSektor());
			Collections.shuffle(KontrolerDrumskogSaobracaja.getListaSektoraZaVozila());
			cesta.setBrojVozilaNaCesti(brojVozilaNaCestiNovaVrijednost);
		}
	}

}
