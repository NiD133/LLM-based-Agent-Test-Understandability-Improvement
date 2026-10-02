package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests for {@link Tag#equals(Object)}, which compares a Tag by its
 * tagName, namespace, normalName, and options fields.
 */
public class TagTest_equals {

    /**
     * Tag equality ignores the case of the requested tag name, so "script" and
     * "SCRIPT" resolve to equal tags. When resolved through the same TagSet they
     * are even the identical (==) cached instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Resolving via Tag.valueOf with differing case yields equal tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Resolving via the same TagSet returns the same cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedLowerCase = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUpperCase = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowerCase, cachedUpperCase);
    }

    /**
     * A clone equals its source, and changing any single field that participates
     * in equality (namespace, tagName, normalName, options) breaks that equality.
     * Each field is mutated, checked, then restored to isolate its effect.
     */
    @Test
    void equals() {
        TagSet tags = TagSet.Html();
        Tag original = tags.get("p", NamespaceHtml);
        Tag clone = original.clone();

        // A fresh clone is equal to its source.
        assertEquals(original, clone);
        // A Tag is never equal to an unrelated object (the TagSet here).
        assertNotEquals(original, tags);

        // Differing namespace breaks equality.
        clone.namespace = "Other";
        assertNotEquals(original, clone);
        clone.namespace = original.namespace;

        // Differing tagName breaks equality.
        clone.tagName = "P";
        assertNotEquals(original, clone);
        clone.tagName = original.tagName;

        // Differing normalName breaks equality.
        clone.normalName = "pp";
        assertNotEquals(original, clone);
        clone.normalName = original.normalName;

        // Differing options breaks equality.
        clone.options = 0;
        assertNotEquals(original, clone);
        clone.options = original.options;
    }
}
