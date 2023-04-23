import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

class Encoder {

    private static final String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";
    private static final StringBuilder readFileAsString = new StringBuilder();
    private static String filePath;

    public static void encryptDecipher(String start) throws Exception {
        Scanner scanner = new Scanner(System.in);
        StringBuilder result;
        saveText();
        if (start.equals("3")){
            result = bruteForce();
        }else {
            System.out.println("Введите ключ");
        }

        if (start.equals("1")){
            result = shiftsEachCharacterByKey(scanner.nextInt());
        }else {
            result = shiftsEachCharacterByKey(scanner.nextInt()*(-1));
        }
        createFileWithShiftedText(result);
    }

    private static void saveText() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу");

        filePath = scanner.nextLine();
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

    private static StringBuilder shiftsEachCharacterByKey(int key) {
        int alphabetLength = alphabet.length();
        if (key<0) alphabetLength = alphabetLength*(-1);
        StringBuilder shiftedText = new StringBuilder();
        for (int i = 0; i < readFileAsString.length(); i++) {
            char character = readFileAsString.charAt(i);
            if (alphabet.indexOf(character) == -1) {
                shiftedText.append(character);
            } else if (alphabet.indexOf(character) + key >= alphabet.length() || (alphabet.indexOf(character) + key < 0)) {
                shiftedText.append(alphabet.charAt(alphabet.indexOf(character) + key - alphabetLength));
            } else {
                shiftedText.append(alphabet.charAt(alphabet.indexOf(character) + key));
            }
        }
        return shiftedText;
    }

    private static void createFileWithShiftedText(StringBuilder shiftedText) throws Exception {
        try {
            String[] strings = filePath.split(".txt");
            strings[0] += ".result.txt";
            filePath = strings[0];
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            Files.createFile(path);
            Files.writeString(path, shiftedText);
        } catch (IOException e) {
            throw new Exception(e);
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
            if (key < alphabet.length()) {
                temp = shiftsEachCharacterByKey( key);
                key++;
            } else {
                System.out.println("Расшифровка не удалась");
                break;
            }
        } while (checkTextOnFalse(temp));
        return shiftsEachCharacterByKey( key - 1);
    }
}
