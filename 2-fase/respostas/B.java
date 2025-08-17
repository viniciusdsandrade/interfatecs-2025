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


    private static int toIdx(char c) {
        return switch (c) {
            case '*' -> 0; // pedra
            case 'O' -> 1; // papel
            case 'V' -> 2; // tesoura
            default -> throw new IllegalArgumentException("movimento inválido: " + c);
        };
    }

    record CaseData(List<String> lines, boolean hasSentinel) {}

    private static String decideWinner(CaseData data) {
        if (!data.hasSentinel) return "INVALID";

        int beatriz = 0, artur = 0;
        for (String line : data.lines) {
            String s = line.trim();
            if (s.isEmpty()) continue;
            if (s.equals("- -")) break;

            String[] p = s.split("\\s+");
            if (p.length < 2) continue;
            if (p[0].equals("-") && p[1].equals("-")) break;

            int b = toIdx(p[0].charAt(0));
            int a = toIdx(p[1].charAt(0));
            if (b == a) continue;

            int diff = (b - a + 3) % 3; // 0 empate, 1 B vence, 2 A vence
            if (diff == 1) beatriz++; else artur++;
        }
        if (beatriz > artur) return "BEATRIZ WIN";
        if (artur > beatriz) return "ARTUR WIN";
        return "TIE";
    }

    private static CaseData extractCase(JsonNode node) {
        List<String> out = new ArrayList<>();
        boolean saw = false;

        if (node == null || node.isNull()) return new CaseData(out, false);

        if (node.isArray()) {
            for (JsonNode el : node) {
                if (saw) break;
                if (el.isTextual()) {
                    String s = el.asText();
                    out.add(s);
                    if (s.trim().equals("- -")) saw = true;
                } else if (el.isArray() && el.size() >= 2) {
                    String b = el.get(0).asText();
                    String a = el.get(1).asText();
                    out.add(b + " " + a);
                    if (b.equals("-") && a.equals("-")) saw = true;
                } else if (el.isObject()) {
                    String b = el.path("b").asText(null);
                    String a = el.path("a").asText(null);
                    if (b != null && a != null) {
                        out.add(b + " " + a);
                        if (b.equals("-") && a.equals("-")) saw = true;
                    }
                }
            }
            return new CaseData(out, saw);
        }

        if (node.isObject()) {
            for (String key : new String[]{"rounds", "lines", "moves"}) {
                JsonNode arr = node.get(key);
                if (arr != null && arr.isArray()) return extractCase(arr);
            }
            String b = node.path("b").asText(null);
            String a = node.path("a").asText(null);
            if (b != null && a != null) out.add(b + " " + a);
            return new CaseData(out, false);
        }

        throw new IllegalArgumentException("JSON de entrada não suportado.");
    }

    /** Encontra a lista de casos na raiz, sem usar JsonNode.fields() (depreca em versões novas) */
    private static List<CaseData> parseCases(JsonNode root) {
        List<CaseData> casesOut = new ArrayList<>();
        if (root.isArray()) {
            for (JsonNode c : root) casesOut.add(extractCase(c));
            return casesOut;
        }
        if (root.isObject()) {
            JsonNode casesNode = root.get("cases");
            if (casesNode != null && casesNode.isArray()) {
                for (JsonNode c : casesNode) casesOut.add(extractCase(c));
                return casesOut;
            }
            // procurar 1º campo array (usando fieldNames, não fields())
            for (Iterator<String> it = root.fieldNames(); it.hasNext(); ) {
                String name = it.next();
                JsonNode v = root.get(name);
                if (v != null && v.isArray()) {
                    for (JsonNode c : v) casesOut.add(extractCase(c));
                    if (!casesOut.isEmpty()) return casesOut;
                }
            }
            // caso único
            casesOut.add(extractCase(root));
            return casesOut;
        }
        throw new IllegalArgumentException("JSON de entrada não suportado.");
    }

    static Path resolveJsonPath(String[] args) throws IOException {
        if (args != null && args.length > 0 && !args[0].isBlank()) {
            return Path.of(args[0]).toRealPath(); // valida + resolve symlinks
        }
        Path rel = Path.of("2-fase", "respostas", "inputs", "b-inputs.json");
        Path cwd = Path.of("").toAbsolutePath().normalize();
        for (Path p = cwd; p != null; p = p.getParent()) {
            Path candidate = p.resolve(rel).normalize();
            if (exists(candidate)) {
                return candidate.toRealPath();
            }
        }
        throw new NoSuchFileException("Não encontrei o JSON: " + rel);
    }

    public static void main(String[] args) throws Exception {
        Path path = resolveJsonPath(args);
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(newBufferedReader(path));

        List<CaseData> cases = parseCases(root);
        StringBuilder out = new StringBuilder();
        for (CaseData oneCase : cases) out.append(decideWinner(oneCase)).append('\n');
        System.out.print(out);
    }
}

