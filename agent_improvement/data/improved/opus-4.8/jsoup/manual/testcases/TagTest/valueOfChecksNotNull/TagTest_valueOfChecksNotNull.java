package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Tag#valueOf}: case-insensitive tag lookup and null-name rejection.
 */
public class TagTest_valueOfChecksNotNull {

    /**
     * Resolving a tag should ignore the case of the supplied name, and must do so consistently
     * regardless of the active locale (e.g. the Turkish locale, where "I" lowercases unusually).
     */
    @MultiLocaleTest
    public void valueOfIsCaseInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf reuses pre-defined tags, so differently-cased names resolve to equal tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Resolving against the same TagSet returns the very same instance for either casing.
        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseFromSet, upperCaseFromSet);
    }

    /**
     * A null tag name is not a valid argument and must be rejected.
     */
    @Test
    public void valueOfRejectsNullTagName() {
        assertThrows(IllegalArgumentException.class, () -> Tag.valueOf(null));
    }
}
