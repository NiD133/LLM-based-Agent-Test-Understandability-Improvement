package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Tag} class focusing on the {@code TextBoundary} option and
 * case-insensitive tag resolution.
 */
public class TagTest_textBoundaryOption {

    /**
     * Verifies that tag lookup via {@link Tag#valueOf} and {@link TagSet#valueOf} treats tag names
     * case-insensitively under any default locale, ensuring "script" and "SCRIPT" resolve to the
     * same tag instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf should consider "script" and "SCRIPT" equal
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper,
            "Tag.valueOf should treat 'script' and 'SCRIPT' as equal regardless of locale");

        // TagSet.valueOf should return the exact same (interned) instance for both cases
        TagSet htmlTags = TagSet.Html();
        Tag scriptLowerFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptLowerFromSet, scriptUpperFromSet,
            "TagSet.valueOf should return the same Tag instance for 'script' and 'SCRIPT'");
    }

    /**
     * Verifies that the {@link Tag#TextBoundary} option can be set on an arbitrary tag and that
     * setting any option automatically marks the tag as {@link Tag#Known}.
     *
     * <p>Flow:
     * <ol>
     *   <li>A newly constructed custom tag has no options set, so {@code TextBoundary} is absent.</li>
     *   <li>After calling {@link Tag#set(int)} with {@code TextBoundary}, the option is present.</li>
     *   <li>Calling {@code set()} also sets the {@code Known} flag, so {@link Tag#isKnownTag()} returns true.</li>
     * </ol>
     */
    @Test
    void textBoundaryOption() {
        Tag customTag = new Tag("foo", NamespaceHtml);

        assertFalse(customTag.is(Tag.TextBoundary),
            "A newly created tag should not have TextBoundary set by default");

        customTag.set(Tag.TextBoundary);

        assertTrue(customTag.is(Tag.TextBoundary),
            "TextBoundary should be set after calling tag.set(Tag.TextBoundary)");
        assertTrue(customTag.isKnownTag(),
            "Calling set() on any option should implicitly mark the tag as Known");
    }
}
