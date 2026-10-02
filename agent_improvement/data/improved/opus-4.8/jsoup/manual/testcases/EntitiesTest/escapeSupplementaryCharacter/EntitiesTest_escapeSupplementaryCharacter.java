package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapeSupplementaryCharacter {

    /**
     * A supplementary character (codepoint above U+FFFF, here U+210C1) is encoded
     * in Java as a surrogate pair. When escaping for a charset that cannot represent
     * it (ASCII), it must be emitted as a numeric character reference; when escaping
     * for a charset that can represent it (UTF-8), it must be left unchanged.
     */
    @Test
    public void escapeSupplementaryCharacter() {
        // U+210C1 is a supplementary-plane codepoint, built as its surrogate-pair String.
        int supplementaryCodePoint = 0x210C1;
        String supplementaryChar = new String(Character.toChars(supplementaryCodePoint));

        // ASCII cannot encode the character, so it is escaped as a hex numeric reference.
        OutputSettings asciiSettings = new OutputSettings().charset("ascii").escapeMode(base);
        String escapedForAscii = Entities.escape(supplementaryChar, asciiSettings);
        assertEquals("&#x210c1;", escapedForAscii);

        // UTF-8 can encode the character directly, so it is left unchanged.
        OutputSettings utf8Settings = new OutputSettings().charset("UTF-8").escapeMode(base);
        String escapedForUtf8 = Entities.escape(supplementaryChar, utf8Settings);
        assertEquals(supplementaryChar, escapedForUtf8);
    }
}
