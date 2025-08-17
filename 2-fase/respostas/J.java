

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import static java.lang.IO.println;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readString;
import static java.util.Arrays.sort;

public class J {
    public static void main(String[] args) throws Exception {
        Path json = resolveJsonPath(args);

        String payload = readString(json);

        ObjectMapper om = new ObjectMapper();
        List<TestCase> cases = om.readValue(payload, new TypeReference<>() {
        });

        for (TestCase tc : cases) {
            if (tc.t == null) throw new IllegalArgumentException("Campo t ausente");
            if (tc.N != tc.t.length) {
                tc.N = tc.t.length;
            }
            int ans = minGroups(tc.D, tc.t);
            println(ans);
        }
    }

    static Path resolveJsonPath(String[] args) throws IOException {
        if (args != null && args.length > 0 && args[0] != null && !args[0].isBlank()) {
            return Path.of(args[0]).toRealPath();
        }

        // Fallback: procurar ascendendo por 2-fase/respostas/inputs/j-inputs.json
        Path rel = Path.of("2-fase", "respostas", "inputs", "j-inputs.json");
        Path cwd = Path.of("").toAbsolutePath().normalize();
        for (Path p = cwd; p != null; p = p.getParent()) {
            Path candidate = p.resolve(rel).normalize();
            if (exists(candidate)) return candidate.toRealPath();
        }
        throw new NoSuchFileException("Não encontrei o JSON: " + rel + " (buscado a partir de " + cwd + ")");
    }

    // ===== solução do problema (ordenar + guloso) =====
    static int minGroups(long D, long[] t) {
        sort(t);
        int groups = 0, i = 0, n = t.length;
        while (i < n) {
            long base = t[i];
            while (i < n && t[i] - base <= D) i++;
            groups++;
        }
        return groups;
    }

    // ===== DTO simples para o JSON =====
    static class TestCase {
        public int N;
        public long D;
        public long[] t;

        public TestCase() {
        }
    }
}
