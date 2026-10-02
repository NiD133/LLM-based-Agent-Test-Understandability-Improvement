package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_customizersArePreservedInSource {

    @Test
    void customizersArePreservedInSource() {
        // Set up a source TagSet with a customizer that marks every new tag as RcData.
        TagSet source = TagSet.Html();
        source.onNewTag(tag -> tag.set(Tag.RcData));

        // Copy the source; the copy should inherit the source's customizer.
        TagSet copy = new TagSet(source);

        // Both source and copy should apply the RcData customizer to known tags pulled from the source.
        assertTrue(copy.valueOf("script", NamespaceHtml).is(Tag.RcData));
        assertTrue(source.valueOf("script", NamespaceHtml).is(Tag.RcData));

        // Add a second customizer to the copy only; it should not bleed back into source.
        copy.onNewTag(tag -> tag.set(Tag.Void));

        // The copy applies its own Void customizer to unknown tags.
        assertTrue(copy.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));

        // The source has no Void customizer, so unknown tags it resolves are not marked Void.
        assertFalse(source.valueOf("custom-tag", NamespaceHtml).is(Tag.Void));
    }
}
