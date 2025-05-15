package org.kelompok5.utils;

import java.util.Arrays;
import java.util.Scanner;

// TODO: Indira buat method input tipe data lain pelajari cara pakai method di App.java
public class Validator {
    private Scanner scanner = new Scanner(System.in);

    public String inputString(String placeholder, String pesanError) {
        System.out.print(placeholder);
        String string = scanner.nextLine();

        while (string.trim().isEmpty()) {
            System.out.println("ERROR: " + pesanError);
            System.out.print(placeholder);
            string = scanner.nextLine();
        }
        return string;
    }

    public String inputString(String placeholder, String pesanError, String[] validInput) {
        System.out.print(placeholder);
        String input = scanner.nextLine().trim();

        while (true) {
            if (input.isEmpty()) {
                System.out.println("ERROR: " + pesanError);
            } else if (validInput != null && validInput.length > 0 && !Arrays.asList(validInput).contains(input)) {
                System.out.println("ERROR: " + pesanError);
                System.out.println("Pilihan valid: " + String.join(", ", validInput));
            } else {
                return input;
            }

            System.out.print(placeholder);
            input = scanner.nextLine().trim();
        }
    }

    public int inputInt(String placehorder, String pesanError) {
        System.out.print(placehorder);

        while (!scanner.hasNextInt()) {
            System.out.println("Error: " + pesanError);
            System.out.print(placehorder);
            scanner.nextInt(); // buang input invalid
        }
        return scanner.nextInt();
    }

    public String inputNama(String placeholder, String pesanError) {
        String nama = inputString(placeholder, pesanError);
        while (nama.length() < 3) {
            System.out.println("ERROR: " + pesanError);
            nama = inputString(placeholder, pesanError);
        }
        return nama;
    }

    public String inputNIM(String placeholder, String pesanError) {
        String nim = inputString(placeholder, pesanError);
        while (!nim.matches("^[A-Z]\\d{9}$")) {
            System.out.println("ERROR: " + pesanError);
            nim = inputString(placeholder, pesanError);
        }
        return nim;
    }

    public double inputNilai(String placeholder, String pesanError) {
        double nilai = -1;
        while (true) {
            String input = inputString(placeholder, pesanError);
            try {
                nilai = Double.parseDouble(input);
                if (nilai >= 0 && nilai <= 100) {
                    break;
                } else {
                    System.out.println("ERROR: " + pesanError);
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: " + pesanError);
            }
        }
        return nilai;
    }

    public String inputPassword(String placeholder, String pesanError) {
        String password = inputString(placeholder, pesanError);
        while (password.length() < 6) {
            System.out.println("ERROR: " + pesanError);
            password = inputString(placeholder, pesanError);
        }
        return password;
    }

}