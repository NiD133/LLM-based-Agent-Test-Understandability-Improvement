package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_customizersArePreservedInSource {

    @Test
    void customizersArePreservedInSource() {
        TagSet source = TagSet.Html();
        source.onNewTag(tag -> tag.set(Tag.RcData));

        TagSet copy = new TagSet(source);

        Tag copiedScript = copy.valueOf("script", NamespaceHtml);
        Tag sourceScript = source.valueOf("script", NamespaceHtml);
        assertTrue(copiedScript.is(Tag.RcData));
        assertTrue(sourceScript.is(Tag.RcData));

        copy.onNewTag(tag -> tag.set(Tag.Void));

        Tag copiedCustomTag = copy.valueOf("custom-tag", NamespaceHtml);
        Tag sourceCustomTag = source.valueOf("custom-tag", NamespaceHtml);
        assertTrue(copiedCustomTag.is(Tag.Void));
        assertFalse(sourceCustomTag.is(Tag.Void));
    }
}
