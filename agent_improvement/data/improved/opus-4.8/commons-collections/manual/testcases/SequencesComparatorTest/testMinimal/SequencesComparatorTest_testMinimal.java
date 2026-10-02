package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link SequencesComparator} produces a <em>minimal</em> edit script.
 * <p>
 * The idea: start from a fixed sequence, then apply a known number of random
 * mutations (each mutation is a single insertion or deletion) to derive a second
 * sequence. Since the comparator implements Myers' shortest-edit-script algorithm,
 * the number of modifications it reports must never exceed the number of mutations
 * we actually applied.
 */
public class SequencesComparatorTest_testMinimal {

    /** Fixed seed so the random mutation sequence is reproducible across runs. */
    private static final long RANDOM_SEED = 4564634237452342L;

    /** The "Shadok" alphabet: the only words that may appear in the sequences. */
    private static final String[] SHADOK_ALPHABET = { "GA", "BU", "ZO", "MEU" };

    /**
     * Indices into {@link #SHADOK_ALPHABET} describing the original, fixed sequence.
     * (e.g. 0 -> "GA", 2 -> "ZO", ...)
     */
    private static final int[] ORIGINAL_WORD_INDICES =
        { 0, 2, 3, 1, 0, 0, 2, 1, 3, 0, 2, 1, 3, 2, 2, 0, 1, 3, 0, 3 };

    @Test
    void testMinimal() {
        final List<String> original = buildOriginalSequence();
        final Random random = new Random(RANDOM_SEED);

        // Apply an increasing number of random mutations and check minimality each time.
        for (int mutationCount = 0; mutationCount <= 40; mutationCount += 5) {
            final List<String> mutated = applyRandomMutations(original, mutationCount, random);

            final SequencesComparator<String> comparator =
                new SequencesComparator<>(original, mutated);
            final int reportedModifications = comparator.getScript().getModifications();

            assertTrue(reportedModifications <= mutationCount,
                "edit script must not report more modifications than were applied");
        }
    }

    /** Builds the fixed original sequence from {@link #ORIGINAL_WORD_INDICES}. */
    private List<String> buildOriginalSequence() {
        final List<String> sequence = new ArrayList<>();
        for (final int wordIndex : ORIGINAL_WORD_INDICES) {
            sequence.add(SHADOK_ALPHABET[wordIndex]);
        }
        return sequence;
    }

    /**
     * Returns a copy of {@code source} after applying {@code mutationCount} random
     * single-element mutations. Each mutation is either an insertion of a random
     * word at a random position, or a deletion of the element at a random position.
     */
    private List<String> applyRandomMutations(final List<String> source,
                                              final int mutationCount,
                                              final Random random) {
        final List<String> mutated = new ArrayList<>(source);
        for (int i = 0; i < mutationCount; i++) {
            final boolean insert = random.nextInt(2) == 0;
            if (insert) {
                final int position = random.nextInt(mutated.size() + 1);
                final String word = SHADOK_ALPHABET[random.nextInt(SHADOK_ALPHABET.length)];
                mutated.add(position, word);
            } else {
                mutated.remove(random.nextInt(mutated.size()));
            }
        }
        return mutated;
    }
}
