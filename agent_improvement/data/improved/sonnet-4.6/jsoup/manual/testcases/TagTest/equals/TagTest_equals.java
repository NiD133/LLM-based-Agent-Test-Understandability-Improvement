package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_equals {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static valueOf should treat "script" and "SCRIPT" as equal in HTML namespace
        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);

        // TagSet.valueOf returns the same interned Tag instance for case-insensitive lookups
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    @Test
    void clonedTagEqualsOriginal() {
        TagSet tags = TagSet.Html();
        Tag original = tags.get("p", NamespaceHtml);
        Tag clone = original.clone();

        assertEquals(original, clone);
    }

    @Test
    void tagDoesNotEqualNonTagObject() {
        TagSet tags = TagSet.Html();
        Tag p = tags.get("p", NamespaceHtml);

        assertNotEquals(p, tags);
    }

    @Test
    void tagsWithDifferentNamespaceAreNotEqual() {
        TagSet tags = TagSet.Html();
        Tag p1 = tags.get("p", NamespaceHtml);
        Tag p2 = p1.clone();

        p2.namespace = "Other";
        assertNotEquals(p1, p2);

        p2.namespace = p1.namespace;
    }

    @Test
    void tagsWithDifferentTagNameAreNotEqual() {
        TagSet tags = TagSet.Html();
        Tag p1 = tags.get("p", NamespaceHtml);
        Tag p2 = p1.clone();

        p2.tagName = "P";
        assertNotEquals(p1, p2);

        p2.tagName = p1.tagName;
    }

    @Test
    void tagsWithDifferentNormalNameAreNotEqual() {
        TagSet tags = TagSet.Html();
        Tag p1 = tags.get("p", NamespaceHtml);
        Tag p2 = p1.clone();

        p2.normalName = "pp";
        assertNotEquals(p1, p2);

        p2.normalName = p1.normalName;
    }

    @Test
    void tagsWithDifferentOptionsAreNotEqual() {
        TagSet tags = TagSet.Html();
        Tag p1 = tags.get("p", NamespaceHtml);
        Tag p2 = p1.clone();

        p2.options = 0;
        assertNotEquals(p1, p2);

        p2.options = p1.options;
    }
}
