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

public class RandomStringGeneratorTest_testNoLoneSurrogates {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    void testNoLoneSurrogates() {
        final int length = 5000;
        final String str = RandomStringGenerator.builder().get().generate(length);
        char lastChar = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (Character.isLowSurrogate(c)) {
                assertTrue(Character.isHighSurrogate(lastChar));
            }
            if (Character.isHighSurrogate(lastChar)) {
                assertTrue(Character.isLowSurrogate(c));
            }
            if (Character.isHighSurrogate(c)) {
                // test this isn't the last character in the string
                assertTrue(i + 1 < str.length());
            }
            lastChar = c;
        }
    }
}
