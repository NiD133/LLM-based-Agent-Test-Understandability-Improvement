package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link StringTokenizer} tokenizes a semicolon-delimited string that mixes
 * quoting, escaped quotes, leading spaces and empty fields.
 */
public class StringTokenizerTest_test3 {

    @Test
    void tokenizesSemicolonDelimitedStringWithQuotesAndEmptyFields() {
        // Input field-by-field (delimiter is ';'):
        //   a            -> "a"
        //   b            -> "b"
        //   " c"         -> " c"   (leading space preserved, no trimmer configured)
        //   "d;""e"      -> "d;\"e" (quoted: delimiter kept as data, "" is an escaped quote)
        //   f            -> "f"
        //   " "          -> " "
        //   " "          -> " "
        //   (trailing)   -> ""     (empty field after the final delimiter)
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] expected = { "a", "b", " c", "d;\"e", "f", " ", " ", "" };
        final String[] actual = tokenizer.getTokenArray();

        assertEquals(expected.length, actual.length,
                "unexpected number of tokens: " + Arrays.toString(actual));
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i],
                    "token[" + i + "] was '" + actual[i] + "' but was expected to be '" + expected[i] + "'");
        }
    }
}
