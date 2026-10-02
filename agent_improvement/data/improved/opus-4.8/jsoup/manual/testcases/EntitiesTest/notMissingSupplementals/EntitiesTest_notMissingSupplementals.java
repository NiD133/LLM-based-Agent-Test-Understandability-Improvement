package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link Entities#unescape(String)} correctly resolves named entities whose
 * characters live in the Unicode supplementary planes (code points above U+FFFF, which Java
 * represents with surrogate pairs). These references must not be dropped or corrupted.
 */
public class EntitiesTest_notMissingSupplementals {

    @Test
    public void notMissingSupplementals() {
        // &npolint; -> U+2A14 (a Basic-Multilingual-Plane character)
        // &qfr;     -> U+1D52E "MATHEMATICAL FRAKTUR SMALL Q", a supplementary character
        //              encoded in Java as the surrogate pair 𝔮.
        String escaped = "&npolint; &qfr;";
        String expectedUnescaped = "⨔ 𝔮";

        assertEquals(expectedUnescaped, Entities.unescape(escaped));
    }
}
