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
 */
class StreamParserTest {
    private static final String STREAM_HTML =
        "<title>Test</title></head><div id=1>D1</div><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";
    private static final String STREAM_XML =
        "<outmost><DIV id=1>D1</DIV><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";
    private static final String STREAM_HTML_SEEN =
        "title[Test];head+;div#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];body;html;#root;";
    private static final String STREAM_XML_SEEN =
        "DIV#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];outmost;#root;";

    private static final String BASIC_HTML = "<div>One</div><div><p>Two</div>";
    private static final String REMOVAL_HTML = "<div>One</div><div>DESTROY</div><div>Two</div>";
    private static final String TABLE_FRAGMENT =
        "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
    private static final String TABLE_FRAGMENT_SEEN =
        "td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;tbody;table;#root;";

    @Test
    void canStream() {
        try (StreamParser parser = parseHtml(STREAM_HTML)) {
            assertStreamOrder(parser, STREAM_HTML_SEEN);
        }
    }

    @Test
    void canStreamXml() {
        try (StreamParser parser = parseXml(STREAM_XML)) {
            assertStreamOrder(parser, STREAM_XML_SEEN);
        }
    }

    @Test void canIterate() {
        StreamParser parser = parseHtml(STREAM_HTML);

        assertIteratorOrder(parser, STREAM_HTML_SEEN);
    }

    @Test void canReuse() {
        StreamParser parser = new StreamParser(Parser.htmlParser());

        parser.parse("<p>One<p>Two", "");
        assertStreamOrder(parser, "head+;p[One]+;p[Two];body;html;#root;");

        parser.parse("<div>Three<div>Four</div></div>", "");
        assertStreamOrder(parser, "head+;div[Four];div[Three];body;html;#root;");

        assertStreamOrder(parser, "");
    }

    @Test void canStopAndCompleteAndReuse() throws IOException {
        StreamParser parser = new StreamParser(Parser.htmlParser());
        parser.parse("<p>One<p>Two", "");

        Element p = parser.expectFirst("p");
        assertEquals("One", p.text());
        parser.stop();

        Iterator<Element> it = parser.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);

        Element p2 = parser.selectNext("p");
        assertNull(p2);

        Document completed = parser.complete();
        Elements ps = completed.select("p");
        assertEquals(2, ps.size());
        assertEquals("One", ps.get(0).text());
        assertEquals("Two", ps.get(1).text());

        parser.parse("<div>DIV", "");
        Element div = parser.expectFirst("div");
        assertEquals("DIV", div.text());
    }

    @Test void select() throws IOException {
        StreamParser parser = parseHtml("<title>One</title><p id=1>P One</p><p id=2>P Two</p>");

        Element title = parser.expectFirst("title");
        assertEquals("One", title.text());

        Document partialDoc = title.ownerDocument();
        assertNotNull(partialDoc);

        Elements ps = partialDoc.select("p");
        assertEquals(1, ps.size());
        assertEquals("", ps.get(0).text());
        assertSame(partialDoc, parser.document());

        Element title2 = parser.selectFirst("title");
        assertSame(title2, title);

        Element p1 = parser.expectNext("p");
        assertEquals("P One", p1.text());

        Element p2 = parser.expectNext("p");
        assertEquals("P Two", p2.text());

        Element pNone = parser.selectNext("p");
        assertNull(pNone);
    }

    @Test void canRemoveFromDom() {
        StreamParser parser = parseHtml(REMOVAL_HTML);
        parser.parse(REMOVAL_HTML, "");

        parser.stream().forEach(
            el -> {
                if (el.ownText().equals("DESTROY"))
                    el.remove();
            });

        assertRemainingDivs(parser.document());
    }

    @Test void canRemoveWithIterator() {
        StreamParser parser = parseHtml(REMOVAL_HTML);
        parser.parse(REMOVAL_HTML, "");

        Iterator<Element> it = parser.iterator();
        while (it.hasNext()) {
            Element el = it.next();
            if (el.ownText().equals("DESTROY"))
                it.remove();
        }

        assertRemainingDivs(parser.document());
    }

    @Test void canSelectWithHas() throws IOException {
        StreamParser parser = basic();

        Element el = parser.expectNext("div:has(p)");
        assertEquals("Two", el.text());
    }

    @Test void canSelectWithSibling() throws IOException {
        StreamParser parser = basic();

        Element el = parser.expectNext("div:first-of-type");
        assertEquals("One", el.text());

        Element el2 = parser.selectNext("div:first-of-type");
        assertNull(el2);
    }

    @Test void canLoopOnSelectNext() throws IOException {
        StreamParser streamer = parseHtml("<div><p>One<p>Two<p>Thr</div>");

        int count = 0;
        Element e;
        while ((e = streamer.selectNext("p")) != null) {
            assertEquals(3, e.text().length());
            e.remove();
            count++;
        }

        assertEquals(3, count);
        assertEquals(0, streamer.document().select("p").size());
        assertTrue(isClosed(streamer));
    }

    @Test void worksWithXmlParser() throws IOException {
        StreamParser streamer = parseXml("<div><p>One</p><p>Two</p><p>Thr</p></div>");

        int count = 0;
        Element e;
        while ((e = streamer.selectNext("p")) != null) {
            assertEquals(3, e.text().length());
            e.remove();
            count++;
        }

        assertEquals(3, count);
        assertEquals(0, streamer.document().select("p").size());
        assertTrue(isClosed(streamer));
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
        Iterator<Element> it = streamer.iterator();
        while (it.hasNext()) {
            it.next();
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

        assertTrue(isClosed(copy));
    }

    @Test void doesNotReadPastParse() throws IOException {
        StreamParser streamer = basic();

        Element div = streamer.expectFirst("div");

        Element sib = div.nextElementSibling();
        assertNotNull(sib);
        assertEquals("div", sib.tagName());
        assertEquals(0, sib.childNodeSize());
        assertTrue(getReader(streamer).matches("<p>Two"));
    }

    @Test void canParseFileReader() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");

        InputStreamReader input = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(input);
        StreamParser streamer = new StreamParser(Parser.htmlParser()).parse(reader, file.getAbsolutePath());

        Element last = null, e;
        while ((e = streamer.selectNext("p")) != null) {
            last = e;
        }

        assertTrue(last.text().startsWith("VESTIBULUM"));
        assertTrue(isClosed(streamer));
        assertThrows(IOException.class, reader::ready);
    }

    @Test void canParseFile() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");
        StreamParser streamer = DataUtil.streamParser(file.toPath(), StandardCharsets.UTF_8, "", Parser.htmlParser());

        Element last = null, e;
        while ((e = streamer.selectNext("p")) != null) {
            last = e;
        }

        assertTrue(last.text().startsWith("VESTIBULUM"));
        assertTrue(isClosed(streamer));
    }

    @Test void canCleanlyConsumePortionOfUrl() throws IOException {
        String url = TestServer.origin().file.url("/htmltests/large.html");

        AtomicReference<Float> seenPercent = new AtomicReference<>(0.0f);
        StreamParser parserRef;

        Connection con = Jsoup.connect(url)
            .onResponseProgress((processed, total, percent, response) -> {
                seenPercent.set(percent);
            });

        Connection.Response response = con.execute();
        try (StreamParser parser = response.streamParser()) {
            parserRef = parser;
            Element head = parser.selectFirst("head");
            Element title = head.expectFirst("title");
            assertEquals("Large HTML", title.text());
        }

        assertTrue(isClosed(parserRef));
        assertTrue(seenPercent.get() > 0.0f);
        assertTrue(seenPercent.get() < 100.0f);
    }

    @Test
    void canStreamFragment() {
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(TABLE_FRAGMENT, context, "")) {
            assertStreamOrder(parser, TABLE_FRAGMENT_SEEN);
            assertTrue(isClosed(parser));
        }
    }

    @Test void canIterateFragment() {
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(TABLE_FRAGMENT, context, "")) {
            assertIteratorOrder(parser, TABLE_FRAGMENT_SEEN);
            assertTrue(isClosed(parser));
        }
    }

    @Test
    void canSelectAndCompleteFragment() throws IOException {
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(TABLE_FRAGMENT, context, "")) {
            Element first = parser.expectNext("td");
            assertEquals("One", first.ownText());

            Element el = parser.expectNext("td");
            assertEquals("Two", el.ownText());

            el = parser.expectNext("td");
            assertEquals("Three", el.ownText());

            el = parser.selectNext("td");
            assertNull(el);

            List<Node> nodes = parser.completeFragment();
            assertEquals(1, nodes.size());
            Node tbody = nodes.get(0);
            assertEquals("tbody", tbody.nodeName());
            List<Node> trs = tbody.childNodes();
            assertEquals(3, trs.size());
            assertSame(trs.get(0).childNode(0), first);
            assertSame(parser.document(), first.ownerDocument());
        }
    }

    @Test
    void canStreamFragmentXml() throws IOException {
        String html = "<tr id=1><td>One</td></tr><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        Element context = new Element("Other");

        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parseFragment(html, context, "")) {
            assertStreamOrder(parser, "td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;#root;");
            assertTrue(isClosed(parser));

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
        try (StreamParser parser = parseHtml(html)) {
            assertStreamOrder(parser, "head+;a[Link];body;html;#root;");
        }
    }

    private static StreamParser parseHtml(String html) {
        return new StreamParser(Parser.htmlParser()).parse(html, "");
    }

    private static StreamParser parseXml(String xml) {
        return new StreamParser(Parser.xmlParser()).parse(xml, "");
    }

    private static void assertStreamOrder(StreamParser parser, String expected) {
        StringBuilder seen = new StringBuilder();
        parser.stream().forEachOrdered(el -> trackSeen(el, seen));
        assertEquals(expected, seen.toString());
    }

    private static void assertIteratorOrder(StreamParser parser, String expected) {
        StringBuilder seen = new StringBuilder();
        Iterator<Element> it = parser.iterator();
        while (it.hasNext()) {
            trackSeen(it.next(), seen);
        }
        assertEquals(expected, seen.toString());
    }

    private static void assertRemainingDivs(Document doc) {
        Elements divs = doc.select("div");
        assertEquals(2, divs.size());
        assertEquals("One Two", divs.text());
    }

    static void trackSeen(Element el, StringBuilder actual) {
        actual.append(el.tagName());
        if (el.hasAttr("id"))
            actual.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            actual.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            actual.append("+");

        actual.append(";");
    }

    static StreamParser basic() {
        StreamParser parser = parseHtml(BASIC_HTML);
        parser.parse(BASIC_HTML, "");
        return parser;
    }

    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }
}
