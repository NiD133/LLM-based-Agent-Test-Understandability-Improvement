package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagSetTest_customizersArePreservedInSource {

    /**
     * When a TagSet is copied, the customizers registered on the source must be carried over to the copy, but
     * customizers added later to one TagSet must not leak back into the other.
     */
    @Test
    void customizersArePreservedInSource() {
        // Register a customizer on the source that marks every new tag as RcData.
        TagSet source = TagSet.Html();
        source.onNewTag(tag -> tag.set(Tag.RcData));

        // The copy inherits the source's existing customizers.
        TagSet copy = new TagSet(source);

        // Both TagSets apply the inherited customizer to newly resolved tags.
        assertTrue(copy.valueOf("script", NamespaceHtml).is(Tag.RcData));
        assertTrue(source.valueOf("script", NamespaceHtml).is(Tag.RcData));

        // A customizer added to the copy after copying affects only the copy, not the source.
        copy.onNewTag(tag -> tag.set(Tag.Void));
        assertTrue(copy.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
        assertFalse(source.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
    }
}
