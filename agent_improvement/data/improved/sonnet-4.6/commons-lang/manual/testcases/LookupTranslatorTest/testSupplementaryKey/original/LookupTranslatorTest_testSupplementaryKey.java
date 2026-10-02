package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    @Test
    void testSupplementaryKey() throws IOException {
        // a single supplementary code point
        final String key = new String(Character.toChars(0x1D54F));
        final LookupTranslator lt = new LookupTranslator(new CharSequence[][] { { key, "X" } });
        final StringWriter out = new StringWriter();
        final int result = lt.translate(key, 0, out);
        assertEquals(1, result, "Incorrect code point consumption");
        assertEquals("X", out.toString(), "Incorrect value");
        // the matched key must not over-consume the following character
        assertEquals("XY", lt.translate(key + "Y"), "Trailing character must be preserved");
    }
}
