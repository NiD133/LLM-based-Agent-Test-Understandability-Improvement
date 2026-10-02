package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagSetTest_copyPullWithCustomizerThroughDoesNotMutateSource {

    @Test
    void copyPullWithCustomizerThroughDoesNotMutateSource() {
        TagSet source = TagSet.Html();
        TagSet copy = new TagSet(source);
        AtomicInteger sourceAddCount = new AtomicInteger();

        source.onNewTag(tag -> sourceAddCount.incrementAndGet());

        assertNotNull(copy.get("div", NamespaceHtml));
        assertEquals(0, sourceAddCount.get());
    }
}
