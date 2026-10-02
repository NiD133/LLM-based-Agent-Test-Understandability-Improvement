package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SegmentUtilsTest_testMatches {

    public static final IMatcher even = new MultipleMatches(2);

    public static final IMatcher five = new MultipleMatches(5);

    static Stream<Arguments> countArgs() {
        return Stream.of(Arguments.of("()V", 0), Arguments.of("(D)V", 1), Arguments.of("([D)V", 1), Arguments.of("([[D)V", 1), Arguments.of("(DD)V", 2), Arguments.of("(DDD)V", 3), Arguments.of("(Lblah/blah;D)V", 2), Arguments.of("(Lblah/blah;DLbLah;)V", 3));
    }

    static Stream<Arguments> countInvokeInterfaceArgs() {
        return Stream.of(Arguments.of("(Z)V", 1), Arguments.of("(D)V", 2), Arguments.of("(J)V", 2), Arguments.of("([D)V", 1), Arguments.of("([[D)V", 1), Arguments.of("(DD)V", 4), Arguments.of("(Lblah/blah;D)V", 3), Arguments.of("(Lblah/blah;DLbLah;)V", 4), Arguments.of("([Lblah/blah;DLbLah;)V", 4));
    }

    @Test
    void testMatches() {
        final long[] oneToTen = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        assertEquals(6, SegmentUtils.countMatches(new long[][] { oneToTen, new long[] { 5, 6, 7 } }, even));
        assertEquals(5, SegmentUtils.countMatches(new long[][] { oneToTen }, even));
        assertEquals(5, SegmentUtils.countMatches(oneToTen, even));
        assertEquals(3, SegmentUtils.countMatches(new long[][] { oneToTen, new long[] { 5, 6, 7 } }, five));
        assertEquals(2, SegmentUtils.countMatches(new long[][] { oneToTen }, five));
        assertEquals(2, SegmentUtils.countMatches(oneToTen, five));
    }
}
