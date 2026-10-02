package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies which {@link Tag}s a {@link TagSet} treats as "known".
 *
 * <p>The rule under test: a tag is "known" when it is explicitly registered
 * (via {@link TagSet#add}, or by being part of the built-in HTML defaults),
 * or when its {@code Known} flag is toggled through {@code set}/{@code clear}.
 * Tags that merely materialise on demand via {@code valueOf} are <em>not</em>
 * known, unless they were cloned from an already-known tag.</p>
 */
public class TagSetTest_knownTags {

    @Test
    void knownTags() {
        TagSet tags = TagSet.Html();

        // A freshly constructed Tag is not known until it is registered.
        Tag custom = new Tag("custom");
        assertEquals("custom", custom.name());
        assertEquals(NamespaceHtml, custom.namespace());
        assertFalse(custom.isKnownTag());

        // Built-in HTML defaults (e.g. "br") are known, and valueOf returns that same instance.
        Tag br = tags.get("br", NamespaceHtml);
        assertNotNull(br);
        assertTrue(br.isKnownTag());
        assertSame(br, tags.valueOf("br", NamespaceHtml));

        // A tag conjured on demand by valueOf is not known.
        Tag foo = tags.valueOf("foo", NamespaceHtml);
        assertFalse(foo.isKnownTag());

        // Explicitly adding the custom tag makes it known and the registered instance.
        tags.add(custom);
        assertTrue(custom.isKnownTag());
        assertSame(custom, tags.get("custom", NamespaceHtml));
        assertSame(custom, tags.valueOf("custom", NamespaceHtml));

        // Looking up "Custom" clones the known "custom" tag, so the clone is also known.
        Tag capCustom = tags.valueOf("Custom", NamespaceHtml);
        assertTrue(capCustom.isKnownTag());

        // Toggling any option flag marks a tag as known; only clearing Known unsets it.
        Tag bar = new Tag("bar");
        assertFalse(bar.isKnownTag());
        bar.set(Tag.Block);
        assertTrue(bar.isKnownTag());
        bar.clear(Tag.Block);
        assertTrue(bar.isKnownTag());
        bar.clear(Tag.Known);
        assertFalse(bar.isKnownTag());
    }
}
