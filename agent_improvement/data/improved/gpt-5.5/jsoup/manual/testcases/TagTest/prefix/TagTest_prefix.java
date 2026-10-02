package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TagTest_prefix {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag scriptFromLowerCaseName = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromUpperCaseName = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptFromLowerCaseName, scriptFromUpperCaseName);

        TagSet htmlTags = TagSet.Html();
        Tag cachedScriptFromLowerCaseName = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedScriptFromUpperCaseName = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedScriptFromLowerCaseName, cachedScriptFromUpperCaseName);
    }

    @Test
    void prefix() {
        Tag tagWithoutPrefix = Tag.valueOf("img");
        Tag tagWithPrefix = Tag.valueOf("bk:book");

        assertEquals("", tagWithoutPrefix.prefix());
        assertEquals("bk", tagWithPrefix.prefix());
    }
}
