package org.jsoup.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.DataUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.integration.TestServer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 Tests for the StreamParser. There are also some tests in {@link org.jsoup.integration.ConnectTest}.

 <p>Many tests assert the order in which elements are emitted during a streaming parse by building up a compact
 textual trace with {@link #trackSeen(Element, StringBuilder)}. See that method for the trace encoding.</p>
 */
class StreamParserTest {

    // HTML inputs shared by several tests. Each is named for the structural feature it exercises.

    /** A full document with nested divs, paragraphs and a span; used to verify document-order emission. */
    private static final String NESTED_DOC_HTML =
        "<title>Test</title></head><div id=1>D1</div><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";

    /** Three sibling divs where the middle one is intended to be removed mid-parse. */
    private static final String THREE_DIVS_HTML = "<div>One</div><div>DESTROY</div><div>Two</div>";

    /** Table rows with missing {@code </tr>} tags, which the HTML parser infers; used by the fragment tests. */
    private static final String TABLE_ROWS_HTML =
        "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";

    @Test
    void canStream() {
        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parse(NESTED_DOC_HTML, "")) {
            StringBuilder seen = new StringBuilder();
            parser.stream().forEachOrdered(el -> trackSeen(el, seen));
            assertEquals("title[Test];head+;div#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];body;html;#root;", seen.toString());
        }
    }

    @Test
    void canStreamXml() {
        String html = "<outmost><DIV id=1>D1</DIV><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";
        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parse(html, "")) {
            StringBuilder seen = new StringBuilder();
            parser.stream().forEachOrdered(el -> trackSeen(el, seen));
            assertEquals("DIV#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];outmost;#root;", seen.toString());
        }
    }

    @Test void canIterate() {
        // Same expectation as canStream(), reached through the Iterator interface instead of the Stream interface.
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(NESTED_DOC_HTML, "");
        StringBuilder seen = new StringBuilder();

        Iterator<Element> iterator = parser.iterator();
        while (iterator.hasNext()) {
            trackSeen(iterator.next(), seen);
        }

        assertEquals("title[Test];head+;div#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];body;html;#root;", seen.toString());
    }

    @Test void canReuse() {
        StreamParser parser = new StreamParser(Parser.htmlParser());

        // First parse.
        parser.parse("<p>One<p>Two", "");
        StringBuilder firstSeen = new StringBuilder();
        parser.stream().forEach(el -> trackSeen(el, firstSeen));
        assertEquals("head+;p[One]+;p[Two];body;html;#root;", firstSeen.toString());

        // Re-using the same parser for a second, independent parse.
        parser.parse("<div>Three<div>Four</div></div>", "");
        StringBuilder secondSeen = new StringBuilder();
        parser.stream().forEach(el -> trackSeen(el, secondSeen));
        assertEquals("head+;div[Four];div[Three];body;html;#root;", secondSeen.toString());

        // Streaming again without a fresh parse yields nothing.
        StringBuilder drainedSeen = new StringBuilder();
        parser.stream().forEach(el -> trackSeen(el, drainedSeen));
        assertEquals("", drainedSeen.toString());
    }

    @Test void canStopAndCompleteAndReuse() throws IOException {
        StreamParser parser = new StreamParser(Parser.htmlParser());
        parser.parse("<p>One<p>Two", "");

        Element firstP = parser.expectFirst("p");
        assertEquals("One", firstP.text());
        parser.stop();

        // After stop(), the iterator is exhausted.
        Iterator<Element> iterator = parser.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);

        // And selectNext() finds nothing further.
        Element noMorePs = parser.selectNext("p");
        assertNull(noMorePs);

        // complete() still finishes the parse, exposing both paragraphs.
        Document completed = parser.complete();
        Elements ps = completed.select("p");
        assertEquals(2, ps.size());
        assertEquals("One", ps.get(0).text());
        assertEquals("Two", ps.get(1).text());

        // The parser can be reused for a fresh input.
        parser.parse("<div>DIV", "");
        Element div = parser.expectFirst("div");
        assertEquals("DIV", div.text());
    }

    /**
     Appends a compact, human-readable trace of {@code element} to {@code trace}, used to assert emission order.
     <p>The encoding for a single element is: {@code tagName} optionally followed by:</p>
     <ul>
       <li>{@code #id} if the element has an {@code id} attribute,</li>
       <li>{@code [ownText]} if the element has its own (non-child) text,</li>
       <li>{@code +} if the element had a next sibling at the time it was emitted,</li>
     </ul>
     <p>and is terminated by {@code ;}. For example, {@code div#1[D1]+;} is a {@code <div id=1>} whose own text is
     "D1" and which had a following sibling when emitted.</p>
     */
    static void trackSeen(Element element, StringBuilder trace) {
        trace.append(element.tagName());
        if (element.hasAttr("id"))
            trace.append("#").append(element.id());
        if (!element.ownText().isEmpty())
            trace.append("[").append(element.ownText()).append("]");
        if (element.nextElementSibling() != null)
            trace.append("+");

        trace.append(";");
    }

    @Test void select() throws IOException {
        String html = "<title>One</title><p id=1>P One</p><p id=2>P Two</p>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");

        Element title = parser.expectFirst("title");
        assertEquals("One", title.text());

        Document partialDoc = title.ownerDocument();
        assertNotNull(partialDoc);
        // At this point we should have one empty P, as the title was emitted while the parser was on the P's head.
        Elements ps = partialDoc.select("p");
        assertEquals(1, ps.size());
        assertEquals("", ps.get(0).text());
        assertSame(partialDoc, parser.document());

        // Re-selecting an already-parsed element returns the same instance.
        Element titleAgain = parser.selectFirst("title");
        assertSame(titleAgain, title);

        Element firstP = parser.expectNext("p");
        assertEquals("P One", firstP.text());

        Element secondP = parser.expectNext("p");
        assertEquals("P Two", secondP.text());

        Element noMorePs = parser.selectNext("p");
        assertNull(noMorePs);
    }

    @Test void canRemoveFromDom() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(THREE_DIVS_HTML, "");
        parser.parse(THREE_DIVS_HTML, "");

        // Remove the "DESTROY" div from the DOM as it streams past.
        parser.stream().forEach(
            el -> {
                if (el.ownText().equals("DESTROY"))
                    el.remove();
            });

        Document doc = parser.document();
        Elements divs = doc.select("div");
        assertEquals(2, divs.size());
        assertEquals("One Two", divs.text());
    }

    @Test void canRemoveWithIterator() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(THREE_DIVS_HTML, "");
        parser.parse(THREE_DIVS_HTML, "");

        // Same removal as canRemoveFromDom(), but via Iterator.remove() instead of Element.remove().
        Iterator<Element> iterator = parser.iterator();
        while (iterator.hasNext()) {
            Element el = iterator.next();
            if (el.ownText().equals("DESTROY"))
                iterator.remove();
        }

        Document doc = parser.document();
        Elements divs = doc.select("div");
        assertEquals(2, divs.size());
        assertEquals("One Two", divs.text());
    }

    @Test void canSelectWithHas() throws IOException {
        StreamParser parser = basic();

        Element el = parser.expectNext("div:has(p)");
        assertEquals("Two", el.text());
    }

    @Test void canSelectWithSibling() throws IOException {
        StreamParser parser = basic();

        Element firstDiv = parser.expectNext("div:first-of-type");
        assertEquals("One", firstDiv.text());

        // There is only one first-of-type div, so a second select finds nothing.
        Element noMoreDivs = parser.selectNext("div:first-of-type");
        assertNull(noMoreDivs);
    }

    @Test void canLoopOnSelectNext() throws IOException {
        StreamParser streamer = new StreamParser(Parser.htmlParser()).parse("<div><p>One<p>Two<p>Thr</div>", "");

        int count = 0;
        Element p;
        while ((p = streamer.selectNext("p")) != null) {
            assertEquals(3, p.text().length()); // each paragraph has a 3-char body
            p.remove();
            count++;
        }

        assertEquals(3, count);
        assertEquals(0, streamer.document().select("p").size()); // all removed during iteration

        assertTrue(isClosed(streamer)); // read to the end
    }

    @Test void worksWithXmlParser() throws IOException {
        StreamParser streamer = new StreamParser(Parser.xmlParser()).parse("<div><p>One</p><p>Two</p><p>Thr</p></div>", "");

        int count = 0;
        Element p;
        while ((p = streamer.selectNext("p")) != null) {
            assertEquals(3, p.text().length()); // each paragraph has a 3-char body
            p.remove();
            count++;
        }

        assertEquals(3, count);
        assertEquals(0, streamer.document().select("p").size()); // all removed during iteration

        assertTrue(isClosed(streamer)); // read to the end
    }

    @Test void closedOnStreamDrained() {
        StreamParser streamer = basic();
        assertFalse(isClosed(streamer));

        long count = streamer.stream().count();
        assertEquals(7, count);

        assertTrue(isClosed(streamer));
    }

    @Test void closedOnIteratorDrained() {
        StreamParser streamer = basic();

        int count = 0;
        Iterator<Element> iterator = streamer.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        assertEquals(7, count);
        assertTrue(isClosed(streamer));
    }

    @Test void closedOnComplete() throws IOException {
        StreamParser streamer = basic();
        Document doc = streamer.complete();
        assertTrue(isClosed(streamer));
    }

    @Test void closedOnTryWithResources() {
        StreamParser copy;
        try (StreamParser streamer = basic()) {
            copy = streamer;
            assertFalse(isClosed(copy));
        }
        // Leaving the try-with-resources block closes the parser.
        assertTrue(isClosed(copy));
    }

    /** Builds a parser over a small two-div document, ready to be consumed. */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    /** A parser is considered closed once its backing reader has been released. */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    /** Reaches into the parser internals to obtain the backing reader (a back door for the close-state assertions). */
    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test void doesNotReadPastParse() throws IOException {
        StreamParser streamer = basic();
        Element div = streamer.expectFirst("div");

        // We should have read the sibling div, but not yet its child p.
        Element sib = div.nextElementSibling();
        assertNotNull(sib);
        assertEquals("div", sib.tagName());
        assertEquals(0, sib.childNodeSize());

        // The reader should be positioned at "<p>Two" because we haven't consumed it yet.
        assertTrue(getReader(streamer).matches("<p>Two"));
    }

    @Test void canParseFileReader() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");

        // Can't use FileReader from Java 11 here, so wrap the stream explicitly as UTF-8.
        InputStreamReader input = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(input);
        StreamParser streamer = new StreamParser(Parser.htmlParser()).parse(reader, file.getAbsolutePath());

        Element lastP = null, p;
        while ((p = streamer.selectNext("p")) != null) {
            lastP = p;
        }
        assertTrue(lastP.text().startsWith("VESTIBULUM"));

        // The reader is closed once the streamer finishes reading.
        assertTrue(isClosed(streamer));

        assertThrows(IOException.class, reader::ready); // ready() checks isOpen and throws once closed
    }

    @Test void canParseFile() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");
        StreamParser streamer = DataUtil.streamParser(file.toPath(), StandardCharsets.UTF_8, "", Parser.htmlParser());

        Element lastP = null, p;
        while ((p = streamer.selectNext("p")) != null) {
            lastP = p;
        }
        assertTrue(lastP.text().startsWith("VESTIBULUM"));

        // The reader is closed once the streamer finishes reading.
        assertTrue(isClosed(streamer));
    }

    @Test void canCleanlyConsumePortionOfUrl() throws IOException {
        // Verify that we can read just the head section of large.html, reading only the minimum required from the URL.
        String url = TestServer.origin().file.url("/htmltests/large.html"); // 280 K

        AtomicReference<Float> seenPercent = new AtomicReference<>(0.0f);
        StreamParser parserRef;

        Connection con = Jsoup.connect(url)
            .onResponseProgress((processed, total, percent, response) -> seenPercent.set(percent));

        Connection.Response response = con.execute();
        try (StreamParser parser = response.streamParser()) {
            parserRef = parser;
            // Read only as far as the head section.
            Element head = parser.selectFirst("head");
            Element title = head.expectFirst("title");
            assertEquals("Large HTML", title.text());
        }
        // Having left the try block, both the stream parser and the response body stream should be closed.
        assertTrue(isClosed(parserRef));

        // We should not have read the whole stream: progress is past the start but short of complete.
        assertTrue(seenPercent.get() > 0.0f);
        assertTrue(seenPercent.get() < 100.0f);
    }

    // Fragments

    @Test
    void canStreamFragment() {
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(TABLE_ROWS_HTML, context, "")) {
            StringBuilder seen = new StringBuilder();
            parser.stream().forEachOrdered(el -> trackSeen(el, seen));
            // We get the fragment (plus the context element at the end of the stack), not a full document.
            assertEquals("td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;tbody;table;#root;", seen.toString());

            assertTrue(isClosed(parser)); // read to completion
        }
    }

    @Test void canIterateFragment() {
        // Same expectation as canStreamFragment(), reached through the Iterator interface.
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(TABLE_ROWS_HTML, context, "")) {
            StringBuilder seen = new StringBuilder();

            Iterator<Element> iterator = parser.iterator();
            while (iterator.hasNext()) {
                trackSeen(iterator.next(), seen);
            }

            assertEquals("td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;tbody;table;#root;", seen.toString());

            assertTrue(isClosed(parser)); // read to completion
        }
    }

    @Test
    void canSelectAndCompleteFragment() throws IOException {
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(TABLE_ROWS_HTML, context, "")) {
            Element firstCell = parser.expectNext("td");
            assertEquals("One", firstCell.ownText());

            Element cell = parser.expectNext("td");
            assertEquals("Two", cell.ownText());

            cell = parser.expectNext("td");
            assertEquals("Three", cell.ownText());

            cell = parser.selectNext("td");
            assertNull(cell);

            List<Node> nodes = parser.completeFragment();
            assertEquals(1, nodes.size()); // the inferred tbody
            Node tbody = nodes.get(0);
            assertEquals("tbody", tbody.nodeName());

            List<Node> trs = tbody.childNodes();
            assertEquals(3, trs.size()); // the three TRs
            assertSame(trs.get(0).childNode(0), firstCell); // tr -> td

            assertSame(parser.document(), firstCell.ownerDocument()); // the shell document for this fragment
        }
    }

    @Test
    void canStreamFragmentXml() throws IOException {
        String html = "<tr id=1><td>One</td></tr><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        Element context = new Element("Other");

        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parseFragment(html, context, "")) {
            StringBuilder seen = new StringBuilder();
            parser.stream().forEachOrdered(el -> trackSeen(el, seen));
            // We get the fragment only (no inferred tbody, no wrapping document) under the XML parser.
            assertEquals("td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;#root;", seen.toString());

            assertTrue(isClosed(parser)); // read to completion

            List<Node> nodes = parser.completeFragment();
            assertEquals(3, nodes.size());
            assertEquals("tr", nodes.get(0).nodeName());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "<html><body><a>Link</a></body></html>",
        "<html><body><a>Link</a>",
        "<a>Link</a></body></html>",
        "<a>Link</a>",
        "<a>Link",
        "<a>Link</body>",
    })
    void emitsOnlyOnce(String html) {
        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "")) {
            // https://github.com/jhy/jsoup/issues/2295
            // Previously a /body or /html was emitted twice, due to a fake onNodeClosed fired to track source positions.
            StringBuilder seen = new StringBuilder();
            parser.stream().forEach(el -> trackSeen(el, seen));
            assertEquals("head+;a[Link];body;html;#root;", seen.toString());
        }
    }

}
