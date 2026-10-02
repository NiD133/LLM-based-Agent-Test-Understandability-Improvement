package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class SequencesComparatorTest_testMinimal {

    private static final String[] SHADOK_ALPHABET = { "GA", "BU", "ZO", "MEU" };
    private static final long RANDOM_SEED = 4564634237452342L;
    private static final int MAX_RANDOM_CHANGES = 40;
    private static final int RANDOM_CHANGE_STEP = 5;

    @Test
    void testMinimal() {
        final List<String> sentenceBefore = createOriginalSentence();
        final List<String> sentenceAfter = new ArrayList<>();
        final Random random = new Random(RANDOM_SEED);

        for (int numberOfChanges = 0; numberOfChanges <= MAX_RANDOM_CHANGES; numberOfChanges += RANDOM_CHANGE_STEP) {
            sentenceAfter.clear();
            sentenceAfter.addAll(sentenceBefore);
            applyRandomChanges(sentenceAfter, numberOfChanges, random);

            final SequencesComparator<String> comparator = new SequencesComparator<>(sentenceBefore, sentenceAfter);
            assertTrue(comparator.getScript().getModifications() <= numberOfChanges);
        }
    }

    private static List<String> createOriginalSentence() {
        return Arrays.asList(
                SHADOK_ALPHABET[0],
                SHADOK_ALPHABET[2],
                SHADOK_ALPHABET[3],
                SHADOK_ALPHABET[1],
                SHADOK_ALPHABET[0],
                SHADOK_ALPHABET[0],
                SHADOK_ALPHABET[2],
                SHADOK_ALPHABET[1],
                SHADOK_ALPHABET[3],
                SHADOK_ALPHABET[0],
                SHADOK_ALPHABET[2],
                SHADOK_ALPHABET[1],
                SHADOK_ALPHABET[3],
                SHADOK_ALPHABET[2],
                SHADOK_ALPHABET[2],
                SHADOK_ALPHABET[0],
                SHADOK_ALPHABET[1],
                SHADOK_ALPHABET[3],
                SHADOK_ALPHABET[0],
                SHADOK_ALPHABET[3]);
    }

    private static void applyRandomChanges(final List<String> sentenceAfter, final int numberOfChanges,
            final Random random) {
        for (int i = 0; i < numberOfChanges; i++) {
            if (random.nextInt(2) == 0) {
                sentenceAfter.add(random.nextInt(sentenceAfter.size() + 1), SHADOK_ALPHABET[random.nextInt(4)]);
            } else {
                sentenceAfter.remove(random.nextInt(sentenceAfter.size()));
            }
        }
    }
}
