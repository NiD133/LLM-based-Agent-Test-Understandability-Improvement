package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagTest_valueOfChecksNotNull {
    private static final String LOWERCASE_SCRIPT = "script";
    private static final String UPPERCASE_SCRIPT = "SCRIPT";

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowercaseScript = htmlTag(LOWERCASE_SCRIPT);
        Tag uppercaseScript = htmlTag(UPPERCASE_SCRIPT);
        assertEquals(lowercaseScript, uppercaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag lowercaseScriptFromTagSet = htmlTags.valueOf(LOWERCASE_SCRIPT, NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScriptFromTagSet = htmlTags.valueOf(UPPERCASE_SCRIPT, NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowercaseScriptFromTagSet, uppercaseScriptFromTagSet);
    }

    @Test
    public void valueOfChecksNotNull() {
        assertThrows(IllegalArgumentException.class, () -> Tag.valueOf(null));
    }

    private static Tag htmlTag(String tagName) {
        return Tag.valueOf(tagName, NamespaceHtml, ParseSettings.htmlDefault);
    }
}
