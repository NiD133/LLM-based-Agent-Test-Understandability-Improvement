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
public class Printer_ESTest_test18 extends Printer_ESTest_scaffolding {

    private static final String BASE_URI = "\"";
    private static final int PRESERVE_WHITESPACE_OPTION = 3;
    private static final String EMPTY_SHELL_HTML = "<html><head></head><body></body></html>";

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Document shellDocument = Document.createShell(BASE_URI);
        Tag.PreserveWhitespace = PRESERVE_WHITESPACE_OPTION;

        String renderedHtml = shellDocument.toString();

        assertEquals(EMPTY_SHELL_HTML, renderedHtml);
    }
}
