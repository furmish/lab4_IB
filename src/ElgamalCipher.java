import java.math.BigInteger;
import java.util.Scanner;
/*Класс шифрования и дешифрования по схеме Эль-Гамаля*/
public class ElgamalCipher extends ElgamalKeys {
    private static BigInteger[] cipherCode;
    private static BigInteger[] openTextCode;

    /**
     * Главный метод запуска алгоритма шифрования/дешифрования,
     * содержащий в себе алгоритм взаимодействия с пользователем
     * через консоль
     */
    public static void start() {
        askQuestion();
        String userAnswer;
        Scanner sc = new Scanner(System.in);
        while (!(userAnswer = sc.next()).equals("0")) {
            if (userAnswer.equals("1")) {
                initKeys();
                System.out.println("Ваш текст для шифрования: ");
                encryptText();
                askQuestion();
            }
            if (userAnswer.equals("2")) {
                if (p != null) {
                    System.out.println("1 - Расшифровать ранее зашифрованный текст\n2 - Расшифровать другой текст с другими ключами");
                    if (sc.next().equals("1")) {
                        decryptText();
                        askQuestion();
                        continue;
                    }
                }
                initKeys();
                System.out.println("Ваш закодированный текст для дешифрования: ");
                cipherCode = null;
                decryptText();
                askQuestion();
            }
        }
    }

    /**
     * Метод шифрования по схеме Эль-Гамаля.
     * Шифрует каждый элемент массива кодов cipherCode, изменяя их
     * по формуле cipherCode[i] = openTextCode[i] * y^k mod(p) <=> δ = m * y^k mod(p),
     * полученный результат записывается в массив cipherCode и является в нашем случае δ.
     * Метод выводит результат на экран.
     */
    private static void encryptText() {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        String openText = sc.nextLine();
        openTextCode = openText.codePoints().mapToObj(BigInteger::valueOf).toArray(BigInteger[]::new);
        cipherCode = new BigInteger[openTextCode.length];
        for (int i = 0; i < cipherCode.length; i++) {
            cipherCode[i] = openTextCode[i].multiply(y.modPow(k, p));
        }
        System.out.print("Ваша зашифрованная строка в виде кода:\n");
        for (BigInteger code : cipherCode) {
            sb.append(code).append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    /**
     * Метод дешифрования по схеме Эль-Гамаля.
     * Вычисляет значение γ по формуле γ = a^k mod(p) и записывает результат в переменную g
     * Дешифрует каждый элемент массива зашифрованных кодов cipherCode, изменяя их по формуле
     * openTextCode[i] = (cipherCode[i] * g^(p - 1 - x)) mod(p) <=> m = δ * γ^(p - 1 -x) mod(p),
     * полученный результат перезаписывается в массив openTextCode и является в нашем случае m.
     * Метод выводит результат на экран.
     */
    private static void decryptText() {
        if (cipherCode == null) {
            Scanner sc = new Scanner(System.in);
            String[] codes = sc.nextLine().trim().split("\\s");
            cipherCode = new BigInteger[codes.length];
            for (int i = 0; i < codes.length; i++) {
                cipherCode[i] = new BigInteger(codes[i]);
            }
        }
        BigInteger g = a.modPow(k, p);
        for (int i = 0; i < cipherCode.length; i++) {
            openTextCode[i] = cipherCode[i].multiply(g.pow(p.subtract(BigInteger.valueOf(1)).subtract(x).intValue())).mod(p);
        }
        System.out.println("Расшифрованная строка:");
        for (BigInteger bi : openTextCode) {
            System.out.print((char) bi.intValue());
        }
        System.out.println();
    }

    /**
     * Метод, который выводит в консоль вопрос "Что вы хотите сделать? 1 - зашифровать, 2 - расшифровать, 0 - выход"
     */
    private static void askQuestion() {
        System.out.println("\nЧто вы хотите сделать?\n1 - зашифровать\n2 - расшифровать\n0 - выход");
    }
}
