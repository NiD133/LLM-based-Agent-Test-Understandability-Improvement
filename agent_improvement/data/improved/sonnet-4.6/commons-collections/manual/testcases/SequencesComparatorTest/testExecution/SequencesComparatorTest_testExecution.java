package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SequencesComparatorTest_testExecution {

    /**
     * Replays an edit script against a mutable copy of the original sequence,
     * then exposes the result as a String.
     */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        private List<T> v;
        private int index;

        public void setList(final List<T> list) {
            v = new ArrayList<>(list);
            index = 0;
        }

        public String getString() {
            final StringBuilder buffer = new StringBuilder();
            for (final T c : v) {
                buffer.append(c);
            }
            return buffer.toString();
        }

        @Override
        public void visitDeleteCommand(final T object) {
            v.remove(index);
        }

        @Override
        public void visitInsertCommand(final T object) {
            v.add(index++, object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            ++index;
        }
    }

    // Pairs of (original, expected-after-edit) strings covering diverse edit scenarios:
    // same-length rewrites, empty inputs, prefix truncation, classic diff examples, etc.
    private List<String> before;
    private List<String> after;
    private int[] length;

    /** Converts a String into a List of Characters for use with SequencesComparator. */
    private List<Character> toCharList(final String string) {
        final List<Character> list = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            list.add(Character.valueOf(string.charAt(i)));
        }
        return list;
    }

    @BeforeEach
    public void setUp() {
        before = Arrays.asList(
            "bottle",
            "nematode knowledge",
            StringUtils.EMPTY,
            "aa",
            "prefixed string",
            "ABCABBA",
            "glop glop",
            "coq",
            "spider-man");

        after = Arrays.asList(
            "noodle",
            "empty bottle",
            StringUtils.EMPTY,
            "C",
            "prefix",
            "CBABAC",
            "pas glop pas glop",
            "ane",
            "klingon");

        length = new int[] { 6, 16, 0, 3, 9, 5, 8, 6, 13 };
    }

    @AfterEach
    public void tearDown() {
        before = null;
        after  = null;
        length = null;
    }

    /**
     * Verifies that applying the edit script produced by SequencesComparator
     * to the "before" character sequence yields the "after" string exactly.
     */
    @Test
    void testExecution() {
        final ExecutionVisitor<Character> ev = new ExecutionVisitor<>();
        for (int i = 0; i < before.size(); ++i) {
            final List<Character> beforeChars = toCharList(before.get(i));
            final List<Character> afterChars  = toCharList(after.get(i));

            ev.setList(beforeChars);
            new SequencesComparator<>(beforeChars, afterChars).getScript().visit(ev);

            assertEquals(after.get(i), ev.getString(),
                "Applying edit script to \"" + before.get(i) + "\" should produce \"" + after.get(i) + "\"");
        }
    }
}
