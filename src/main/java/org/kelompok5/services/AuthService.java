package org.kelompok5.services;

import java.util.ArrayList;

import org.kelompok5.models.*;
import org.kelompok5.utils.JsonHelper;
import org.kelompok5.utils.Validator;

public class AuthService {
    private Validator validator;
    private Laboratorium laboratorium;
    private User loggedInUser;

    public AuthService(Laboratorium laboratorium) {
        this.laboratorium = laboratorium;
        this.validator = new Validator();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    // TODO: wahyu simpan data user ke file
    public void register() {
        System.out.println("\n=== REGISTER ===");
        String role = validator.inputString("Pilih Role (asisten/praktikan): ", "Role tidak valid",
                new String[] { "asisten", "praktikan" });
        String nama = validator.inputNama("Masukkan Nama: ", "Nama minimal 3 karakter");
        String nim = validator.inputNIM("Masukkan NIM: ", "Format NIM tidak valid");
        while (laboratorium.nimSudahTerdaftar(nim)) {
            System.out.println("ERROR: NIM sudah terdaftar!");
            nim = validator.inputNIM("Masukkan NIM: ", "Format NIM tidak valid");
        }
        String password = validator.inputPassword("Masukkan Password: ", "Password minimal 6 karakter");
        double nilai = validator.inputNilai("Masukkan Nilai (0-100): ", "Nilai harus antara 0 dan 100");

        if (role.equals("asisten")) {
            Asisten asisten = new Asisten(nama, nim, password, nilai);
            laboratorium.tambahDaftar(asisten);
        } else {
            System.out.println("\n--- Pilih Asisten Pembimbing ---");
            int index = 1;
            for (Asisten asisten : laboratorium.getDaftarAsisten()) {
                System.out.println(index + ". " + asisten.nama + " (NIM: " + asisten.nim + ")");
                index++;
            }

            ArrayList<Asisten> daftarAsisten = laboratorium.getDaftarAsisten();

            if (daftarAsisten.isEmpty()) {
                System.out.println("Tidak ada asisten tersedia.");
                return;
            }

            String inputPlaceholder = "Masukkan nomor asisten yang kamu pilih: ";
            int pilihan = validator.inputInt(inputPlaceholder, "Input tidak valid");

            // Validasi pilihan
            while (pilihan < 1 || pilihan > daftarAsisten.size()) {
                System.out.println("Asisten tidak ditemukan");
                pilihan = validator.inputInt(inputPlaceholder, "Input tidak valid");
            }

            Asisten asistenPembimbing = daftarAsisten.get(pilihan - 1);

            Praktikan praktikan = new Praktikan(nama, nim, password, nilai, asistenPembimbing);
            asistenPembimbing.tambahAsuhan(praktikan);
            laboratorium.tambahDaftar(praktikan);

        }

        JsonHelper.simpanDataUser(laboratorium);

        System.out.println("Registrasi berhasil!. Silahkan login \n");
    }

    public User login() {
        System.out.println("\n=== LOGIN ===");
        String nim = validator.inputString("Masukkan NIM: ", "NIM tidak boleh kosong");
        String password = validator.inputPassword("Masukkan Password: ", "Password minimal 6 karakter");

        User user = laboratorium.cariUser(nim, password);

        if (user != null) {
            System.out.println("Login berhasil! Selamat datang, " + user.nama);
            this.loggedInUser = user;
        } else {
            System.out.println("Login gagal! NIM atau password salah.");
        }

        return user;
    }

    public void logout() {
        this.loggedInUser = null;
    }
}
