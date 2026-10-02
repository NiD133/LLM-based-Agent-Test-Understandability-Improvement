package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} correctly parses a semicolon-separated
 * string that exercises quoting, escaped quotes and empty fields.
 */
public class StringTokenizerTest_test2 {

    @Test
    void parsesQuotedAndEmptyFieldsWithSemicolonDelimiter() {
        // Input fields (separated by ';'):
        //   a            -> "a"
        //   b            -> "b"
        //   c            -> "c " (trailing space kept; the default tokenizer does not trim)
        //   "d;""e"      -> "d;\"e" (quoted, so the ';' is data and "" is an escaped quote)
        //   f            -> "f"
        //   (space)      -> " "
        //   (space)      -> " "
        //   (empty)      -> "" (trailing delimiter yields a final empty token)
        final String input = "a;b;c ;\"d;\"\"e\";f; ; ;";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] expectedTokens = { "a", "b", "c ", "d;\"e", "f", " ", " ", "" };
        final String[] actualTokens = tokenizer.getTokenArray();

        assertEquals(expectedTokens.length, actualTokens.length,
                "Unexpected number of tokens");
        for (int i = 0; i < expectedTokens.length; i++) {
            assertEquals(expectedTokens[i], actualTokens[i],
                    "Mismatch at token index " + i);
        }
    }
}
