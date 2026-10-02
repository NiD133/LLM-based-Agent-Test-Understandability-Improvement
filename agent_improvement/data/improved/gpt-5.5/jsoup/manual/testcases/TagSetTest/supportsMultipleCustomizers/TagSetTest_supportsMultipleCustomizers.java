package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_supportsMultipleCustomizers {

    @Test
    void supportsMultipleCustomizers() {
        TagSet tags = TagSet.Html();

        // Existing HTML tags are cloned into this TagSet and still pass through
        // registered customizers when first requested.
        tags.onNewTag(tag -> {
            if (tag.normalName().equals("script")) {
                tag.set(Tag.SelfClose);
            }
        });

        // Unknown tags are created on demand, so this customizer should affect
        // custom-tag but not the known script tag.
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.RcData);
            }
        });

        assertTrue(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose));
        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.RcData));
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.RcData));
    }
}
