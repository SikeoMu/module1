import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

class  Encoder{

    private static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";
    private static  StringBuilder readFileAsString = new StringBuilder();

    public static  void encrypt(){
        saveText();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ключ");
        StringBuilder result = ShiftsEachCharacterByKey(readFileAsString, scanner.nextInt());
        createFileWithShiftedText(result);
    }

    public static void decipher (){
        saveText();
        StringBuilder result = bruteForce();
        createFileWithShiftedText(result);
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
