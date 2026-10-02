package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest_updateNameAndNamespace {
    private static final String LOWERCASE_SCRIPT = "script";
    private static final String UPPERCASE_SCRIPT = "SCRIPT";
    private static final String ORIGINAL_TAG_NAME = "foo";
    private static final String RENAMED_TAG_NAME = "bar";
    private static final String CASE_PRESERVED_TAG_NAME = "BAR";
    private static final String ORIGINAL_DOCUMENT_HTML = "<foo>One</foo><foo>Two</foo>";
    private static final String RENAMED_DOCUMENT_HTML = "<BAR>One</BAR><BAR>Two</BAR>";

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag script1 = Tag.valueOf(LOWERCASE_SCRIPT, NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf(UPPERCASE_SCRIPT, NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);

        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf(LOWERCASE_SCRIPT, NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf(UPPERCASE_SCRIPT, NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    @Test
    void updateNameAndNamespace() {
        Tag tag = new Tag(ORIGINAL_TAG_NAME, NamespaceHtml);

        tag.name(RENAMED_TAG_NAME).namespace(NamespaceSvg);
        tag.set(Tag.Block);

        assertTagNameAndNamespace(tag, RENAMED_TAG_NAME, NamespaceSvg);
        assertTrue(tag.isBlock());

        Document doc = Jsoup.parse(ORIGINAL_DOCUMENT_HTML);
        Tag foo = doc.expectFirst(ORIGINAL_TAG_NAME).tag();
        foo.name(CASE_PRESERVED_TAG_NAME);

        assertEquals(RENAMED_DOCUMENT_HTML, doc.body().html());
    }

    private static void assertTagNameAndNamespace(Tag tag, String expectedName, String expectedNamespace) {
        assertEquals(expectedName, tag.name());
        assertEquals(expectedNamespace, tag.namespace());
    }
}
