package org.jsoup.parser;

import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

public class TagTest_unknownTagNamespace {

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // Tag.valueOf should treat tag names case-insensitively under htmlDefault settings
        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript,
            "Tag.valueOf should return equal tags for 'script' and 'SCRIPT' under htmlDefault settings");

        // TagSet.valueOf should return the same cached instance for case-insensitive lookups
        TagSet htmlTags = TagSet.Html();
        Tag lowerCaseScriptFromSet = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScriptFromSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(lowerCaseScriptFromSet, upperCaseScriptFromSet,
            "TagSet.valueOf should return the same Tag instance for 'script' and 'SCRIPT' under htmlDefault settings");
    }

    @Test
    public void unknownTagNamespace() {
        // When no namespace is specified, Tag.valueOf defaults to the HTML namespace
        Tag fooWithDefaultNamespace = Tag.valueOf("foo");
        assertEquals(NamespaceHtml, fooWithDefaultNamespace.namespace(),
            "Tag.valueOf(name) with no namespace argument should default to the HTML namespace");

        // When an explicit SVG namespace is provided, the tag should carry that namespace
        Tag fooWithSvgNamespace = Tag.valueOf("foo", Parser.NamespaceSvg, ParseSettings.htmlDefault);
        assertEquals(NamespaceSvg, fooWithSvgNamespace.namespace(),
            "Tag.valueOf with SVG namespace should retain the SVG namespace");

        // Both tags are for an unknown element name ("foo"), so neither should be marked as a known tag
        assertFalse(fooWithDefaultNamespace.isKnownTag(),
            "An unknown tag in the HTML namespace should not be marked as a known tag");
        assertFalse(fooWithSvgNamespace.isKnownTag(),
            "An unknown tag in the SVG namespace should not be marked as a known tag");
    }
}
