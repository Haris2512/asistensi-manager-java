package org.kelompok5.models;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

// TODO : Haris
// - Buatkan attr dan method KartuKontrol
// - Buat behavior untuk menampilkan riwayat asistensi
public class KartuKontrol {
    private ArrayList<Asistensi> riwayatAsistensi;
    private Praktikan praktikan;

    public KartuKontrol(Praktikan praktikan) {
        this.praktikan = praktikan;
        this.riwayatAsistensi = new ArrayList<>();
    }

    public void tambahAsistensi(Asistensi asistensi) {
        for (Asistensi a : riwayatAsistensi) {
            if (a.equals(asistensi)) {
                System.out.println("Tugas " + asistensi.getTugas().judul + " sudah ada di riwayat");
                return;
            }
        }
        riwayatAsistensi.add(asistensi);
        System.out.println("Asistensi untuk tugas " + asistensi.getTugas().judul + " berhasil ditambahkan");
    }

    public void showInfo() {
        System.out.println("\n==============================");
        System.out.println("   Kartu Kontrol Praktikan   ");
        System.out.println("==============================");
        System.out.println("Nama Praktikan: " + praktikan.nama);
        System.out.println("NIM: " + praktikan.nim);
        if (praktikan.getAsisten() != null) {
            System.out.println("Asisten: " + praktikan.getAsisten().nama);
        } else {
            System.out.println("Belum memiliki asisten");
        }

        System.out.println("==============================");
        System.out.println("No   Tanggal            Nilai");
        System.out.println("==============================");

        if (riwayatAsistensi.isEmpty()) {
            System.out.println("     Belum ada asistensi");
        } else {
            int index = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID"));
            for (Asistensi asistensi : riwayatAsistensi) {
                String tanggalFormatted = asistensi.getTanggal().format(formatter);
                System.out.printf("%2d  %s      %d%n", index++, tanggalFormatted, asistensi.getNilaiAsistensi());
            }
        }
        System.out.println("==============================");
    }

    public ArrayList<Asistensi> getRiwayatAsistensi() {
        return riwayatAsistensi;
    }

    public Praktikan getPraktikan() {
        return praktikan;
    }
}