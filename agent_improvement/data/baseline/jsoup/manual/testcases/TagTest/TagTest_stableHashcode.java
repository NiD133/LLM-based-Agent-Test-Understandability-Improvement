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

public class TagTest_stableHashcode {

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
    void stableHashcode() {
        // tests that the hashcode is stable and suitable as a key
        HashSet<Tag> tags = new HashSet<>();
        Tag img = Tag.valueOf("img");
        Tag IMG = Tag.valueOf("IMG");
        Tag imgS = Tag.valueOf("img", NamespaceSvg, ParseSettings.htmlDefault);
        assertEquals(-2074969810, img.hashCode());
        assertEquals(-2075954866, IMG.hashCode());
        assertEquals(-292873947, imgS.hashCode());
        tags.add(img);
        tags.add(IMG);
        tags.add(imgS);
        imgS.set(Tag.Block);
        assertEquals(-292873947, imgS.hashCode());
        assertTrue(tags.contains(img));
        assertTrue(tags.contains(IMG));
        assertTrue(tags.contains(imgS));
    }
}
