package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WordUtils#capitalizeFully(String)}.
 *
 * <p>{@code capitalizeFully} title-cases the first letter of every
 * whitespace-separated word and lower-cases the remaining letters of that word,
 * leaving all whitespace untouched.</p>
 */
public class WordUtilsTest_testCapitalizeFully_String {

    /**
     * Every Unicode code point that {@link Character#isWhitespace(char)} treats as
     * whitespace, concatenated into a single String. Used to verify that
     * {@code capitalizeFully} preserves the full range of whitespace characters.
     */
    private static final String ALL_WHITESPACE_CHARS =
            IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT)
                    .filter(Character::isWhitespace)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

    @Test
    void nullInputReturnsNull() {
        assertNull(WordUtils.capitalizeFully(null));
    }

    @Test
    void emptyAndWhitespaceOnlyInputsAreReturnedUnchanged() {
        assertEquals("", WordUtils.capitalizeFully(""));
        assertEquals("  ", WordUtils.capitalizeFully("  "));
    }

    @Test
    void singleLetterIsTitleCased() {
        assertEquals("I", WordUtils.capitalizeFully("I"));
        assertEquals("I", WordUtils.capitalizeFully("i"));
    }

    @Test
    void singleWordIsTitleCased() {
        assertEquals("Alphabet", WordUtils.capitalizeFully("alphabet"));
    }

    @Test
    void eachWordIsTitleCasedRegardlessOfOriginalCasing() {
        final String expected = "I Am Here 123";
        assertEquals(expected, WordUtils.capitalizeFully("i am here 123"));
        assertEquals(expected, WordUtils.capitalizeFully("I Am Here 123"));
        assertEquals(expected, WordUtils.capitalizeFully("i am HERE 123"));
        assertEquals(expected, WordUtils.capitalizeFully("I AM HERE 123"));
    }

    @Test
    void wordsSeparatedByVariousWhitespaceArePreserved() {
        assertEquals("A\tB\nC D", WordUtils.capitalizeFully("a\tb\nc d"));
        assertEquals("And \tBut \nCleat  Dome",
                WordUtils.capitalizeFully("and \tbut \ncleat  dome"));
    }

    @Test
    void allWhitespaceCharactersAreLeftUntouched() {
        assertEquals(ALL_WHITESPACE_CHARS, WordUtils.capitalizeFully(ALL_WHITESPACE_CHARS));
        assertEquals("A" + ALL_WHITESPACE_CHARS + "B",
                WordUtils.capitalizeFully("a" + ALL_WHITESPACE_CHARS + "b"));
    }
}
