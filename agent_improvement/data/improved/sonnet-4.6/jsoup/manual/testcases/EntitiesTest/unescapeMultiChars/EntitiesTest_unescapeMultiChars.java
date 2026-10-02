package org.jsoup.nodes;

import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that HTML entities resolving to multi-codepoint sequences (e.g. &nGg; → ⋙ + combining stroke)
 * are correctly unescaped and that re-escaping produces the canonical entity names.
 */
public class EntitiesTest_unescapeMultiChars {

    /**
     * Several "greater-than" related named entities can map to the same base character (U+226B ≫),
     * while others like &nGg; and &nGt; expand to two codepoints (base char + combining overlay).
     *
     * After unescaping these entities and then re-escaping back to ASCII using the extended escape mode,
     * the output should use the canonical entity name (&Gt; for U+226B) plus numeric references for
     * any combining characters that have no standalone named entity.
     *
     * A final unescape of the re-escaped string must reproduce the original unescaped Unicode text,
     * confirming a lossless round-trip through escape → unescape.
     */
    @Test
    public void unescapeMultiChars() {
        // Input: six named entities that all involve U+226B (MUCH GREATER-THAN, ≫).
        // &nGg; and &nGtv; are "negated" forms that add a combining stroke (U+0338 ̸) after ≫.
        // &nGt; adds a different combining overlay (U+20D2 ⃒) after ≫.
        // &NestedGreaterGreater;, &Gt;, and &gg; are three different names for the plain ≫ character.
        String htmlWithMultiCharEntities = "&NestedGreaterGreater; &nGg; &nGt; &nGtv; &Gt; &gg;";

        // Expected Unicode string after unescaping all six entities.
        // ≫         (U+226B)                — from &NestedGreaterGreater;
        // ⋙̸        (U+22D9 + U+0338)       — from &nGg; (triple-greater + combining long solidus)
        // ≫⃒        (U+226B + U+20D2)       — from &nGt; (much-greater + combining long vertical line)
        // ≫̸         (U+226B + U+0338)       — from &nGtv; (much-greater + combining long solidus)
        // ≫         (U+226B)                — from &Gt;
        // ≫         (U+226B)                — from &gg;
        String expectedUnescaped = "≫ ⋙̸ ≫⃒ ≫̸ ≫ ≫";
        assertEquals(expectedUnescaped, Entities.unescape(htmlWithMultiCharEntities));

        // Re-escape the Unicode string to ASCII using the extended entity set.
        // The encoder uses canonical names: &Gt; for U+226B and &Gg; for U+22D9.
        // Combining characters (U+0338, U+20D2) have no named entity, so they become numeric hex refs.
        OutputSettings asciiExtended = new OutputSettings().charset("ascii").escapeMode(extended);
        String reEscaped = Entities.escape(expectedUnescaped, asciiExtended);
        assertEquals("&Gt; &Gg;&#x338; &Gt;&#x20d2; &Gt;&#x338; &Gt; &Gt;", reEscaped);

        // Verify round-trip: unescaping the re-escaped form reproduces the original Unicode text.
        assertEquals(expectedUnescaped, Entities.unescape(reEscaped));
    }
}
