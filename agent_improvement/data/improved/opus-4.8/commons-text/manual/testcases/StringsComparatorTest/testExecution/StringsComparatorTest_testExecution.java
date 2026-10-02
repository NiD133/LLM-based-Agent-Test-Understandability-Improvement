package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the {@link EditScript} produced by {@link StringsComparator} actually
 * transforms the "source" string into the "target" string.
 *
 * <p>For every (source, target) pair the test:</p>
 * <ol>
 *   <li>computes the edit script that turns {@code source} into {@code target};</li>
 *   <li>replays that script through an {@link ExecutionVisitor}, which rebuilds the
 *       resulting string command by command;</li>
 *   <li>asserts that the rebuilt string equals the expected {@code target}.</li>
 * </ol>
 */
public class StringsComparatorTest_testExecution {

    /** Source strings to be transformed (the "left" sequence of each comparison). */
    private static final List<String> SOURCES = Arrays.asList(
            "bottle",
            "nematode knowledge",
            "",
            "aa",
            "prefixed string",
            "ABCABBA",
            "glop glop",
            "coq",
            "spider-man");

    /** Expected target strings (the "right" sequence of each comparison). */
    private static final List<String> TARGETS = Arrays.asList(
            "noodle",
            "empty bottle",
            "",
            "C",
            "prefix",
            "CBABAC",
            "pas glop pas glop",
            "ane",
            "klingon");

    @Test
    void testExecution() {
        for (int i = 0; i < SOURCES.size(); ++i) {
            final String source = SOURCES.get(i);
            final String target = TARGETS.get(i);

            final ExecutionVisitor<Character> visitor = new ExecutionVisitor<>();
            new StringsComparator(source, target).getScript().visit(visitor);

            assertEquals(target, visitor.getString(),
                    "Replaying the edit script from \"" + source + "\" should rebuild \"" + target + "\"");
        }
    }

    /**
     * Rebuilds the target string by replaying an {@link EditScript}: kept and inserted
     * characters belong to the target and are appended, while deleted characters belong
     * only to the source and are dropped.
     */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private final StringBuilder rebuilt = new StringBuilder();

        @Override
        public void visitInsertCommand(final T object) {
            rebuilt.append(object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            rebuilt.append(object);
        }

        @Override
        public void visitDeleteCommand(final T object) {
            // A deleted character is present only in the source, so it is not part of the target.
        }

        public String getString() {
            return rebuilt.toString();
        }
    }
}
