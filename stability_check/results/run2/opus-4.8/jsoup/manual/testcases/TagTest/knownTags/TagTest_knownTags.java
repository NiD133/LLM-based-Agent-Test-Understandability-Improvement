package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests how {@link Tag} resolves tag names: case-insensitive lookups and the
 * recognition of built-in HTML tags.
 */
public class TagTest_knownTags {

    /**
     * Looking up a tag by name is case-insensitive, so requesting "script" and
     * "SCRIPT" resolves to the same tag definition regardless of the active locale.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf uses the shared HTML tag set, so the two casings are equal.
        Tag scriptLowerCase = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperCase = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLowerCase, scriptUpperCase);

        // Resolving against a single TagSet instance returns the very same object.
        TagSet htmlTags = TagSet.Html();
        Tag scriptFromSetLowerCase = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromSetUpperCase = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromSetLowerCase, scriptFromSetUpperCase);
    }

    /**
     * A predefined HTML tag such as "div" is reported as known, while an
     * arbitrary tag name such as "explain" is not.
     */
    @Test
    public void knownTags() {
        assertTrue(Tag.isKnownTag("div"));
        assertFalse(Tag.isKnownTag("explain"));
    }
}
