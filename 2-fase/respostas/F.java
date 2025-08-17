import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.IO.print;
import static java.lang.Long.parseLong;
import static java.lang.Math.*;
import static java.lang.String.format;
import static java.lang.System.in;

public class F {
    static double solveHS(long ND) {
        if (ND <= 1L) return 1.0;
        final int PREC = 100;
        Apfloat nd = new Apfloat(ND, PREC);
        Apfloat ln = ApfloatMath.log(nd);
        Apfloat w = ApfloatMath.w(ln);
        Apfloat hs = ApfloatMath.exp(w);
        return hs.doubleValue();
    }

    static String fmt(double minutes) {
        long totalMs = round(minutes * 60000.0);
        long mm = totalMs / 60000;
        int ss = (int) ((totalMs % 60000) / 1000);
        int ms = (int) (totalMs % 1000);
        return format("%d:%02d:%03d", mm, ss, ms);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        for (String line; (line = br.readLine()) != null; ) {
            line = line.trim();
            if (line.isEmpty()) continue;
            long ND = parseLong(line);
            if (ND == 0) break;
            double hs = solveHS(ND);
            sb.append(fmt(hs)).append('\n');
        }
        print(sb);
    }
}
