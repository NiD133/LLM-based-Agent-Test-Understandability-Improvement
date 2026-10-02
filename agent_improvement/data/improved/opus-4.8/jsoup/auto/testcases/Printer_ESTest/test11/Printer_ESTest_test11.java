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
public class Printer_ESTest_test11 extends Printer_ESTest_scaffolding {

    /**
     * Verifies that the pretty Printer renders a shell document followed by a
     * top-level comment and a top-level text node. The comment and the trailing
     * text are appended directly to the document (siblings of the {@code <html>}
     * element), so each is emitted on its own line after the closing tags.
     */
    @Test(timeout = 4000)
    public void appendsCommentAndTextAfterShellDocument() throws Throwable {
        Document document = Document.createShell("br");
        Comment comment = new Comment("br");
        document.appendChild(comment);
        document.appendText("br");

        String rendered = document.toString();

        String expectedHtml =
            "<html>\n" +
            " <head></head>\n" +
            " <body></body>\n" +
            "</html>\n" +
            "<!--br-->\n" +
            "br";
        assertEquals(expectedHtml, rendered);
    }
}
