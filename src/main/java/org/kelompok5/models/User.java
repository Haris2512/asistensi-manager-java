package org.kelompok5.models;

public abstract class User {
    public String nama, nim;
    private String password;
    private double nilai;

    public User(String nama, String nim, String password, double nilai) {
        this.nama = nama;
        this.nim = nim;
        this.nilai = nilai;
        this.password = password;
    }

    // Abstract method (polymorphism)
    public abstract void showInfo();

    public void setNilai(double nilai) {
        this.nilai = nilai;
    }

    public double getNilai() {
        return nilai;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public abstract String getRole();
}
