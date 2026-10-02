package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getFileAsString;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest_sequentialTextNodesDontCollapse {

    @Test
    void sequentialTextNodesDontCollapse() {
        // tests that the pretty printer does not collapse (trim leading | trailing whitespace) when there are
        // sequential textnodes. That doesn't happen in a parse, but can when manipulated.
        // needs to be text that would indent
        Document doc = Jsoup.parse("<div><div></div>Hello</div>");
        Element div = doc.expectFirst("div");
        TextNode hello = (TextNode) div.childNode(1);
        hello.after(" there.");
        assertEquals("Hello", hello.getWholeText());
        assertEquals(" there.", ((TextNode) hello.nextSibling()).getWholeText());
        assertEquals("Hello there.", div.text());
        assertEquals("<div></div>\nHello there.", div.html());
        assertEquals("<div>\n <div></div>\n Hello there.\n</div>", div.outerHtml());
    }
}
