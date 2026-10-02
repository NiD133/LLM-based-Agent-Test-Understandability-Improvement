package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests how {@link Tag} treats tag-name case and how it derives a tag's local name.
 */
public class TagTest_localname {

    /**
     * Tag look-up is case-insensitive: "script" and "SCRIPT" resolve to equivalent tags,
     * regardless of the active locale.
     *
     * <p>Two ways of resolving a tag are exercised:</p>
     * <ul>
     *   <li>{@link Tag#valueOf(String, String, ParseSettings)} returns tags that are equal
     *       (compared with {@code equals}).</li>
     *   <li>Resolving against the same {@link TagSet} returns the very same cached instance
     *       (compared with {@code ==}).</li>
     * </ul>
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Via Tag.valueOf: differently cased names produce equal tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Via a shared TagSet: differently cased names return the same cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedLowerCaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUpperCaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowerCaseScript, cachedUpperCaseScript);
    }

    /**
     * A tag's local name is its name without any namespace prefix:
     * "img" has no prefix, while "bk:book" has local name "book".
     */
    @Test
    void localName() {
        Tag unprefixedTag = Tag.valueOf("img");
        Tag prefixedTag = Tag.valueOf("bk:book");

        assertEquals("img", unprefixedTag.localName());
        assertEquals("book", prefixedTag.localName());
    }
}
