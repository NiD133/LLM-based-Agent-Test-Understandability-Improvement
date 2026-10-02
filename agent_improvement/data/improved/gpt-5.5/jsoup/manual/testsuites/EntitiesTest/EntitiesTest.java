package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest {
    private static final String MIXED_TEXT = "Hello &<> Å å π 新 there ¾ © » ' \"";
    private static final String MIXED_TEXT_WITHOUT_QUOTES = "Hello &<> Å å π 新 there ¾ © »";
    private static final String HUNDRED_POINTS_EMOJI = "\uD83D\uDCAF";

    @Test public void escape() {
        // Escape is maximal: it covers both text and attribute contexts.
        String text = MIXED_TEXT;
        String escapedAscii = Entities.escape(text, outputSettings("ascii", base));
        String escapedAsciiFull = Entities.escape(text, outputSettings("ascii", extended));
        String escapedAsciiXhtml = Entities.escape(text, outputSettings("ascii", xhtml));
        String escapedUtfFull = Entities.escape(text, outputSettings("UTF-8", extended));
        String escapedUtfMin = Entities.escape(text, outputSettings("UTF-8", xhtml));

        assertEquals("Hello &amp;&lt;&gt; &Aring; &aring; &#x3c0; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;", escapedAscii);
        assertEquals("Hello &amp;&lt;&gt; &angst; &aring; &pi; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;", escapedAsciiFull);
        assertEquals("Hello &amp;&lt;&gt; &#xc5; &#xe5; &#x3c0; &#x65b0; there &#xbe; &#xa9; &#xbb; &#x27; &quot;", escapedAsciiXhtml);
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;", escapedUtfFull);
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &#x27; &quot;", escapedUtfMin);
        // Odd that it is defined as aring in base but angst in full.

        assertEquals(text, Entities.unescape(escapedAscii));
        assertEquals(text, Entities.unescape(escapedAsciiFull));
        assertEquals(text, Entities.unescape(escapedAsciiXhtml));
        assertEquals(text, Entities.unescape(escapedUtfFull));
        assertEquals(text, Entities.unescape(escapedUtfMin));
    }

    @Test public void escapeDefaults() {
        String escaped = Entities.escape(MIXED_TEXT);
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;", escaped);
    }

    @Test public void escapedSupplementary() {
        String text = "\uD835\uDD59";

        String escapedAscii = Entities.escape(text, outputSettings("ascii", base));
        assertEquals("&#x1d559;", escapedAscii);

        String escapedAsciiFull = Entities.escape(text, outputSettings("ascii", extended));
        assertEquals("&hopf;", escapedAsciiFull);

        String escapedUtf = Entities.escape(text, outputSettings("UTF-8", extended));
        assertEquals(text, escapedUtf);
    }

    @Test public void unescapeMultiChars() {
        // gg is not a combo, but 8811 could conflict with NestedGreaterGreater or others.
        String text = "&NestedGreaterGreater; &nGg; &nGt; &nGtv; &Gt; &gg;";
        String unescaped = "≫ ⋙̸ ≫⃒ ≫̸ ≫ ≫";

        assertEquals(unescaped, Entities.unescape(text));

        String escaped = Entities.escape(unescaped, outputSettings("ascii", extended));
        assertEquals("&Gt; &Gg;&#x338; &Gt;&#x20d2; &Gt;&#x338; &Gt; &Gt;", escaped);
        assertEquals(unescaped, Entities.unescape(escaped));
    }

    @Test public void xhtml() {
        assertEquals(38, xhtml.codepointForName("amp"));
        assertEquals(62, xhtml.codepointForName("gt"));
        assertEquals(60, xhtml.codepointForName("lt"));
        assertEquals(34, xhtml.codepointForName("quot"));

        assertEquals("amp", xhtml.nameForCodepoint(38));
        assertEquals("gt", xhtml.nameForCodepoint(62));
        assertEquals("lt", xhtml.nameForCodepoint(60));
        assertEquals("quot", xhtml.nameForCodepoint(34));
    }

    @Test public void getByName() {
        assertEquals("≫⃒", Entities.getByName("nGt"));
        assertEquals("fj", Entities.getByName("fjlig"));
        assertEquals("≫", Entities.getByName("gg"));
        assertEquals("©", Entities.getByName("copy"));
    }

    @Test public void escapeSupplementaryCharacter() {
        String text = new String(Character.toChars(135361));

        String escapedAscii = Entities.escape(text, outputSettings("ascii", base));
        assertEquals("&#x210c1;", escapedAscii);

        String escapedUtf = Entities.escape(text, outputSettings("UTF-8", base));
        assertEquals(text, escapedUtf);
    }

    @Test public void notMissingMultis() {
        String text = "&nparsl;";
        String unescaped = "\u2AFD\u20E5";
        assertEquals(unescaped, Entities.unescape(text));
    }

    @Test public void notMissingSupplementals() {
        String text = "&npolint; &qfr;";
        String unescaped = "⨔ \uD835\uDD2E"; // 𝔮
        assertEquals(unescaped, Entities.unescape(text));
    }

    @Test public void unescape() {
        String text = "Hello &AElig; &amp;&LT&gt; &reg &angst; &angst &#960; &#960 &#x65B0; there &! &frac34; &copy; &COPY;";

        assertEquals("Hello Æ &<> ® Å &angst π π 新 there &! ¾ © ©", Entities.unescape(text));
        assertEquals("&0987654321; &unknown", Entities.unescape("&0987654321; &unknown"));
    }

    @Test public void strictUnescape() {
        // Attribute-mode unescaping is strict: an entity must be semicolon-terminated.
        String text = "Hello &amp= &amp;";

        assertEquals("Hello &amp= &", Entities.unescape(text, true));
        assertEquals("Hello &= &", Entities.unescape(text));
        assertEquals("Hello &= &", Entities.unescape(text, false));
    }

    @Test public void prefixMatch() {
        // https://github.com/jhy/jsoup/issues/2207
        // Example from https://html.spec.whatwg.org/multipage/parsing.html#character-reference-state
        String text = "I'm &notit; I tell you. I'm &notin; I tell you.";

        assertEquals("I'm ¬it; I tell you. I'm ∉ I tell you.", Entities.unescape(text, false));
        assertEquals("I'm &notit; I tell you. I'm ∉ I tell you.", Entities.unescape(text, true));
    }

    @Test public void caseSensitive() {
        String unescaped = "Ü ü & &";
        assertEquals("&Uuml; &uuml; &amp; &amp;",
                Entities.escape(unescaped, outputSettings("ascii", extended)));

        String escaped = "&Uuml; &uuml; &amp; &AMP";
        assertEquals("Ü ü & &", Entities.unescape(escaped));
    }

    @Test public void quoteReplacements() {
        String escaped = "&#92; &#36;";
        String unescaped = "\\ $";

        assertEquals(unescaped, Entities.unescape(escaped));
    }

    @Test public void letterDigitEntities() {
        String html = "<p>&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;</p>";
        Document doc = Jsoup.parse(html);
        doc.outputSettings().charset("ascii");
        Element p = doc.select("p").first();

        assertEquals("&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;", p.html());
        assertEquals("¹²³¼½¾", p.text());

        doc.outputSettings().charset("UTF-8");
        assertEquals("¹²³¼½¾", p.html());
    }

    @Test public void noSpuriousDecodes() {
        String string = "http://www.foo.com?a=1&num_rooms=1&children=0&int=VA&b=2";
        assertEquals(string, Entities.unescape(string));
    }

    @Test public void alwaysEscapeLtAndGtInAttributeValues() {
        // https://github.com/jhy/jsoup/issues/2337
        String docHtml = "<a title='<p>One</p>'>One</a>";
        Document doc = Jsoup.parse(docHtml);
        Element element = doc.select("a").first();

        doc.outputSettings().escapeMode(base);
        assertEquals("<a title=\"&lt;p&gt;One&lt;/p&gt;\">One</a>", element.outerHtml());

        doc.outputSettings().escapeMode(xhtml);
        assertEquals("<a title=\"&lt;p&gt;One&lt;/p&gt;\">One</a>", element.outerHtml());
    }

    @Test public void controlCharactersAreEscaped() {
        // https://github.com/jhy/jsoup/issues/1556
        // Escape control characters in HTML for legibility; remove them from XML.
        String input = "<a foo=\"&#x1b;esc&#x7;bell\">Text &#x1b; &#x7;</a>";

        Document doc = Jsoup.parse(input);
        assertEquals(input, doc.body().html());

        Document xml = Jsoup.parse(input, "", Parser.xmlParser());
        assertEquals("<a foo=\"escbell\">Text  </a>", xml.html());
    }

    @Test public void escapeByClonedOutputSettings() {
        OutputSettings outputSettings = new OutputSettings();
        OutputSettings clone1 = outputSettings.clone();
        OutputSettings clone2 = outputSettings.clone();

        String escaped1 = assertDoesNotThrow(() -> Entities.escape(MIXED_TEXT_WITHOUT_QUOTES, clone1));
        String escaped2 = assertDoesNotThrow(() -> Entities.escape(MIXED_TEXT_WITHOUT_QUOTES, clone2));
        assertEquals(escaped1, escaped2);
    }

    @Test void parseHtmlEncodedEmojiMultipoint() {
        String emoji = Parser.unescapeEntities("&#55357;&#56495;", false); // 💯
        assertEquals(HUNDRED_POINTS_EMOJI, emoji);
    }

    @Test void parseHtmlEncodedEmoji() {
        String emoji = Parser.unescapeEntities("&#128175;", false); // 💯
        assertEquals(HUNDRED_POINTS_EMOJI, emoji);
    }

    private static OutputSettings outputSettings(String charset, EscapeMode escapeMode) {
        return new OutputSettings().charset(charset).escapeMode(escapeMode);
    }
}
