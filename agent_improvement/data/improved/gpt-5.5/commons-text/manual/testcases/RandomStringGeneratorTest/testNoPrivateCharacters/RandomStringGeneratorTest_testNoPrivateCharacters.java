package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testNoPrivateCharacters {

    private static final int FIRST_PRIVATE_USE_BMP_CODE_POINT = 0xE000;
    private static final int LAST_BMP_CODE_POINT = Character.MIN_SUPPLEMENTARY_CODE_POINT - 1;
    private static final int GENERATED_CODE_POINT_COUNT = 5000;

    @Test
    void testNoPrivateCharacters() {
        final String generated = RandomStringGenerator.builder()
                .withinRange(FIRST_PRIVATE_USE_BMP_CODE_POINT, LAST_BMP_CODE_POINT)
                .get()
                .generate(GENERATED_CODE_POINT_COUNT);

        for (int offset = 0; offset < generated.length();) {
            final int codePoint = generated.codePointAt(offset);
            assertFalse(Character.getType(codePoint) == Character.PRIVATE_USE);
            offset += Character.charCount(codePoint);
        }
    }
}
