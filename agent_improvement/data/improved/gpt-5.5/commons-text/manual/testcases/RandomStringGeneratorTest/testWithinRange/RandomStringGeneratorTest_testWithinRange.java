package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testWithinRange {

    private static final int GENERATED_CODE_POINT_COUNT = 5000;
    private static final int MINIMUM_CODE_POINT = 'a';
    private static final int MAXIMUM_CODE_POINT = 'z';

    @Test
    void testWithinRange() {
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange(MINIMUM_CODE_POINT, MAXIMUM_CODE_POINT)
                .get();

        final String generated = generator.generate(GENERATED_CODE_POINT_COUNT);

        for (int index = 0; index < generated.length();) {
            final int codePoint = generated.codePointAt(index);
            assertTrue(codePoint >= MINIMUM_CODE_POINT && codePoint <= MAXIMUM_CODE_POINT);
            index += Character.charCount(codePoint);
        }
    }
}
