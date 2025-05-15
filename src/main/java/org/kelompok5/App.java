package org.kelompok5;

import java.time.LocalDate;

import org.kelompok5.models.Asisten;
import org.kelompok5.models.Laboratorium;
import org.kelompok5.models.Praktikan;
import org.kelompok5.models.Tugas;
import org.kelompok5.models.User;
import org.kelompok5.services.AuthService;
import org.kelompok5.utils.JsonHelper;
import org.kelompok5.utils.Validator;

public class App {
    static Validator validator = new Validator();
    static Laboratorium laboratorium = new Laboratorium();
    static AuthService authService = new AuthService(laboratorium);

    public static void main(String[] args) {

        JsonHelper.loadDataUser("src/main/resources/Users.json", laboratorium);
        JsonHelper.loadDataTugas("src/main/resources/Tugas.json", laboratorium);

        runApp();
    }

    public static void runApp() {
        System.out.println("\n=====================================");
        System.out.println(" Selamat Datang Di Asistensi Manager ");
        System.out.println("=====================================");
        System.out.println("Pilih Menu :");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Berhenti");
        String[] validMenu = { "1", "2", "3" };
        System.out.println("-------------------------------------");
        String menu = validator.inputString("Masukkan Pilihan: ", "Tolong pilih menu", validMenu);

        switch (menu) {
            case "1":
                User user = authService.login();

                if (user != null) {
                    if (user instanceof Asisten) {
                        menuAsisten();
                    } else if (user instanceof Praktikan) {
                        menuPraktikan();
                    }
                }
                break;
            case "2":
                authService.register();
                runApp();
                break;
            case "3":
                System.exit(0);
        }
    }

    private static void menuAsisten() {
        while (true) {
            System.out.println("\n=== MENU ASISTEN ===");
            System.out.println("1. Daftar praktikan asuhan");
            System.out.println("2. Tambahkan praktikan asuhan");
            System.out.println("3. Tambahkan tugas");
            System.out.println("4. Tampilkan daftar tugas");
            System.out.println("5. Logout");
            String[] validMenu = { "1", "2", "3", "4", "5" };
            String pilih = validator.inputString("Pilih: ", "Pilihan tidak valid", validMenu);

            switch (pilih) {
                case "1":
                    ((Asisten) authService.getLoggedInUser()).tampilkanDaftarPraktikanAsuhan();
                    break;
                case "2":

                    break;
                case "3":
                    menuTambahkanTugas();
                    runApp();
                    break;
                case "4":
                    laboratorium.tampilkanDaftarTugas();
                    menuAsisten();
                    break;
                case "5":
                    authService.logout();
                    runApp();
                    return;
            }
        }
    }

    private static void menuPraktikan() {
        while (true) {
            System.out.println("\n=== MENU PRAKTIKAN ===");
            System.out.println("1. Tampilkan info saya");
            System.out.println("2. Tampilkan kartu kontrol");
            System.out.println("3. Tampilkan daftar tugas");
            System.out.println("4. Asistensi tugas praktikum");
            System.out.println("5. Logout");
            String[] validMenu = { "1", "2", "3", "4", "5" };
            String pilih = validator.inputString("Pilih: ", "ERROR: Pilihan tidak valid", validMenu);

            switch (pilih) {
                case "1":
                    authService.getLoggedInUser().showInfo();
                    break;
                case "2":
                    ((Praktikan) authService.getLoggedInUser()).tampilkanKartuKontrol();
                    break;
                case "3":
                    laboratorium.tampilkanDaftarTugas();
                    menuPraktikan();
                    break;
                case "4":
                    menuAsistensiTugas();
                    menuPraktikan();
                    break;
                case "5":
                    authService.logout();
                    runApp();
                    return;
            }
        }
    }

    private static void menuTambahkanTugas() {
        System.out.println("=======================");
        System.out.println("   Menu Input Tugas    ");
        System.out.println("=======================");

        String judul = validator.inputString(
                "Masukkan judul tugas: ",
                "Judul tidak boleh kosong");

        String deskripsi = validator.inputString(
                "Masukkan deskripsi tugas: ",
                "Deskripsi tidak boleh kosong");

        LocalDate deadline = null;
        while (deadline == null) {
            try {
                String tanggalStr = validator.inputString(
                        "Masukkan deadline tugas (format: YYYY-MM-DD): ",
                        "Format tanggal tidak valid");
                deadline = LocalDate.parse(tanggalStr);

                if (deadline.isBefore(LocalDate.now())) {
                    System.out.println("ERROR: Deadline tidak boleh sebelum hari ini");
                    deadline = null;
                }
            } catch (Exception e) {
                System.out.println("ERROR: Format tanggal tidak valid. Gunakan format YYYY-MM-DD");
            }
        }

        Tugas tugasBaru = new Tugas(judul, deskripsi, deadline);

        System.out.println("Tugas berhasil ditambahkan!");
        laboratorium.tambahDaftar(tugasBaru);

        JsonHelper.simpanDataTugas(laboratorium.getDaftarTugas());

        menuAsisten();
    }

    private static void menuAsistensiTugas() {

        Praktikan praktikan = (Praktikan) authService.getLoggedInUser();

        if (praktikan.getAsisten() == null) {
            System.out.println("Anda belum memiliki asisten");
            return;
        }

        laboratorium.tampilkanDaftarTugas();

        String judulTugas = validator.inputString("Ketik Judul Tugas: ", "Input Tidak Valid");

        Tugas tugas = laboratorium.getTugasByJudul(judulTugas);

        if (tugas == null) {
            System.out.println("Tugas tidak ditemukan");
        } else {

            laboratorium.laksanakanAsistensi(tugas, praktikan.getAsisten(), praktikan);
        }

    }

}
