package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted6 {

    @Test
    void testBasicQuoted6() {
        // Input uses ':' as delimiter and contains mixed single- and double-quoted segments.
        // With the quoteMatcher active, 'b' and "c" are quoted sections; adjacent quoted
        // sections are concatenated and the enclosing quotes are stripped.
        // The ':' inside "c':d" is part of the quoted region, so it is NOT a delimiter.
        // Expected tokens: "a" | "b\"c:d"  (only one split at the first unquoted ':')
        final String input = "a:'b'\"c':d";
        final StringTokenizer tok = new StringTokenizer(input, ':');
        tok.setQuoteMatcher(StringMatcherFactory.INSTANCE.quoteMatcher());

        assertEquals("a", tok.next());
        assertEquals("b\"c:d", tok.next());
        assertFalse(tok.hasNext());
    }
}
