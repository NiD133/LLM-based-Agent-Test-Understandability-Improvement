package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test3 {

    @Test
    void test3() {
        // Tokenize a semicolon-delimited string with double-quote escaping and empty tokens retained.
        // The input "d;""e" is a quoted token containing a literal semicolon and escaped double-quote.
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";
        final StringTokenizer tok = new StringTokenizer(input);
        tok.setDelimiterChar(';');
        tok.setQuoteChar('"');
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tok.setIgnoreEmptyTokens(false);

        final String[] tokens = tok.getTokenArray();
        final String[] expected = { "a", "b", " c", "d;\"e", "f", " ", " ", "" };

        assertEquals(expected.length, tokens.length, Arrays.toString(tokens));
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tokens[i], "token[" + i + "]");
        }
    }
}
