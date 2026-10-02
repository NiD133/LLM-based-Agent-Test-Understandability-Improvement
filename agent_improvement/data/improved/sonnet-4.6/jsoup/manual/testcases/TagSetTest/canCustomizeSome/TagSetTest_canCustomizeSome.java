package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_canCustomizeSome {

    @Test
    void canCustomizeSome() {
        TagSet tags = TagSet.Html();

        // Register a customizer: only unknown (non-built-in) tags become self-closing
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.SelfClose);
            }
        });

        // "script" is a known HTML tag, so the customizer must not make it self-closing
        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose),
            "Known tag 'script' should not be self-closing");

        // Lookup is case-insensitive for known tags; SCRIPT must also remain non-self-closing
        assertFalse(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose),
            "Known tag 'SCRIPT' (upper-case) should not be self-closing");

        // "custom-tag" is not a built-in HTML tag, so the customizer should mark it self-closing
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.SelfClose),
            "Unknown custom tag 'custom-tag' should be self-closing after customizer");
    }
}
