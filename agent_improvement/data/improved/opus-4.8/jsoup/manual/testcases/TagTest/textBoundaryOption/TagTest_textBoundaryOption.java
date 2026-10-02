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
 * Tests for {@link Tag} option handling, covering case-insensitive tag lookup
 * and the {@link Tag#TextBoundary} option flag.
 */
public class TagTest_textBoundaryOption {

    /**
     * Tag names are matched case-insensitively, so "script" and "SCRIPT" resolve to
     * the same tag definition regardless of the current default locale.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf() resolves names case-insensitively, so both spellings are equal.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Looking up via the same TagSet returns the very same cached instance for both spellings.
        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseFromSet, upperCaseFromSet);
    }

    /**
     * Setting the {@link Tag#TextBoundary} option flips its flag on and, as a side effect,
     * marks the tag as a known tag.
     */
    @Test
    void textBoundaryOption() {
        Tag tag = new Tag("foo", NamespaceHtml);

        // The option starts unset on a freshly created tag.
        assertFalse(tag.is(Tag.TextBoundary));

        tag.set(Tag.TextBoundary);

        // After setting, the option reads back as enabled...
        assertTrue(tag.is(Tag.TextBoundary));
        // ...and setting any option also makes the tag "known".
        assertTrue(tag.isKnownTag());
    }
}
