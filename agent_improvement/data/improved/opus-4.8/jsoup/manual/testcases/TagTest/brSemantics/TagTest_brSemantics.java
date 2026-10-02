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
 * Tests for {@link Tag} covering case-insensitive tag lookup and the inline/block
 * classification of the {@code <br>} tag.
 */
public class TagTest_brSemantics {

    /**
     * Looking up a tag by name should be case-insensitive, regardless of the active locale
     * (e.g. the Turkish locale, where lower-casing "I" behaves unusually).
     *
     * <p>{@link Tag#valueOf} resolves "script" and "SCRIPT" to equal tags, and resolving them
     * through the same {@link TagSet} returns the very same cached instance.</p>
     */
    @MultiLocaleTest
    public void tagLookupIsCaseInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static lookup: differently-cased names resolve to equal tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Lookup via a shared TagSet returns the identical cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag sharedLowerCaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag sharedUpperCaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(sharedLowerCaseScript, sharedUpperCaseScript);
    }

    /**
     * The {@code <br>} tag is an inline element, not a block element.
     */
    @Test
    public void brTagIsInlineNotBlock() {
        Tag br = Tag.valueOf("br");

        assertTrue(br.isInline());
        assertFalse(br.isBlock());
    }
}
