package org.apache.commons.collections4.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SequencesComparatorTest_testLength {

    /**
     * Converts a string into a list of its characters for use as a sequence input.
     */
    private static List<Character> toCharacterList(final String string) {
        final List<Character> list = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            list.add(string.charAt(i));
        }
        return list;
    }

    /**
     * Provides (beforeString, afterString, expectedModifications) triples.
     * expectedModifications is the number of insert/delete commands in the shortest edit script
     * needed to transform the before sequence into the after sequence.
     */
    static Stream<Arguments> sequenceComparisonCases() {
        return Stream.of(
            Arguments.of("bottle",              "noodle",             6),
            Arguments.of("nematode knowledge",  "empty bottle",       16),
            Arguments.of(StringUtils.EMPTY,     StringUtils.EMPTY,    0),
            Arguments.of("aa",                  "C",                  3),
            Arguments.of("prefixed string",     "prefix",             9),
            Arguments.of("ABCABBA",             "CBABAC",             5),
            Arguments.of("glop glop",           "pas glop pas glop",  8),
            Arguments.of("coq",                 "ane",                6),
            Arguments.of("spider-man",          "klingon",            13)
        );
    }

    @ParameterizedTest
    @MethodSource("sequenceComparisonCases")
    void testLength(final String beforeString, final String afterString, final int expectedModifications) {
        final SequencesComparator<Character> comparator =
                new SequencesComparator<>(toCharacterList(beforeString), toCharacterList(afterString));
        assertEquals(expectedModifications, comparator.getScript().getModifications());
    }
}
