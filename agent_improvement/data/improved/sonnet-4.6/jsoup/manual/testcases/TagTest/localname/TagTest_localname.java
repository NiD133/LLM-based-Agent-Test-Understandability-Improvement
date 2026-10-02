package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_localname {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // assertEquals: htmlDefault normalises both to the same value; assertSame below checks caching
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        // TagSet caches known tags, so both lookups must return the identical instance
        TagSet htmlTags = TagSet.Html();
        Tag script3 = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script4 = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(script3, script4);
    }

    @Test
    void localname() {
        Tag img = Tag.valueOf("img");       // no prefix: localName == full tag name
        Tag book = Tag.valueOf("bk:book");  // prefix "bk": localName strips "bk:" leaving "book"
        assertEquals("img", img.localName());
        assertEquals("book", book.localName());
    }
}
