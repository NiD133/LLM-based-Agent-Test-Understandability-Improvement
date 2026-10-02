package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_knownSvgNamespace {

    /**
     * Verifies that tag lookup is case-insensitive for HTML tags: "script" and "SCRIPT"
     * should resolve to the same tag regardless of the JVM's default locale.
     * Using a TagSet also guarantees identity (==), not just equality.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Via the static helper: different cases should be equal (but not necessarily identical)
        Tag scriptLowerCase = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperCase = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLowerCase, scriptUpperCase);

        // Via a TagSet instance: the same canonical Tag object must be returned for both cases
        TagSet htmlTags = TagSet.Html();
        Tag scriptFromTagSetLower = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromTagSetUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromTagSetLower, scriptFromTagSetUpper);
    }

    /**
     * Verifies namespace-aware tag lookup for the SVG "svg" element.
     *
     * <p>The SVG {@code <svg>} element is only a "known" tag when looked up with the SVG
     * namespace.  When looked up without an explicit namespace (defaulting to HTML), the
     * parser has no predefined definition for it and therefore treats it as an unknown,
     * generated tag.  This distinction affects how the pretty-printer and other consumers
     * handle the element.</p>
     */
    @Test
    public void knownSvgNamespace() {
        // Lookup "svg" without specifying a namespace defaults to the HTML namespace.
        // No HTML definition exists for <svg>, so the resulting tag is auto-generated (not known).
        Tag svgInHtmlNamespace = Tag.valueOf("svg");
        assertEquals(NamespaceHtml, svgInHtmlNamespace.namespace(),
            "Tag looked up without namespace should default to the HTML namespace");
        assertFalse(svgInHtmlNamespace.isKnownTag(),
            "<svg> in the HTML namespace is not a pre-defined tag and must be treated as generated");

        // Lookup "svg" with the SVG namespace resolves to the pre-defined SVG tag definition.
        Tag svgInSvgNamespace = Tag.valueOf("svg", NamespaceSvg, ParseSettings.htmlDefault);
        assertEquals(NamespaceSvg, svgInSvgNamespace.namespace(),
            "Tag looked up with the SVG namespace should retain that namespace");
        assertTrue(svgInSvgNamespace.isKnownTag(),
            "<svg> in the SVG namespace is a pre-defined tag and must be recognised as known");
    }
}
