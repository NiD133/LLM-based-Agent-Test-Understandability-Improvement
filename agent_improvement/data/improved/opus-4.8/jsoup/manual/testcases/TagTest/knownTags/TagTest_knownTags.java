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
 * Tests how {@link Tag} resolves tag names, focusing on case-insensitivity and
 * recognition of known HTML tags.
 */
public class TagTest_knownTags {

    /**
     * Resolving a tag should ignore the case of the supplied name, so "script"
     * and "SCRIPT" refer to the same tag.
     *
     * <p>Two lookup paths are checked:</p>
     * <ul>
     *   <li>{@link Tag#valueOf(String, String, ParseSettings)} returns tags that
     *       are {@code equal} for either casing.</li>
     *   <li>{@link TagSet#valueOf(String, String, ParseSettings)} returns the very
     *       same cached instance for either casing.</li>
     * </ul>
     */
    @MultiLocaleTest
    public void resolvesTagNameIgnoringCase(Locale locale) {
        // Tag name normalisation is locale-sensitive, so exercise it under the given locale.
        Locale.setDefault(locale);

        // Tag.valueOf: lower- and upper-case names resolve to equal tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // TagSet.valueOf: lower- and upper-case names resolve to the identical cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedLowerCaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUpperCaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowerCaseScript, cachedUpperCaseScript);
    }

    /**
     * A predefined HTML tag (such as "div") is reported as known, while an
     * arbitrary made-up name (such as "explain") is not.
     */
    @Test
    public void recognisesKnownHtmlTags() {
        assertTrue(Tag.isKnownTag("div"));
        assertFalse(Tag.isKnownTag("explain"));
    }
}
