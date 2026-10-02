package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest {

    @Test public void escape() {
        // escape() is maximal: it escapes characters for use in both text and attributes,
        // unlike Element.html() which minimises escapes based on context.
        String text = "Hello &<> Å å π 新 there ¾ © » ' \"";

        String escapedAscii      = Entities.escape(text, new OutputSettings().charset("ascii").escapeMode(base));
        String escapedAsciiFull  = Entities.escape(text, new OutputSettings().charset("ascii").escapeMode(extended));
        String escapedAsciiXhtml = Entities.escape(text, new OutputSettings().charset("ascii").escapeMode(xhtml));
        String escapedUtfFull    = Entities.escape(text, new OutputSettings().charset("UTF-8").escapeMode(extended));
        String escapedUtfMin     = Entities.escape(text, new OutputSettings().charset("UTF-8").escapeMode(xhtml));

        // ASCII + base: uses base HTML entity names (note: Aring in base, but angst in extended)
        assertEquals("Hello &amp;&lt;&gt; &Aring; &aring; &#x3c0; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;", escapedAscii);
        // ASCII + extended: uses full HTML entity names where available
        assertEquals("Hello &amp;&lt;&gt; &angst; &aring; &pi; &#x65b0; there &frac34; &copy; &raquo; &apos; &quot;", escapedAsciiFull);
        // ASCII + xhtml: only numeric character references (no named entities beyond &amp;/&lt;/&gt;/&quot;)
        assertEquals("Hello &amp;&lt;&gt; &#xc5; &#xe5; &#x3c0; &#x65b0; there &#xbe; &#xa9; &#xbb; &#x27; &quot;", escapedAsciiXhtml);
        // UTF-8 + extended: characters representable in UTF-8 pass through unescaped
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;", escapedUtfFull);
        // UTF-8 + xhtml: single quote uses numeric reference; xhtml has no named &apos;
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &#x27; &quot;", escapedUtfMin);

        // Every escaped form must round-trip back to the original text via unescape
        assertEquals(text, Entities.unescape(escapedAscii));
        assertEquals(text, Entities.unescape(escapedAsciiFull));
        assertEquals(text, Entities.unescape(escapedAsciiXhtml));
        assertEquals(text, Entities.unescape(escapedUtfFull));
        assertEquals(text, Entities.unescape(escapedUtfMin));
    }

    @Test public void escapeDefaults() {
        String text = "Hello &<> Å å π 新 there ¾ © » ' \"";
        String escaped = Entities.escape(text);
        // Default settings: UTF-8 charset + base escape mode — only special chars are escaped
        assertEquals("Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;", escaped);
    }

    @Test public void escapedSupplementary() {
        // U+1D559 (MATHEMATICAL DOUBLE-STRUCK SMALL P), a supplementary code point outside the BMP
        String text = "𝕙";

        String escapedAscii     = Entities.escape(text, new OutputSettings().charset("ascii").escapeMode(base));
        String escapedAsciiFull = Entities.escape(text, new OutputSettings().charset("ascii").escapeMode(extended));
        String escapedUtf       = Entities.escape(text, new OutputSettings().charset("UTF-8").escapeMode(extended));

        assertEquals("&#x1d559;", escapedAscii);   // base mode: numeric hex reference
        assertEquals("&hopf;", escapedAsciiFull);  // extended mode: named entity
        assertEquals(text, escapedUtf);            // UTF-8: passthrough, no escaping needed
    }

    @Test public void unescapeMultiChars() {
        // Entities that expand to multi-codepoint strings (base character + combining mark)
        String text = "&NestedGreaterGreater; &nGg; &nGt; &nGtv; &Gt; &gg;";
        String un   = "≫ ⋙̸ ≫⃒ ≫̸ ≫ ≫";
        assertEquals(un, Entities.unescape(text));

        // Round-trip: re-escaping the unescaped form produces canonical entity names
        String escaped = Entities.escape(un, new OutputSettings().charset("ascii").escapeMode(extended));
        assertEquals("&Gt; &Gg;&#x338; &Gt;&#x20d2; &Gt;&#x338; &Gt; &Gt;", escaped);
        assertEquals(un, Entities.unescape(escaped));
    }

    @Test public void xhtml() {
        // XHTML escape mode supports only the four mandatory XML predefined entities
        final int AMP_CODEPOINT  = 38;  // '&'
        final int GT_CODEPOINT   = 62;  // '>'
        final int LT_CODEPOINT   = 60;  // '<'
        final int QUOT_CODEPOINT = 34;  // '"'

        assertEquals(AMP_CODEPOINT,  xhtml.codepointForName("amp"));
        assertEquals(GT_CODEPOINT,   xhtml.codepointForName("gt"));
        assertEquals(LT_CODEPOINT,   xhtml.codepointForName("lt"));
        assertEquals(QUOT_CODEPOINT, xhtml.codepointForName("quot"));

        assertEquals("amp",  xhtml.nameForCodepoint(AMP_CODEPOINT));
        assertEquals("gt",   xhtml.nameForCodepoint(GT_CODEPOINT));
        assertEquals("lt",   xhtml.nameForCodepoint(LT_CODEPOINT));
        assertEquals("quot", xhtml.nameForCodepoint(QUOT_CODEPOINT));
    }

    @Test public void getByName() {
        assertEquals("≫⃒", Entities.getByName("nGt"));   // multi-codepoint result
        assertEquals("fj",  Entities.getByName("fjlig")); // ligature of two ASCII chars
        assertEquals("≫",   Entities.getByName("gg"));    // single codepoint
        assertEquals("©",   Entities.getByName("copy"));  // copyright sign
    }

    @Test public void escapeSupplementaryCharacter() {
        // U+210C1 is a supplementary character (beyond BMP; 135361 in decimal)
        String text = new String(Character.toChars(135361));

        String escapedAscii = Entities.escape(text, new OutputSettings().charset("ascii").escapeMode(base));
        assertEquals("&#x210c1;", escapedAscii);  // ASCII: numeric hex reference

        String escapedUtf = Entities.escape(text, new OutputSettings().charset("UTF-8").escapeMode(base));
        assertEquals(text, escapedUtf);           // UTF-8: character is representable, no escaping needed
    }

    @Test public void notMissingMultis() {
        // &nparsl; decodes to two codepoints (U+2AFD PARALLEL WITH SOLIDUS + U+20E5 COMBINING REVERSE SOLIDUS)
        String text = "&nparsl;";
        String un   = "⫽⃥";
        assertEquals(un, Entities.unescape(text));
    }

    @Test public void notMissingSupplementals() {
        // &qfr; is U+1D52E (𝔮), a supplementary codepoint represented as a surrogate pair in Java
        String text = "&npolint; &qfr;";
        String un   = "⨔ 𝔮";
        assertEquals(un, Entities.unescape(text));
    }

    @Test public void unescape() {
        // Covers various entity forms: named, numeric decimal, numeric hex, and malformed references
        String text = "Hello &AElig; &amp;&LT&gt; &reg &angst; &angst &#960; &#960 &#x65B0; there &! &frac34; &copy; &COPY;";
        assertEquals("Hello Æ &<> ® Å &angst π π 新 there &! ¾ © ©", Entities.unescape(text));

        // Invalid entities (unknown name, numeric overflow) are left unchanged
        assertEquals("&0987654321; &unknown", Entities.unescape("&0987654321; &unknown"));
    }

    @Test public void strictUnescape() {
        // In strict mode (used for attribute values), entity references must end with ';'
        String text = "Hello &amp= &amp;";

        // Strict: &amp= is not recognised (missing ';'), so only &amp; is decoded
        assertEquals("Hello &amp= &", Entities.unescape(text, true));

        // Non-strict: &amp= is also decoded even without the trailing semicolon
        assertEquals("Hello &= &", Entities.unescape(text));
        assertEquals("Hello &= &", Entities.unescape(text, false));
    }

    @Test public void prefixMatch() {
        // https://github.com/jhy/jsoup/issues/2207
        // Per the HTML spec (character-reference-state), &notit; is parsed as &not; + literal "it;"
        // because "notit" is not a known entity, but "not" is a known prefix.
        String text = "I'm &notit; I tell you. I'm &notin; I tell you.";

        // Non-strict: prefix match finds &not; inside &notit;, leaving "it;" as literal text
        assertEquals("I'm ¬it; I tell you. I'm ∉ I tell you.", Entities.unescape(text, false));
        // Strict (attribute context): &notit; is not a known entity ending with ';', so left as-is
        assertEquals("I'm &notit; I tell you. I'm ∉ I tell you.", Entities.unescape(text, true));
    }

    @Test public void caseSensitive() {
        // Entity names are case-sensitive: &Uuml; (U+00DC Ü) vs &uuml; (U+00FC ü)
        String unescaped = "Ü ü & &";
        assertEquals("&Uuml; &uuml; &amp; &amp;",
                Entities.escape(unescaped, new OutputSettings().charset("ascii").escapeMode(extended)));

        // Unescape is also case-sensitive: &AMP (uppercase, no semicolon) is treated leniently
        String escaped = "&Uuml; &uuml; &amp; &AMP";
        assertEquals("Ü ü & &", Entities.unescape(escaped));
    }

    @Test public void quoteReplacements() {
        // Numeric character references for backslash (&#92; = 0x5C) and dollar sign (&#36; = 0x24)
        String escaped   = "&#92; &#36;";
        String unescaped = "\\ $";
        assertEquals(unescaped, Entities.unescape(escaped));
    }

    @Test public void letterDigitEntities() {
        String html = "<p>&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;</p>";
        Document doc = Jsoup.parse(html);
        Element p = doc.select("p").first();

        // ASCII output: characters that can't be encoded directly are emitted as named entity references
        doc.outputSettings().charset("ascii");
        assertEquals("&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;", p.html());
        assertEquals("¹²³¼½¾", p.text());

        // UTF-8 output: all characters are representable, so they are emitted literally
        doc.outputSettings().charset("UTF-8");
        assertEquals("¹²³¼½¾", p.html());
    }

    @Test public void noSpuriousDecodes() {
        // Ampersands in URLs that don't form valid entity references must not be decoded
        String string = "http://www.foo.com?a=1&num_rooms=1&children=0&int=VA&b=2";
        assertEquals(string, Entities.unescape(string));
    }

    @Test public void alwaysEscapeLtAndGtInAttributeValues() {
        // https://github.com/jhy/jsoup/issues/2337
        // '<' and '>' in attribute values must always be escaped, regardless of escape mode
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
        // In HTML: control characters are escaped for legibility (e.g. &#x1b;, &#x7;)
        // In XML:  control characters outside the allowed XML 1.0 range are silently removed
        String input = "<a foo=\"&#x1b;esc&#x7;bell\">Text &#x1b; &#x7;</a>";

        Document doc = Jsoup.parse(input);
        assertEquals(input, doc.body().html());

        Document xml = Jsoup.parse(input, "", Parser.xmlParser());
        assertEquals("<a foo=\"escbell\">Text  </a>", xml.html());
    }

    @Test public void escapeByClonedOutputSettings() {
        // Cloned OutputSettings instances must produce identical output when used independently
        OutputSettings outputSettings = new OutputSettings();
        String text = "Hello &<> Å å π 新 there ¾ © »";

        OutputSettings clone1 = outputSettings.clone();
        OutputSettings clone2 = outputSettings.clone();

        String escaped1 = assertDoesNotThrow(() -> Entities.escape(text, clone1));
        String escaped2 = assertDoesNotThrow(() -> Entities.escape(text, clone2));
        assertEquals(escaped1, escaped2);
    }

    @Test void parseHtmlEncodedEmojiMultipoint() {
        // 💯 encoded as two numeric references for the UTF-16 surrogate pair: U+D83D (high) + U+DCAF (low)
        String emoji = Parser.unescapeEntities("&#55357;&#56495;", false);
        assertEquals("💯", emoji);
    }

    @Test void parseHtmlEncodedEmoji() {
        // 💯 encoded as a single decimal numeric reference for codepoint U+1F4AF (128175 decimal)
        String emoji = Parser.unescapeEntities("&#128175;", false);
        assertEquals("💯", emoji);
    }
}
