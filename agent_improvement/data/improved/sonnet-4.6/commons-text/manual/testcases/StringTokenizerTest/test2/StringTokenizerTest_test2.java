package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test2 {

    @Test
    void test2() {
        // Tokenize using ';' as delimiter and '"' as quote char, with no ignored chars and empty tokens preserved.
        // The quoted segment "d;""e" yields the literal token d;"e (doubled quote is an escaped quote inside quotes).
        final String input = "a;b;c ;\"d;\"\"e\";f; ; ;";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] tokens = tokenizer.getTokenArray();
        final String[] expected = { "a", "b", "c ", "d;\"e", "f", " ", " ", "" };

        assertEquals(expected.length, tokens.length, Arrays.toString(tokens));
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tokens[i],
                    "token[" + i + "] was '" + tokens[i] + "' but was expected to be '" + expected[i] + "'");
        }
    }
}
