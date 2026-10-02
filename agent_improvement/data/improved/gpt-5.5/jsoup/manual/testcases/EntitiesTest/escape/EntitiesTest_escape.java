package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.jsoup.nodes.Entities.EscapeMode.xhtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escape {
    private static final String TEXT_WITH_HTML_AND_NON_ASCII_CHARS =
        "Hello &<> Å å π 新 there ¾ © » ' \"";

    @Test
    public void escape() {
        // Entities.escape is intentionally maximal: it escapes for safe use in both text and attributes.
        String escapedAsciiBase = Entities.escape(
            TEXT_WITH_HTML_AND_NON_ASCII_CHARS,
            new OutputSettings().charset("ascii").escapeMode(base)
        );
        String escapedAsciiExtended = Entities.escape(
            TEXT_WITH_HTML_AND_NON_ASCII_CHARS,
            new OutputSettings().charset("ascii").escapeMode(extended)
        );
        String escapedAsciiXhtml = Entities.escape(
            TEXT_WITH_HTML_AND_NON_ASCII_CHARS,
            new OutputSettings().charset("ascii").escapeMode(xhtml)
        );
        String escapedUtfExtended = Entities.escape(
            TEXT_WITH_HTML_AND_NON_ASCII_CHARS,
            new OutputSettings().charset("UTF-8").escapeMode(extended)
        );
        String escapedUtfXhtml = Entities.escape(
            TEXT_WITH_HTML_AND_NON_ASCII_CHARS,
            new OutputSettings().charset("UTF-8").escapeMode(xhtml)
        );

        assertEquals(
            "Hello &amp;&lt;&gt; &Aring; &aring; &#x3c0; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;",
            escapedAsciiBase
        );
        assertEquals(
            "Hello &amp;&lt;&gt; &angst; &aring; &pi; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;",
            escapedAsciiExtended
        );
        assertEquals(
            "Hello &amp;&lt;&gt; &#xc5; &#xe5; &#x3c0; &#x65b0; there &#xbe; &#xa9; &#xbb; &#x27; &quot;",
            escapedAsciiXhtml
        );
        assertEquals(
            "Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;",
            escapedUtfExtended
        );
        assertEquals(
            "Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &#x27; &quot;",
            escapedUtfXhtml
        );

        assertEquals(TEXT_WITH_HTML_AND_NON_ASCII_CHARS, Entities.unescape(escapedAsciiBase));
        assertEquals(TEXT_WITH_HTML_AND_NON_ASCII_CHARS, Entities.unescape(escapedAsciiExtended));
        assertEquals(TEXT_WITH_HTML_AND_NON_ASCII_CHARS, Entities.unescape(escapedAsciiXhtml));
        assertEquals(TEXT_WITH_HTML_AND_NON_ASCII_CHARS, Entities.unescape(escapedUtfExtended));
        assertEquals(TEXT_WITH_HTML_AND_NON_ASCII_CHARS, Entities.unescape(escapedUtfXhtml));
    }
}
