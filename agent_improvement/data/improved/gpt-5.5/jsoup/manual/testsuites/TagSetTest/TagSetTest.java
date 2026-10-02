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
    private static final String OTHER_NAMESPACE = "SomeOtherNamespace";

    @Test void canRetrieveNewTagsSensitive() {
        Document doc = Jsoup.parse("<div><p>One</p></div>", "", Parser.htmlParser().settings(ParseSettings.preserveCase));
        TagSet tags = doc.parser().tagSet();
        assertHtmlMetaTagIsKnown(tags);

        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());

        assertNull(tags.get("FOO", NamespaceHtml));
        p.tagName("FOO");

        Tag foo = p.tag();
        assertEquals("FOO", foo.name());
        assertEquals("foo", foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());
        assertFalse(foo.isKnownTag());

        assertSame(foo, tags.get("FOO", NamespaceHtml));
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml));
        assertNull(tags.get("FOO", OTHER_NAMESPACE));
    }

    @Test void canRetrieveNewTagsInsensitive() {
        Document doc = Jsoup.parse("<div><p>One</p></div>");
        TagSet tags = doc.parser().tagSet();
        assertHtmlMetaTagIsKnown(tags);

        Element p = doc.expectFirst("p");
        assertTrue(p.tag().isKnownTag());

        assertNull(tags.get("FOO", NamespaceHtml));
        p.tagName("FOO");

        Tag foo = p.tag();
        assertEquals("foo", foo.name());
        assertEquals("foo", foo.normalName());
        assertEquals(NamespaceHtml, foo.namespace());
        assertFalse(foo.isKnownTag());

        assertSame(foo, tags.get("foo", NamespaceHtml));
        assertSame(foo, tags.valueOf("FOO", NamespaceHtml, doc.parser().settings()));
        assertNull(tags.get("foo", OTHER_NAMESPACE));
    }

    @Test void supplyCustomTagSet() {
        TagSet tags = TagSet.Html();
        tags.valueOf("custom", NamespaceHtml).set(Tag.PreserveWhitespace).set(Tag.Block);
        Parser parser = Parser.htmlParser().tagSet(tags);

        Document doc = Jsoup.parse("<body><custom>\n\nFoo\n Bar</custom></body>", parser);
        Element custom = doc.expectFirst("custom");

        assertTrue(custom.tag().preserveWhitespace());
        assertTrue(custom.tag().isBlock());
        assertEquals("<custom>\n" +
            "\n" +
            "Foo\n" +
            " Bar" +
            "</custom>", custom.outerHtml());
    }

    @Test void knownTags() {
        TagSet tags = TagSet.Html();
        Tag custom = new Tag("custom");

        assertEquals("custom", custom.name());
        assertEquals(NamespaceHtml, custom.namespace());
        assertFalse(custom.isKnownTag());

        Tag br = tags.get("br", NamespaceHtml);
        assertNotNull(br);
        assertTrue(br.isKnownTag());
        assertSame(br, tags.valueOf("br", NamespaceHtml));

        Tag foo = tags.valueOf("foo", NamespaceHtml);
        assertFalse(foo.isKnownTag());

        tags.add(custom);
        assertTrue(custom.isKnownTag());
        assertSame(custom, tags.get("custom", NamespaceHtml));
        assertSame(custom, tags.valueOf("custom", NamespaceHtml));

        Tag capCustom = tags.valueOf("Custom", NamespaceHtml);
        assertTrue(capCustom.isKnownTag());

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
        TagSet tags = TagSet.Html();
        tags.onNewTag(tag -> tag.set(Tag.SelfClose));

        assertTrue(tags.get("script", NamespaceHtml).is(Tag.SelfClose));
        assertTrue(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose));
        assertTrue(tags.valueOf("custom", NamespaceHtml).is(Tag.SelfClose));

        Tag foo = new Tag("foo", NamespaceHtml);
        assertFalse(foo.is(Tag.SelfClose));
        tags.add(foo);
        assertTrue(foo.is(Tag.SelfClose));
    }

    @Test void canCustomizeSome() {
        TagSet tags = TagSet.Html();
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.SelfClose);
            }
        });

        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose));
        assertFalse(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose));
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.SelfClose));
    }

    @Test void canParseWithCustomization() {
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (tag.normalName().equals("script")) {
                tag.set(Tag.SelfClose);
            }
        });

        Document doc = Jsoup.parse("<script />Text", parser);
        assertEquals("<html>\n <head>\n  <script></script>\n </head>\n <body>Text</body>\n</html>", doc.html());
    }

    @Test void canParseWithGeneralCustomization() {
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.SelfClose);
            }
        });

        Document doc = Jsoup.parse("<custom-data />Bar <script />Text", parser);
        assertEquals("<custom-data></custom-data>Bar\n<script>Text</script>", doc.body().html());
    }

    @Test void customTextBoundaryTagsAffectTextExtraction() {
        TagSet tags = TagSet.Html();
        tags.valueOf("custom-widget", NamespaceHtml).set(Tag.TextBoundary);
        Parser parser = Parser.htmlParser().tagSet(tags);

        Document doc = Jsoup.parse("<p>One<custom-widget>Two</custom-widget>Three</p>", parser);
        assertEquals("One Two Three", doc.text());
        assertEquals("OneTwoThree", doc.wholeText());
    }

    @Test void supportsMultipleCustomizers() {
        TagSet tags = TagSet.Html();
        tags.onNewTag(tag -> {
            if (tag.normalName().equals("script")) {
                tag.set(Tag.SelfClose);
            }
        });
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.RcData);
            }
        });

        assertTrue(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose));
        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.RcData));
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.RcData));
    }

    @Test void customizersArePreservedInSource() {
        TagSet source = TagSet.Html();
        source.onNewTag(tag -> tag.set(Tag.RcData));
        TagSet copy = new TagSet(source);

        assertTrue(copy.valueOf("script", NamespaceHtml).is(Tag.RcData));
        assertTrue(source.valueOf("script", NamespaceHtml).is(Tag.RcData));

        copy.onNewTag(tag -> tag.set(Tag.Void));
        assertTrue(copy.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
        assertFalse(source.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
    }

    @Test void copyPullThroughDoesNotMutateSource() {
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        int sourceNamespacesBefore = tagSetNamespaceCount(source);
        assertNotNull(copy.get("div", NamespaceHtml));
        int sourceNamespacesAfter = tagSetNamespaceCount(source);
        assertEquals(sourceNamespacesBefore, sourceNamespacesAfter);
    }

    @Test void copyPullWithCustomizerThroughDoesNotMutateSource() {
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);
        AtomicInteger sourceAdds = new AtomicInteger();

        source.onNewTag(tag -> sourceAdds.incrementAndGet());

        assertNotNull(copy.get("div", NamespaceHtml));
        assertEquals(0, sourceAdds.get());
    }

    private static void assertHtmlMetaTagIsKnown(TagSet tags) {
        Tag meta = tags.get("meta", NamespaceHtml);
        assertNotNull(meta);
        assertTrue(meta.isKnownTag());
    }

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
