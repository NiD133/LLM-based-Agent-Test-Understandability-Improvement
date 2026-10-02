package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StringsComparatorTest_testLength {

    static Stream<Arguments> stringPairsWithExpectedModifications() {
        return Stream.of(
            Arguments.of("bottle",             "noodle",            6),
            Arguments.of("nematode knowledge", "empty bottle",     16),
            Arguments.of("",                   "",                   0),
            Arguments.of("aa",                 "C",                  3),
            Arguments.of("prefixed string",    "prefix",             9),
            Arguments.of("ABCABBA",            "CBABAC",             5),
            Arguments.of("glop glop",          "pas glop pas glop",  8),
            Arguments.of("coq",                "ane",                6),
            Arguments.of("spider-man",         "klingon",           13)
        );
    }

    @ParameterizedTest(name = "\"{0}\" vs \"{1}\" => {2} modifications")
    @MethodSource("stringPairsWithExpectedModifications")
    void testLength(final String before, final String after, final int expectedModifications) {
        final StringsComparator comparator = new StringsComparator(before, after);
        assertEquals(expectedModifications, comparator.getScript().getModifications());
    }
}
