package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link NumericEntityUnescaper} correctly unescapes a numeric entity
 * whose code point lies outside the Basic Multilingual Plane (a "supplementary" character).
 */
@Deprecated
public class NumericEntityUnescaperTest_testSupplementaryUnescaping extends AbstractLangTest {

    @Test
    void testSupplementaryUnescaping() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        // "&#68642;" is the XML numeric entity for decimal code point 68642 (U+10C22),
        // a supplementary character that Java encodes as the UTF-16 surrogate pair U+D803 U+DC22.
        final String entity = "&#68642;";
        final String expectedSurrogatePair = "𐰢";

        final String actual = unescaper.translate(entity);

        assertEquals(expectedSurrogatePair, actual,
                "Failed to unescape numeric entities supplementary characters");
    }
}
