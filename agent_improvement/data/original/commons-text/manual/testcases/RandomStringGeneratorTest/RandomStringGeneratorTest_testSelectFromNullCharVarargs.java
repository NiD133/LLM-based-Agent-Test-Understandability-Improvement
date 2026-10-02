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

public class RandomStringGeneratorTest_testSelectFromNullCharVarargs {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    void testSelectFromNullCharVarargs() {
        final int length = 5;
        RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom(null).get();
        String randomText = generator.generate(length);
        assertEquals(length, codePointLength(randomText));
        for (final char c : randomText.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
        //
        final Builder builder = RandomStringGenerator.builder().selectFrom('a');
        generator = builder.get();
        randomText = generator.generate(length);
        for (final char c : randomText.toCharArray()) {
            assertEquals('a', c);
        }
        // null input resets
        generator = builder.selectFrom(null).get();
        randomText = generator.generate(length);
        assertEquals(length, codePointLength(randomText));
        for (final char c : randomText.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
    }
}
