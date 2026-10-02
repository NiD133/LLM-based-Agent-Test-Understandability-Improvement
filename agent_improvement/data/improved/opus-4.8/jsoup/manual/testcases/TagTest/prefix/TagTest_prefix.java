package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TagTest_prefix {

    /**
     * Tags resolved with the default HTML settings are case-insensitive: looking up the same tag name in different
     * cases yields equal (and, when resolved through a shared TagSet, identical) Tag instances.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Resolving via the static factory: different cases produce equal Tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Resolving via a shared TagSet: different cases return the very same cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseFromSet, upperCaseFromSet);
    }

    /**
     * A tag's prefix is the part before the first colon in its name, or the empty string when there is no colon.
     */
    @Test
    void prefix() {
        Tag tagWithoutPrefix = Tag.valueOf("img");
        Tag tagWithPrefix = Tag.valueOf("bk:book");

        assertEquals("", tagWithoutPrefix.prefix());
        assertEquals("bk", tagWithPrefix.prefix());
    }
}
