package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link Entities#escape(String, OutputSettings)}.
 *
 * <p>The escape method is <em>maximal</em>: it escapes characters suitable for use in
 * both text nodes and attributes (e.g. both {@code '} and {@code "} are escaped),
 * unlike {@code Element.html()} which minimises escapes based on context.
 */
public class EntitiesTest_escape {

    /**
     * Input string that exercises a broad range of characters:
     * HTML special characters ({@code & < >}), named-entity characters
     * ({@code Å å π ¾ © »}), a CJK character ({@code 新}) that has no named entity,
     * and quote characters ({@code ' "}).
     */
    private static final String INPUT = "Hello &<> Å å π 新 there ¾ © » ' \"";

    /**
     * ASCII charset + base entity set: non-ASCII characters are encoded as numeric hex
     * references unless the base entity table has a named form.
     * Note: Å is named &amp;Aring; in the base set but &amp;angst; in the extended set.
     */
    @Test
    public void escapeWithAsciiCharsetAndBaseEntities() {
        String escaped = Entities.escape(INPUT, new OutputSettings().charset("ascii").escapeMode(base));

        assertEquals(
            "Hello &amp;&lt;&gt; &Aring; &aring; &#x3c0; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;",
            escaped
        );
        assertEquals(INPUT, Entities.unescape(escaped), "round-trip should reproduce original input");
    }

    /**
     * ASCII charset + extended entity set: uses the full HTML5 named-entity table.
     * Å becomes &amp;angst; here (not &amp;Aring; as in the base set) – a known quirk
     * of how the two tables name the same codepoint differently.
     */
    @Test
    public void escapeWithAsciiCharsetAndExtendedEntities() {
        String escaped = Entities.escape(INPUT, new OutputSettings().charset("ascii").escapeMode(extended));

        assertEquals(
            "Hello &amp;&lt;&gt; &angst; &aring; &pi; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;",
            escaped
        );
        assertEquals(INPUT, Entities.unescape(escaped), "round-trip should reproduce original input");
    }

    /**
     * ASCII charset + xhtml entity set: only the four XHTML named entities
     * ({@code lt}, {@code gt}, {@code amp}, {@code quot}) are used; every other
     * non-ASCII character becomes a numeric hex reference.
     */
    @Test
    public void escapeWithAsciiCharsetAndXhtmlEntities() {
        String escaped = Entities.escape(INPUT, new OutputSettings().charset("ascii").escapeMode(xhtml));

        assertEquals(
            "Hello &amp;&lt;&gt; &#xc5; &#xe5; &#x3c0; &#x65b0; there &#xbe; &#xa9; &#xbb; &#x27; &quot;",
            escaped
        );
        assertEquals(INPUT, Entities.unescape(escaped), "round-trip should reproduce original input");
    }

    /**
     * UTF-8 charset + extended entity set: Unicode characters the charset can represent
     * are left as-is; only HTML special characters and quote characters are escaped.
     */
    @Test
    public void escapeWithUtf8CharsetAndExtendedEntities() {
        String escaped = Entities.escape(INPUT, new OutputSettings().charset("UTF-8").escapeMode(extended));

        assertEquals(
            "Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;",
            escaped
        );
        assertEquals(INPUT, Entities.unescape(escaped), "round-trip should reproduce original input");
    }

    /**
     * UTF-8 charset + xhtml entity set: Unicode characters are left as-is, but the
     * single-quote has no named entity in the xhtml set so it becomes {@code &#x27;}.
     */
    @Test
    public void escapeWithUtf8CharsetAndXhtmlEntities() {
        String escaped = Entities.escape(INPUT, new OutputSettings().charset("UTF-8").escapeMode(xhtml));

        assertEquals(
            "Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &#x27; &quot;",
            escaped
        );
        assertEquals(INPUT, Entities.unescape(escaped), "round-trip should reproduce original input");
    }
}
