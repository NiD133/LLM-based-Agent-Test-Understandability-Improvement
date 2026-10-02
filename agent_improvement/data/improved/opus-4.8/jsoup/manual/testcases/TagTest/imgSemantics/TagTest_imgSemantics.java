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
 * Tests how {@link Tag} resolves tag names and exposes the semantic flags of well-known HTML tags.
 */
public class TagTest_imgSemantics {

    /**
     * Tag lookup is case-insensitive: "script" and "SCRIPT" resolve to equal tags.
     * <p>
     * Additionally, tags resolved from the same {@link TagSet} are cached, so repeated
     * lookups of the same name return the very same instance ({@code assertSame}).
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf resolves against a shared HTML TagSet; differing case yields equal tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Resolving from a single TagSet caches by normalised name, so we get the same instance back.
        TagSet htmlTags = TagSet.Html();
        Tag cachedLowerCase = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUpperCase = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowerCase, cachedUpperCase);
    }

    /**
     * The {@code <img>} tag is an inline, self-closing (void) element and is therefore not a block.
     */
    @Test
    public void imgSemantics() {
        Tag img = Tag.valueOf("img");

        assertTrue(img.isInline(), "img should be inline");
        assertTrue(img.isSelfClosing(), "img should be self-closing");
        assertFalse(img.isBlock(), "img should not be a block tag");
    }
}
