package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testDelimString {

    private static final String INPUT_WITH_REPEATED_HASH_DELIMITER = "a##b##c";
    private static final String REPEATED_HASH_DELIMITER = "##";

    @Test
    void testDelimString() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_REPEATED_HASH_DELIMITER, REPEATED_HASH_DELIMITER);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
