package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#wrap(String, int, String, boolean, String)}.
 *
 * <p>This overload word-wraps {@code str} at {@code wrapLength} columns, inserting
 * {@code newLineStr} as the line separator and breaking words on characters matched
 * by the {@code wrapOn} regex. When {@code wrapLongWords} is {@code true}, words that
 * are themselves longer than {@code wrapLength} are split as well.</p>
 */
public class WordUtilsTest_testWrap_StringIntStringBooleanString {

    /** Line separator inserted between wrapped lines. */
    private static final String NEW_LINE = "\n";

    /** Regex of characters the wrapper is allowed to break on. */
    private static final String WRAP_ON_SLASH = "/";

    @Test
    void testWrap_StringIntStringBooleanString() {
        // Width larger than the input: nothing is wrapped, the text is returned unchanged.
        assertEquals(
            "flammable/inflammable",
            WordUtils.wrap("flammable/inflammable", 30, NEW_LINE, false, WRAP_ON_SLASH));

        // Tiny width: even without long-word splitting, the break still happens at the "/".
        assertEquals(
            "flammable\ninflammable",
            WordUtils.wrap("flammable/inflammable", 2, NEW_LINE, false, WRAP_ON_SLASH));

        // Width 9 with long-word splitting: "inflammable" (11 chars) exceeds the width,
        // so it is split as well as broken on the "/".
        assertEquals(
            "flammable\ninflammab\nle",
            WordUtils.wrap("flammable/inflammable", 9, NEW_LINE, true, WRAP_ON_SLASH));

        // Width 15 with long-word splitting: each segment around the "/" fits within 15,
        // so only the "/" break is applied.
        assertEquals(
            "flammable\ninflammable",
            WordUtils.wrap("flammable/inflammable", 15, NEW_LINE, true, WRAP_ON_SLASH));

        // Width 15 with long-word splitting and no "/" to break on: the single 20-char
        // word is forcibly split at column 15.
        assertEquals(
            "flammableinflam\nmable",
            WordUtils.wrap("flammableinflammable", 15, NEW_LINE, true, WRAP_ON_SLASH));
    }
}
