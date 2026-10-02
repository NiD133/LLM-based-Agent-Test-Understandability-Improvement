package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_canCustomizeAll {

    @Test
    void canCustomizeAll() {
        TagSet tagSet = TagSet.Html();
        tagSet.onNewTag(tag -> tag.set(Tag.SelfClose));

        Tag existingHtmlTag = tagSet.get("script", NamespaceHtml);
        assertTrue(existingHtmlTag.is(Tag.SelfClose));

        Tag clonedHtmlTag = tagSet.valueOf("SCRIPT", NamespaceHtml);
        assertTrue(clonedHtmlTag.is(Tag.SelfClose));

        Tag customTag = tagSet.valueOf("custom", NamespaceHtml);
        assertTrue(customTag.is(Tag.SelfClose));

        Tag explicitTag = new Tag("foo", NamespaceHtml);
        assertFalse(explicitTag.is(Tag.SelfClose));

        tagSet.add(explicitTag);
        assertTrue(explicitTag.is(Tag.SelfClose));
    }
}
