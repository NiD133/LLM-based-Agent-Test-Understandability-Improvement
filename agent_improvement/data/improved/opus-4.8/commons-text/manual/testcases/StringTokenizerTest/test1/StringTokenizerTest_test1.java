package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test1 {

    /**
     * Verifies tokenization of a semicolon-delimited string that exercises quoting,
     * escaped quotes, surrounding whitespace, and empty fields all at once.
     *
     * <p>The input {@code a;b;c;"d;""e";f; ; ;  } is parsed with:</p>
     * <ul>
     *   <li>{@code ;} as the delimiter character,</li>
     *   <li>{@code "} as the quote character,</li>
     *   <li>a trimmer that removes surrounding whitespace, and</li>
     *   <li>empty tokens retained (not ignored).</li>
     * </ul>
     *
     * <p>Expected behaviour, field by field:</p>
     * <ul>
     *   <li>{@code a}, {@code b}, {@code c} &rarr; plain tokens,</li>
     *   <li>{@code "d;""e"} &rarr; {@code d;"e} (the delimiter is protected by quotes and
     *       the doubled quote is unescaped to a single quote),</li>
     *   <li>{@code f} &rarr; plain token,</li>
     *   <li>the three trailing whitespace-only fields &rarr; three empty tokens.</li>
     * </ul>
     */
    @Test
    void parsesQuotedAndEmptyFieldsWithSemicolonDelimiter() {
        final String input = "a;b;c;\"d;\"\"e\";f; ; ;  ";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] actualTokens = tokenizer.getTokenArray();
        final String[] expectedTokens = { "a", "b", "c", "d;\"e", "f", "", "", "" };

        assertEquals(expectedTokens.length, actualTokens.length, Arrays.toString(actualTokens));
        for (int i = 0; i < expectedTokens.length; i++) {
            assertEquals(expectedTokens[i], actualTokens[i],
                    "token[" + i + "] was '" + actualTokens[i] + "' but was expected to be '" + expectedTokens[i] + "'");
        }
    }
}
