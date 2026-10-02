package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_canCustomizeAll {

    /**
     * A customizer registered via {@link TagSet#onNewTag} should be applied to every tag the TagSet
     * produces: known tags fetched with {@code get}, tags created on demand via {@code valueOf}
     * (whether previously known or completely new), and tags added explicitly with {@code add}.
     */
    @Test
    void canCustomizeAll() {
        TagSet tags = TagSet.Html();

        // Mark every tag this set hands out or accepts as self-closing.
        tags.onNewTag(tag -> tag.set(Tag.SelfClose));

        // Applied to a known tag retrieved with get().
        assertTrue(tags.get("script", NamespaceHtml).is(Tag.SelfClose));

        // Applied to a known tag resolved (and case-normalized) via valueOf().
        assertTrue(tags.valueOf("SCRIPT", NamespaceHtml).is(Tag.SelfClose));

        // Applied to a brand-new, previously unknown tag created via valueOf().
        assertTrue(tags.valueOf("custom", NamespaceHtml).is(Tag.SelfClose));

        // Applied to a tag added explicitly with add(): it gains the option only once added.
        Tag foo = new Tag("foo", NamespaceHtml);
        assertFalse(foo.is(Tag.SelfClose));
        tags.add(foo);
        assertTrue(foo.is(Tag.SelfClose));
    }
}
