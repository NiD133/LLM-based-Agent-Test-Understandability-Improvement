package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test13 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that content appended directly to a Document (as siblings of the root
     * &lt;html&gt; element) is serialized after the closing &lt;/html&gt; tag.
     *
     * Both {@code append()} and {@code appendText()} place nodes outside the normal
     * html/head/body structure, so they appear as "brbr" after "&lt;/html&gt;" in output.
     */
    @Test(timeout = 4000)
    public void test_documentSerializesNodesAppendedOutsideHtmlElement() throws Throwable {
        Document document = Document.createShell("br");
        // append() parses "br" as an HTML fragment and attaches it as a sibling of <html>
        document.append("br");
        // appendText() attaches a plain text node as another sibling of <html>
        document.appendText("br");

        String serialized = document.toString();

        String expectedOutput = "<html>\n <head></head>\n <body></body>\n</html>\nbrbr";
        assertEquals(expectedOutput, serialized);
    }
}
