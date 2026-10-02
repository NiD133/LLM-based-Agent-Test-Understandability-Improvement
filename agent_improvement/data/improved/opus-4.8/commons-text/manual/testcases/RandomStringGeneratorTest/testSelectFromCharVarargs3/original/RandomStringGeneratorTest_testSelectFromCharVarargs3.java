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

public class RandomStringGeneratorTest_testSelectFromCharVarargs3 {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void testSelectFromCharVarargs3(final boolean accumulate) {
        final String str = "abcde";
        // @formatter:off
        final RandomStringGenerator generator = RandomStringGenerator.builder().setAccumulate(accumulate).selectFrom('a', 'b', 'c', 'd', 'e').selectFrom('a', 'b', 'c', 'd').selectFrom('a', 'b', 'c').selectFrom('a', 'b').selectFrom(null).selectFrom().get();
        // @formatter:on
        final String randomText = generator.generate(10);
        for (final char c : randomText.toCharArray()) {
            assertEquals(accumulate, str.indexOf(c) != -1);
        }
    }
}
