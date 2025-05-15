package org.kelompok5.utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.kelompok5.models.Asisten;
import org.kelompok5.models.Laboratorium;
import org.kelompok5.models.Praktikan;
import org.kelompok5.models.Tugas;
import org.kelompok5.models.User;

public class JsonHelper {

    static JSONParser jsonParser = new JSONParser();

    public static void loadDataUser(String filePath, Laboratorium laboratorium) {
        try (FileReader reader = new FileReader(filePath)) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                String nama = (String) jsonObject.get("nama");
                String nim = (String) jsonObject.get("nim");
                String password = (String) jsonObject.get("password");
                double nilai = (double) jsonObject.get("nilai");
                String peran = (String) jsonObject.get("peran");

                if (peran.equals("Asisten")) {
                    JSONArray asuhanArray = (JSONArray) jsonObject.get("praktikanAsuhan");

                    Asisten asisten = new Asisten(nama, nim, password, nilai);
                    for (Object asuhanObject : asuhanArray) {
                        JSONObject praktikanObject = (JSONObject) asuhanObject;

                        String namaPraktikan = (String) praktikanObject.get("nama");
                        String nimPraktikan = (String) praktikanObject.get("nim");
                        String passwordPraktikan = (String) praktikanObject.get("password");
                        double nilaiPraktikan = (double) praktikanObject.get("nilai");

                        Praktikan praktikan = new Praktikan(namaPraktikan, nimPraktikan, passwordPraktikan,
                                nilaiPraktikan, asisten);

                        praktikan.setAsisten(asisten);

                        asisten.tambahAsuhan(praktikan);
                        laboratorium.tambahDaftar(praktikan);
                    }

                    laboratorium.tambahDaftar(asisten);
                } else {
                    Praktikan praktikan = new Praktikan(nama, nim, password, nilai);

                    laboratorium.tambahDaftar(praktikan);
                }

            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void loadDataTugas(String filePath, Laboratorium laboratorium) {
        try (FileReader reader = new FileReader(filePath)) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                String judul = (String) jsonObject.get("judul");
                String deskripsi = (String) jsonObject.get("deskripsi");
                String deadlineStr = (String) jsonObject.get("deadline");

                LocalDate deadline = LocalDate.parse(deadlineStr);

                Tugas tugas = new Tugas(judul, deskripsi, deadline);

                laboratorium.tambahDaftar(tugas);
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat data tugas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // ⬅️ tambahkan ini
    public static void simpanDataUser(Laboratorium laboratorium) {
        JSONArray jsonArray = new JSONArray();

        for (User user : laboratorium.getSemuaUser()) {

            JSONObject userObject = new JSONObject(); // ⬅️ juga bisa ditaruh di sini

            userObject.put("nama", user.nama);
            userObject.put("nim", user.nim);
            userObject.put("password", user.getPassword());
            userObject.put("nilai", user.getNilai());

            if (user instanceof Asisten) {
                Asisten asisten = (Asisten) user;
                userObject.put("peran", "Asisten");

                JSONArray asuhanArray = new JSONArray();

                for (Praktikan praktikan : asisten.getPraktikanAsuhan()) {
                    JSONObject praktikanObj = new JSONObject();

                    praktikanObj.put("nama", praktikan.nama);
                    praktikanObj.put("nim", praktikan.nim);
                    praktikanObj.put("password", praktikan.getPassword());
                    praktikanObj.put("nilai", praktikan.getNilai());

                    asuhanArray.add(praktikanObj);
                }

                userObject.put("praktikanAsuhan", asuhanArray);
            } else {
                userObject.put("peran", "Praktikan");
            }

            jsonArray.add(userObject);
        }

        try (FileWriter file = new FileWriter("src/main/resources/Users.json")) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (Exception e) {
            System.err.println("Gagal menyimpan data user: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void simpanDataTugas(List<Tugas> daftarTugas) {
        JSONArray jsonArray = new JSONArray();

        final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Tugas tugas : daftarTugas) {
            JSONObject tugasObject = new JSONObject();
            tugasObject.put("judul", tugas.judul);
            tugasObject.put("deskripsi", tugas.deskripsi);
            tugasObject.put("deadline", tugas.deadline.format(DATE_FORMATTER));
            jsonArray.add(tugasObject);
        }

        try (FileWriter file = new FileWriter("src/main/resources/Tugas.json")) {
            file.write(jsonArray.toJSONString());
            file.flush();
            System.out.println("Data tugas berhasil disimpan ke " + "src/main/resources/Tugas.json");
        } catch (Exception e) {
            System.err.println("Gagal menyimpan data tugas: " + e.getMessage());
        }
    }

}
