package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StringsComparatorTest_testLongestCommonSubsequence {

    static Stream<Arguments> lcsTestCases() {
        return Stream.of(
            Arguments.of("bottle",            "noodle",            3),
            Arguments.of("nematode knowledge", "empty bottle",      7),
            Arguments.of("",                  "",                   0),
            Arguments.of("aa",                "C",                  0),
            Arguments.of("prefixed string",   "prefix",             6),
            Arguments.of("ABCABBA",           "CBABAC",             4),
            Arguments.of("glop glop",         "pas glop pas glop",  9),
            Arguments.of("coq",               "ane",                0),
            Arguments.of("spider-man",        "klingon",            2)
        );
    }

    @ParameterizedTest(name = "LCS(\"{0}\", \"{1}\") == {2}")
    @MethodSource("lcsTestCases")
    void testLongestCommonSubsequence(String left, String right, int expectedLcs) {
        StringsComparator comparator = new StringsComparator(left, right);
        assertEquals(expectedLcs, comparator.getScript().getLCSLength());
    }
}
