package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_updateNameAndNamespace {

    // Verifies that tag name comparison is case-insensitive across different locales.
    // The Turkish locale is a common edge-case where lower-casing 'I' produces 'ı' instead of 'i',
    // so running under multiple locales guards against locale-sensitive string comparisons.
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    @Test
    void updateNameAndNamespace() {
        // Mutate both name and namespace on a standalone tag
        Tag tag = new Tag("foo", NamespaceHtml);
        tag.name("bar").namespace(NamespaceSvg);
        tag.set(Tag.Block);

        assertEquals("bar", tag.name());
        assertEquals(NamespaceSvg, tag.namespace());
        // Changing name/namespace must not reset options that were set independently
        assertTrue(tag.isBlock());

        // Tags are shared objects inside a Document: renaming one Tag instance
        // propagates to every Element that holds a reference to it.
        Document doc = Jsoup.parse("<foo>One</foo><foo>Two</foo>");
        Tag foo = doc.expectFirst("foo").tag();
        foo.name("BAR");

        // Tag names are stored case-sensitively; "BAR" is not normalised to "bar"
        assertEquals("<BAR>One</BAR><BAR>Two</BAR>", doc.body().html());
    }
}
