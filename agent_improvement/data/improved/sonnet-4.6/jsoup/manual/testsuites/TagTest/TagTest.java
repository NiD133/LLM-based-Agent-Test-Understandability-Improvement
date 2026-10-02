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
 Tests for {@link Tag}: creation, identity, semantics, options, and namespace handling.
 @author Jonathan Hedley, jonathan@hedley.net */
public class TagTest {

    @Test public void isCaseSensitive() {
        // Tag.valueOf without settings is case-sensitive: "P" and "p" are distinct tags
        Tag upperP = Tag.valueOf("P");
        Tag lowerP = Tag.valueOf("p");
        assertNotEquals(upperP, lowerP);
    }

    @MultiLocaleTest
    public void canBeInsensitive(Locale locale) {
        // htmlDefault settings normalise names to lowercase, so "SCRIPT" resolves to the same tag as "script"
        Locale.setDefault(locale);

        Tag script1 = Tag.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag script2 = Tag.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertEquals(script1, script2);

        // Within a shared TagSet the normalised names map to the identical cached instance
        TagSet htmlTags = TagSet.Html();
        Tag scriptFromTagSet      = htmlTags.valueOf("script", NamespaceHtml, ParseSettings.htmlDefault);
        Tag scriptUpperFromTagSet = htmlTags.valueOf("SCRIPT", NamespaceHtml, ParseSettings.htmlDefault);
        assertSame(scriptFromTagSet, scriptUpperFromTagSet);
    }

    @Test public void trims() {
        // Leading and trailing whitespace in the tag name is ignored
        Tag p            = Tag.valueOf("p");
        Tag pWithSpaces  = Tag.valueOf(" p ");
        assertEquals(p, pWithSpaces);
    }

    @Test public void equality() {
        // Tag.valueOf creates a fresh TagSet.Html() clone each time, so instances are equal but not identical
        Tag p1 = Tag.valueOf("p");
        Tag p2 = Tag.valueOf("p");
        assertEquals(p1, p2);
        assertNotSame(p1, p2); // separate TagSet clones → separate Tag instances

        // Two TagSet.Html() calls produce equal but distinct sets
        TagSet html1 = TagSet.Html();
        TagSet html2 = TagSet.Html();
        assertEquals(html1, html2);
        assertNotSame(html1, html2);

        // Within a single TagSet, repeated lookups return the same cached instance
        Tag pFromHtml1a = html1.valueOf("p", NamespaceHtml);
        Tag pFromHtml1b = html1.valueOf("p", NamespaceHtml);
        Tag pFromHtml2a = html2.valueOf("p", NamespaceHtml);
        Tag pFromHtml2b = html2.valueOf("p", NamespaceHtml);

        assertEquals(p1, pFromHtml1a);
        assertEquals(pFromHtml1a, pFromHtml1b);
        assertEquals(pFromHtml1b, pFromHtml2a);
        assertSame(pFromHtml1a, pFromHtml1b);    // same TagSet → same cached instance
        assertSame(pFromHtml2a, pFromHtml2b);    // same TagSet → same cached instance
        assertNotSame(pFromHtml1a, pFromHtml2a); // different TagSets → different instances
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
        // An unrecognised tag name ("FOO") gets unknown, inline defaults with no special options
        Tag foo  = Tag.valueOf("FOO");
        Tag foo2 = Tag.valueOf("FOO");

        assertEquals(foo, foo2);
        assertFalse(foo.isKnownTag());
        assertTrue(foo.isInline());
        assertFalse(foo.isBlock());
        assertFalse(foo.is(Tag.InlineContainer));
        assertFalse(foo.preserveWhitespace());
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
        // "svg" without a namespace defaults to the HTML namespace and is not the predefined SVG tag
        Tag svgInHtmlNs = Tag.valueOf("svg");
        Tag svgInSvgNs  = Tag.valueOf("svg", Parser.NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml,       svgInHtmlNs.namespace());
        assertEquals(Parser.NamespaceSvg, svgInSvgNs.namespace());

        assertFalse(svgInHtmlNs.isKnownTag()); // generated, not the predefined SVG tag
        assertTrue(svgInSvgNs.isKnownTag());   // known predefined tag in the SVG namespace
    }

    @Test public void unknownTagNamespace() {
        // "foo" is an unknown tag in both HTML and SVG namespaces
        Tag fooInHtmlNs = Tag.valueOf("foo");
        Tag fooInSvgNs  = Tag.valueOf("foo", Parser.NamespaceSvg, ParseSettings.htmlDefault);

        assertEquals(NamespaceHtml,       fooInHtmlNs.namespace());
        assertEquals(Parser.NamespaceSvg, fooInSvgNs.namespace());

        assertFalse(fooInHtmlNs.isKnownTag()); // generated
        assertFalse(fooInSvgNs.isKnownTag());  // generated
    }

    @Test void canSetOptions() {
        Tag tag = new Tag("foo", NamespaceHtml);
        assertFalse(tag.isKnownTag());
        assertFalse(tag.isEmpty());
        tag.set(Tag.Void);
        assertTrue(tag.isEmpty());
        assertTrue(tag.isKnownTag()); // set() marks the tag as known
    }

    @Test void textBoundaryOption() {
        Tag tag = new Tag("foo", NamespaceHtml);
        assertFalse(tag.is(Tag.TextBoundary));
        tag.set(Tag.TextBoundary);
        assertTrue(tag.is(Tag.TextBoundary));
        assertTrue(tag.isKnownTag()); // set() marks the tag as known
    }

    @Test void updateNameAndNamespace() {
        Tag tag = new Tag("foo", NamespaceHtml);
        tag.name("bar").namespace(NamespaceSvg);
        tag.set(Tag.Block);
        assertEquals("bar", tag.name());
        assertEquals(NamespaceSvg, tag.namespace());
        assertTrue(tag.isBlock()); // properties are unchanged

        // Tags are shared across all elements in a Document, so renaming the tag updates every element
        Document doc = Jsoup.parse("<foo>One</foo><foo>Two</foo>");
        Tag foo = doc.expectFirst("foo").tag();
        foo.name("BAR");
        assertEquals("<BAR>One</BAR><BAR>Two</BAR>", doc.body().html()); // is case-sensitive
    }

    @Test void formSubmittable() {
        // https://github.com/jhy/jsoup/issues/2323
        // isFormSubmittable() must be a pure query — it must not mutate the tag's options
        Tag img   = Tag.valueOf("img");
        Tag input = Tag.valueOf("input");
        int imgOptionsBefore   = img.options;
        int inputOptionsBefore = input.options;

        assertFalse(img.isFormSubmittable());
        assertTrue(input.isFormSubmittable());

        assertEquals(imgOptionsBefore,   img.options);   // options unchanged after query
        assertEquals(inputOptionsBefore, input.options);
    }

    @Test void stableHashcode() {
        // hashCode is based solely on tagName + namespace (not options), so mutations don't break Map/Set membership
        HashSet<Tag> tags = new HashSet<>();
        Tag img      = Tag.valueOf("img");
        Tag imgUpper = Tag.valueOf("IMG");
        Tag imgSvg   = Tag.valueOf("img", NamespaceSvg, ParseSettings.htmlDefault);

        // Exact hash values serve as a regression guard for the hashCode implementation
        assertEquals(-2074969810, img.hashCode());
        assertEquals(-2075954866, imgUpper.hashCode());
        assertEquals(-292873947,  imgSvg.hashCode());

        tags.add(img);
        tags.add(imgUpper);
        tags.add(imgSvg);

        // Mutating options must not change the hashCode so the tag stays findable in the Set
        imgSvg.set(Tag.Block);
        assertEquals(-292873947, imgSvg.hashCode());

        assertTrue(tags.contains(img));
        assertTrue(tags.contains(imgUpper));
        assertTrue(tags.contains(imgSvg));
    }

    @Test void prefix() {
        Tag img  = Tag.valueOf("img");
        Tag book = Tag.valueOf("bk:book");

        assertEquals("",   img.prefix());   // no prefix
        assertEquals("bk", book.prefix());  // namespace prefix before ':'
    }

    @Test void localname() {
        Tag img  = Tag.valueOf("img");
        Tag book = Tag.valueOf("bk:book");

        assertEquals("img",  img.localName());  // no prefix → full name is local name
        assertEquals("book", book.localName()); // local name is the part after ':'
    }

    @Test void valueOfWithSettings() {
        // htmlDefault normalises tag names to lowercase; preserveCase keeps them as-is
        Tag img1 = Tag.valueOf("img", ParseSettings.htmlDefault);
        Tag img2 = Tag.valueOf("IMG", ParseSettings.htmlDefault);
        Tag img3 = Tag.valueOf("IMG", ParseSettings.preserveCase);

        assertNotSame(img1, img2); // Tag.valueOf always creates a fresh TagSet.Html() clone
        assertNotSame(img1, img3);
        assertEquals("IMG", img3.toString()); // case preserved
        assertEquals("img", img1.toString()); // normalised to lowercase

        // Within a shared TagSet, htmlDefault resolves "IMG" to the same cached instance as "img"
        TagSet tagSet = TagSet.Html();
        assertSame(
            tagSet.valueOf("img", NamespaceHtml, ParseSettings.htmlDefault),
            tagSet.valueOf("IMG", NamespaceHtml, ParseSettings.htmlDefault)
        );

        // Without settings (case-sensitive lookup), "img" and "IMG" are different entries
        assertNotSame(
            tagSet.valueOf("img", NamespaceHtml),
            tagSet.valueOf("IMG", NamespaceHtml)
        );
    }

    @Test void equals() {
        // Tag.equals() compares tagName, namespace, normalName, and options — each field contributes
        TagSet tags = TagSet.Html();
        Tag p1 = tags.get("p", NamespaceHtml);
        Tag p2 = p1.clone();
        assertEquals(p1, p2);
        assertNotEquals(p1, tags); // different type → not equal

        p2.namespace = "Other";
        assertNotEquals(p1, p2);
        p2.namespace = p1.namespace;

        p2.tagName = "P";
        assertNotEquals(p1, p2);
        p2.tagName = p1.tagName;

        p2.normalName = "pp";
        assertNotEquals(p1, p2);
        p2.normalName = p1.normalName;

        p2.options = 0;
        assertNotEquals(p1, p2);
        p2.options = p1.options;
    }
}
