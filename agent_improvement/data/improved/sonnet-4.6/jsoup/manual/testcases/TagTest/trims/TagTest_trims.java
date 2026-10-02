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

public class TagTest_trims {

    // Verifies that tag name lookup is case-insensitive regardless of the JVM's active locale,
    // since lowercasing can behave differently in locales like Turkish (e.g. 'I' -> 'ı').
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Static Tag.valueOf normalises the name, so "script" and "SCRIPT" should be equal.
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript,
            "Tag.valueOf should treat tag names case-insensitively under locale: " + locale);

        // TagSet.valueOf returns the same interned instance for both cases, so identity must hold.
        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseScriptFromTagSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScriptFromTagSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseScriptFromTagSet, upperCaseScriptFromTagSet,
            "TagSet.valueOf should return the same interned Tag instance for both cases under locale: " + locale);
    }

    // Verifies that Tag.valueOf strips leading and trailing whitespace from the tag name,
    // so " p " is treated as equivalent to "p".
    @Test
    public void trims() {
        Tag tagWithoutWhitespace = Tag.valueOf("p");
        Tag tagWithSurroundingWhitespace = Tag.valueOf(" p ");
        assertEquals(tagWithoutWhitespace, tagWithSurroundingWhitespace,
            "Tag.valueOf should trim surrounding whitespace from the tag name");
    }
}
