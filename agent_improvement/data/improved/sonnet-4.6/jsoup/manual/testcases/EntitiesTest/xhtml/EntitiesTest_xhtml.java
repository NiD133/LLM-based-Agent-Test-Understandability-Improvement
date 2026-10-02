package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Entities.EscapeMode.xhtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the XHTML escape mode, which restricts entity references to the four
 * XML-required entities: amp, gt, lt, and quot.
 */
public class EntitiesTest_xhtml {

    // Unicode codepoints for the four XHTML-mandated entities
    private static final int CODEPOINT_AMP  = 38;  // '&'
    private static final int CODEPOINT_GT   = 62;  // '>'
    private static final int CODEPOINT_LT   = 60;  // '<'
    private static final int CODEPOINT_QUOT = 34;  // '"'

    @Test
    public void xhtml_nameToCodepoint_returnsCorrectCodepointForEachXhtmlEntity() {
        assertEquals(CODEPOINT_AMP,  xhtml.codepointForName("amp"));
        assertEquals(CODEPOINT_GT,   xhtml.codepointForName("gt"));
        assertEquals(CODEPOINT_LT,   xhtml.codepointForName("lt"));
        assertEquals(CODEPOINT_QUOT, xhtml.codepointForName("quot"));
    }

    @Test
    public void xhtml_codepointToName_returnsCorrectNameForEachXhtmlCodepoint() {
        assertEquals("amp",  xhtml.nameForCodepoint(CODEPOINT_AMP));
        assertEquals("gt",   xhtml.nameForCodepoint(CODEPOINT_GT));
        assertEquals("lt",   xhtml.nameForCodepoint(CODEPOINT_LT));
        assertEquals("quot", xhtml.nameForCodepoint(CODEPOINT_QUOT));
    }
}
