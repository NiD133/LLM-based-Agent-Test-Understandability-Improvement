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

public class TagSetTest {
    // A namespace name that does not exist in the tag set, used to verify namespace-scoped lookups return null
    private static final String UnknownNamespace = "SomeOtherNamespace";

    @Test void canRetrieveNewTagsSensitive() {
        // case-preserving parser: tag names are stored and looked up exactly as written
        Document doc = Jsoup.parse("<div><p>One</p></div>", "", Parser.htmlParser().settings(ParseSettings.preserveCase));
        TagSet tags = doc.parser().tagSet();

        // built-in HTML tags are always known regardless of case sensitivity setting
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta, "Built-in 'meta' tag must exist in the HTML tag set");
        assertTrue(meta.isKnownTag(), "'meta' must be a known tag");

        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag(), "Parsed <p> must be a known tag");

        // rename <p> to an unrecognized tag name using the original casing
        assertNull(tags.get("FOO", NamespaceHtml), "Unknown tag 'FOO' must not exist before assignment");
        p.tagName("FOO");
        Tag foo = p.tag();

        // with case preservation the stored name matches what was assigned; normalName is always lower-case
        assertEquals("FOO", foo.name(), "Preserved name must match the assigned uppercase 'FOO'");
        assertEquals("foo", foo.normalName(), "Normal name must be the lowercase equivalent");
        assertEquals(NamespaceHtml, foo.namespace(), "Dynamic tag must belong to the HTML namespace");
        assertFalse(foo.isKnownTag(), "Dynamically created tag must not be a known tag");

        // the newly created tag is registered in the tag set under its exact (case-sensitive) name
        assertSame(foo, tags.get("FOO", NamespaceHtml), "get() must return the same Tag instance for 'FOO'");
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml), "valueOf() must return the same Tag instance for 'FOO'");
        assertNull(tags.get("FOO", UnknownNamespace), "Tag 'FOO' must not exist under a different namespace");
    }

    @Test void canRetrieveNewTagsInsensitive() {
        // default (case-insensitive) parser: tag names are normalized to lower-case when stored
        Document doc = Jsoup.parse("<div><p>One</p></div>");
        TagSet tags = doc.parser().tagSet();

        // built-in HTML tags are always known
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta, "Built-in 'meta' tag must exist in the HTML tag set");
        assertTrue(meta.isKnownTag(), "'meta' must be a known tag");

        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag(), "Parsed <p> must be a known tag");

        // rename <p> to an unrecognized tag name; the insensitive parser normalizes it to lower-case
        assertNull(tags.get("FOO", NamespaceHtml), "Unknown tag 'FOO' must not exist before assignment");
        p.tagName("FOO");
        Tag foo = p.tag();

        // both name() and normalName() are lower-case when case preservation is off
        assertEquals("foo", foo.name(), "Case-insensitive parser must store tag name in lower-case");
        assertEquals("foo", foo.normalName(), "Normal name must also be lower-case");
        assertEquals(NamespaceHtml, foo.namespace(), "Dynamic tag must belong to the HTML namespace");
        assertFalse(foo.isKnownTag(), "Dynamically created tag must not be a known tag");

        assertSame(foo, tags.get("foo", NamespaceHtml), "get() must return the same Tag instance for 'foo'");
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml, doc.parser().settings()),
            "valueOf() with parser settings must resolve 'FOO' to the same lower-cased tag instance");
        assertNull(tags.get("foo", UnknownNamespace), "Tag 'foo' must not exist under a different namespace");
    }

    @Test void supplyCustomTagSet() {
        // build a custom tag set that pre-configures a "custom" tag as a block-level whitespace-preserving element
        TagSet tags = TagSet.Html();
        tags.valueOf("custom", NamespaceHtml).set(Tag.PreserveWhitespace).set(Tag.Block);
        Parser parser = Parser.htmlParser().tagSet(tags);

        Document doc = Jsoup.parse("<body><custom>\n\nFoo\n Bar</custom></body>", parser);
        Element custom = doc.expectFirst("custom");

        assertTrue(custom.tag().preserveWhitespace(), "Custom tag must preserve whitespace as configured");
        assertTrue(custom.tag().isBlock(), "Custom tag must be a block element as configured");
        assertEquals("<custom>\n" +
            "\n" +
            "Foo\n" +
            " Bar" +
            "</custom>", custom.outerHtml(), "Whitespace inside a preserve-whitespace block tag must not be collapsed");
    }

    @Test void knownTags() {
        // tags explicitly inserted via add() are 'known'; those that come implicitly via valueOf() are not
        TagSet tags = TagSet.Html();

        Tag custom = new Tag("custom");
        assertEquals("custom", custom.name());
        assertEquals(NamespaceHtml, custom.namespace());
        assertFalse(custom.isKnownTag(), "Newly constructed Tag must not be known until added to a TagSet");

        // built-in tags retrieved from the HTML tag set are always known
        Tag br = tags.get("br", NamespaceHtml);
        assertNotNull(br, "'br' must exist as a built-in HTML tag");
        assertTrue(br.isKnownTag(), "Built-in 'br' must be a known tag");
        assertSame(br, tags.valueOf("br", NamespaceHtml), "valueOf() for an existing tag must return the same instance");

        // dynamically created tags (not pre-registered) are not known
        Tag foo = tags.valueOf("foo", NamespaceHtml);
        assertFalse(foo.isKnownTag(), "Dynamically created 'foo' tag must not be known");

        // after explicit add(), the tag becomes known and is retrievable by name
        tags.add(custom);
        assertTrue(custom.isKnownTag(), "Tag must become known after being added to a TagSet");
        assertSame(custom, tags.get("custom", NamespaceHtml), "get() must return the added Tag instance");
        assertSame(custom, tags.valueOf("custom", NamespaceHtml), "valueOf() must return the same Tag instance");

        // a case-variant lookup clones the known tag, so the clone also carries the 'known' flag
        Tag capitalizedCustom = tags.valueOf("Custom", NamespaceHtml);
        assertTrue(capitalizedCustom.isKnownTag(),
            "A clone of a known tag (produced by a case-variant valueOf) must also be known");

        // set()/clear() promote a tag to known; only an explicit clear(Known) can remove that status
        Tag bar = new Tag("bar");
        assertFalse(bar.isKnownTag());
        bar.set(Tag.Block);
        assertTrue(bar.isKnownTag(), "Calling set() must implicitly mark the tag as known");
        bar.clear(Tag.Block);
        assertTrue(bar.isKnownTag(), "Clearing a non-Known flag must not affect the known status");
        bar.clear(Tag.Known);
        assertFalse(bar.isKnownTag(), "Explicitly clearing the Known flag must remove known status");
    }

    @Test void canCustomizeAll() {
        TagSet tags = TagSet.Html();
        // customizer runs for every tag, including built-in ones that have already been registered
        tags.onNewTag(tag -> tag.set(Tag.SelfClose));

        assertTrue(tags.get("script", NamespaceHtml).is(Tag.SelfClose),
            "Customizer must apply SelfClose to built-in tags pulled from the source");
        assertTrue(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose),
            "Customizer must apply SelfClose when looking up a tag by case-variant name");
        assertTrue(tags.valueOf("custom", NamespaceHtml).is(Tag.SelfClose),
            "Customizer must apply SelfClose to newly created unknown tags");

        // a tag constructed externally and then added via add() also triggers the customizer
        Tag foo = new Tag("foo", NamespaceHtml);
        assertFalse(foo.is(Tag.SelfClose), "Tag created outside the TagSet must not have SelfClose set yet");
        tags.add(foo);
        assertTrue(foo.is(Tag.SelfClose), "add() must run customizers, setting SelfClose on the externally created tag");
    }

    @Test void canCustomizeSome() {
        TagSet tags = TagSet.Html();
        // customizer only modifies unknown tags, leaving built-in tags untouched
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.SelfClose);
            }
        });

        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose),
            "Built-in 'script' tag must not receive SelfClose from the unknown-only customizer");
        assertFalse(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose),
            "Case-variant lookup of built-in 'script' must also not receive SelfClose");
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.SelfClose),
            "Unknown 'custom-tag' must receive SelfClose from the customizer");
    }

    @Test void canParseWithCustomization() {
        // real-world usage example: treat <script> as self-closing during parsing
        // (in practice one would use tags.valueOf("script"); this is just a demonstration)
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        Document doc = Jsoup.parse("<script />Text", parser);
        // even though <script /> was self-closed in input, the output is still valid HTML
        assertEquals("<html>\n <head>\n  <script></script>\n </head>\n <body>Text</body>\n</html>", doc.html());
    }

    @Test void canParseWithGeneralCustomization() {
        Parser parser = Parser.htmlParser();
        // make all unknown tags self-closing; known tags like <script> are unaffected
        parser.tagSet().onNewTag(tag -> {
            if (!tag.isKnownTag())
                tag.set(Tag.SelfClose);
        });

        Document doc = Jsoup.parse("<custom-data />Bar <script />Text", parser);
        assertEquals("<custom-data></custom-data>Bar\n<script>Text</script>", doc.body().html());
    }

    @Test void customTextBoundaryTagsAffectTextExtraction() {
        TagSet tags = TagSet.Html();
        // TextBoundary causes text() to insert spaces at the element boundary, improving readability of extracted text
        tags.valueOf("custom-widget", NamespaceHtml).set(Tag.TextBoundary);
        Parser parser = Parser.htmlParser().tagSet(tags);

        Document doc = Jsoup.parse("<p>One<custom-widget>Two</custom-widget>Three</p>", parser);

        assertEquals("One Two Three", doc.text(),
            "text() must insert spaces around a TextBoundary element");
        assertEquals("OneTwoThree", doc.wholeText(),
            "wholeText() must not insert boundary spaces");
    }

    @Test void supportsMultipleCustomizers() {
        TagSet tags = TagSet.Html();
        // first customizer: mark 'script' as self-closing
        tags.onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });
        // second customizer: mark all unknown tags as RcData
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag())
                tag.set(Tag.RcData);
        });

        // 'script' is known, so the second customizer does not apply to it
        assertTrue(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose),
            "First customizer must set SelfClose on 'script'");
        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.RcData),
            "Second customizer must not set RcData on the known 'script' tag");

        // 'custom-tag' is unknown, so only the second customizer applies
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.RcData),
            "Second customizer must set RcData on the unknown 'custom-tag'");
    }

    @Test void customizersArePreservedInSource() {
        // customizers are copied from the source TagSet into the new TagSet; each TagSet's customizers are independent
        TagSet source = TagSet.Html();
        source.onNewTag(tag -> tag.set(Tag.RcData));

        TagSet copy = new TagSet(source);

        // the copied customizer applies to both the copy and the source
        assertTrue(copy.valueOf("script", NamespaceHtml).is(Tag.RcData),
            "Customizer inherited from source must apply in the copy");
        assertTrue(source.valueOf("script", NamespaceHtml).is(Tag.RcData),
            "Customizer must still apply in the original source");

        // adding a customizer to the copy does not affect the source
        copy.onNewTag(tag -> tag.set(Tag.Void));
        assertTrue(copy.valueOf("custom-tag", NamespaceHtml).is(Tag.Void),
            "New customizer added to copy must apply in the copy");
        assertFalse(source.valueOf("custom-tag", NamespaceHtml).is(Tag.Void),
            "Customizer added only to the copy must not affect the source");
    }

    @Test void copyPullThroughDoesNotMutateSource() {
        // pulling a tag from a copy via lazy read-through must not add namespaces to the source's internal map
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        int sourceNamespacesBefore = tagSetNamespaceCount(source);
        assertNotNull(copy.get("div", NamespaceHtml), "'div' must exist in the copy's tag set");
        int sourceNamespacesAfter = tagSetNamespaceCount(source);

        assertEquals(sourceNamespacesBefore, sourceNamespacesAfter,
            "A tag lookup in the copy must not mutate the source TagSet's namespace map");
    }

    @Test void copyPullWithCustomizerThroughDoesNotMutateSource() {
        // a customizer on the source must not fire when a copy lazily resolves a tag from it
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        AtomicInteger sourceCustomizerInvocations = new AtomicInteger();
        source.onNewTag(tag -> sourceCustomizerInvocations.incrementAndGet());

        assertNotNull(copy.get("div", NamespaceHtml), "'div' must exist in the copy's tag set");
        assertEquals(0, sourceCustomizerInvocations.get(),
            "Source customizer must not be invoked when the copy resolves a tag via read-through");
    }

    /** Uses reflection to count the number of namespace entries in the TagSet's internal map. */
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
