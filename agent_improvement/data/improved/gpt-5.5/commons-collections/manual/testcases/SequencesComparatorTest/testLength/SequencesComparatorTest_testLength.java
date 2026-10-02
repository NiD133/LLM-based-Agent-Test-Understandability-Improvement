package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SequencesComparatorTest_testLength {

    private List<String> originalSequences;

    private List<String> revisedSequences;

    private int[] expectedModificationCounts;

    private List<Character> sequence(final String string) {
        final List<Character> characterSequence = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            characterSequence.add(Character.valueOf(string.charAt(i)));
        }
        return characterSequence;
    }

    @BeforeEach
    public void setUp() {
        originalSequences = Arrays.asList("bottle", "nematode knowledge", StringUtils.EMPTY, "aa", "prefixed string", "ABCABBA", "glop glop", "coq", "spider-man");
        revisedSequences = Arrays.asList("noodle", "empty bottle", StringUtils.EMPTY, "C", "prefix", "CBABAC", "pas glop pas glop", "ane", "klingon");
        expectedModificationCounts = new int[] { 6, 16, 0, 3, 9, 5, 8, 6, 13 };
    }

    @AfterEach
    public void tearDown() {
        originalSequences = null;
        revisedSequences = null;
        expectedModificationCounts = null;
    }

    @Test
    void testLength() {
        for (int i = 0; i < originalSequences.size(); ++i) {
            assertModificationCount(i);
        }
    }

    private void assertModificationCount(final int scenarioIndex) {
        final SequencesComparator<Character> comparator = new SequencesComparator<>(
                sequence(originalSequences.get(scenarioIndex)),
                sequence(revisedSequences.get(scenarioIndex)));
        assertEquals(expectedModificationCounts[scenarioIndex], comparator.getScript().getModifications());
    }
}
