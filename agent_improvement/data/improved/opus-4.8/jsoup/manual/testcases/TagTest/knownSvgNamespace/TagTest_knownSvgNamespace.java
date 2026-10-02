package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest_knownSvgNamespace {

    /**
     * Tag name matching is case-insensitive in the HTML namespace, regardless of the active locale.
     * "script" and "SCRIPT" must resolve to equal tags, and to the very same instance when looked up
     * from a shared TagSet.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf reuses pre-defined tags, so different-cased names yield equal tags.
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper);

        // Looked up from the same TagSet, the known tag is cached, so both lookups return one instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedScriptLower = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedScriptUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedScriptLower, cachedScriptUpper);
    }

    /**
     * "svg" is only a known, pre-defined tag in the SVG namespace. Without an explicit namespace it
     * defaults to HTML, where it is just an auto-generated generic tag.
     */
    @Test
    public void knownSvgNamespace() {
        // No namespace given: defaults to HTML, where "svg" is not pre-defined.
        Tag svgInHtml = Tag.valueOf("svg");
        // Explicit SVG namespace: resolves to the pre-defined SVG tag.
        Tag svgInSvg = Tag.valueOf("svg", NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml, svgInHtml.namespace());
        assertEquals(NamespaceSvg, svgInSvg.namespace());

        assertFalse(svgInHtml.isKnownTag(), "svg in the HTML namespace is auto-generated, not known");
        assertTrue(svgInSvg.isKnownTag(), "svg in the SVG namespace is a pre-defined, known tag");
    }
}
