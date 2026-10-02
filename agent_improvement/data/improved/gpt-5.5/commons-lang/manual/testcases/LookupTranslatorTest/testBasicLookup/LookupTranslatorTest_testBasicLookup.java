package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class LookupTranslatorTest_testBasicLookup extends AbstractLangTest {

    private static final String LOOKUP_KEY = "one";
    private static final String LOOKUP_VALUE = "two";
    private static final int START_OF_INPUT = 0;
    private static final int EXPECTED_CONSUMED_CODE_POINTS = 3;

    @Test
    void testBasicLookup() throws IOException {
        final LookupTranslator translator = new LookupTranslator(new CharSequence[][] { { LOOKUP_KEY, LOOKUP_VALUE } });
        final StringWriter output = new StringWriter();

        final int consumedCodePoints = translator.translate(LOOKUP_KEY, START_OF_INPUT, output);

        assertEquals(EXPECTED_CONSUMED_CODE_POINTS, consumedCodePoints, "Incorrect code point consumption");
        assertEquals(LOOKUP_VALUE, output.toString(), "Incorrect value");
    }
}
