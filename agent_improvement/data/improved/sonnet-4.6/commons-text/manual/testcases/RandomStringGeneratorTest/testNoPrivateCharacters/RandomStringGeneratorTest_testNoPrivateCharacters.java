package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testNoPrivateCharacters {

    // First code point of the BMP private-use area (U+E000..U+F8FF)
    private static final int START_OF_BMP_PRIVATE_USE_AREA = 0xE000;

    // Last BMP code point before the supplementary planes begin
    private static final int LAST_BMP_CODE_POINT = Character.MIN_SUPPLEMENTARY_CODE_POINT - 1;

    @Test
    void testNoPrivateCharacters() {
        // Request a 5000-code-point string from the BMP range that is largely occupied
        // by private-use characters. The generator must skip those and return only
        // characters whose Unicode category is not PRIVATE_USE.
        final String generated = RandomStringGenerator.builder()
                .withinRange(START_OF_BMP_PRIVATE_USE_AREA, LAST_BMP_CODE_POINT)
                .get()
                .generate(5000);

        generated.codePoints().forEach(codePoint ->
                assertFalse(
                        Character.getType(codePoint) == Character.PRIVATE_USE,
                        "Generated string must not contain private-use code points"));
    }
}
