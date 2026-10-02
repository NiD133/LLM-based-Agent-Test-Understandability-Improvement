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
 * Tests for {@link Tag} covering case-insensitive tag lookup and the semantic
 * flags exposed for the {@code <div>} element.
 */
public class TagTest_divSemantics {

    /**
     * Tag names are matched case-insensitively when resolved through the default
     * HTML parse settings, so "script" and "SCRIPT" must produce equal tags.
     * When resolved from the same {@link TagSet}, the lookups even return the
     * very same cached instance.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseFromSet, upperCaseFromSet);
    }

    /**
     * {@code <div>} is a well-known block-level element, so it reports as a block
     * tag, not an inline tag, and is recognised as a known tag.
     */
    @Test
    public void divSemantics() {
        Tag div = Tag.valueOf("div");

        assertTrue(div.isBlock(), "<div> is a block-level tag");
        assertFalse(div.isInline(), "<div> is not an inline tag");
        assertTrue(div.isKnownTag(), "<div> is a known, pre-defined tag");
    }
}
