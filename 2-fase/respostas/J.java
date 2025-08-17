import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.function.Supplier;

import static java.lang.IO.println;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.in;
import static java.util.Arrays.sort;

public class J {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final StringTokenizer[] st = {null};
        // função local para obter próximo token (sem classe dedicada)
        Supplier<String> next = () -> {
            try {
                while (st[0] == null || !st[0].hasMoreTokens()) {
                    String line = br.readLine();
                    if (line == null) return null;
                    st[0] = new StringTokenizer(line);
                }
                return st[0].nextToken();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };

        int N = parseInt(next.get());
        long D = parseLong(next.get());
        long[] t = new long[N];
        for (int i = 0; i < N; i++) t[i] = parseLong(next.get());

        sort(t);

        int grupos = 0;
        int i = 0;
        while (i < N) {
            long base = t[i];
            while (i < N && t[i] - base <= D) i++;
            grupos++;
        }
        println(grupos);
    }
}
