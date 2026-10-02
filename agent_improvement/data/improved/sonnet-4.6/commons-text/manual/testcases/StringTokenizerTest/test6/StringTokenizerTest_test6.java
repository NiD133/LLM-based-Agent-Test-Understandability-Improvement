package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test6 {

    @Test
    void test6() {
        // Input with semicolon delimiter, quoted tokens containing escaped quotes,
        // leading/trailing whitespace to be ignored, and trailing empty fields.
        // Empty tokens are preserved as null (default emptyTokenAsNull behaviour when
        // ignoreEmptyTokens is disabled).
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";
        final StringTokenizer tok = new StringTokenizer(input);
        tok.setDelimiterChar(';');
        tok.setQuoteChar('"');
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tok.setIgnoreEmptyTokens(false);
        // Note: emptyTokenAsNull is intentionally left at its default (false), so
        // empty fields are returned as null tokens rather than empty strings by the
        // tokenizer's internal handling when content is fully stripped by the ignored matcher.
        // tok.setTreatingEmptyAsNull(true);

        final String[] tokens = tok.getTokenArray();

        // Expected: whitespace ignored around tokens, quoted "d;""e" unescaped to d;"e,
        // and three trailing empty (null) tokens for "; ; ;" after 'f'.
        final String[] expected = { "a", "b", " c", "d;\"e", "f", null, null, null };

        assertEquals(expected.length, tokens.length,
                "Token count mismatch; actual tokens: " + Arrays.toString(tokens));

        // Verify bidirectional iteration covers all tokens exactly once.
        int nextCount = 0;
        while (tok.hasNext()) {
            tok.next();
            nextCount++;
        }

        int prevCount = 0;
        while (tok.hasPrevious()) {
            tok.previous();
            prevCount++;
        }

        assertEquals(expected.length, nextCount,
                "could not cycle through entire token list using the 'hasNext' and 'next' methods");
        assertEquals(expected.length, prevCount,
                "could not cycle through entire token list using the 'hasPrevious' and 'previous' methods");
    }
}
