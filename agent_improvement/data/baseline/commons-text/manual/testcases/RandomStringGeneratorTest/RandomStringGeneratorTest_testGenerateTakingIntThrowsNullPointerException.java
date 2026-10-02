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

public class RandomStringGeneratorTest_testGenerateTakingIntThrowsNullPointerException {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    void testGenerateTakingIntThrowsNullPointerException() {
        assertThrowsExactly(NullPointerException.class, () -> {
            final RandomStringGenerator.Builder randomStringGeneratorBuilder = RandomStringGenerator.builder();
            final CharacterPredicate[] characterPredicateArray = new CharacterPredicate[2];
            randomStringGeneratorBuilder.filteredBy(characterPredicateArray);
            final RandomStringGenerator randomStringGenerator = randomStringGeneratorBuilder.get();
            randomStringGenerator.generate(18);
        });
    }
}
