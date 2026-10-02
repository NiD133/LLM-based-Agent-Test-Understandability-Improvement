package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrinterTest_dontCollapseTextAfterNonElements {

    @Test
    void dontCollapseTextAfterNonElements() {
        Document doc = Jsoup.parse("<div><div></div>Hello <!-- -_- --> there</div>");
        Element body = doc.body();

        assertEquals("Hello there", body.text());
        assertEquals("<div>\n <div></div>\n Hello <!-- -_- -->\n  there\n</div>", body.html());
    }
}
