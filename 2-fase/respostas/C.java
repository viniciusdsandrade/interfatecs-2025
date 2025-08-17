import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.IO.println;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class C {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long xj = parseLong(st.nextToken());
        long yj = parseLong(st.nextToken());
        long R  = parseLong(st.nextToken());
        long r2 = R * R;

        int N = parseInt(br.readLine().trim());
        long count = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long x = parseLong(st.nextToken());
            long y = parseLong(st.nextToken());
            long dx = x - xj;
            long dy = y - yj;
            long d2 = dx*dx + dy*dy;
            if (d2 <= r2) count++;
        }

       println(count);
    }
}
