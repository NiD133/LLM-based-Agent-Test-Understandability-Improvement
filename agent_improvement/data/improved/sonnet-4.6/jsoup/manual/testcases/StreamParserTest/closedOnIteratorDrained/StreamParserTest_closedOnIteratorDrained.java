package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_closedOnIteratorDrained {

    /**
     * Creates a StreamParser pre-loaded with a simple two-div HTML fragment.
     * The parser is initialised twice (parse called twice) to exercise the reuse path:
     * the second parse() call closes any prior reader and reinitialises state.
     */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser streamParser = new StreamParser(Parser.htmlParser()).parse(html, "");
        streamParser.parse(html, "");
        return streamParser;
    }

    /**
     * Returns true when the StreamParser has been closed, detected by checking that
     * its internal CharacterReader has been released to null by completeParse().
     * This accesses package-private state via the tree builder as a back-door probe.
     */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    /**
     * Verifies that fully draining the iterator automatically closes the StreamParser.
     * The HTML produces 7 elements (html, head, body, two divs, one p, plus an implicit
     * element from the unclosed &lt;p&gt; inside the second div).
     */
    @Test
    void closedOnIteratorDrained() {
        StreamParser streamer = basic();
        Iterator<Element> it = streamer.iterator();

        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }

        assertEquals(7, count);
        assertTrue(isClosed(streamer));
    }
}
