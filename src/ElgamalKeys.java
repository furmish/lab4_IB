import java.math.BigInteger;
import java.util.Scanner;
/*Абстрактный класс содержащий в себе ключи, необходимые для класса ElgamalCipher*/
public abstract class ElgamalKeys {
    protected static BigInteger p, a, x, k, y;

    /**
     * Метод инициализации ключей с консоли с оформленным консольным выводом сообщений.
     */
    protected static void initKeys() {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Введите ключи:
                p - простое число, где p >= 1500 (размер алфавита)
                a ∈ [2; p - 1]
                x ∈ [2; p - 2]
                k - ваше случайное число, k ∈ [2; p - 2]""");
        System.out.print("p = ");
        p = new BigInteger(sc.next());
        while (!p.isProbablePrime(101)) {
            System.out.println("Возможно ваше число не простое. Попробуйте еще раз.");
            System.out.print("p = ");
            p = new BigInteger(sc.next());
        }
        while (!(p.intValue() >= 1103)) {
            System.out.println("Возможно ваше число меньше допустимого. Попробуйте еще раз.");
            System.out.print("p = ");
            p = new BigInteger(sc.next());
        }
        System.out.print("a = ");
        a = new BigInteger(sc.next());
        while (!(a.intValue() >= 2 && a.intValue() <= p.intValue() - 1)) {
            System.out.println("Возможно ваше число не в диапазоне [2; p - 1]. Попробуйте еще раз.");
            System.out.print("a = ");
            a = new BigInteger(sc.next());
        }
        System.out.print("x = ");
        x = new BigInteger(sc.next());
        while (!(x.intValue() >= 2 && x.intValue() <= p.intValue() - 2)) {
            System.out.println("Возможно ваше число не в диапазоне [2; p - 2]. Попробуйте еще раз.");
            System.out.print("x = ");
            x = new BigInteger(sc.next());
        }
        System.out.print("k = ");
        k = new BigInteger(sc.next());
        while (!(k.intValue() >= 2 && k.intValue() <= p.intValue() - 2)) {
            System.out.println("Возможно ваше число не в диапазоне [2; p - 2]. Попробуйте еще раз.");
            System.out.print("k = ");
            k = new BigInteger(sc.next());
        }
        System.out.printf("y = %s\n", y = a.modPow(x, p));
    }
}
