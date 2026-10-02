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
 * Tests how {@link Tag} resolves tag names and exposes the semantic options of
 * a {@code <button>} element.
 */
public class TagTest_buttonSemantics {

    /**
     * Resolving a tag name is case-insensitive: "script" and "SCRIPT" describe
     * the same tag regardless of the platform's default locale.
     *
     * <p>Two lookup paths are checked:</p>
     * <ul>
     *   <li>the static {@link Tag#valueOf} factory, whose results are equal; and</li>
     *   <li>a shared {@link TagSet}, which additionally returns the very same
     *       cached instance.</li>
     * </ul>
     */
    @MultiLocaleTest
    public void tagNameLookupIsCaseInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static factory: lower- and upper-case names resolve to equal tags.
        Tag scriptLowerCase = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperCase = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLowerCase, scriptUpperCase);

        // Shared TagSet: both names map to the identical cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedScriptLowerCase = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedScriptUpperCase = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedScriptLowerCase, cachedScriptUpperCase);
    }

    /**
     * A {@code <button>} is a known, inline (non-block) tag that acts as a
     * readable-text boundary.
     */
    @Test
    public void buttonSemantics() {
        Tag button = Tag.valueOf("button");

        assertTrue(button.isInline(), "button is an inline tag");
        assertFalse(button.isBlock(), "button is not a block tag");
        assertTrue(button.is(Tag.TextBoundary), "button is a text boundary");
        assertTrue(button.isKnownTag(), "button is a known, pre-defined tag");
    }
}
