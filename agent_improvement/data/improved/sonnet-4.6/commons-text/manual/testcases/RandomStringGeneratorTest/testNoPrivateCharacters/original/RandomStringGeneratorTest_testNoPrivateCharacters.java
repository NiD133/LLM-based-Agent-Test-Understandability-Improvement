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

public class RandomStringGeneratorTest_testNoPrivateCharacters {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    void testNoPrivateCharacters() {
        final int startOfPrivateBMPChars = 0xE000;
        // Request a string in an area of the Basic Multilingual Plane that is
        // largely occupied by private characters
        final String str = RandomStringGenerator.builder().withinRange(startOfPrivateBMPChars, Character.MIN_SUPPLEMENTARY_CODE_POINT - 1).get().generate(5000);
        int i = 0;
        do {
            final int codePoint = str.codePointAt(i);
            assertFalse(Character.getType(codePoint) == Character.PRIVATE_USE);
            i += Character.charCount(codePoint);
        } while (i < str.length());
    }
}
