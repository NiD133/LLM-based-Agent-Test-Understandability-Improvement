package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests how {@link Tag} treats letter case when resolving tags by name.
 */
public class TagTest_isCaseSensitive {

    /**
     * When tags are looked up with the default HTML parse settings (which normalise the case),
     * names that differ only in case resolve to the same tag.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        // The lookup normalises case using the default locale, so exercise it under several locales.
        Locale.setDefault(locale);

        // Tag.valueOf normalises the name, so "script" and "SCRIPT" are equal tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // A shared TagSet caches known tags, so the two names resolve to the very same instance.
        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseFromSet, upperCaseFromSet);
    }

    /**
     * The plain {@link Tag#valueOf(String)} preserves case, so names that differ only in case
     * produce distinct (non-equal) tags.
     */
    @Test
    public void isCaseSensitive() {
        Tag upperCaseP = Tag.valueOf("P");
        Tag lowerCaseP = Tag.valueOf("p");
        assertNotEquals(upperCaseP, lowerCaseP);
    }
}
