package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_prefix {

    /**
     * Tag lookup should be case-insensitive: "script" and "SCRIPT" must resolve to the same tag
     * both through the static factory and through a TagSet instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static factory: equal-but-not-same (unknown tags compare by value)
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // TagSet instance: returns the identical (same) cached tag object
        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseScriptFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScriptFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseScriptFromSet, upperCaseScriptFromSet);
    }

    /**
     * Tags without a namespace prefix return an empty string; tags with a prefix (e.g. "bk:book")
     * return only the portion before the colon.
     */
    @Test
    void prefix() {
        Tag noPrefix = Tag.valueOf("img");
        Tag withPrefix = Tag.valueOf("bk:book");

        assertEquals("", noPrefix.prefix(), "A plain tag name should have no prefix");
        assertEquals("bk", withPrefix.prefix(), "The prefix of 'bk:book' should be 'bk'");
    }
}
