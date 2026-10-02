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
     * Verifies that jsoup parses a fragment containing an unusual tag name with a backtick character
     * and serializes it back to a fully-structured HTML document with the tag name lowercased.
     *
     * The input mixes plain text with an unknown element whose name includes a backtick (`).
     * jsoup normalizes the tag name to lowercase, wraps the content in html/head/body, and
     * auto-closes the unknown element in the output.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // Input contains plain text followed by an unknown tag whose name has a backtick: <HGG0N`Y>
        String htmlFragment = ";LsK[I<HGG0N`Y>jC@@";
        String baseUri = "6lKNy";

        Document document = Parser.parse(htmlFragment, baseUri);
        String serializedHtml = document.toString();

        // jsoup normalizes the unknown tag name to lowercase and wraps the fragment in full HTML structure
        String expectedHtml = "<html>\n <head></head>\n <body>\n  ;LsK[I<hgg0n`y>jC@@</hgg0n`y>\n </body>\n</html>";
        assertEquals(expectedHtml, serializedHtml);
    }
}
