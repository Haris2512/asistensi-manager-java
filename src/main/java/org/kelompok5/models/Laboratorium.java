package org.kelompok5.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Laboratorium {
    private ArrayList<Praktikan> daftarPraktikan = new ArrayList<>();
    private ArrayList<Asisten> daftarAsisten = new ArrayList<>();
    private ArrayList<Tugas> daftarTugas = new ArrayList<>();

    // TODO: Haris buat logika asistensi
    // cek kalau tanggal sekarang melebihi satu minggu dari deadline tugas maka
    // kurangi nilai asistensi 10%, dan jika melebihi dua minggu kurangi nilai 30%
    public void laksanakanAsistensi(Tugas tugas, Asisten asisten, Praktikan praktikan) {
        if (!asisten.praktikanAsuhan.contains(praktikan)) {
            System.out.println("Praktikan bukan merupakan asuhan asisten");
            return;
        }
        // Validasi bahwa tugas terdaftar di laboratorium sebelum melanjutkan
        if (!daftarTugas.contains(tugas)) {
            System.out.println("Tugas tidak terdaftar di laboratorium");
            return;
        }
        // TODO: cek apakah tugas telah dikerjakan oleh praktikan
        for (Asistensi asistensi : praktikan.getKartuKontrol().getRiwayatAsistensi()) {
            if (asistensi.getTugas().equals(tugas)) {
                System.out.println("Anda telah melakukan asistensi " + tugas.judul);
                return;
            }
        }
        // Membuat objek Asistensi dengan tanggal saat ini dan tugas yang diberikan
        Asistensi asistensi = new Asistensi(tugas, LocalDate.now());
        // Menambahkan asistensi ke kartu kontrol praktikan
        praktikan.getKartuKontrol().tambahAsistensi(asistensi);
        // Menampilkan konfirmasi asistensi yang berhasil dengan detail
        System.out.printf("Asistensi untuk tugas %s berhasil dilakukan oleh %s untuk %s.%n",
                tugas.judul, asisten.nama, praktikan.nama);
        System.out.printf("Status: %s, Nilai: %d%n",
                asistensi.getStatusAsistensi().toString(), asistensi.getNilaiAsistensi());
    }

    public void tambahDaftar(Praktikan praktikan) {
        if (daftarPraktikan.contains(praktikan)) {
            System.out.println("Praktikan sudah terdaftar");
            return;
        }

        daftarPraktikan.add(praktikan);
    }

    public void tambahDaftar(Asisten asisten) {
        if (daftarAsisten.contains(asisten)) {
            System.out.println("Asisten sudah terdaftar");
            return;
        }

        if (asisten.getNilai() < 80) {
            System.out.println("Nilai asisten tidak mencukupi untuk menjadi asisten, Minimum = 80");
            return;
        }

        daftarAsisten.add(asisten);
    }

    public void tambahDaftar(Tugas tugas) {
        if (daftarTugas.contains(tugas)) {
            System.out.println("Tugas sudah terdaftar");
            return;
        }

        daftarTugas.add(tugas);
    }

    public void tampilkanDaftarPraktikan() {
        System.out.println("\n=======================");
        System.out.println("   Daftar Praktikan   ");
        System.out.println("=======================");
        for (Praktikan praktikan : daftarPraktikan) {
            System.out.printf("%s. %s \n", praktikan.nim, praktikan.nama);
        }
        System.out.println("=======================");
    }

    public void tampilkanDaftarAsistenPraktikan() {
        System.out.println("\n=======================");
        System.out.println("   Daftar Praktikan   ");
        System.out.println("=======================");
        for (Praktikan praktikan : daftarPraktikan) {

            if (praktikan.getAsisten() == null) {
                System.out.printf("%s. %s. Belum memiliki asisten \n", praktikan.nim, praktikan.nama);
            } else {
                System.out.printf("%s. %s. Asuhan %s \n", praktikan.nim, praktikan.nama, praktikan.getAsisten().nama);
            }
        }
        System.out.println("=======================");
    }

    public void tampilkanDaftarAsisten() {
        System.out.println("\n=======================");
        System.out.println("   Daftar Asisten   ");
        System.out.println("=======================");
        for (Asisten asisten : daftarAsisten) {
            System.out.printf("%s. %s \n", asisten.nim, asisten.nama);
        }
        System.out.println("=======================");
    }

    public void tampilkanDaftarTugas() {
        System.out.println("\n=======================");
        System.out.println("   Daftar Tugas   ");
        System.out.println("=======================");
        for (Tugas tugas : daftarTugas) {
            System.out.println("-----------------------");
            System.out.println("Judul     : " + tugas.judul);
            System.out.println("Deskripsi : " + tugas.deskripsi);
            System.out.println("Deadline  : " + tugas.deadline);
            System.out.println("-----------------------");
        }
        System.out.println("=======================");
    }

    public boolean nimSudahTerdaftar(String nim) {
        for (Praktikan p : daftarPraktikan) {
            if (p.nim.equals(nim))
                return true;
        }
        for (Asisten a : daftarAsisten) {
            if (a.nim.equals(nim))
                return true;
        }
        return false;
    }

    public User cariUser(String nim, String password) {
        for (Praktikan p : daftarPraktikan) {
            if (p.nim.equals(nim) && getPassword(p).equals(password))
                return p;
        }
        for (Asisten a : daftarAsisten) {
            if (a.nim.equals(nim) && getPassword(a).equals(password))
                return a;
        }
        System.out.println("Login gagal: NIM atau password salah.");
        return null;
    }

    public Praktikan getPraktikanByNIM(String nim) {
        for (Praktikan praktikan : daftarPraktikan) {
            if (praktikan.nim.equals(nim)) {
                return praktikan;
            }
        }
        return null;
    }

    private String getPassword(User user) {
        try {
            java.lang.reflect.Field field = User.class.getDeclaredField("password");
            field.setAccessible(true);
            return (String) field.get(user);
        } catch (Exception e) {
            return "";
        }
    }

    public ArrayList<Asisten> getDaftarAsisten() {
        return daftarAsisten;
    }

    public ArrayList<User> getSemuaUser() {
        ArrayList<User> semua = new ArrayList<>();
        semua.addAll(daftarPraktikan);
        semua.addAll(daftarAsisten);
        return semua;
    }

    public Tugas getTugasByJudul(String judul) {
        for (Tugas tugas : daftarTugas) {
            if (tugas.judul.equalsIgnoreCase(judul)) {
                return tugas;
            }
        }
        return null;
    }

    public ArrayList<Tugas> getDaftarTugas() {
        return daftarTugas;
    }

}