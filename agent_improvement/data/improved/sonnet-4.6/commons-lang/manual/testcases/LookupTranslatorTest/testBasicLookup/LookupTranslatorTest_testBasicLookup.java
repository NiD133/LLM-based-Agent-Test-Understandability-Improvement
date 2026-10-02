package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LookupTranslator} correctly translates a matched key to its mapped value
 * and reports how many characters were consumed from the input.
 */
@Deprecated
public class LookupTranslatorTest_testBasicLookup extends AbstractLangTest {

    /**
     * Verifies that when the translator finds the key "one" at position 0 of the input,
     * it writes the translated value "two" to the output and returns 3 (the length of "one")
     * to indicate that three characters were consumed.
     */
    @Test
    void testBasicLookup() throws IOException {
        // Build a translator with a single "one" -> "two" mapping
        final LookupTranslator translator = new LookupTranslator(new CharSequence[][] { { "one", "two" } });

        // Translate starting at index 0 of the input string "one"
        final StringWriter output = new StringWriter();
        final int charsConsumed = translator.translate("one", 0, output);

        // The entire 3-character key "one" should have been consumed ...
        assertEquals(3, charsConsumed, "Incorrect code point consumption");
        // ... and the translated value "two" should have been written to the output
        assertEquals("two", output.toString(), "Incorrect value");
    }
}
