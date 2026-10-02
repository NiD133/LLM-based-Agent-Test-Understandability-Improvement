package org.jsoup.parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TagSet}: looking tags up, dynamically discovering unknown tags, tracking which
 * tags are "known", customizing tags via {@code onNewTag}, and copying tag sets without mutating
 * the source.
 */
public class TagSetTest {
    /** A namespace that the HTML tag set knows nothing about, used to assert lookups miss. */
    private static final String UNKNOWN_NAMESPACE = "SomeOtherNamespace";

    @Test void canRetrieveNewTagsSensitive() {
        // Parse with preserveCase, so a renamed tag keeps its original casing.
        Document doc = Jsoup.parse("<div><p>One</p></div>", "", Parser.htmlParser().settings(ParseSettings.preserveCase));
        TagSet tags = doc.parser().tagSet();

        // The standard HTML tags are present and flagged as known.
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta);
        assertTrue(meta.isKnownTag());

        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());

        // "FOO" is unknown until we rename an element to it.
        assertNull(tags.get("FOO", NamespaceHtml));
        p.tagName("FOO");

        // The renamed tag preserves its case, normalizes to lower case, and is not a known tag.
        Tag foo = p.tag();
        assertEquals("FOO", foo.name());
        assertEquals("foo", foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());
        assertFalse(foo.isKnownTag());

        // The new tag is now retrievable from the set (case-sensitively) but only in its own namespace.
        assertSame(foo, tags.get("FOO", NamespaceHtml));
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml));
        assertNull(tags.get("FOO", UNKNOWN_NAMESPACE));
    }

    @Test void canRetrieveNewTagsInsensitive() {
        // Parse with default (case-insensitive) settings, so a renamed tag is lower-cased.
        Document doc = Jsoup.parse("<div><p>One</p></div>");
        TagSet tags = doc.parser().tagSet();

        // The standard HTML tags are present and flagged as known.
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta);
        assertTrue(meta.isKnownTag());

        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());

        // "FOO" is unknown until we rename an element to it.
        assertNull(tags.get("FOO", NamespaceHtml));
        p.tagName("FOO");

        // Case-insensitive parsing lower-cases the name, and the new tag is not a known tag.
        Tag foo = p.tag();
        assertEquals("foo", foo.name());
        assertEquals("foo", foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());
        assertFalse(foo.isKnownTag());

        // The new tag is retrievable by its normalized name, but only in its own namespace.
        assertSame(foo, tags.get("foo", NamespaceHtml));
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml, doc.parser().settings()));
        assertNull(tags.get("foo", UNKNOWN_NAMESPACE));
    }

    @Test void supplyCustomTagSet() {
        // Define a custom block tag that preserves whitespace, then parse with it.
        TagSet tags = TagSet.Html();
        tags.valueOf("custom", NamespaceHtml).set(Tag.PreserveWhitespace).set(Tag.Block);
        Parser parser = Parser.htmlParser().tagSet(tags);

        Document doc = Jsoup.parse("<body><custom>\n\nFoo\n Bar</custom></body>", parser);
        Element custom = doc.expectFirst("custom");

        // The custom tag's flags take effect: whitespace is preserved and it lays out as a block.
        assertTrue(custom.tag().preserveWhitespace());
        assertTrue(custom.tag().isBlock());
        assertEquals("<custom>\n" +
            "\n" +
            "Foo\n" +
            " Bar" +
            "</custom>", custom.outerHtml());
    }

    @Test void knownTags() {
        // Tags explicitly inserted via .add are 'known'; those created implicitly via valueOf are not.
        TagSet tags = TagSet.Html();

        // A freshly constructed Tag is not yet known.
        Tag custom = new Tag("custom");
        assertEquals("custom", custom.name());
        assertEquals(NamespaceHtml, custom.namespace());
        assertFalse(custom.isKnownTag());

        // A built-in HTML tag is known, and valueOf returns the very same instance.
        Tag br = tags.get("br", NamespaceHtml);
        assertNotNull(br);
        assertTrue(br.isKnownTag());
        assertSame(br, tags.valueOf("br", NamespaceHtml));

        // A tag discovered via valueOf (not previously defined) is not known.
        Tag foo = tags.valueOf("foo", NamespaceHtml);
        assertFalse(foo.isKnownTag());

        // Explicitly adding the custom tag marks it known and makes it retrievable.
        tags.add(custom);
        assertTrue(custom.isKnownTag());
        assertSame(custom, tags.get("custom", NamespaceHtml));
        assertSame(custom, tags.valueOf("custom", NamespaceHtml));

        // A differently-cased lookup clones the known tag, so the clone is also known.
        Tag capCustom = tags.valueOf("Custom", NamespaceHtml);
        assertTrue(capCustom.isKnownTag());

        // Calling set or clear on any option marks a tag known; only clearing the Known flag undoes it.
        Tag bar = new Tag("bar");
        assertFalse(bar.isKnownTag());
        bar.set(Tag.Block);
        assertTrue(bar.isKnownTag());
        bar.clear(Tag.Block);
        assertTrue(bar.isKnownTag());
        bar.clear(Tag.Known);
        assertFalse(bar.isKnownTag());
    }

    @Test void canCustomizeAll() {
        // A customizer that marks every tag self-closing applies to existing, looked-up, and added tags.
        TagSet tags = TagSet.Html();
        tags.onNewTag(tag -> tag.set(Tag.SelfClose));

        assertTrue(tags.get("script", NamespaceHtml).is(Tag.SelfClose));
        assertTrue(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose));
        assertTrue(tags.valueOf("custom", NamespaceHtml).is(Tag.SelfClose));

        // The customizer also runs when a tag is added explicitly.
        Tag foo = new Tag("foo", NamespaceHtml);
        assertFalse(foo.is(Tag.SelfClose));
        tags.add(foo);
        assertTrue(foo.is(Tag.SelfClose));
    }

    @Test void canCustomizeSome() {
        // A customizer can be selective: only unknown tags become self-closing here.
        TagSet tags = TagSet.Html();
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.SelfClose);
            }
        });

        // Known tags (script, in any case) are left alone; the unknown custom-tag is customized.
        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose));
        assertFalse(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose));
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.SelfClose));
    }

    @Test void canParseWithCustomization() {
        // Make <script> self-closing via a customizer, then parse to confirm it produces valid HTML.
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        Document doc = Jsoup.parse("<script />Text", parser);
        assertEquals("<html>\n <head>\n  <script></script>\n </head>\n <body>Text</body>\n</html>", doc.html());
    }

    @Test void canParseWithGeneralCustomization() {
        // Make every unknown tag self-closing, then parse a mix of unknown and known tags.
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (!tag.isKnownTag())
                tag.set(Tag.SelfClose);
        });

        Document doc = Jsoup.parse("<custom-data />Bar <script />Text", parser);
        assertEquals("<custom-data></custom-data>Bar\n<script>Text</script>", doc.body().html());
    }

    @Test void customTextBoundaryTagsAffectTextExtraction() {
        // Mark a custom tag as a text boundary so it inserts a separator in text() but not wholeText().
        TagSet tags = TagSet.Html();
        tags.valueOf("custom-widget", NamespaceHtml).set(Tag.TextBoundary);
        Parser parser = Parser.htmlParser().tagSet(tags);

        Document doc = Jsoup.parse("<p>One<custom-widget>Two</custom-widget>Three</p>", parser);
        assertEquals("One Two Three", doc.text());
        assertEquals("OneTwoThree", doc.wholeText());
    }

    @Test void supportsMultipleCustomizers() {
        // Register two customizers: one targets script, the other targets unknown tags.
        TagSet tags = TagSet.Html();
        tags.onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag())
                tag.set(Tag.RcData);
        });

        // script gets SelfClose (known, so not RcData); the unknown custom-tag gets RcData.
        assertTrue(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose));
        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.RcData));
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.RcData));
    }

    @Test void customizersArePreservedInSource() {
        // A customizer on the source is copied into a new TagSet, and both apply it independently.
        TagSet source = TagSet.Html();
        source.onNewTag(tag -> tag.set(Tag.RcData));
        TagSet copy = new TagSet(source);
        assertTrue(copy.valueOf("script", NamespaceHtml).is(Tag.RcData));
        assertTrue(source.valueOf("script", NamespaceHtml).is(Tag.RcData));

        // A customizer added to the copy afterwards does not leak back to the source.
        copy.onNewTag(tag -> tag.set(Tag.Void));
        assertTrue(copy.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
        assertFalse(source.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
    }

    @Test void copyPullThroughDoesNotMutateSource() {
        // Looking a tag up through a copy must not lazily add tags back into the source.
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        int sourceNamespacesBefore = tagSetNamespaceCount(source);
        assertNotNull(copy.get("div", NamespaceHtml));
        int sourceNamespacesAfter = tagSetNamespaceCount(source);
        assertEquals(sourceNamespacesBefore, sourceNamespacesAfter);
    }

    @Test void copyPullWithCustomizerThroughDoesNotMutateSource() {
        // A lookup through the copy must not trigger the source's onNewTag customizer.
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        AtomicInteger sourceAdds = new AtomicInteger();
        source.onNewTag(tag -> sourceAdds.incrementAndGet());

        assertNotNull(copy.get("div", NamespaceHtml));
        assertEquals(0, sourceAdds.get());
    }

    /**
     * Reads the private {@code tags} map of a TagSet via reflection and returns the number of
     * namespaces it currently holds. Used to detect whether a lookup mutated the set.
     */
    private static int tagSetNamespaceCount(TagSet tagSet) {
        try {
            Field tagsField = TagSet.class.getDeclaredField("tags");
            tagsField.setAccessible(true);
            Map<?, ?> tags = (Map<?, ?>) tagsField.get(tagSet);
            return tags.size();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
