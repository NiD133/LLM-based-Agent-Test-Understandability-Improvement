package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test5 {

    /**
     * Tests tokenization of a semicolon-delimited string with:
     * - double-quote quoting (escaped quotes within a token use "" notation)
     * - whitespace trimming applied to each token before it is returned
     * - empty tokens preserved (not dropped), but returned as null
     *
     * Input:  "a;b; c;\"d;\"\"e\";f; ; ;"
     *
     * Breakdown of each field between semicolons:
     *   [0] "a"          -> token "a"
     *   [1] "b"          -> token "b"
     *   [2] " c"         -> trim -> token "c"
     *   [3] "\"d;\"\"e\""-> quoted section: d;""e -> token "d;\"e"
     *   [4] "f"          -> token "f"
     *   [5] " "          -> trim -> empty -> null
     *   [6] " "          -> trim -> empty -> null
     *   [7] ""           -> empty -> null
     */
    @Test
    void test5() {
        // Arrange
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        // Act
        final String[] tokens = tokenizer.getTokenArray();

        // Assert
        final String[] expected = { "a", "b", "c", "d;\"e", "f", null, null, null };

        assertEquals(expected.length, tokens.length,
                "Token count mismatch; actual tokens: " + Arrays.toString(tokens));

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tokens[i],
                    "token[" + i + "] expected <" + expected[i] + "> but was <" + tokens[i] + ">");
        }
    }
}
