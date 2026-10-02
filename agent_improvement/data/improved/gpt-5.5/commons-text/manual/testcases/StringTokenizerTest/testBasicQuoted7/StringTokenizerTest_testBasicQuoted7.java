package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted7 {

    private static final char FIELD_DELIMITER = ':';
    private static final String QUOTED_MIDDLE_FIELD = "There's a reason here";
    private static final String INPUT_WITH_QUOTED_DELIMITERS = "a:\"" + QUOTED_MIDDLE_FIELD + "\":b";

    @Test
    void testBasicQuoted7() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_QUOTED_DELIMITERS, FIELD_DELIMITER);
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.quoteMatcher());

        assertEquals("a", tokenizer.next());
        assertEquals(QUOTED_MIDDLE_FIELD, tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
