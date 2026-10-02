package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link Entities#unescape} strict-mode behaviour.
 *
 * <p>Strict unescaping requires that a named entity reference is properly
 * terminated with a semicolon (e.g. {@code &amp;}). Without the semicolon
 * (e.g. {@code &amp=}) the sequence is left untouched in strict mode but is
 * still unescaped in non-strict (lenient) mode.
 */
public class EntitiesTest_strictUnescape {

    // Contains both a malformed entity ("&amp=" — semicolon missing) and a
    // well-formed entity ("&amp;" — semicolon present).
    private static final String INPUT_WITH_MIXED_ENTITIES = "Hello &amp= &amp;";

    @Test
    public void strictMode_leavesEntityWithoutSemicolonUntouched() {
        // "&amp=" is not terminated with ';', so strict mode keeps it as-is.
        // "&amp;" is well-formed and is unescaped to '&'.
        String result = Entities.unescape(INPUT_WITH_MIXED_ENTITIES, true);
        assertEquals("Hello &amp= &", result);
    }

    @Test
    public void defaultNonStrictMode_unescapesBothFormats() {
        // The default (non-strict) overload unescapes "&amp=" and "&amp;" alike.
        String result = Entities.unescape(INPUT_WITH_MIXED_ENTITIES);
        assertEquals("Hello &= &", result);
    }

    @Test
    public void explicitNonStrictMode_unescapesBothFormats() {
        // Passing strict=false explicitly must produce the same result as the
        // no-argument overload.
        String result = Entities.unescape(INPUT_WITH_MIXED_ENTITIES, false);
        assertEquals("Hello &= &", result);
    }
}
