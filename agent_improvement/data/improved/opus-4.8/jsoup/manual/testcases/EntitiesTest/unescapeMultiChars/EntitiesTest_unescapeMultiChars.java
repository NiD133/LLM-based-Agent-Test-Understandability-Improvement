package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_unescapeMultiChars {

    /**
     * Verifies handling of named entities that map to multiple characters, including ones whose
     * names share a prefix (e.g. "Gt", "Gg", "NestedGreaterGreater") so prefix matching must pick
     * the correct, full entity rather than a shorter conflicting one.
     */
    @Test
    public void unescapeMultiChars() {
        // A mix of single- and multi-character entities. "gg" is not a multi-char combo, but the
        // codepoint 8811 (≫) could be confused with NestedGreaterGreater or other ≫-based entities.
        String escapedInput = "&NestedGreaterGreater; &nGg; &nGt; &nGtv; &Gt; &gg;";
        String expectedUnescaped = "≫ ⋙̸ ≫⃒ ≫̸ ≫ ≫";

        // Unescaping the named entities yields the expected characters.
        assertEquals(expectedUnescaped, Entities.unescape(escapedInput));

        // Re-escaping to ASCII with the extended entity set: each character maps back to a known
        // entity where possible, with combining marks emitted as numeric references.
        OutputSettings asciiExtended = new OutputSettings().charset("ascii").escapeMode(extended);
        String reEscaped = Entities.escape(expectedUnescaped, asciiExtended);
        assertEquals("&Gt; &Gg;&#x338; &Gt;&#x20d2; &Gt;&#x338; &Gt; &Gt;", reEscaped);

        // The round trip is stable: unescaping the re-escaped form returns the same characters.
        assertEquals(expectedUnescaped, Entities.unescape(reEscaped));
    }
}
