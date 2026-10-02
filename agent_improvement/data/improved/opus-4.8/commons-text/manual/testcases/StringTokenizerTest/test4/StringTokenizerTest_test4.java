package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} correctly parses a semicolon-delimited
 * string while honouring quoting, whitespace trimming and empty-token removal.
 */
public class StringTokenizerTest_test4 {

    @Test
    void parsesSemicolonDelimitedStringWithQuotesTrimmingAndEmptyRemoval() {
        // Input broken down:
        //   a            -> "a"
        //   b            -> "b"
        //   " c"         -> "c"        (leading space trimmed)
        //   "\"d;\"\"e\"" -> d;"e      (quoted: delimiter and escaped quote kept)
        //   f            -> "f"
        //   " ", " ", "" -> dropped    (empty/whitespace-only tokens ignored)
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(true);

        final String[] actualTokens = tokenizer.getTokenArray();
        final String[] expectedTokens = { "a", "b", "c", "d;\"e", "f" };

        assertEquals(expectedTokens.length, actualTokens.length, Arrays.toString(actualTokens));
        for (int i = 0; i < expectedTokens.length; i++) {
            assertEquals(expectedTokens[i], actualTokens[i],
                    "token[" + i + "] was '" + actualTokens[i] + "' but was expected to be '" + expectedTokens[i] + "'");
        }
    }
}
