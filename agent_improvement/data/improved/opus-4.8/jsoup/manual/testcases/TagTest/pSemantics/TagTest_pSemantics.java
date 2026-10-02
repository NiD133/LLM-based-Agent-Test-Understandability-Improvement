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
 * Tests how {@link Tag} resolves tag names and exposes the semantic properties
 * of well-known HTML tags.
 */
public class TagTest_pSemantics {

    /**
     * Tag name lookups are case-insensitive: resolving the same name in different
     * cases yields equal tags. When the lookups go through a shared {@link TagSet},
     * the resolved tags are not just equal but the very same cached instance.
     */
    @MultiLocaleTest
    public void tagLookupIsCaseInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Resolving "script" and "SCRIPT" via the static factory yields equal tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Resolving them through a single TagSet returns the same cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseFromSet, upperCaseFromSet);
    }

    /**
     * The {@code <p>} paragraph tag is a known, block-level (non-inline) HTML tag.
     */
    @Test
    public void paragraphTagIsKnownBlockLevelTag() {
        Tag paragraph = Tag.valueOf("p");

        assertTrue(paragraph.isKnownTag(), "p should be a predefined, known tag");
        assertTrue(paragraph.isBlock(), "p should be a block-level tag");
        assertFalse(paragraph.isInline(), "p should not be an inline tag");
    }
}
