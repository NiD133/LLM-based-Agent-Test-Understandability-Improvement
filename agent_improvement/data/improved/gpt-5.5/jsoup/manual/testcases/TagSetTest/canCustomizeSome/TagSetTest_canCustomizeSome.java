package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_canCustomizeSome {

    @Test
    void canCustomizeSome() {
        TagSet tags = TagSet.Html();
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.SelfClose);
            }
        });

        Tag knownLowercaseScript = tags.valueOf("script", NamespaceHtml);
        Tag unknownUppercaseScript = tags.valueOf("SCRIPT", NamespaceHtml);
        Tag unknownCustomTag = tags.valueOf("custom-tag", NamespaceHtml);

        assertFalse(knownLowercaseScript.is(Tag.SelfClose));
        assertFalse(unknownUppercaseScript.is(Tag.SelfClose));
        assertTrue(unknownCustomTag.is(Tag.SelfClose));
    }
}
