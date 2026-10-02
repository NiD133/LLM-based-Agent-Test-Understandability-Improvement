package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.jsoup.nodes.Entities.EscapeMode.xhtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escape {

    /**
     * The sample text mixes characters that exercise every escaping decision:
     * required HTML escapes ({@code & < >}), named entities (Å, å, ¾, ©, »),
     * code-point-only references (π, 新), and the apostrophe / quote special cases.
     */
    private static final String SAMPLE_TEXT = "Hello &<> Å å π 新 there ¾ © » ' \"";

    /**
     * Escapes {@link #SAMPLE_TEXT} using the given character set and escape mode.
     * {@code Entities.escape} is "maximal": it escapes for use in both text and
     * attributes, unlike {@code Element.html()} which minimises escapes by context.
     */
    private static String escapeSample(String charset, Entities.EscapeMode escapeMode) {
        return Entities.escape(SAMPLE_TEXT, new OutputSettings().charset(charset).escapeMode(escapeMode));
    }

    @Test
    public void escape() {
        // The amount of escaping depends on the output charset (can the char be emitted directly?)
        // and the escape mode (which named entities are available).
        String escapedAscii      = escapeSample("ascii",  base);     // ASCII output, base entity set
        String escapedAsciiFull  = escapeSample("ascii",  extended); // ASCII output, full entity set
        String escapedAsciiXhtml = escapeSample("ascii",  xhtml);    // ASCII output, only lt/gt/amp/quot named
        String escapedUtfFull    = escapeSample("UTF-8",  extended); // UTF-8 output, full entity set
        String escapedUtfMin     = escapeSample("UTF-8",  xhtml);    // UTF-8 output, minimal escaping

        // ASCII can't emit non-ASCII chars directly, so every accented/symbol char is escaped.
        // base maps Å to &Aring; whereas extended prefers &angst; - odd that base uses aring but full uses angst.
        assertEquals("Hello &amp;&lt;&gt; &Aring; &aring; &#x3c0; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;", escapedAscii);
        assertEquals("Hello &amp;&lt;&gt; &angst; &aring; &pi; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;", escapedAsciiFull);
        // xhtml has only four named entities, so everything else falls back to numeric references.
        assertEquals("Hello &amp;&lt;&gt; &#xc5; &#xe5; &#x3c0; &#x65b0; there &#xbe; &#xa9; &#xbb; &#x27; &quot;", escapedAsciiXhtml);
        // UTF-8 can emit the accented/symbol chars directly; only the required escapes remain.
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;", escapedUtfFull);
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &#x27; &quot;", escapedUtfMin);

        // Round trip: every escaped form must unescape back to the original text.
        assertEquals(SAMPLE_TEXT, Entities.unescape(escapedAscii));
        assertEquals(SAMPLE_TEXT, Entities.unescape(escapedAsciiFull));
        assertEquals(SAMPLE_TEXT, Entities.unescape(escapedAsciiXhtml));
        assertEquals(SAMPLE_TEXT, Entities.unescape(escapedUtfFull));
        assertEquals(SAMPLE_TEXT, Entities.unescape(escapedUtfMin));
    }
}
