package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link StringTokenizer} tokenizes a semicolon-delimited string when
 * quoting, whitespace trimming and empty-token handling are all enabled.
 */
public class StringTokenizerTest_test5 {

    @Test
    void tokenizesQuotedSemicolonSeparatedInputKeepingEmptyTokensAsNull() {
        // Input broken down (delimiter ';', quote '"'):
        //   a          -> "a"
        //   b          -> "b"
        //   " c"       -> "c"            (leading space trimmed away)
        //   "d;""e"    -> "d;\"e"        (quotes protect the ';' and an escaped quote)
        //   f          -> "f"
        //   " "        -> null           (whitespace-only field becomes empty, then null)
        //   " "        -> null
        //   (trailing) -> null
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        final String[] actualTokens = tokenizer.getTokenArray();
        final String[] expectedTokens = { "a", "b", "c", "d;\"e", "f", null, null, null };

        assertEquals(expectedTokens.length, actualTokens.length, Arrays.toString(actualTokens));
        for (int i = 0; i < expectedTokens.length; i++) {
            assertEquals(expectedTokens[i], actualTokens[i],
                "token[" + i + "] was '" + actualTokens[i] + "' but was expected to be '" + expectedTokens[i] + "'");
        }
    }
}
