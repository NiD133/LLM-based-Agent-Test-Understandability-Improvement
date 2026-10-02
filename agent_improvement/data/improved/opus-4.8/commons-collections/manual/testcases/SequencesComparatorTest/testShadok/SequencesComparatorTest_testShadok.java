package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link SequencesComparator} on an exhaustive set of "Shadok" sentences.
 * <p>
 * Each sentence is a list of words drawn from a tiny four-word vocabulary. We
 * generate every sentence up to a fixed length, then for every ordered pair
 * (source, target) we:
 * <ol>
 *   <li>compute the edit script that turns {@code source} into {@code target},</li>
 *   <li>replay that script with an {@link ExecutionVisitor} seeded with {@code source}, and</li>
 *   <li>assert the visitor ends up holding exactly {@code target}.</li>
 * </ol>
 * In other words: applying the comparator's edit script must reconstruct the
 * target sequence from the source sequence.
 */
public class SequencesComparatorTest_testShadok {

    /**
     * A {@link CommandVisitor} that replays an edit script against a working copy
     * of the source list, transforming it into the target list as commands are
     * visited. {@link #getString()} returns the current contents concatenated.
     *
     * @param <T> the element type
     */
    private static final class ExecutionVisitor<T> implements CommandVisitor<T> {

        /** Working copy of the sequence being transformed. */
        private List<T> v;

        /** Current cursor position within {@link #v}. */
        private int index;

        /**
         * Resets the visitor to start transforming a fresh copy of the given list.
         *
         * @param list  the source list to copy and transform
         */
        public void setList(final List<T> list) {
            v = new ArrayList<>(list);
            index = 0;
        }

        @Override
        public void visitInsertCommand(final T object) {
            v.add(index++, object);
        }

        @Override
        public void visitKeepCommand(final T object) {
            ++index;
        }

        @Override
        public void visitDeleteCommand(final T object) {
            v.remove(index);
        }

        /**
         * @return the current working sequence with all elements concatenated
         */
        public String getString() {
            final StringBuilder buffer = new StringBuilder();
            for (final T c : v) {
                buffer.append(c);
            }
            return buffer.toString();
        }
    }

    /** The four-word Shadok vocabulary used to build sentences. */
    private static final String[] SHADOK_VOCABULARY = { "GA", "BU", "ZO", "MEU" };

    /** Generate all sentences whose length is strictly less than this bound. */
    private static final int MAX_SENTENCE_LENGTH = 5;

    /**
     * Builds every possible sentence (word list) whose length ranges from 0 up to
     * {@link #MAX_SENTENCE_LENGTH} - 1, using words from {@link #SHADOK_VOCABULARY}.
     *
     * @return all generated sentences
     */
    private static List<List<String>> buildAllShadokSentences() {
        List<List<String>> sentences = new ArrayList<>();
        for (int length = 0; length < MAX_SENTENCE_LENGTH; ++length) {
            final List<List<String>> nextGeneration = new ArrayList<>();
            // The empty sentence is always present.
            nextGeneration.add(new ArrayList<>());
            // Extend every sentence from the previous generation by one word.
            for (final String word : SHADOK_VOCABULARY) {
                for (final List<String> sentence : sentences) {
                    final List<String> extended = new ArrayList<>(sentence);
                    extended.add(word);
                    nextGeneration.add(extended);
                }
            }
            sentences = nextGeneration;
        }
        return sentences;
    }

    /**
     * Concatenates the words of a sentence into a single string, mirroring how the
     * {@link ExecutionVisitor} accumulates the words it visits.
     *
     * @param sentence  the sentence to concatenate
     * @return the words joined together with no separator
     */
    private static String concatenate(final List<String> sentence) {
        final StringBuilder concat = new StringBuilder();
        for (final String word : sentence) {
            concat.append(word);
        }
        return concat.toString();
    }

    @Test
    void testShadok() {
        final List<List<String>> shadokSentences = buildAllShadokSentences();

        final ExecutionVisitor<String> visitor = new ExecutionVisitor<>();
        for (final List<String> source : shadokSentences) {
            for (final List<String> target : shadokSentences) {
                // Replay the edit script (source -> target) starting from source.
                visitor.setList(source);
                new SequencesComparator<>(source, target).getScript().visit(visitor);

                // The replayed script must rebuild the target sentence exactly.
                assertEquals(concatenate(target), visitor.getString());
            }
        }
    }
}
