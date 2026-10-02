package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_canCustomizeSome {

    /**
     * Verifies that an {@code onNewTag} customizer only affects the tags it chooses to.
     * <p>
     * The customizer below makes every <em>unknown</em> tag self-closing while leaving
     * the built-in HTML tags untouched. So:
     * <ul>
     *   <li>{@code script} (a known tag, any case) must stay non-self-closing, and</li>
     *   <li>{@code custom-tag} (unknown) must become self-closing.</li>
     * </ul>
     */
    @Test
    void canCustomizeSome() {
        TagSet tags = TagSet.Html();
        tags.onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.SelfClose);
            }
        });

        // Known HTML tags are unaffected by the customizer, regardless of letter case.
        assertFalse(tags.valueOf("script", NamespaceHtml).is(Tag.SelfClose));
        assertFalse(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose));

        // Unknown tags are made self-closing by the customizer.
        assertTrue(tags.valueOf("custom-tag", NamespaceHtml).is(Tag.SelfClose));
    }
}
