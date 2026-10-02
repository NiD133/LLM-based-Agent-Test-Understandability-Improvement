package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test16 extends Printer_ESTest_scaffolding {

    /**
     * Parsing arbitrary text with an embedded tag-like token (";LsK[I<HGG0N`Y>jC@@")
     * produces a document whose pretty-printed serialization (driven by Printer)
     * wraps the text in a generated <hgg0n`y> element inside the standard
     * html/head/body skeleton, with tag names lower-cased.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        String malformedHtml = ";LsK[I<HGG0N`Y>jC@@";
        String baseUri = "6lKNy";

        Document document = Parser.parse(malformedHtml, baseUri);
        String prettyPrinted = document.toString();

        String expectedHtml =
            "<html>\n" +
            " <head></head>\n" +
            " <body>\n" +
            "  ;LsK[I<hgg0n`y>jC@@</hgg0n`y>\n" +
            " </body>\n" +
            "</html>";
        assertEquals(expectedHtml, prettyPrinted);
    }
}
