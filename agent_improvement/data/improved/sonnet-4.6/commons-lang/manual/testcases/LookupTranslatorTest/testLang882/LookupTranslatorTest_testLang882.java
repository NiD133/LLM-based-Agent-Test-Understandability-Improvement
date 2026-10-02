package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Regression test for LANG-882: LookupTranslator must accept any CharSequence
 * implementation (e.g. StringBuffer, StringBuilder) as lookup keys, not only String.
 * Internally the translator converts keys to String so that hashCode/equals work
 * correctly inside its HashMap.
 *
 * @see <a href="https://issues.apache.org/jira/browse/LANG-882">LANG-882</a>
 */
@Deprecated
public class LookupTranslatorTest_testLang882 extends AbstractLangTest {

    @Test
    void testLang882() throws IOException {
        // Use StringBuffer (not String) as key and value to exercise LANG-882.
        // Prior to the fix, a StringBuffer key was not found in the internal HashMap
        // because StringBuffer.equals(String) returns false.
        final CharSequence[][] lookupTable = {
            { new StringBuffer("one"), new StringBuffer("two") }
        };
        final LookupTranslator translator = new LookupTranslator(lookupTable);

        final StringWriter output = new StringWriter();
        final int codePointsConsumed = translator.translate(new StringBuffer("one"), 0, output);

        // "one" is 3 characters, so 3 code points should have been consumed.
        assertEquals(3, codePointsConsumed, "Incorrect code point consumption");
        // The matched key "one" must be translated to "two".
        assertEquals("two", output.toString(), "Incorrect value");
    }
}
