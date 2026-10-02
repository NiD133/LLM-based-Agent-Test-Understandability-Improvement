package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TagTest_localname {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowercaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowercaseScript, uppercaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag lowercaseScriptFromTagSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScriptFromTagSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowercaseScriptFromTagSet, uppercaseScriptFromTagSet);
    }

    @Test
    void localname() {
        Tag unprefixedTag = Tag.valueOf("img");
        Tag prefixedTag = Tag.valueOf("bk:book");

        assertEquals("img", unprefixedTag.localName());
        assertEquals("book", prefixedTag.localName());
    }
}
