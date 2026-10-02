package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted6 {

    private static final char DELIMITER = ':';
    private static final String INPUT_WITH_QUOTED_DELIMITER = "a:'b'\"c':d";

    @Test
    void testBasicQuoted6() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_QUOTED_DELIMITER, DELIMITER);
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.quoteMatcher());

        assertEquals("a", tokenizer.next());
        assertEquals("b\"c:d", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
