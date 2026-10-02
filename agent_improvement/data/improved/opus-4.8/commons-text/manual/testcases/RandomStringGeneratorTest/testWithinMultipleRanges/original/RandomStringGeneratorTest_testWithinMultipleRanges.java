package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import org.apache.commons.lang3.ArraySorter;
import org.apache.commons.text.RandomStringGenerator.Builder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RandomStringGeneratorTest_testWithinMultipleRanges {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    void testWithinMultipleRanges() {
        final int length = 5000;
        final char[][] pairs = { { 'a', 'z' }, { '0', '9' } };
        // @formatter:off
        final RandomStringGenerator generator = RandomStringGenerator.builder().withinRange().withinRange((char[][]) null).withinRange(pairs).get();
        // @formatter:on
        final String str = generator.generate(length);
        int minimumCodePoint = 0, maximumCodePoint = 0;
        for (final char[] pair : pairs) {
            minimumCodePoint = Math.min(minimumCodePoint, pair[0]);
            maximumCodePoint = Math.max(maximumCodePoint, pair[1]);
        }
        int i = 0;
        do {
            final int codePoint = str.codePointAt(i);
            assertTrue(codePoint >= minimumCodePoint && codePoint <= maximumCodePoint);
            i += Character.charCount(codePoint);
        } while (i < str.length());
    }
}
