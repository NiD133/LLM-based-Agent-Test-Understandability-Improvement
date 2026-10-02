package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed4 {

    @Test
    void testBasicIgnoreTrimmed4() {
        // Input has three colon-delimited fields; "IGNORE" substrings and surrounding whitespace
        // are stripped outside quotes. The quoted sections ('bIGNOREc' and 'd') are joined because
        // they are adjacent with no delimiter between them, yielding "bIGNOREcd" after quote removal.
        // The third field is empty; because emptyTokenAsNull=true it is returned as null.
        final String input = "IGNOREaIGNORE: IGNORE 'bIGNOREc'IGNORE'd' IGNORE : IGNORE ";
        final StringTokenizer tok = new StringTokenizer(input, ':', '\'');
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tok.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);

        assertEquals("a", tok.next());
        assertEquals("bIGNOREcd", tok.next());
        assertNull(tok.next());
        assertFalse(tok.hasNext());
    }
}
