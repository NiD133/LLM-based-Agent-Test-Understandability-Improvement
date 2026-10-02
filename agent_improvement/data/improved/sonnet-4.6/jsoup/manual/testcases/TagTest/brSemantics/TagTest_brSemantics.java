package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Tag semantics: layout classification (inline vs block) and case-insensitive tag lookup.
 */
public class TagTest_brSemantics {

    /**
     * Verifies that HTML tag lookup is case-insensitive regardless of the JVM default locale.
     *
     * The {@code htmlDefault} ParseSettings normalises tag names to lowercase, so "SCRIPT" and
     * "script" must resolve to the same Tag. The locale guard catches the classic
     * Turkish-locale pitfall where {@code "SCRIPT".toLowerCase()} yields {@code "scrıpt"} (dotless-i).
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf: equal by value (not necessarily the same instance)
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper,
            "Tag.valueOf should treat 'script' and 'SCRIPT' as equal under htmlDefault settings");

        // TagSet.valueOf: returns cached instances, so both lookups must be the exact same object
        TagSet htmlTags = TagSet.Html();
        Tag scriptFromTagSetLower = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromTagSetUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromTagSetLower, scriptFromTagSetUpper,
            "TagSet.valueOf should return the same cached Tag instance for 'script' and 'SCRIPT'");
    }

    /**
     * Verifies that {@code <br>} is classified as an inline tag (not a block tag).
     *
     * In HTML, {@code <br>} is a void inline element. The Tag API expresses layout via two
     * complementary predicates: {@code isInline()} and {@code isBlock()}; both must be
     * consistent with each other for the same tag.
     */
    @Test
    public void brSemantics() {
        Tag br = Tag.valueOf("br");

        assertTrue(br.isInline(), "<br> should be an inline element");
        assertFalse(br.isBlock(), "<br> should not be a block element");
    }
}
