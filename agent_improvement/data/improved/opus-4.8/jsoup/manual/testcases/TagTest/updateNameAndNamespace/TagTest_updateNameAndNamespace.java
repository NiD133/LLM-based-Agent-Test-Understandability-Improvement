package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_updateNameAndNamespace {

    /**
     * Tag name lookup ignores case: "script" and "SCRIPT" resolve to equal (and, within a
     * single TagSet, the very same) Tag instance, regardless of the active locale.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Looking up the same tag in different cases yields equal Tags.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // Within one TagSet, the case-insensitive lookups even return the identical cached instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedLowerCaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedUpperCaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedLowerCaseScript, cachedUpperCaseScript);
    }

    /**
     * A Tag's name and namespace can be reassigned after construction, the change does not disturb
     * previously set options, and renaming a parsed Tag is case-sensitive in the rendered output.
     */
    @Test
    void updateNameAndNamespace() {
        // Arrange: a "foo" tag in the HTML namespace, then rename, re-namespace, and mark it as a block.
        Tag tag = new Tag("foo", NamespaceHtml);
        tag.name("bar").namespace(NamespaceSvg);
        tag.set(Tag.Block);

        // The name and namespace reflect the new values.
        assertEquals("bar", tag.name());
        assertEquals(NamespaceSvg, tag.namespace());
        // The previously applied Block option survives the renaming.
        assertTrue(tag.isBlock());

        // Renaming a tag that is shared across a parsed document is case-sensitive: the new
        // casing appears verbatim in every occurrence of the rendered output.
        Document doc = Jsoup.parse("<foo>One</foo><foo>Two</foo>");
        Tag foo = doc.expectFirst("foo").tag();
        foo.name("BAR");
        assertEquals("<BAR>One</BAR><BAR>Two</BAR>", doc.body().html());
    }
}
