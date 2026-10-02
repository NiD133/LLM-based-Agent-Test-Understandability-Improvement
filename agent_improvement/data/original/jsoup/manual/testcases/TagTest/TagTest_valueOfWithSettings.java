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

public class TagTest_valueOfWithSettings {

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
    void valueOfWithSettings() {
        Tag img1 = Tag.valueOf("img", ParseSettings.htmlDefault);
        Tag img2 = Tag.valueOf("IMG", ParseSettings.htmlDefault);
        Tag img3 = Tag.valueOf("IMG", ParseSettings.preserveCase);
        // because we are creating new TagSets with html()
        assertNotSame(img1, img2);
        assertNotSame(img1, img3);
        assertEquals("IMG", img3.toString());
        assertEquals("img", img1.toString());
        TagSet tagSet = TagSet.Html();
        assertSame(tagSet.valueOf("img", NamespaceHtml, ParseSettings.htmlDefault), tagSet.valueOf("IMG", NamespaceHtml, ParseSettings.htmlDefault));
        assertNotSame(tagSet.valueOf("img", NamespaceHtml), tagSet.valueOf("IMG", NamespaceHtml));
    }
}
