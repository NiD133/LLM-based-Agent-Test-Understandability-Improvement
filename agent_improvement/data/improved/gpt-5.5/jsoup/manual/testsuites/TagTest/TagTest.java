package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Locale;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceSvg;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Tag} creation, equality, namespace handling, and option flags.
 */
public class TagTest {
    @Test public void isCaseSensitive() {
        Tag upperCaseP = Tag.valueOf("P");
        Tag lowerCaseP = Tag.valueOf("p");

        assertNotEquals(upperCaseP, lowerCaseP);
    }

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        Tag lowerCaseScript = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag upperCaseScript = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(lowerCaseScript, upperCaseScript);

        TagSet htmlTags = TagSet.Html();
        Tag scriptFromLowerCaseName = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptFromUpperCaseName = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromLowerCaseName, scriptFromUpperCaseName);
    }

    @Test public void trims() {
        Tag p = Tag.valueOf("p");
        Tag paddedP = Tag.valueOf(" p ");

        assertEquals(p, paddedP);
    }

    @Test public void equality() {
        Tag pFromStaticLookup = Tag.valueOf("p");
        Tag secondPFromStaticLookup = Tag.valueOf("p");
        assertEquals(pFromStaticLookup, secondPFromStaticLookup);
        assertNotSame(pFromStaticLookup, secondPFromStaticLookup);

        TagSet firstHtmlTags = TagSet.Html();
        TagSet secondHtmlTags = TagSet.Html();
        assertEquals(firstHtmlTags, secondHtmlTags);
        assertNotSame(firstHtmlTags, secondHtmlTags);

        Tag firstPFromFirstSet = firstHtmlTags.valueOf("p", NamespaceHtml);
        Tag secondPFromFirstSet = firstHtmlTags.valueOf("p", NamespaceHtml);
        Tag firstPFromSecondSet = secondHtmlTags.valueOf("p", NamespaceHtml);
        Tag secondPFromSecondSet = secondHtmlTags.valueOf("p", NamespaceHtml);
        assertEquals(pFromStaticLookup, firstPFromFirstSet);
        assertEquals(firstPFromFirstSet, secondPFromFirstSet);
        assertEquals(secondPFromFirstSet, firstPFromSecondSet);
        assertSame(firstPFromFirstSet, secondPFromFirstSet);
        assertSame(firstPFromSecondSet, secondPFromSecondSet);
        assertNotSame(firstPFromFirstSet, firstPFromSecondSet);
    }

    @Test public void divSemantics() {
        Tag div = Tag.valueOf("div");

        assertTrue(div.isBlock());
        assertFalse(div.isInline());
        assertTrue(div.isKnownTag());
    }

    @Test public void pSemantics() {
        Tag p = Tag.valueOf("p");

        assertTrue(p.isKnownTag());
        assertTrue(p.isBlock());
        assertFalse(p.isInline());
    }

    @Test public void brSemantics() {
        Tag br = Tag.valueOf("br");

        assertTrue(br.isInline());
        assertFalse(br.isBlock());
    }

    @Test public void imgSemantics() {
        Tag img = Tag.valueOf("img");

        assertTrue(img.isInline());
        assertTrue(img.isSelfClosing());
        assertFalse(img.isBlock());
    }

    @Test public void buttonSemantics() {
        Tag button = Tag.valueOf("button");

        assertTrue(button.isInline());
        assertFalse(button.isBlock());
        assertTrue(button.is(Tag.TextBoundary));
        assertTrue(button.isKnownTag());
    }

    @Test public void defaultSemantics() {
        Tag unknownFoo = Tag.valueOf("FOO");
        Tag anotherUnknownFoo = Tag.valueOf("FOO");

        assertEquals(unknownFoo, anotherUnknownFoo);
        assertFalse(unknownFoo.isKnownTag());
        assertTrue(unknownFoo.isInline());
        assertFalse(unknownFoo.isBlock());
        assertFalse(unknownFoo.is(Tag.InlineContainer));
        assertFalse(unknownFoo.preserveWhitespace());
    }

    @Test public void valueOfChecksNotNull() {
        assertThrows(IllegalArgumentException.class, () -> Tag.valueOf(null));
    }

    @Test public void valueOfChecksNotEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Tag.valueOf(" "));
    }

    @Test public void knownTags() {
        assertTrue(Tag.isKnownTag("div"));
        assertFalse(Tag.isKnownTag("explain"));
    }

    @Test public void knownSvgNamespace() {
        Tag svgInDefaultHtmlNamespace = Tag.valueOf("svg");
        Tag svgInSvgNamespace = Tag.valueOf("svg", Parser.NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml, svgInDefaultHtmlNamespace.namespace());
        assertEquals(Parser.NamespaceSvg, svgInSvgNamespace.namespace());

        assertFalse(svgInDefaultHtmlNamespace.isKnownTag());
        assertTrue(svgInSvgNamespace.isKnownTag());
    }

    @Test public void unknownTagNamespace() {
        Tag fooInDefaultHtmlNamespace = Tag.valueOf("foo");
        Tag fooInSvgNamespace = Tag.valueOf("foo", Parser.NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml, fooInDefaultHtmlNamespace.namespace());
        assertEquals(Parser.NamespaceSvg, fooInSvgNamespace.namespace());

        assertFalse(fooInDefaultHtmlNamespace.isKnownTag());
        assertFalse(fooInSvgNamespace.isKnownTag());
    }

    @Test void canSetOptions() {
        Tag tag = new Tag("foo", NamespaceHtml);
        assertFalse(tag.isKnownTag());
        assertFalse(tag.isEmpty());

        tag.set(Tag.Void);

        assertTrue(tag.isEmpty());
        assertTrue(tag.isKnownTag());
    }

    @Test void textBoundaryOption() {
        Tag tag = new Tag("foo", NamespaceHtml);
        assertFalse(tag.is(Tag.TextBoundary));

        tag.set(Tag.TextBoundary);

        assertTrue(tag.is(Tag.TextBoundary));
        assertTrue(tag.isKnownTag());
    }

    @Test void updateNameAndNamespace() {
        Tag tag = new Tag("foo", NamespaceHtml);
        tag.name("bar").namespace(NamespaceSvg);
        tag.set(Tag.Block);
        assertEquals("bar", tag.name());
        assertEquals(NamespaceSvg, tag.namespace());
        assertTrue(tag.isBlock());

        Document doc = Jsoup.parse("<foo>One</foo><foo>Two</foo>");
        Tag foo = doc.expectFirst("foo").tag();
        foo.name("BAR");
        assertEquals("<BAR>One</BAR><BAR>Two</BAR>", doc.body().html());
    }

    @Test void formSubmittable() {
        Tag img = Tag.valueOf("img");
        Tag input = Tag.valueOf("input");
        int originalImgOptions = img.options;
        int originalInputOptions = input.options;

        assertFalse(img.isFormSubmittable());
        assertTrue(input.isFormSubmittable());
        assertEquals(originalImgOptions, img.options);
        assertEquals(originalInputOptions, input.options);
    }

    @Test void stableHashcode() {
        HashSet<Tag> tags = new HashSet<>();
        Tag lowercaseHtmlImg = Tag.valueOf("img");
        Tag uppercaseHtmlImg = Tag.valueOf("IMG");
        Tag lowercaseSvgImg = Tag.valueOf("img", NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(-2074969810, lowercaseHtmlImg.hashCode());
        assertEquals(-2075954866, uppercaseHtmlImg.hashCode());
        assertEquals(-292873947, lowercaseSvgImg.hashCode());

        tags.add(lowercaseHtmlImg);
        tags.add(uppercaseHtmlImg);
        tags.add(lowercaseSvgImg);

        lowercaseSvgImg.set(Tag.Block);
        assertEquals(-292873947, lowercaseSvgImg.hashCode());

        assertTrue(tags.contains(lowercaseHtmlImg));
        assertTrue(tags.contains(uppercaseHtmlImg));
        assertTrue(tags.contains(lowercaseSvgImg));
    }

    @Test void prefix() {
        Tag img = Tag.valueOf("img");
        Tag namespacedBook = Tag.valueOf("bk:book");

        assertEquals("", img.prefix());
        assertEquals("bk", namespacedBook.prefix());
    }

    @Test void localname() {
        Tag img = Tag.valueOf("img");
        Tag namespacedBook = Tag.valueOf("bk:book");

        assertEquals("img", img.localName());
        assertEquals("book", namespacedBook.localName());
    }

    @Test void valueOfWithSettings() {
        Tag lowerCaseImg = Tag.valueOf("img", ParseSettings.htmlDefault);
        Tag upperCaseImgWithHtmlSettings = Tag.valueOf("IMG", ParseSettings.htmlDefault);
        Tag upperCaseImgWithPreserveCaseSettings = Tag.valueOf("IMG", ParseSettings.preserveCase);

        assertNotSame(lowerCaseImg, upperCaseImgWithHtmlSettings);
        assertNotSame(lowerCaseImg, upperCaseImgWithPreserveCaseSettings);
        assertEquals("IMG", upperCaseImgWithPreserveCaseSettings.toString());
        assertEquals("img", lowerCaseImg.toString());

        TagSet tagSet = TagSet.Html();
        assertSame(
            tagSet.valueOf("img", NamespaceHtml, ParseSettings.htmlDefault),
            tagSet.valueOf("IMG", NamespaceHtml, ParseSettings.htmlDefault)
        );

        assertNotSame(
            tagSet.valueOf("img", NamespaceHtml),
            tagSet.valueOf("IMG", NamespaceHtml)
        );
    }

    @Test void equals() {
        TagSet tags = TagSet.Html();
        Tag p = tags.get("p", NamespaceHtml);
        Tag clonedP = p.clone();

        assertEquals(p, clonedP);
        assertNotEquals(p, tags);

        clonedP.namespace = "Other";
        assertNotEquals(p, clonedP);
        clonedP.namespace = p.namespace;

        clonedP.tagName = "P";
        assertNotEquals(p, clonedP);
        clonedP.tagName = p.tagName;

        clonedP.normalName = "pp";
        assertNotEquals(p, clonedP);
        clonedP.normalName = p.normalName;

        clonedP.options = 0;
        assertNotEquals(p, clonedP);
        clonedP.options = p.options;
    }
}
