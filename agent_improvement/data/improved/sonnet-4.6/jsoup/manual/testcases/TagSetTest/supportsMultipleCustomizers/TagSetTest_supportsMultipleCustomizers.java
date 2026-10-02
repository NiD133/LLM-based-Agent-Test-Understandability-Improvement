package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_supportsMultipleCustomizers {

    @Test
    void supportsMultipleCustomizers() {
        TagSet tags = TagSet.Html();

        // First customizer: mark "script" as self-closing
        tags.onNewTag(tag -> {
            if (tag.normalName().equals("script"))
                tag.set(Tag.SelfClose);
        });

        // Second customizer: mark any unknown (custom) tag as RcData
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag())
                tag.set(Tag.RcData);
        });

        // "script" is a known tag: first customizer applies, second should not
        Tag scriptTag = tags.valueOf("script", NamespaceHtml);
        assertTrue(scriptTag.is(Tag.SelfClose),  "known 'script' tag should be self-closing via first customizer");
        assertFalse(scriptTag.is(Tag.RcData),    "known 'script' tag should not be RcData (second customizer is for unknowns only)");

        // "custom-tag" is unknown: second customizer applies
        Tag customTag = tags.valueOf("custom-tag", NamespaceHtml);
        assertTrue(customTag.is(Tag.RcData), "unknown 'custom-tag' should be RcData via second customizer");
    }
}
