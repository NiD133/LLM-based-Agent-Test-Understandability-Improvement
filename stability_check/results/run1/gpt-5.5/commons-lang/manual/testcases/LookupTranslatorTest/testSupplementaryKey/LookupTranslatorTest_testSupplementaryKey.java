package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    private static final int SUPPLEMENTARY_CODE_POINT = 0x1D54F;
    private static final String REPLACEMENT = "X";
    private static final String TRAILING_CHARACTER = "Y";

    @Test
    void testSupplementaryKey() throws IOException {
        final String supplementaryKey = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));
        final LookupTranslator translator = new LookupTranslator(new CharSequence[][] { { supplementaryKey, REPLACEMENT } });

        final StringWriter translatedOutput = new StringWriter();
        final int consumedCodePoints = translator.translate(supplementaryKey, 0, translatedOutput);

        assertEquals(1, consumedCodePoints, "Incorrect code point consumption");
        assertEquals(REPLACEMENT, translatedOutput.toString(), "Incorrect value");
        assertEquals(REPLACEMENT + TRAILING_CHARACTER, translator.translate(supplementaryKey + TRAILING_CHARACTER),
                "Trailing character must be preserved");
    }
}
