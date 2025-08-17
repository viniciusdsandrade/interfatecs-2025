import java.util.Scanner;

import static java.lang.IO.println;
import static java.lang.System.in;

public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();

        int res = 0;
        for (int i = 1; i <= n; i++)
            res = (res + k) % i;

        println(res + 1);
    }
}
