import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Encoder encoder = new Encoder();
        System.out.println("""
                Введи:
                1-Зашифровать
                2-Расшифровать
                3-Метод грубой расшифровки""");
        Scanner scanner = new Scanner(System.in);
        String selectWorkMethod = scanner.nextLine();
        if (selectWorkMethod.equals("1")) encoder.encryptDecipher("1");
        else if (selectWorkMethod.equals("2")) encoder.encryptDecipher("2");
        else if (selectWorkMethod.equals("3")) encoder.encryptDecipher("3");
        else System.out.println("Введено не правильное число");
    }
}