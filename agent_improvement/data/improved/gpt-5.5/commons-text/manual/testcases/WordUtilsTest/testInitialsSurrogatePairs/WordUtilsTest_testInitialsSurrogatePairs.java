package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testInitialsSurrogatePairs {

    private static final String FIRST_INITIAL = "\uD800\uDF00";
    private static final String FIRST_WORD_REMAINDER = "\uD800\uDF01";
    private static final String SECOND_INITIAL = "\uD800\uDF02";
    private static final String SECOND_WORD_REMAINDER = "\uD800\uDF03";

    private static final String UTF_32_DELIMITER = "\uD800\uDF14";
    private static final String ANOTHER_UTF_32_DELIMITER = "\uD800\uDF18";

    private static final String EXPECTED_INITIALS = FIRST_INITIAL + SECOND_INITIAL;

    @Test
    void testInitialsSurrogatePairs() {
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(FIRST_INITIAL + FIRST_WORD_REMAINDER + " " + SECOND_INITIAL + SECOND_WORD_REMAINDER));
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(FIRST_INITIAL + FIRST_WORD_REMAINDER + " " + SECOND_INITIAL + SECOND_WORD_REMAINDER, null));
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(FIRST_INITIAL + " " + SECOND_INITIAL + " ", null));

        assertEquals(EXPECTED_INITIALS, WordUtils.initials(FIRST_INITIAL + FIRST_WORD_REMAINDER + "." + SECOND_INITIAL + SECOND_WORD_REMAINDER, new char[] { '.' }));
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(FIRST_INITIAL + FIRST_WORD_REMAINDER + "A" + SECOND_INITIAL + SECOND_WORD_REMAINDER, new char[] { 'A' }));

        assertEquals(EXPECTED_INITIALS, WordUtils.initials(FIRST_INITIAL + FIRST_WORD_REMAINDER + UTF_32_DELIMITER + SECOND_INITIAL + SECOND_WORD_REMAINDER, new char[] { '\uD800', '\uDF14' }));
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(FIRST_INITIAL + FIRST_WORD_REMAINDER + UTF_32_DELIMITER + ANOTHER_UTF_32_DELIMITER + SECOND_INITIAL + SECOND_WORD_REMAINDER, new char[] { '\uD800', '\uDF14', '\uD800', '\uDF18' }));
    }
}
