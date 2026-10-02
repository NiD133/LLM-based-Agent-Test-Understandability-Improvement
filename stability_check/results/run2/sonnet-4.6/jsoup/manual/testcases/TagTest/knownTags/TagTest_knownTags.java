package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_knownTags {

    // Tag lookup should normalize to lowercase regardless of the JVM's active locale,
    // so that "script" and "SCRIPT" always resolve to the same Tag instance.
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper,
            "Tag.valueOf should treat 'script' and 'SCRIPT' as equal regardless of locale");

        TagSet htmlTags = TagSet.Html();
        Tag scriptLowerFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        // TagSet caches known tags, so both lookups must return the exact same instance
        assertSame(scriptLowerFromSet, scriptUpperFromSet,
            "TagSet should return the identical cached Tag instance for case-insensitive lookups");
    }

    // isKnownTag distinguishes HTML-spec tags from arbitrary/unknown tag names.
    @Test
    public void knownTags() {
        assertTrue(Tag.isKnownTag("div"),
            "'div' is a standard HTML block element and should be recognised as a known tag");
        assertFalse(Tag.isKnownTag("explain"),
            "'explain' is not a standard HTML tag and should not be recognised as a known tag");
    }
}
