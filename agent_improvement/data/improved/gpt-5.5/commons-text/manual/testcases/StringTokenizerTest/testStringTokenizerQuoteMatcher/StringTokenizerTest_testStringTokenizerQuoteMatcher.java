package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testStringTokenizerQuoteMatcher {

    @Test
    void testStringTokenizerQuoteMatcher() {
        final char[] quotedTokenInput = { '\'', 'a', 'c', '\'', 'd' };
        final StringMatcher commaDelimiter = StringMatcherFactory.INSTANCE.commaMatcher();
        final StringMatcher singleQuoteMatcher = StringMatcherFactory.INSTANCE.quoteMatcher();

        final StringTokenizer tokens = new StringTokenizer(quotedTokenInput, commaDelimiter, singleQuoteMatcher);

        assertEquals("acd", tokens.next());
    }
}
