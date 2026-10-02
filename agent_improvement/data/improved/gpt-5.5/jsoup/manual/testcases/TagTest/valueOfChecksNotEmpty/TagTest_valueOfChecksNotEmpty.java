package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagTest_valueOfChecksNotEmpty {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseScriptFromTagSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScriptFromTagSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseScriptFromTagSet, upperCaseScriptFromTagSet);
    }

    @Test
    public void valueOfChecksNotEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Tag.valueOf(" "));
    }
}
