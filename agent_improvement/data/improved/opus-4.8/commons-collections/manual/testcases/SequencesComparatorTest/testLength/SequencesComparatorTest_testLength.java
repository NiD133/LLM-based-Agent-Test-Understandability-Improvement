package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SequencesComparator#getScript()} reports the expected
 * number of edit modifications (deletions + insertions) needed to transform a
 * "before" string into an "after" string.
 *
 * <p>Each {@link DiffCase} pairs the two strings with the modification count
 * that the Myers diff algorithm is expected to produce, so the relationship
 * between an input pair and its expected result is visible at a glance.</p>
 */
public class SequencesComparatorTest_testLength {

    /**
     * A single comparison scenario: transform {@code before} into {@code after}
     * and expect exactly {@code expectedModifications} edit commands.
     */
    private static final class DiffCase {

        private final String before;
        private final String after;
        private final int expectedModifications;

        DiffCase(final String before, final String after, final int expectedModifications) {
            this.before = before;
            this.after = after;
            this.expectedModifications = expectedModifications;
        }
    }

    /** The scenarios under test, mirroring the original parallel-array fixtures. */
    private static final List<DiffCase> DIFF_CASES = Arrays.asList(
            new DiffCase("bottle",             "noodle",            6),
            new DiffCase("nematode knowledge", "empty bottle",     16),
            new DiffCase(StringUtils.EMPTY,    StringUtils.EMPTY,   0),
            new DiffCase("aa",                 "C",                 3),
            new DiffCase("prefixed string",    "prefix",            9),
            new DiffCase("ABCABBA",            "CBABAC",            5),
            new DiffCase("glop glop",          "pas glop pas glop", 8),
            new DiffCase("coq",                "ane",               6),
            new DiffCase("spider-man",         "klingon",          13));

    /**
     * Converts a string into a list of its characters, the input form expected
     * by {@link SequencesComparator}.
     */
    private List<Character> toCharacterList(final String string) {
        final List<Character> characters = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            characters.add(Character.valueOf(string.charAt(i)));
        }
        return characters;
    }

    @Test
    void testLength() {
        for (final DiffCase diffCase : DIFF_CASES) {
            final SequencesComparator<Character> comparator =
                    new SequencesComparator<>(toCharacterList(diffCase.before), toCharacterList(diffCase.after));

            assertEquals(diffCase.expectedModifications, comparator.getScript().getModifications(),
                    () -> "Unexpected modification count transforming \""
                            + diffCase.before + "\" into \"" + diffCase.after + "\"");
        }
    }
}
