package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_notMissingSupplementals {

    @Test
    public void notMissingSupplementals() {
        // Verifies that named HTML entities mapping to supplemental Unicode code points
        // (i.e. above U+FFFF, represented as surrogate pairs in Java) are not omitted
        // from the entity table and are resolved correctly by Entities.unescape().
        //
        // &npolint; -> U+2A14 N-ARY INTERSECTION WITH DOT (⨔), a BMP character
        // &qfr;     -> U+1D52E MATHEMATICAL FRAKTUR SMALL Q, a supplemental character
        //              encoded in Java as the surrogate pair 𝔮
        String htmlWithSupplementalEntities = "&npolint; &qfr;";
        String expectedUnescaped = "⨔ 𝔮";

        assertEquals(expectedUnescaped, Entities.unescape(htmlWithSupplementalEntities));
    }
}
