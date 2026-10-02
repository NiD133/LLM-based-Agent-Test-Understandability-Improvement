package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Tag} semantics, covering case-insensitive tag lookup and
 * the layout/void properties of the {@code <img>} element.
 */
public class TagTest_imgSemantics {

    /**
     * Verifies that tag lookup is case-insensitive in HTML, regardless of the active JVM locale.
     * Tags retrieved via {@link Tag#valueOf} and {@link TagSet#valueOf} should be considered equal
     * whether the name is lower- or upper-case.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf: equal tags for different cases
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2, "Tag.valueOf should treat 'script' and 'SCRIPT' as equal");

        // TagSet.valueOf: identical (same) instance for different cases
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4, "TagSet.valueOf should return the same Tag instance for 'script' and 'SCRIPT'");
    }

    /**
     * Verifies the semantic properties of the {@code <img>} tag:
     * it is an inline element, it is self-closing (void), and it is not a block element.
     */
    @Test
    public void imgSemantics() {
        Tag img = Tag.valueOf("img");

        assertTrue(img.isInline(), "img should be an inline element");
        assertTrue(img.isSelfClosing(), "img should be self-closing (void)");
        assertFalse(img.isBlock(), "img should not be a block element");
    }
}
