package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringTokenizer#getContent()}.
 *
 * <p>{@code getContent()} returns the original, unparsed text that the tokenizer
 * was given, or {@code null} when the tokenizer was created without any input.</p>
 */
public class StringTokenizerTest_testGetContent {

    /** Sample input containing whitespace and a quoted section, returned verbatim by getContent(). */
    private static final String INPUT = "a   b c \"d e\" f ";

    @Test
    void getContentReturnsInputGivenAsString() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT);

        assertEquals(INPUT, tokenizer.getContent());
    }

    @Test
    void getContentReturnsInputGivenAsCharArray() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT.toCharArray());

        assertEquals(INPUT, tokenizer.getContent());
    }

    @Test
    void getContentReturnsNullWhenNoInputProvided() {
        final StringTokenizer tokenizer = new StringTokenizer();

        assertNull(tokenizer.getContent());
    }
}
