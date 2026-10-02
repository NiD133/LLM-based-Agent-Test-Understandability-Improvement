package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalizeFully_String {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT)
            .filter(Character::isWhitespace)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    @Test
    void testCapitalizeFully_String() {
        assertNull(WordUtils.capitalizeFully(null));

        assertCapitalizeFully("", "");
        assertCapitalizeFully("  ", "  ");

        assertCapitalizeFully("I", "I");
        assertCapitalizeFully("I", "i");
        assertCapitalizeFully("Alphabet", "alphabet");

        assertCapitalizeFully("I Am Here 123", "i am here 123");
        assertCapitalizeFully("I Am Here 123", "I Am Here 123");
        assertCapitalizeFully("I Am Here 123", "i am HERE 123");
        assertCapitalizeFully("I Am Here 123", "I AM HERE 123");

        assertCapitalizeFully("A\tB\nC D", "a\tb\nc d");
        assertCapitalizeFully("And \tBut \nCleat  Dome", "and \tbut \ncleat  dome");

        assertCapitalizeFully(WHITESPACE, WHITESPACE);
        assertCapitalizeFully("A" + WHITESPACE + "B", "a" + WHITESPACE + "b");
    }

    private static void assertCapitalizeFully(final String expected, final String input) {
        assertEquals(expected, WordUtils.capitalizeFully(input));
    }
}
