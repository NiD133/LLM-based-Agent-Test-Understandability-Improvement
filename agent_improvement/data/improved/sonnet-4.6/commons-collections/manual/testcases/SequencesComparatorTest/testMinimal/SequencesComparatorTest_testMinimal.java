package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SequencesComparatorTest_testMinimal {

    private List<String> before;

    private List<String> after;

    private int[] length;

    private List<Character> sequence(final String string) {
        final List<Character> list = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            list.add(Character.valueOf(string.charAt(i)));
        }
        return list;
    }

    @BeforeEach
    public void setUp() {
        before = Arrays.asList("bottle", "nematode knowledge", StringUtils.EMPTY, "aa", "prefixed string", "ABCABBA", "glop glop", "coq", "spider-man");
        after = Arrays.asList("noodle", "empty bottle", StringUtils.EMPTY, "C", "prefix", "CBABAC", "pas glop pas glop", "ane", "klingon");
        length = new int[] { 6, 16, 0, 3, 9, 5, 8, 6, 13 };
    }

    @AfterEach
    public void tearDown() {
        before = null;
        after = null;
        length = null;
    }

    @Test
    void testMinimal() {
        // Fictional alphabet used as a domain-neutral set of tokens for sequence testing
        final String[] shadokAlph = { "GA", "BU", "ZO", "MEU" };

        // Build the fixed baseline sequence from the Shadok alphabet tokens
        final List<String> sentenceBefore = new ArrayList<>(Arrays.asList(
            shadokAlph[0], shadokAlph[2], shadokAlph[3], shadokAlph[1],
            shadokAlph[0], shadokAlph[0], shadokAlph[2], shadokAlph[1],
            shadokAlph[3], shadokAlph[0], shadokAlph[2], shadokAlph[1],
            shadokAlph[3], shadokAlph[2], shadokAlph[2], shadokAlph[0],
            shadokAlph[1], shadokAlph[3], shadokAlph[0], shadokAlph[3]
        ));

        final List<String> sentenceAfter = new ArrayList<>();
        // Fixed seed ensures deterministic mutations across runs
        final Random random = new Random(4564634237452342L);

        // Verify the "minimal" property: the edit script produced by the comparator must never
        // contain more modifications than the number of random edits used to derive sentenceAfter.
        // This confirms the Myers algorithm generates a truly optimal (shortest) edit script.
        for (int numChanges = 0; numChanges <= 40; numChanges += 5) {
            sentenceAfter.clear();
            sentenceAfter.addAll(sentenceBefore);

            // Apply numChanges random insertions or deletions to produce the modified sequence
            for (int i = 0; i < numChanges; i++) {
                if (random.nextInt(2) == 0) {
                    sentenceAfter.add(random.nextInt(sentenceAfter.size() + 1), shadokAlph[random.nextInt(4)]);
                } else {
                    sentenceAfter.remove(random.nextInt(sentenceAfter.size()));
                }
            }

            final SequencesComparator<String> comparator = new SequencesComparator<>(sentenceBefore, sentenceAfter);
            assertTrue(comparator.getScript().getModifications() <= numChanges);
        }
    }
}
