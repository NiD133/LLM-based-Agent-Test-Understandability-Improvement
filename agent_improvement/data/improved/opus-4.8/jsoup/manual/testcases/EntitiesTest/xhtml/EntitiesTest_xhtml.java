package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Entities.EscapeMode.xhtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies the round-trip mapping between the four XHTML named entities and their
 * Unicode codepoints, using the {@link Entities.EscapeMode#xhtml} escape mode.
 *
 * <p>The {@code xhtml} escape mode supports exactly four entities: {@code amp}, {@code gt},
 * {@code lt} and {@code quot}. For each one we check both directions:
 * <ul>
 *   <li>{@code codepointForName} maps the entity name to its codepoint, and</li>
 *   <li>{@code nameForCodepoint} maps that codepoint back to the entity name.</li>
 * </ul>
 */
public class EntitiesTest_xhtml {

    // The four XHTML entities, paired with the ASCII codepoint of the character each represents.
    private static final String AMP_NAME = "amp";   // '&'
    private static final int AMP_CODEPOINT = 38;

    private static final String GT_NAME = "gt";     // '>'
    private static final int GT_CODEPOINT = 62;

    private static final String LT_NAME = "lt";     // '<'
    private static final int LT_CODEPOINT = 60;

    private static final String QUOT_NAME = "quot"; // '"'
    private static final int QUOT_CODEPOINT = 34;

    @Test
    public void xhtml() {
        // Name -> codepoint
        assertEquals(AMP_CODEPOINT, xhtml.codepointForName(AMP_NAME));
        assertEquals(GT_CODEPOINT, xhtml.codepointForName(GT_NAME));
        assertEquals(LT_CODEPOINT, xhtml.codepointForName(LT_NAME));
        assertEquals(QUOT_CODEPOINT, xhtml.codepointForName(QUOT_NAME));

        // Codepoint -> name
        assertEquals(AMP_NAME, xhtml.nameForCodepoint(AMP_CODEPOINT));
        assertEquals(GT_NAME, xhtml.nameForCodepoint(GT_CODEPOINT));
        assertEquals(LT_NAME, xhtml.nameForCodepoint(LT_CODEPOINT));
        assertEquals(QUOT_NAME, xhtml.nameForCodepoint(QUOT_CODEPOINT));
    }
}
