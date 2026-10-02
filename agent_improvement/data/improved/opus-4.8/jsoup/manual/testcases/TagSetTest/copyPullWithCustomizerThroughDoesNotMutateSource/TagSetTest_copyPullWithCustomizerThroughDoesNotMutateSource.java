package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagSetTest_copyPullWithCustomizerThroughDoesNotMutateSource {

    /**
     * When a tag is lazily pulled through a copied TagSet, the copy should resolve the tag on its own.
     * The original source TagSet must stay untouched: in particular, an {@code onNewTag} customizer
     * registered on the source must never fire because of work done on the copy.
     */
    @Test
    void copyPullWithCustomizerThroughDoesNotMutateSource() {
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        // Count how many tags get added to the source after the copy is taken.
        AtomicInteger sourceTagAdditions = new AtomicInteger();
        source.onNewTag(tag -> sourceTagAdditions.incrementAndGet());

        // Resolve "div" through the copy; this triggers a lazy pull-through.
        assertNotNull(copy.get("div", NamespaceHtml));

        // The source was not mutated, so its customizer was never invoked.
        assertEquals(0, sourceTagAdditions.get());
    }
}
