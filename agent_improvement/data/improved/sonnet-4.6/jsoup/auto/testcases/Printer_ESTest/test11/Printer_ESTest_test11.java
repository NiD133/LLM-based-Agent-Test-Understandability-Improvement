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

    // Nodes appended directly to the Document (outside <html>) are serialized after the closing </html> tag.
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Document document = Document.createShell("br");

        Comment comment = new Comment("br");
        document.appendChild(comment);
        document.appendText("br");

        String html = document.toString();

        assertEquals(
            "<html>\n <head></head>\n <body></body>\n</html>\n<!--br-->\nbr",
            html
        );
    }
}
