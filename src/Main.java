import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введи:\n"+"1-Зашифровать\n"+"2-Расшифровать");
        Scanner scanner = new Scanner(System.in);
        String start = scanner.nextLine();
        if (start.equals("1")) Encoder.encrypt();
        else if (start.equals("2")) Encoder.decipher();
        else System.out.println("Введено не правильное число");
    }

}


class Encoder {

    private static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";
    private static  StringBuilder readFileAsString = new StringBuilder();

    public static  void encrypt(){
        Encoder.saveText();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ключ");
        StringBuilder result = Encoder.ShiftsEachCharacterByKey(Encoder.readFileAsString, scanner.nextInt());
        Encoder.createFileWithShiftedText(result);
    }

    public static void decipher (){
        Encoder.saveText();
        StringBuilder result = Encoder.bruteForce();
        Encoder.createFileWithShiftedText(result);
    }

    private static void saveText() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу");

        String filePath = scanner.nextLine();
        while (filePath == null || filePath.isEmpty() || Files.notExists(Paths.get(filePath))) {
            System.out.println("Файл не найден");
            filePath = scanner.nextLine();
        }
        File file = new File(filePath);
        try (Scanner scannerFile = new Scanner(file)) {
            while (scannerFile.hasNextLine()) {
                readFileAsString.append(scannerFile.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder ShiftsEachCharacterByKey(StringBuilder text, int key) {
        StringBuilder shiftedText = new StringBuilder();
        for (int i = 0; i < readFileAsString.length(); i++) {
            char character = readFileAsString.charAt(i);
            if (alphabet.indexOf(character) == -1) {
                shiftedText.append(character);
            } else if (alphabet.indexOf(character) + key >= alphabet.length()) {
                shiftedText.append(Character.toString(alphabet.charAt(alphabet.indexOf(character) + key - alphabet.length())));
            } else {
                shiftedText.append(Character.toString(alphabet.charAt(alphabet.indexOf(character) + key)));
            }
        }
        return shiftedText;
    }

    private static void createFileWithShiftedText(StringBuilder shiftedText) {
        try {
            Path path = Paths.get("result.txt");
            if (Files.exists(path)) {
                Files.delete(path);
            }

            Files.createFile(path);
            Files.writeString(path, shiftedText);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static boolean checkTextOnFalse(StringBuilder text) {
        int pathTextOrFullText = Math.min(text.length(), 102);
        for (int i = 1; i < pathTextOrFullText - 2; i++) {
            if (text.charAt(i) == '.' && text.charAt(i + 1) == ' ' && Character.isUpperCase(text.charAt(i + 2)))
                return false;
        }
        return true;
    }

    private static StringBuilder bruteForce() {
        int key = 0;
        StringBuilder temp;
        do {
            if (key < 74) {
                temp = ShiftsEachCharacterByKey(readFileAsString, key);
                key++;
            } else {
                System.out.println("Расшифровка не удалась");
                break;
            }
        } while (checkTextOnFalse(temp));
        return ShiftsEachCharacterByKey(readFileAsString,key-1);
    }
}