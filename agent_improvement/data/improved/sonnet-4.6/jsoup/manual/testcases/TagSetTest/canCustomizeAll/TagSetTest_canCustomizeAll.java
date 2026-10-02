package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_canCustomizeAll {

    @Test
    void canCustomizeAll() {
        TagSet tags = TagSet.Html();
        // Register a customizer that marks every tag (existing or new) as self-closing.
        tags.onNewTag(tag -> tag.set(Tag.SelfClose));

        // Known HTML tag cloned on demand from the default set applies the customizer.
        assertTrue(tags.get("script", NamespaceHtml).is(Tag.SelfClose));

        // Case-insensitive valueOf lookup for an already-known tag also applies the customizer.
        assertTrue(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose));

        // Completely unknown tag created via valueOf is customized as well.
        assertTrue(tags.valueOf("custom", NamespaceHtml).is(Tag.SelfClose));

        // A Tag constructed externally does NOT have SelfClose before being added to the set.
        Tag externalTag = new Tag("foo", NamespaceHtml);
        assertFalse(externalTag.is(Tag.SelfClose));

        // After add(), the customizer runs and SelfClose is applied.
        tags.add(externalTag);
        assertTrue(externalTag.is(Tag.SelfClose));
    }
}
