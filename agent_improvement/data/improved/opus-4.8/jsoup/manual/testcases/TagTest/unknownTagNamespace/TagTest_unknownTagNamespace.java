package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TagTest_unknownTagNamespace {

    /**
     * Tag lookup is case-insensitive: requesting a tag by its name in different
     * letter cases yields equivalent tags, regardless of the active locale.
     */
    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf(...) returns tags that are equal across letter case (but not necessarily the same instance).
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper);

        // A shared TagSet caches known tags, so case-insensitive lookups return the very same instance.
        TagSet htmlTags = TagSet.Html();
        Tag cachedScriptLower = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag cachedScriptUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(cachedScriptLower, cachedScriptUpper);
    }

    /**
     * An unknown tag adopts the namespace it is requested in (defaulting to HTML
     * when none is given), and is never reported as a known tag.
     */
    @Test
    public void unknownTagNamespace() {
        // Looking up "foo" without a namespace defaults it to the HTML namespace.
        Tag fooDefaultNamespace = Tag.valueOf("foo");
        // Looking up "foo" while explicitly requesting the SVG namespace keeps that namespace.
        Tag fooSvgNamespace = Tag.valueOf("foo", Parser.NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml, fooDefaultNamespace.namespace());
        assertEquals(Parser.NamespaceSvg, fooSvgNamespace.namespace());

        // "foo" is not a predefined tag, so it is generated on demand and reported as unknown.
        assertFalse(fooDefaultNamespace.isKnownTag());
        assertFalse(fooSvgNamespace.isKnownTag());
    }
}
