package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test1 {

    @Test
    @DisplayName("Tokenize semicolon-delimited input with quoted fields containing escaped quotes and empty tokens")
    void test1() {
        // Input: semicolon-delimited, with a quoted field "d;""e" (escaped quote inside), and trailing empty tokens
        final String input = "a;b;c;\"d;\"\"e\";f; ; ;  ";

        final StringTokenizer tok = new StringTokenizer(input);
        tok.setDelimiterChar(';');
        tok.setQuoteChar('"');
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tok.setIgnoreEmptyTokens(false);

        final String[] expected = { "a", "b", "c", "d;\"e", "f", "", "", "" };
        final String[] tokens = tok.getTokenArray();

        assertEquals(expected.length, tokens.length, "Token count mismatch");
        assertArrayEquals(expected, tokens, "Tokenized values do not match expected");
    }
}
