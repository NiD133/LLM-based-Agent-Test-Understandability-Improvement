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

public class TagTest_knownSvgNamespace {

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
    public void knownSvgNamespace() {
        // no namespace specified, defaults to html, so not the known tag
        Tag svgHtml = Tag.valueOf("svg");
        Tag svg = Tag.valueOf("svg", Parser.NamespaceSvg, ParseSettings.htmlDefault);
        assertEquals(NamespaceHtml, svgHtml.namespace());
        assertEquals(Parser.NamespaceSvg, svg.namespace());
        // generated
        assertFalse(svgHtml.isKnownTag());
        // known
        assertTrue(svg.isKnownTag());
    }
}
