package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class LookupTranslatorTest_testLang882 extends AbstractLangTest {

    // Tests: https://issues.apache.org/jira/browse/LANG-882
    @Test
    void testLang882() throws IOException {
        final CharSequence[][] lookup = { { new StringBuffer("one"), new StringBuffer("two") } };
        final LookupTranslator translator = new LookupTranslator(lookup);
        final StringWriter translatedText = new StringWriter();

        final int consumedCodePoints = translator.translate(new StringBuffer("one"), 0, translatedText);

        assertEquals(3, consumedCodePoints, "Incorrect code point consumption");
        assertEquals("two", translatedText.toString(), "Incorrect value");
    }
}
