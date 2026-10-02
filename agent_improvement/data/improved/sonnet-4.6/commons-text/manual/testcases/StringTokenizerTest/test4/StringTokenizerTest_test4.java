package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test4 {

    @Test
    void test4() {
        // Tokenize with ';' delimiter, '"' quote char, trim-whitespace ignored matcher,
        // and empty-token suppression. Quoted sections may contain escaped quotes ("").
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";
        final StringTokenizer tok = new StringTokenizer(input);
        tok.setDelimiterChar(';');
        tok.setQuoteChar('"');
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tok.setIgnoreEmptyTokens(true);

        final String[] tokens = tok.getTokenArray();
        final String[] expected = { "a", "b", "c", "d;\"e", "f" };

        assertEquals(expected.length, tokens.length, Arrays.toString(tokens));
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tokens[i],
                    "token[" + i + "] was '" + tokens[i] + "' but was expected to be '" + expected[i] + "'");
        }
    }
}
