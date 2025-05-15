package org.kelompok5.models;

public class Praktikan extends User {
    private KartuKontrol kartuKontrol;
    private Asisten asisten;

    public Praktikan(String nama, String nim, String password, double nilai,
            Asisten asisten) {
        super(nama, nim, password, nilai);
        this.setAsisten(asisten);
        this.kartuKontrol = new KartuKontrol(this);
    }

    public Praktikan(String nama, String nim, String password, double nilai) {
        super(nama, nim, password, nilai);
        this.kartuKontrol = new KartuKontrol(this);
    }

    public void setAsisten(Asisten asisten) {
        this.asisten = asisten;
    }

    public Asisten getAsisten() {
        return asisten;
    }

    public void tampilkanKartuKontrol() {
        kartuKontrol.showInfo();
    }

    @Override
    public void showInfo() {
        System.out.println("=======================");
        System.out.println("   Detail Praktikan   ");
        System.out.println("=======================");
        System.out.println("Nama  : " + this.nama);
        System.out.println("Nim   : " + this.nim);
        System.out.println("Nilai : " + this.getNilai());
        System.out.println("=======================");
    }

    public KartuKontrol getKartuKontrol() {
        return kartuKontrol;
    }

    @Override
    public String getRole() {
        return "Praktikan";
    }
}