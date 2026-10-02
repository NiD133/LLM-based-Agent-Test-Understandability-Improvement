package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_knownTags {

    // Verifies that tag lookup is case-insensitive, both via the static Tag.valueOf
    // and via the TagSet instance method, regardless of the active JVM locale.
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static Tag.valueOf should treat "script" and "SCRIPT" as equal tags
        Tag tagLowerCase = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag tagUpperCase = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(tagLowerCase, tagUpperCase,
            "Tag.valueOf should return equal Tags for the same name in different cases");

        // TagSet.valueOf returns the same cached Tag instance for any case variant
        TagSet htmlTags = TagSet.Html();
        Tag tagLowerCaseFromTagSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag tagUpperCaseFromTagSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(tagLowerCaseFromTagSet, tagUpperCaseFromTagSet,
            "TagSet.valueOf should return the identical Tag instance regardless of case");
    }

    // Verifies that standard HTML tags are recognized as known, while invented tag names are not.
    @Test
    public void knownTags() {
        assertTrue(Tag.isKnownTag("div"),
            "\"div\" is a standard HTML tag and should be recognized as known");
        assertFalse(Tag.isKnownTag("explain"),
            "\"explain\" is not a standard HTML tag and should not be recognized as known");
    }
}
