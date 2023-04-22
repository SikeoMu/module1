import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        System.out.println("Введи:\n"+"1-Зашифровать\n"+"2-Расшифровать");
        Scanner scanner = new Scanner(System.in);
        String start = scanner.nextLine();
        if (start.equals("1")) encoder.encrypt();
        else if (start.equals("2")) encoder.decipher();
        else System.out.println("Введено не правильное число");
    }

}