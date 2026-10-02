package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.jsoup.nodes.Entities.EscapeMode.xhtml;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest {

    // A sample string mixing plain ASCII, the HTML-special characters (& < > ' "), accented Latin letters,
    // a Greek letter, a CJK character, and some symbols. Used to exercise escaping across charsets/modes.
    private static final String MIXED_TEXT = "Hello &<> Å å π 新 there ¾ © » ' \"";

    /** Output settings for the {@code ascii} charset with the given escape mode. */
    private static OutputSettings ascii(Entities.EscapeMode mode) {
        return new OutputSettings().charset("ascii").escapeMode(mode);
    }

    /** Output settings for the {@code UTF-8} charset with the given escape mode. */
    private static OutputSettings utf8(Entities.EscapeMode mode) {
        return new OutputSettings().charset("UTF-8").escapeMode(mode);
    }

    @Test public void escape() {
        // escape is maximal (as in the escapes cover use in both text and attributes; vs Element.html() which checks if attribute or text and minimises escapes

        // Escape the same text under five different charset/mode combinations.
        String escapedAscii      = Entities.escape(MIXED_TEXT, ascii(base));
        String escapedAsciiFull  = Entities.escape(MIXED_TEXT, ascii(extended));
        String escapedAsciiXhtml = Entities.escape(MIXED_TEXT, ascii(xhtml));
        String escapedUtfFull    = Entities.escape(MIXED_TEXT, utf8(extended));
        String escapedUtfMin     = Entities.escape(MIXED_TEXT, utf8(xhtml));

        // ASCII charset forces every non-ASCII character to be escaped; the mode picks named vs numeric forms.
        assertEquals("Hello &amp;&lt;&gt; &Aring; &aring; &#x3c0; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;", escapedAscii);
        assertEquals("Hello &amp;&lt;&gt; &angst; &aring; &pi; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;", escapedAsciiFull);
        assertEquals("Hello &amp;&lt;&gt; &#xc5; &#xe5; &#x3c0; &#x65b0; there &#xbe; &#xa9; &#xbb; &#x27; &quot;", escapedAsciiXhtml);
        // UTF-8 charset can represent the non-ASCII characters directly, so only the HTML-special ones are escaped.
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;", escapedUtfFull);
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &#x27; &quot;", escapedUtfMin);
        // odd that it's defined as aring in base but angst in full

        // round trip: unescaping any of the escaped forms reproduces the original text
        assertEquals(MIXED_TEXT, Entities.unescape(escapedAscii));
        assertEquals(MIXED_TEXT, Entities.unescape(escapedAsciiFull));
        assertEquals(MIXED_TEXT, Entities.unescape(escapedAsciiXhtml));
        assertEquals(MIXED_TEXT, Entities.unescape(escapedUtfFull));
        assertEquals(MIXED_TEXT, Entities.unescape(escapedUtfMin));
    }

    @Test public void escapeDefaults() {
        // The no-settings escape() overload uses UTF-8 + base entities.
        String escaped = Entities.escape(MIXED_TEXT);
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;", escaped);
    }

    @Test public void escapedSupplementary() {
        // A supplementary-plane character (𝕙, U+1D559) encoded as a surrogate pair.
        String text = "𝕙";

        // base + ascii: no named entity, falls back to a numeric reference.
        assertEquals("&#x1d559;", Entities.escape(text, ascii(base)));
        // extended + ascii: a named entity exists for this codepoint.
        assertEquals("&hopf;", Entities.escape(text, ascii(extended)));
        // UTF-8 can represent it directly, so it is left unescaped.
        assertEquals(text, Entities.escape(text, utf8(extended)));
    }

    @Test public void unescapeMultiChars() {
        // gg is not combo, but 8811 could conflict with NestedGreaterGreater or others
        String text = "&NestedGreaterGreater; &nGg; &nGt; &nGtv; &Gt; &gg;";
        String unescaped = "≫ ⋙̸ ≫⃒ ≫̸ ≫ ≫";

        assertEquals(unescaped, Entities.unescape(text));

        // Re-escaping the unescaped form (ascii + extended) yields the canonical named entities.
        String escaped = Entities.escape(unescaped, ascii(extended));
        assertEquals("&Gt; &Gg;&#x338; &Gt;&#x20d2; &Gt;&#x338; &Gt; &Gt;", escaped);
        assertEquals(unescaped, Entities.unescape(escaped));
    }

    @Test public void xhtml() {
        // The xhtml escape mode knows exactly four named entities; check name -> codepoint mapping.
        assertEquals(38, xhtml.codepointForName("amp"));
        assertEquals(62, xhtml.codepointForName("gt"));
        assertEquals(60, xhtml.codepointForName("lt"));
        assertEquals(34, xhtml.codepointForName("quot"));

        // ...and the reverse, codepoint -> name.
        assertEquals("amp", xhtml.nameForCodepoint(38));
        assertEquals("gt", xhtml.nameForCodepoint(62));
        assertEquals("lt", xhtml.nameForCodepoint(60));
        assertEquals("quot", xhtml.nameForCodepoint(34));
    }

    @Test public void getByName() {
        // getByName returns the character(s) for a named entity, including multi-character entities.
        assertEquals("≫⃒", Entities.getByName("nGt"));
        assertEquals("fj", Entities.getByName("fjlig"));
        assertEquals("≫", Entities.getByName("gg"));
        assertEquals("©", Entities.getByName("copy"));
    }

    @Test public void escapeSupplementaryCharacter() {
        // A supplementary-plane character (U+210C1) with no named entity in the base set.
        String text = new String(Character.toChars(135361));

        // ascii cannot represent it, so it becomes a numeric reference.
        assertEquals("&#x210c1;", Entities.escape(text, ascii(base)));
        // UTF-8 can, so it is left as-is.
        assertEquals(text, Entities.escape(text, utf8(base)));
    }

    @Test public void notMissingMultis() {
        // &nparsl; maps to two codepoints (U+2AFD U+20E5).
        String text = "&nparsl;";
        String unescaped = "⫽⃥";
        assertEquals(unescaped, Entities.unescape(text));
    }

    @Test public void notMissingSupplementals() {
        String text = "&npolint; &qfr;";
        String unescaped = "⨔ 𝔮"; // 𝔮
        assertEquals(unescaped, Entities.unescape(text));
    }

    @Test public void unescape() {
        // Exercises a variety of references: named (with and without trailing ';'), decimal, hex, and unknown.
        String text = "Hello &AElig; &amp;&LT&gt; &reg &angst; &angst &#960; &#960 &#x65B0; there &! &frac34; &copy; &COPY;";
        assertEquals("Hello Æ &<> ® Å &angst π π 新 there &! ¾ © ©", Entities.unescape(text));

        // Numeric-looking-but-invalid and unknown names are left untouched.
        assertEquals("&0987654321; &unknown", Entities.unescape("&0987654321; &unknown"));
    }

    @Test public void strictUnescape() {
        // for attributes, enforce strict unescaping (must look like &#xxx; , not just &#xxx)
        String text = "Hello &amp= &amp;";

        // strict: a reference must end in ';', so "&amp=" is left as-is.
        assertEquals("Hello &amp= &", Entities.unescape(text, true));
        // non-strict (default): the trailing ';' is optional, so "&amp" decodes to "&".
        assertEquals("Hello &= &", Entities.unescape(text));
        assertEquals("Hello &= &", Entities.unescape(text, false));
    }

    @Test public void prefixMatch() {
        // https://github.com/jhy/jsoup/issues/2207
        // example from https://html.spec.whatwg.org/multipage/parsing.html#character-reference-state
        String text = "I'm &notit; I tell you. I'm &notin; I tell you.";

        // non-strict (text): "&notit;" matches the prefix "not" (¬), leaving "it;" behind.
        assertEquals("I'm ¬it; I tell you. I'm ∉ I tell you.", Entities.unescape(text, false));
        // strict (attributes): prefix matching is disabled, so "&notit;" stays literal.
        assertEquals("I'm &notit; I tell you. I'm ∉ I tell you.", Entities.unescape(text, true)); // not for attributes
    }

    @Test public void caseSensitive() {
        // Entity names are case-sensitive: Uuml and uuml are distinct.
        String unescaped = "Ü ü & &";
        assertEquals("&Uuml; &uuml; &amp; &amp;",
                Entities.escape(unescaped, ascii(extended)));

        // "&AMP" (no trailing ';') still decodes as a base entity via the legacy/case rules.
        String escaped = "&Uuml; &uuml; &amp; &AMP";
        assertEquals("Ü ü & &", Entities.unescape(escaped));
    }

    @Test public void quoteReplacements() {
        // Decimal references for backslash (92) and dollar (36).
        String escaped = "&#92; &#36;";
        String unescaped = "\\ $";

        assertEquals(unescaped, Entities.unescape(escaped));
    }

    @Test public void letterDigitEntities() {
        String html = "<p>&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;</p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.select("p").first();

        // With ascii output, html() re-escapes the characters as named entities; text() shows the actual glyphs.
        doc.outputSettings().charset("ascii");
        assertEquals("&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;", p.html());
        assertEquals("¹²³¼½¾", p.text());

        // With UTF-8 output, html() can emit the characters directly.
        doc.outputSettings().charset("UTF-8");
        assertEquals("¹²³¼½¾", p.html());
    }

    @Test public void noSpuriousDecodes() {
        // A URL containing '&' separators must not have its query parameters mistaken for entities.
        String string = "http://www.foo.com?a=1&num_rooms=1&children=0&int=VA&b=2";
        assertEquals(string, Entities.unescape(string));
    }

    @Test public void alwaysEscapeLtAndGtInAttributeValues() {
        // https://github.com/jhy/jsoup/issues/2337
        String docHtml = "<a title='<p>One</p>'>One</a>";
        Document doc = Jsoup.parse(docHtml);
        Element element = doc.select("a").first();

        // < and > inside an attribute value are always escaped, in both base and xhtml modes.
        doc.outputSettings().escapeMode(base);
        assertEquals("<a title=\"&lt;p&gt;One&lt;/p&gt;\">One</a>", element.outerHtml());

        doc.outputSettings().escapeMode(xhtml);
        assertEquals("<a title=\"&lt;p&gt;One&lt;/p&gt;\">One</a>", element.outerHtml());
    }

    @Test public void controlCharactersAreEscaped() {
        // https://github.com/jhy/jsoup/issues/1556
        // escape in HTML for legibility; remove from xml
        String input = "<a foo=\"&#x1b;esc&#x7;bell\">Text &#x1b; &#x7;</a>";

        // HTML parser preserves the control-character references for legibility.
        Document doc = Jsoup.parse(input);
        assertEquals(input, doc.body().html());

        // XML parser drops the invalid XML control characters entirely.
        Document xml = Jsoup.parse(input, "", Parser.xmlParser());
        assertEquals("<a foo=\"escbell\">Text  </a>", xml.html());
    }

    @Test public void escapeByClonedOutputSettings() {
        // Escaping concurrently/repeatedly with cloned settings must not throw and must be deterministic.
        OutputSettings outputSettings = new OutputSettings();
        OutputSettings clone1 = outputSettings.clone();
        OutputSettings clone2 = outputSettings.clone();

        String text = "Hello &<> Å å π 新 there ¾ © »";
        String escaped1 = assertDoesNotThrow(() -> Entities.escape(text, clone1));
        String escaped2 = assertDoesNotThrow(() -> Entities.escape(text, clone2));
        assertEquals(escaped1, escaped2);
    }

    @Test void parseHtmlEncodedEmojiMultipoint() {
        // Two decimal references forming a surrogate pair decode to a single emoji. 💯
        String emoji = Parser.unescapeEntities("&#55357;&#56495;", false);
        assertEquals("💯", emoji);
    }

    @Test void parseHtmlEncodedEmoji() {
        // A single decimal reference for a supplementary-plane emoji. 💯
        String emoji = Parser.unescapeEntities("&#128175;", false);
        assertEquals("💯", emoji);
    }
}
