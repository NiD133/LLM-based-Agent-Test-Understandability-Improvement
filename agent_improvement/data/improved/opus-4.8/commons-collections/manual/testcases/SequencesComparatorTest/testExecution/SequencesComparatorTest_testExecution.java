package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the edit script produced by {@link SequencesComparator}
 * actually transforms the "before" sequence into the "after" sequence.
 *
 * <p>For every (before, after) pair the test:</p>
 * <ol>
 *   <li>builds the edit script that turns {@code before} into {@code after},</li>
 *   <li>replays that script through an {@link ExecutionVisitor} seeded with
 *       {@code before},</li>
 *   <li>asserts that the visitor's resulting string equals {@code after}.</li>
 * </ol>
 */
public class SequencesComparatorTest_testExecution {

    /**
     * Replays an {@link EditScript} onto a starting sequence and exposes the
     * resulting sequence as a string. The script's commands are applied in
     * order: keep advances past an element, insert adds an element from the
     * target, and delete removes an element from the source.
     *
     * @param <T> the element type of the sequences being compared
     */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        /** The working sequence, mutated as commands are visited. */
        private List<T> sequence;

        /** Cursor into {@link #sequence} marking where the next command applies. */
        private int index;

        /**
         * Resets the visitor to start from a fresh copy of the given sequence.
         *
         * @param startingSequence the sequence to begin from
         */
        public void setList(final List<T> startingSequence) {
            sequence = new ArrayList<>(startingSequence);
            index = 0;
        }

        @Override
        public void visitInsertCommand(final T object) {
            sequence.add(index++, object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            ++index;
        }

        @Override
        public void visitDeleteCommand(final T object) {
            sequence.remove(index);
        }

        /**
         * @return the working sequence rendered as a string
         */
        public String getString() {
            final StringBuilder buffer = new StringBuilder();
            for (final T element : sequence) {
                buffer.append(element);
            }
            return buffer.toString();
        }
    }

    /** Source strings to be transformed (paired by index with {@link #expectedResults}). */
    private List<String> sourceStrings;

    /** Expected strings after applying each edit script (paired by index with {@link #sourceStrings}). */
    private List<String> expectedResults;

    /**
     * Converts a string into a list of its characters, so it can be fed to
     * the character-based {@link SequencesComparator}.
     *
     * @param string the string to split into characters
     * @return a list containing the string's characters in order
     */
    private List<Character> toCharacterList(final String string) {
        final List<Character> characters = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            characters.add(Character.valueOf(string.charAt(i)));
        }
        return characters;
    }

    @BeforeEach
    public void setUp() {
        sourceStrings = Arrays.asList(
                "bottle", "nematode knowledge", StringUtils.EMPTY, "aa", "prefixed string",
                "ABCABBA", "glop glop", "coq", "spider-man");
        expectedResults = Arrays.asList(
                "noodle", "empty bottle", StringUtils.EMPTY, "C", "prefix",
                "CBABAC", "pas glop pas glop", "ane", "klingon");
    }

    @AfterEach
    public void tearDown() {
        sourceStrings = null;
        expectedResults = null;
    }

    @Test
    void testExecution() {
        final ExecutionVisitor<Character> visitor = new ExecutionVisitor<>();
        for (int i = 0; i < sourceStrings.size(); ++i) {
            final List<Character> source = toCharacterList(sourceStrings.get(i));
            final List<Character> target = toCharacterList(expectedResults.get(i));

            // Seed the visitor with the source sequence, then replay the edit
            // script that converts the source into the target onto it.
            visitor.setList(source);
            new SequencesComparator<>(source, target).getScript().visit(visitor);

            assertEquals(expectedResults.get(i), visitor.getString());
        }
    }
}
