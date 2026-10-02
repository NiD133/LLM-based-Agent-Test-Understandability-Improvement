package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest_knownSvgNamespace {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowercaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag uppercaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowercaseScript, uppercaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag tagSetLowercaseScript = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag tagSetUppercaseScript = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(tagSetLowercaseScript, tagSetUppercaseScript);
    }

    @Test
    public void knownSvgNamespace() {
        Tag svgInDefaultHtmlNamespace = Tag.valueOf("svg");
        Tag svgInSvgNamespace = Tag.valueOf("svg", Parser.NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml, svgInDefaultHtmlNamespace.namespace());
        assertEquals(Parser.NamespaceSvg, svgInSvgNamespace.namespace());

        assertFalse(svgInDefaultHtmlNamespace.isKnownTag());
        assertTrue(svgInSvgNamespace.isKnownTag());
    }
}
