package org.unibl.etf.zs.utility;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import org.unibl.etf.zs.istorija_kretanja.IstorijaKretanja;

public class Serialization {

	public static boolean sacuvajIstorijuKretanja(IstorijaKretanja istorijaKretanja, String putanjaDirektorijuma) {
		String putanja = putanjaDirektorijuma + File.separator + istorijaKretanja.getImeKompozicije() + ".ser";
		File putanjaFile = new File(putanja);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(putanjaFile))) {
			oos.writeObject(istorijaKretanja);
			oos.close();
			return true;
		} catch (IOException ex) {
			FileLogger.log(Level.SEVERE, null, ex);
			Dialog.showErrorDialog("Greska", "Greska tokom serijalizacije istorija kretanja.",
					"Nije moguce sacuvati podatke na sljedecoj putanji: \n" + putanjaFile.getAbsolutePath());
		}
		return false;
	}

	public static IstorijaKretanja ucitajIstorijuKretanja(String putanja) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(putanja)))) {
			IstorijaKretanja istorijaKretanja = (IstorijaKretanja) ois.readObject();
			return istorijaKretanja;
		} catch (EOFException ex) {
			FileLogger.log(Level.WARNING, null, ex);
			return new IstorijaKretanja("");
		} catch (ClassNotFoundException | IOException ex) {
			FileLogger.log(Level.WARNING, null, ex);
			Dialog.showWarningDialog("Upozorenje", "Upozorenje tokom deserijalizacije istorije kretanja.",
					"Nije moguce ucitati podatke sa sljedece putanje: \n" + new File(putanja).getAbsolutePath());
		}
		return null;
	}

}
