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
     * Verifies that elements prepended directly to a Document appear before the
     * <html> element in the serialized output, and that the shell HTML structure
     * (<head> and <body>) is preserved intact beneath them.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Create a minimal document shell with base URI "b"
        Document document = Document.createShell("b");

        // Prepend five <b> elements directly to the document (not to body)
        document.prependElement("b");
        document.prependElement("b");
        document.prependElement("b");
        document.prependElement("b");
        document.prependElement("b");

        // The five <b> elements should precede the <html> block in serialized form
        String expectedHtml =
            "<b></b><b></b><b></b><b></b><b></b>\n" +
            "<html>\n" +
            " <head></head>\n" +
            " <body></body>\n" +
            "</html>";

        String serializedDocument = document.toString();
        assertEquals(expectedHtml, serializedDocument);
    }
}
