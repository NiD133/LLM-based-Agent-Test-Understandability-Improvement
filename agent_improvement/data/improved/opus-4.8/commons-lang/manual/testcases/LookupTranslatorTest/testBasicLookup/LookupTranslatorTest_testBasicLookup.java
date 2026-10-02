package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LookupTranslator} replaces a known key with its mapped
 * value and reports how many code points of the input were consumed.
 */
@Deprecated
public class LookupTranslatorTest_testBasicLookup extends AbstractLangTest {

    @Test
    void testBasicLookup() throws IOException {
        // Lookup table with a single mapping: "one" -> "two".
        final CharSequence[][] lookupTable = { { "one", "two" } };
        final LookupTranslator translator = new LookupTranslator(lookupTable);

        // Translate the input "one" starting at index 0.
        final StringWriter translationOutput = new StringWriter();
        final int consumedCodePoints = translator.translate("one", 0, translationOutput);

        // "one" is three code points long, so all three are consumed.
        assertEquals(3, consumedCodePoints, "Incorrect code point consumption");
        // The matched key "one" is translated to its mapped value "two".
        assertEquals("two", translationOutput.toString(), "Incorrect value");
    }
}
