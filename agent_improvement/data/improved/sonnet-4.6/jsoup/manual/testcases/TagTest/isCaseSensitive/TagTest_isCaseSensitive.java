package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_isCaseSensitive {

    @MultiLocaleTest
    @DisplayName("HTML tags with different cases are equal under case-insensitive settings")
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Using Tag.valueOf with htmlDefault settings normalizes tag names, so "script" == "SCRIPT"
        Tag lowerScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerScript, upperScript);

        // TagSet.valueOf returns cached instances, so same-normalised names are identical objects
        TagSet htmlTags = TagSet.Html();
        Tag lowerScriptFromTagSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperScriptFromTagSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerScriptFromTagSet, upperScriptFromTagSet);
    }

    @Test
    @DisplayName("Tags with different cases are not equal under case-sensitive (default) settings")
    public void isCaseSensitive() {
        Tag upperP = Tag.valueOf("P");
        Tag lowerP = Tag.valueOf("p");
        assertNotEquals(upperP, lowerP);
    }
}
