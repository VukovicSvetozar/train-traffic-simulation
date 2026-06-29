# Train Traffic Simulation

**Train Traffic Simulation** je napredna desktop aplikacija za simulaciju, upravljanje i koordinaciju željezničkog i drumskog saobraćaja u realnom vremenu. Projekat je razvijen u sklopu predmeta **Programski jezici 2** na **Elektrotehničkom fakultetu Banja Luka**[cite: 1]. 

Aplikacija demonstrira praktičnu primjenu naprednih koncepata objektno-orijentisanog programiranja, konkurentnog programiranja (višenitnosti), mehanizama sinhronizacije niti, dinamičkog učitavanja konfiguracije i serijalizacije objekata u Javi[cite: 1].

---

## 🛠️ Ključne Arhitektonske Funkcionalnosti

### 1. Konkurentnost i Sinhronizacija (Multithreading)
* **Nezavisne niti:** Svaka željeznička kompozicija i svako drumsko vozilo funkcionišu kao potpuno nezavisne niti koje se izvršavaju istovremeno bez predvidivog algoritma[cite: 1].
* **Sistem stanica kao kontrolera:** Kako bi se izbjegli sudari na jednokolosječnim dionicama, vozovi ne komuniciraju međusobno, već koordinaciju vrše željezničke stanice (A, B, C, D, E) koje upravljaju dozvolama za pristup dionicama i dinamički prilagođavaju brzine vozova u istom smjeru[cite: 1].
* **Pružni prelazi:** Sinhronizacija između drumskih vozila i vozova rješava se na pružnim prelazima. Vozila detektuju nailazak voza, bezbjedno se zaustavljaju i čekaju oslobađanje dionice i isključenje napona na mreži[cite: 1].
* **Električna mreža pod naponom:** Logika simulacije simulira i energetski podsistem – za kretanje električnih lokomotiva polje ispred, polja koja voz zauzima i polje iza moraju biti pod naponom[cite: 1].

### 2. Dinamičko Praćenje Fajlova (File Watcher)
* Aplikacija aktivno prati (`WatchService`) namjenski folder za vozove. Korisnik u realnom vremenu, putem običnog `.txt` fajla, može definisati novu liniju (sastav kompozicije, brzinu, polazište i odredište). Sistem automatski validira konfiguraciju i pokreće novu nit voza na mapi[cite: 1].

### 3. Istorija Kretanja i Serijalizacija
* Tokom vožnje, svaka kompozicija bilježi detaljnu istoriju (vrijeme, pređene koordinate, zadržavanja u stanicama)[cite: 1]. 
* Po dolasku na odredište, ovi podaci se serijalizuju u folder kretanja, odakle ih poseban GUI modul ponovo učitava (deserijalizuje) i tabelarno prikazuje korisniku[cite: 1].

---

## 📸 Pregled Grafičkog Interfejsa (GUI)

Aplikacija posjeduje bogat grafički interfejs izgrađen pomoću **JavaFX** biblioteke:

### Početni ekran aplikacije
Aplikacija posjeduje personalizovani "Splash Screen" sa logotipom prilikom pokretanja sistema.
![Početni ekran](assets/6.png)

### Simulacija u realnom vremenu
Mapa je generisana na osnovu matrice dimenzija $30\times30$ sa implementiranim stanicama, prugama, dvosmjernim putevima i pružnim prelazima[cite: 1].
* **Čekanje na polazak (Stanica B):** Vozovi čekaju u stanici ukoliko je dionica ispred njih zauzeta[cite: 1].
* **Završeno kretanje i skretanje (Stanica C):** Stanica C ima ulogu čvorišta za usmjeravanje vozova na odgovarajuće pravce[cite: 1].

| Bezbjedno zaustavljanje vozila i prolazak voza | Detaljan pregled statusa stanica |
|---|---|
| ![Kretanje kompozicije](assets/5.jpg) | ![Status stanice B](assets/4.jpg) |

### Deserijalizacija i istorija kretanja
Poseban prozor unutar aplikacije omogućava detaljan pregled istorije kretanja i telemetrije za svaku odabranu kompoziciju nakon obrade serijalizovanih datoteka[cite: 1].
![Istorija kretanja](assets/2.jpg)

---

## 💻 Tehnološki Stog i Alati

* **Jezik:** Java 17 (OpenJDK)
* **GUI Biblioteka:** JavaFX 17
* **Arhitekturalni koncepti:** Multithreading (`Thread`, `Runnable`), Sinhronizacija (`synchronized`, `Locks`), Java I/O i Serijalizacija[cite: 1].
* **Logovanje:** Integrisana `Logger` klasa za robusno upravljanje izuzecima u svim modulima[cite: 1].

---

## 🚀 Kako pokrenuti projekat lokalno

### Preduslovi
* Instaliran **Java 17 JDK**.
* Preuzet **JavaFX SDK 17**.

### Konfiguracija VM argumenata u VS Code
Da bi JavaFX moduli bili ispravno učitani, u vašem `.vscode/launch.json` fajlu dodajte putanju do vašeg lokalnog JavaFX SDK-a:

```json
"vmArgs": "--module-path /putanja/do/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml"
