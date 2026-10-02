package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_knownTags {

    /**
     * Verifies that tag name lookup is case-insensitive under HTML default settings,
     * both via the static Tag.valueOf factory and via a TagSet instance.
     * The TagSet path also guarantees identity (assertSame) because known tags are cached.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static factory: "script" and "SCRIPT" must resolve to equal tags
        Tag lowerCaseScriptTag = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScriptTag = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScriptTag, upperCaseScriptTag);

        // TagSet instance: repeated lookups for the same tag return the identical cached object
        TagSet htmlTags = TagSet.Html();
        Tag cachedLowerScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUpperScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowerScript, cachedUpperScript);
    }

    /**
     * Verifies that standard HTML tags are recognised as known, while arbitrary
     * non-HTML tag names are not.
     */
    @Test
    public void knownTags() {
        // "div" is a standard HTML tag and must be recognised
        assertTrue(Tag.isKnownTag("div"));
        // "explain" is not a defined HTML tag
        assertFalse(Tag.isKnownTag("explain"));
    }
}
