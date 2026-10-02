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
public class Printer_ESTest_test08 extends Printer_ESTest_scaffolding {

    /**
     * Verifies how the Printer renders a Document that has several elements
     * prepended directly to it (outside the usual html/head/body structure).
     *
     * Each prepended {@code <b>} is inserted at the front of the document, so the
     * five inline {@code <b></b>} pairs are printed first on a single line, followed
     * by the pretty-printed shell document.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Document document = Document.createShell("b");

        // Prepend five <b> elements; each new one goes to the front of the document.
        for (int i = 0; i < 5; i++) {
            document.prependElement("b");
        }

        String rendered = document.toString();

        assertEquals(
            "<b></b><b></b><b></b><b></b><b></b>\n<html>\n <head></head>\n <body></body>\n</html>",
            rendered);
    }
}
