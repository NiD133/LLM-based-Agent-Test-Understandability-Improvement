package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests {@link StringUtil#isWhitespace(int)}, which recognises only the five
 * whitespace characters defined by the HTML spec.
 */
public class StringUtilTest_isWhitespace {

    @Test
    public void recognisesHtmlSpecWhitespaceCharacters() {
        // The HTML spec treats exactly these five characters as whitespace.
        assertTrue(StringUtil.isWhitespace('\t'), "horizontal tab");
        assertTrue(StringUtil.isWhitespace('\n'), "line feed");
        assertTrue(StringUtil.isWhitespace('\r'), "carriage return");
        assertTrue(StringUtil.isWhitespace('\f'), "form feed");
        assertTrue(StringUtil.isWhitespace(' '), "space");

        // Other Unicode space-like characters are NOT whitespace under the HTML spec.
        assertFalse(StringUtil.isWhitespace(' '), "non-breaking space");
        assertFalse(StringUtil.isWhitespace(' '), "en quad");
        assertFalse(StringUtil.isWhitespace('　'), "ideographic space");
    }
}
