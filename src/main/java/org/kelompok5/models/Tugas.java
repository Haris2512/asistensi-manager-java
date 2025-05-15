package org.kelompok5.models;

import java.time.LocalDate;

public class Tugas {
    public LocalDate deadline;
    public String judul, deskripsi;

    public Tugas(String judul, String deskripsi, LocalDate deadline) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
    }
}