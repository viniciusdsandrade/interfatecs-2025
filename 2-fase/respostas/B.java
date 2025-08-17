import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.newBufferedReader;

public class B {

    /*
    Beatriz and Artur are siblings, and they usually do a lot of things together. Sometimes, when they have
    different preferences, they play Rock, Paper, Scissors—known in Brazil as Jokenpo—to decide.
    Since they are part of a school group focused on technology, they came up with an idea: to write a computer
    program to play the game for them. Typical laziness!
    They started by creating a random generator to simulate the game turns. This generator uses an asterisk (*)
    for Rock, a capital letter O for Paper, and a capital letter V for Scissors.
    They’re stuck. You’re not. Time to step in, write the rest, and bring this game to life—before their laziness
    takes over!

    Input
    The input consists of a single test case with multiple lines, each containing two characters separated by a
    blank space. The first character represents Beatriz’s move, and the second represents Artur’s. All characters
    are guaranteed to be valid: "*"for Rock, "O"for Paper, and "V"for Scissors. The input ends with two hyphens
    separated by a blank space: - -".

    Output
    The program must print either "BEATRIZ WIN"or "ARTUR WIN", depending on who has more points.
    Since the data is randomly generated, a tie is possible. In that case, the program must output the word:
    "TIE". Important: all output must be in capital letters, must not include quotation marks, and don’t forget
    the line break at the end.

    Exemplo de Entrada 1
    V O
    * *
    O V
    O *
    - -
    Exemplo de Saída 1
    BEATRIZ WIN

    Exemplo de Entrada 2
    * V
    V O
    O *
    * O
    V *
    O V
    - -
    Exemplo de Saída 2
    TIE
     */


    enum Move {
        ROCK('*'), PAPER('O'), SCISSORS('V');

        final char code;

        Move(char code) {
            this.code = code;
        }

        static Move from(char c) {
            return switch (c) {
                case '*' -> ROCK;
                case 'O' -> PAPER;
                case 'V' -> SCISSORS;
                default -> throw new IllegalArgumentException("movimento inválido: " + c);
            };
        }

        int vs(Move other) {
            if (this == other) return 0;
            int a = ordinal(), b = other.ordinal();
            int diff = (a - b + 3) % 3;
            return (diff == 1) ? +1 : -1;
        }
    }

    record CaseData(List<String> roundsBA, boolean hasSentinel) {
    }

    static Path resolveJsonPath(String[] args) throws IOException {
        if (args != null && args.length > 0 && !args[0].isBlank()) {
            return Path.of(args[0]).toRealPath();
        }
        Path rel = Path.of("2-fase", "respostas", "inputs", "b-inputs.json");
        Path cwd = Path.of("").toAbsolutePath().normalize();
        for (Path p = cwd; p != null; p = p.getParent()) {
            Path candidate = p.resolve(rel).normalize();
            if (exists(candidate)) return candidate.toRealPath();
        }
        throw new NoSuchFileException("Não encontrei o JSON: " + rel);
    }

    static String normalizeRound(JsonNode node) {
        if (node.isTextual()) return node.asText();
        if (node.isArray() && node.size() >= 2) return node.get(0).asText() + " " + node.get(1).asText();
        if (node.isObject()) {
            String b = node.path("b").asText(null);
            String a = node.path("a").asText(null);
            if (b != null && a != null) return b + " " + a;
        }
        return null;
    }

    static CaseData readCase(JsonNode node) {
        List<String> out = new ArrayList<>();
        boolean saw = false;

        if (node.isArray()) {
            for (JsonNode el : node) {
                String s = normalizeRound(el);
                if (s == null || s.isBlank()) continue;
                if (isSentinelLine(s)) {
                    saw = true;
                    break;
                }
                out.add(s);
            }
            return new CaseData(out, saw);
        }

        if (node.isObject()) {
            for (String key : new String[]{"rounds", "lines", "moves"}) {
                JsonNode arr = node.get(key);
                if (arr != null && arr.isArray()) return readCase(arr);
            }
            String s = normalizeRound(node);
            if (s != null) out.add(s);
            return new CaseData(out, false);
        }

        throw new IllegalArgumentException("JSON de entrada não suportado para um caso.");
    }

    static List<CaseData> readAllCases(JsonNode root) {
        List<CaseData> cases = new ArrayList<>();

        if (root.isArray()) {
            for (JsonNode c : root) cases.add(readCase(c));
            return cases;
        }

        if (root.isObject()) {
            JsonNode casesNode = root.get("cases");
            if (casesNode != null && casesNode.isArray()) {
                for (JsonNode c : casesNode) cases.add(readCase(c));
                return cases;
            }
            for (Iterator<String> it = root.fieldNames(); it.hasNext(); ) {
                String name = it.next();
                JsonNode v = root.get(name);
                if (v != null && v.isArray()) {
                    for (JsonNode c : v) cases.add(readCase(c));
                    if (!cases.isEmpty()) return cases;
                }
            }
            cases.add(readCase(root));
            return cases;
        }

        throw new IllegalArgumentException("JSON de entrada não suportado.");
    }

    static boolean isSentinelLine(String s) {
        String[] p = s.trim().split("\\s+");
        return p.length >= 2 && p[0].equals("-") && p[1].equals("-");
    }

    static String decideWinner(CaseData data) {
        if (!data.hasSentinel) return "INVALID";

        int beatriz = 0, artur = 0;
        for (String line : data.roundsBA) {
            String[] p = line.split("\\s+");
            if (p.length < 2) continue;
            Move b = Move.from(p[0].charAt(0));
            Move a = Move.from(p[1].charAt(0));
            int r = b.vs(a);
            if (r > 0) beatriz++;
            else if (r < 0) artur++;
        }
        if (beatriz > artur) return "BEATRIZ WIN";
        if (artur > beatriz) return "ARTUR WIN";
        return "TIE";
    }

    public static void main(String[] args) throws Exception {
        Path path = resolveJsonPath(args);
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(newBufferedReader(path));
        List<CaseData> cases = readAllCases(root);

        StringBuilder out = new StringBuilder();
        for (CaseData c : cases) out.append(decideWinner(c)).append('\n');
        System.out.print(out);
    }
}

