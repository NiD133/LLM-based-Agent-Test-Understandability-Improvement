package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_supportsMultipleCustomizers {

    /**
     * Several customizers can be registered on a TagSet via onNewTag, and all of them are applied
     * (in registration order) to every tag the set produces. Here:
     *  - the first customizer marks the "script" tag as self-closing, and
     *  - the second marks any unknown tag as RcData.
     * Each customizer only affects the tags matching its own condition.
     */
    @Test
    void supportsMultipleCustomizers() {
        TagSet tags = TagSet.Html();

        // First customizer: make <script> self-closing.
        tags.onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        // Second customizer: treat any unknown tag as RcData.
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag())
                tag.set(Tag.RcData);
        });

        // "script" is a known tag, so only the first customizer applies.
        Tag script = tags.valueOf("script", NamespaceHtml);
        assertTrue(script.is(Tag.SelfClose));
        assertFalse(script.is(Tag.RcData));

        // "custom-tag" is unknown, so the second customizer applies.
        Tag customTag = tags.valueOf("custom-tag", NamespaceHtml);
        assertTrue(customTag.is(Tag.RcData));
    }
}
