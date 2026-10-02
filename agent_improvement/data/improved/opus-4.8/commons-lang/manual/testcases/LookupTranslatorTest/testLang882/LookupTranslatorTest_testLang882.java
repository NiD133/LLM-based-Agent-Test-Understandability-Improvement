package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Regression test for LANG-882.
 *
 * <p>The lookup table is built from {@link StringBuffer} keys and values rather than
 * {@link String}s. Before LANG-882 the translator compared the raw {@code CharSequence}
 * keys directly, so a {@code StringBuffer} input never matched a {@code StringBuffer} key
 * (their {@code equals} is identity based). The fix converts both keys and inputs to
 * {@code String}, so a matching key must now be found.</p>
 *
 * @see <a href="https://issues.apache.org/jira/browse/LANG-882">LANG-882</a>
 */
@Deprecated
public class LookupTranslatorTest_testLang882 extends AbstractLangTest {

    @Test
    void testLang882() throws IOException {
        // Lookup table with a single rule: the key "one" maps to the value "two",
        // both supplied as StringBuffers to exercise the non-String CharSequence path.
        final LookupTranslator translator = new LookupTranslator(
                new CharSequence[][] { { new StringBuffer("one"), new StringBuffer("two") } });

        // Translate the input "one" starting at index 0.
        final StringWriter out = new StringWriter();
        final int codePointsConsumed = translator.translate(new StringBuffer("one"), 0, out);

        // The whole 3-character key "one" should be matched and consumed...
        assertEquals(3, codePointsConsumed, "Incorrect code point consumption");
        // ...and the mapped value "two" should be written to the output.
        assertEquals("two", out.toString(), "Incorrect value");
    }
}
