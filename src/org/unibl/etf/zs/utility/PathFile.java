package org.unibl.etf.zs.utility;

import java.io.File;

public interface PathFile {

	public final static String PUTANJA_DO_DIREKTORIJUMA_ZA_SVOJSTVA = "properties";

	public final static String PUTANJA_DO_SVOJSTAVA_SAOBRACAJA = PUTANJA_DO_DIREKTORIJUMA_ZA_SVOJSTVA + File.separator
			+ "trafic.properties";

	public final static String PUTANJA_DO_SVOJSTAVA_MAPE = PUTANJA_DO_DIREKTORIJUMA_ZA_SVOJSTVA + File.separator
			+ "map.properties";

	public final static String PUTANJA_DO_DIREKTORIJUMA_ZA_LINIJE = PropertiesUtil
			.vratiSvojstvo("PUTANJA_DO_DIREKTORIJUMA_ZA_LINIJE", PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, String.class);

	public final static String PUTANJA_DO_DIREKTORIJUMA_ZA_SERIJALIZACJU = PropertiesUtil
			.vratiSvojstvo("PUTANJA_DO_DIREKTORIJUMA_ZA_SERIJALIZACJU", PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, String.class);

	public final static String PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE = PropertiesUtil
			.vratiSvojstvo("PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE", PUTANJA_DO_SVOJSTAVA_SAOBRACAJA, String.class);

	public final static String PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_A = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "ZeljeznickaStanicaA.png";

	public final static String PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_B = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "ZeljeznickaStanicaB.png";

	public final static String PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_C = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "ZeljeznickaStanicaC.png";

	public final static String PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_D = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "ZeljeznickaStanicaD.png";

	public final static String PUTANJA_DO_SLIKE_ZELJEZNICKE_STANICE_E = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "ZeljeznickaStanicaE.png";

	public final static String PUTANJA_DO_SLIKE_AUTOMOBILA_1 = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "images" + File.separator + "Automobil_1.png";
	
	public final static String PUTANJA_DO_SLIKE_AUTOMOBILA_2 = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "images" + File.separator + "Automobil_2.png";
	
	public final static String PUTANJA_DO_SLIKE_AUTOMOBILA_3 = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "images" + File.separator + "Automobil_3.png";

	public final static String PUTANJA_DO_SLIKE_KAMIONA = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "images" + File.separator + "Kamion.png";

	public final static String PUTANJA_DO_SLIKE_LOKOMOTIVE = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "images" + File.separator + "Lokomotiva.png";

	public final static String PUTANJA_DO_SLIKE_MANEVARSKE_LOKOMOTIVE = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "Lokomotiva.png";

	public final static String PUTANJA_DO_SLIKE_UNIVERZALNE_LOKOMOTIVE = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "Lokomotiva.png";

	public final static String PUTANJA_DO_SLIKE_TERETNE_LOKOMOTIVE = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "Lokomotiva.png";

	public final static String PUTANJA_DO_SLIKE_PUTNICKE_LOKOMOTIVE = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "Lokomotiva.png";

	public final static String PUTANJA_DO_SLIKE_VAGONA = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator + "images"
			+ File.separator + "Vagon.png";

	public final static String PUTANJA_DO_SLIKE_VAGONA_ZA_POSEBNE_NAMJENE = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE
			+ File.separator + "images" + File.separator + "Vagon_Posebne_Namjene.png";

	public final static String PUTANJA_DO_SLIKE_TERETNOG_VAGONA = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "images" + File.separator + "Vagon_Teretni.png";

	public final static String PUTANJA_DO_SLIKE_PUTNICKOG_VAGONA = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "images" + File.separator + "Vagon_Putnicki.png";

	public final static String PUTANJA_DO_IKONICE = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator + "images"
			+ File.separator + "Ikonica.png";

	public final static String PUTANJA_DO_PODATAKA_O_AUTOMOBILIMA = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "dataVehicle" + File.separator + "automobili.txt";

	public final static String PUTANJA_DO_PODATAKA_O_KAMIONIMA = PUTANJA_DO_DIREKTORIJUMA_ZA_RESURSE + File.separator
			+ "dataVehicle" + File.separator + "kamioni.txt";

}
