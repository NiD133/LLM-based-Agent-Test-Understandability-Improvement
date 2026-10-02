package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_divSemantics {

    /**
     * Tag lookup via Tag.valueOf and TagSet.valueOf should both be case-insensitive for HTML tags,
     * regardless of the active locale (e.g. Turkish locale uppercases 'i' differently).
     * Tag.valueOf returns equal (but possibly distinct) instances; TagSet.valueOf returns the same instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf: "script" and "SCRIPT" should resolve to equal tags
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2, "Tag.valueOf should treat tag names as case-insensitive");

        // TagSet.valueOf: returns the same cached instance for any casing
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4, "TagSet.valueOf should return the same Tag instance regardless of input casing");
    }

    /**
     * A "div" is a standard HTML block-level element.
     * It must be recognised as a block tag (not inline) and as a known (pre-defined) tag.
     */
    @Test
    public void divSemantics() {
        Tag div = Tag.valueOf("div");

        assertTrue(div.isBlock(), "div is a block-level element");
        assertFalse(div.isInline(), "div is not an inline element");
        assertTrue(div.isKnownTag(), "div is a pre-defined (known) HTML tag");
    }
}
