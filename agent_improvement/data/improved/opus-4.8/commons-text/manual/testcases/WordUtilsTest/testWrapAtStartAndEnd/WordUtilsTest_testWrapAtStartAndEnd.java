package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapAtStartAndEnd {

    /**
     * Verifies {@link WordUtils#wrap(String, int, String, boolean, String)} when the
     * break pattern matches at both the very start and the very end of the input.
     *
     * <p>The {@code wrapOn} regex {@code "(?=n)"} is a zero-width look-ahead that marks
     * a break point immediately before every {@code 'n'}. In the input there is an
     * {@code 'n'} at the first and last positions, so a {@code "\n"} separator is
     * inserted at the start and end, while the inner text is left untouched.</p>
     */
    @Test
    void testWrapAtStartAndEnd() {
        final String input = "nabcdefabcdefn";
        final int wrapLength = 2;
        final String newLineSeparator = "\n";
        final boolean wrapLongWords = false;
        final String breakBeforeEachN = "(?=n)";

        final String wrapped = WordUtils.wrap(
                input, wrapLength, newLineSeparator, wrapLongWords, breakBeforeEachN);

        assertEquals("\nabcdefabcdef\n", wrapped);
    }
}
