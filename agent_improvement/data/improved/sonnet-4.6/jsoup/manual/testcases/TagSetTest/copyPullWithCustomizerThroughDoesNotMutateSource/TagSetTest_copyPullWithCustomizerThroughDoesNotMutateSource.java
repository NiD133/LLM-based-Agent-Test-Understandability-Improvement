package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_copyPullWithCustomizerThroughDoesNotMutateSource {

    /**
     * When a copied TagSet lazily pulls a tag through from the underlying source (e.g. the default HTML set),
     * the source's {@code onNewTag} customizer must NOT be invoked. The copy owns the new tag entry;
     * the source TagSet must remain unmodified.
     */
    @Test
    void copyPullWithCustomizerThroughDoesNotMutateSource() {
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);

        // Register a customizer on the source so we can detect any unwanted mutation.
        AtomicInteger sourceCustomizerCallCount = new AtomicInteger();
        source.onNewTag(tag -> sourceCustomizerCallCount.incrementAndGet());

        // Trigger a lazy pull-through on the copy: "div" is not yet in copy's own map,
        // so it is cloned from the underlying default HTML TagSet and added to copy.
        assertNotNull(copy.get("div", NamespaceHtml), "copy should resolve 'div' via lazy pull-through");

        // The pull-through must have gone to the root default set, not through source,
        // so source's customizer should never have fired.
        assertEquals(0, sourceCustomizerCallCount.get(),
            "source TagSet must not be mutated when copy pulls a tag through");
    }
}
