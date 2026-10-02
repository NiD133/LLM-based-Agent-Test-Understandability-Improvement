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
 Tag tests.
 @author Jonathan Hedley, jonathan@hedley.net */
public class TagTest {

    // ---------------------------------------------------------------------
    // Name normalisation and case sensitivity
    // ---------------------------------------------------------------------

    @Test public void isCaseSensitive() {
        // The single-arg valueOf preserves case, so "P" and "p" are different tags.
        Tag upperP = Tag.valueOf("P");
        Tag lowerP = Tag.valueOf("p");
        assertNotEquals(upperP, lowerP);
    }

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        Locale.setDefault(locale);

        // With htmlDefault settings, tag names are folded to lower case, so "script" == "SCRIPT".
        Tag scriptLower = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpper = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(scriptLower, scriptUpper);

        // Within a single TagSet, the case-insensitive lookups return the very same instance.
        TagSet htmlTags = TagSet.Html();
        Tag fromTagSetLower = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag fromTagSetUpper = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(fromTagSetLower, fromTagSetUpper);
    }

    @Test public void trims() {
        // Surrounding whitespace in the tag name is trimmed away.
        Tag p = Tag.valueOf("p");
        Tag paddedP = Tag.valueOf(" p ");
        assertEquals(p, paddedP);
    }

    // ---------------------------------------------------------------------
    // Equality vs. identity
    // ---------------------------------------------------------------------

    @Test public void equality() {
        // Tag.valueOf clones the shared TagSet.Html, so equal-but-not-same tags are returned.
        Tag p1 = Tag.valueOf("p");
        Tag p2 = Tag.valueOf("p");
        assertEquals(p1, p2);
        assertNotSame(p1, p2); // not same because Tag.valueOf creates new clone of the TagSet.Html, so changes don't clobber all

        // Two freshly built HTML TagSets are equal in content but distinct instances.
        TagSet html1 = TagSet.Html();
        TagSet html2 = TagSet.Html();
        assertEquals(html1, html2);
        assertNotSame(html1, html2);

        // Lookups within the same TagSet are identical instances; across TagSets they are only equal.
        Tag html1P_a = html1.valueOf("p", NamespaceHtml);
        Tag html1P_b = html1.valueOf("p", NamespaceHtml);
        Tag html2P_a = html2.valueOf("p", NamespaceHtml);
        Tag html2P_b = html2.valueOf("p", NamespaceHtml);
        assertEquals(p1, html1P_a);
        assertEquals(html1P_a, html1P_b);
        assertEquals(html1P_b, html2P_a);
        assertSame(html1P_a, html1P_b);   // same TagSet -> same instance
        assertSame(html2P_a, html2P_b);   // same TagSet -> same instance
        assertNotSame(html1P_a, html2P_a); // different TagSet -> different instance
    }

    // ---------------------------------------------------------------------
    // Built-in tag semantics (block / inline / void / etc.)
    // ---------------------------------------------------------------------

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
        // An undefined tag name yields a generic, unknown, inline tag.
        Tag foo = Tag.valueOf("FOO"); // not defined
        Tag fooAgain = Tag.valueOf("FOO");

        assertEquals(foo, fooAgain);
        assertFalse(foo.isKnownTag());
        assertTrue(foo.isInline());
        assertFalse(foo.isBlock());
        assertFalse(foo.is(Tag.InlineContainer));
        assertFalse(foo.preserveWhitespace());
    }

    // ---------------------------------------------------------------------
    // valueOf argument validation
    // ---------------------------------------------------------------------

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

    // ---------------------------------------------------------------------
    // Namespace handling
    // ---------------------------------------------------------------------

    @Test public void knownSvgNamespace() {
        Tag svgInHtmlNs = Tag.valueOf("svg"); // no namespace specified, defaults to html, so not the known tag
        Tag svgInSvgNs = Tag.valueOf("svg", Parser.NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml, svgInHtmlNs.namespace());
        assertEquals(Parser.NamespaceSvg, svgInSvgNs.namespace());

        assertFalse(svgInHtmlNs.isKnownTag()); // generated
        assertTrue(svgInSvgNs.isKnownTag());   // known
    }

    @Test public void unknownTagNamespace() {
        Tag fooInHtmlNs = Tag.valueOf("foo"); // no namespace specified, defaults to html
        Tag fooInSvgNs = Tag.valueOf("foo", Parser.NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml, fooInHtmlNs.namespace());
        assertEquals(Parser.NamespaceSvg, fooInSvgNs.namespace());

        assertFalse(fooInHtmlNs.isKnownTag()); // generated
        assertFalse(fooInSvgNs.isKnownTag());  // generated
    }

    // ---------------------------------------------------------------------
    // Mutating options on a tag
    // ---------------------------------------------------------------------

    @Test void canSetOptions() {
        Tag tag = new Tag("foo", NamespaceHtml);
        assertFalse(tag.isKnownTag());
        assertFalse(tag.isEmpty());

        tag.set(Tag.Void);

        assertTrue(tag.isEmpty());
        assertTrue(tag.isKnownTag()); // setting any option marks the tag as known
    }

    @Test void textBoundaryOption() {
        Tag tag = new Tag("foo", NamespaceHtml);
        assertFalse(tag.is(Tag.TextBoundary));

        tag.set(Tag.TextBoundary);

        assertTrue(tag.is(Tag.TextBoundary));
        assertTrue(tag.isKnownTag()); // setting any option marks the tag as known
    }

    @Test void updateNameAndNamespace() {
        Tag tag = new Tag("foo", NamespaceHtml);
        tag.name("bar").namespace(NamespaceSvg);
        tag.set(Tag.Block);
        assertEquals("bar", tag.name());
        assertEquals(NamespaceSvg, tag.namespace());
        assertTrue(tag.isBlock()); // properties are unchanged

        // Renaming a tag used in a document is case-sensitive and applies to every use.
        Document doc = Jsoup.parse("<foo>One</foo><foo>Two</foo>");
        Tag foo = doc.expectFirst("foo").tag();
        foo.name("BAR");
        assertEquals("<BAR>One</BAR><BAR>Two</BAR>", doc.body().html()); // is case-sensitive
    }

    @Test void formSubmittable() {
        // https://github.com/jhy/jsoup/issues/2323
        Tag img = Tag.valueOf("img");
        Tag input = Tag.valueOf("input");

        // Capture the raw option bitmasks to confirm the queries below don't mutate them.
        int imgOpts = img.options;
        int inputOpts = input.options;

        assertFalse(img.isFormSubmittable());
        assertTrue(input.isFormSubmittable());

        assertEquals(imgOpts, img.options);
        assertEquals(inputOpts, input.options);
    }

    // ---------------------------------------------------------------------
    // Hashing and name parsing
    // ---------------------------------------------------------------------

    @Test void stableHashcode() {
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

        // hashCode is derived from name + namespace only, so mutating options keeps it stable.
        imgS.set(Tag.Block);
        assertEquals(-292873947, imgS.hashCode());

        assertTrue(tags.contains(img));
        assertTrue(tags.contains(IMG));
        assertTrue(tags.contains(imgS));
    }

    @Test void prefix() {
        Tag img = Tag.valueOf("img");
        Tag book = Tag.valueOf("bk:book");

        assertEquals("", img.prefix());
        assertEquals("bk", book.prefix());
    }

    @Test void localname() {
        Tag img = Tag.valueOf("img");
        Tag book = Tag.valueOf("bk:book");

        assertEquals("img", img.localName());
        assertEquals("book", book.localName());
    }

    @Test void valueOfWithSettings() {
        Tag img1 = Tag.valueOf("img", ParseSettings.htmlDefault);
        Tag img2 = Tag.valueOf("IMG", ParseSettings.htmlDefault);
        Tag img3 = Tag.valueOf("IMG", ParseSettings.preserveCase);

        assertNotSame(img1, img2); // because we are creating new TagSets with html()
        assertNotSame(img1, img3);
        assertEquals("IMG", img3.toString()); // preserveCase keeps the original casing
        assertEquals("img", img1.toString()); // htmlDefault lower-cases the name

        // Within one TagSet, case-insensitive lookups share an instance, but case-sensitive ones don't.
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

    // ---------------------------------------------------------------------
    // equals() field-by-field contract
    // ---------------------------------------------------------------------

    @Test void equals() {
        TagSet tags = TagSet.Html();
        Tag original = tags.get("p", NamespaceHtml);
        Tag copy = original.clone();
        assertEquals(original, copy);
        assertNotEquals(original, tags); // a Tag never equals a non-Tag

        // Each equality-relevant field, when changed, must break equality; restoring it repairs equality.
        copy.namespace = "Other";
        assertNotEquals(original, copy);
        copy.namespace = original.namespace;

        copy.tagName = "P";
        assertNotEquals(original, copy);
        copy.tagName = original.tagName;

        copy.normalName = "pp";
        assertNotEquals(original, copy);
        copy.normalName = original.normalName;

        copy.options = 0;
        assertNotEquals(original, copy);
        copy.options = original.options;
    }
}
