import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.gen.TreeGenerators;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

/**
 * Parse many Java files with GumTree's java-jdtc generator in ONE JVM and write
 * each tree as compact JSON: node = [type, label-or-null, pos, length, [children]].
 * Usage: java -cp gumtree.jar:. BatchParse listfile   (lines: "input\toutput")
 * The stock `gumtree parse` client cannot write several files (it opens the
 * output directory as a file), hence this helper.
 */
public class BatchParse {
    static void esc(StringBuilder sb, String s) {
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20 || (c >= 0xD800 && c <= 0xDFFF)) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        sb.append('"');
    }

    static void write(StringBuilder sb, Tree t) {
        sb.append('[');
        esc(sb, t.getType().name);
        sb.append(',');
        String l = t.getLabel();
        if (l == null || l.isEmpty()) sb.append("null"); else esc(sb, l);
        sb.append(',').append(t.getPos()).append(',').append(t.getLength()).append(",[");
        List<Tree> ch = t.getChildren();
        for (int i = 0; i < ch.size(); i++) {
            if (i > 0) sb.append(',');
            write(sb, ch.get(i));
        }
        sb.append("]]");
    }

    public static void main(String[] args) throws Exception {
        Run.initGenerators();
        List<String> lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
        int ok = 0, bad = 0;
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] p = line.split("\t");
            try {
                TreeContext tc = TreeGenerators.getInstance().getTree(p[0], "java-jdtc");
                StringBuilder sb = new StringBuilder(1 << 16);
                write(sb, tc.getRoot());
                Path out = Paths.get(p[1]);
                Files.createDirectories(out.getParent());
                Files.write(out, sb.toString().getBytes(StandardCharsets.UTF_8));
                ok++;
            } catch (Throwable e) {
                bad++;
                System.err.println("FAIL\t" + p[0] + "\t" + e);
            }
        }
        System.err.println("done ok=" + ok + " bad=" + bad);
    }
}
