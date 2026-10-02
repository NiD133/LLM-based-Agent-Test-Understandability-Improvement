package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    private static final int SUPPLEMENTARY_CODE_POINT = 0x1D54F;
    private static final String REPLACEMENT_TEXT = "X";
    private static final String TRAILING_TEXT = "Y";

    @Test
    void testSupplementaryKey() throws IOException {
        final String supplementaryKey = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));
        final LookupTranslator translator = new LookupTranslator(
                new CharSequence[][] { { supplementaryKey, REPLACEMENT_TEXT } });

        final StringWriter out = new StringWriter();
        final int consumedCodePoints = translator.translate(supplementaryKey, 0, out);

        assertEquals(1, consumedCodePoints, "Incorrect code point consumption");
        assertEquals(REPLACEMENT_TEXT, out.toString(), "Incorrect value");
        assertEquals(REPLACEMENT_TEXT + TRAILING_TEXT, translator.translate(supplementaryKey + TRAILING_TEXT),
                "Trailing character must be preserved");
    }
}
