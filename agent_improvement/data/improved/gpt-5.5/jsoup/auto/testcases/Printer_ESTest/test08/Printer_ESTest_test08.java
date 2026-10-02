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

    private static final String SHELL_BASE_URI = "b";
    private static final String PREPENDED_TAG = "b";
    private static final int PREPENDED_TAG_COUNT = 5;
    private static final String EXPECTED_SERIALIZED_DOCUMENT = String.join("\n",
            "<b></b><b></b><b></b><b></b><b></b>",
            "<html>",
            " <head></head>",
            " <body></body>",
            "</html>");

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Document document = Document.createShell(SHELL_BASE_URI);
        prependRepeatedTag(document);

        String serializedDocument = document.toString();

        assertEquals(EXPECTED_SERIALIZED_DOCUMENT, serializedDocument);
    }

    private void prependRepeatedTag(Document document) {
        for (int i = 0; i < PREPENDED_TAG_COUNT; i++) {
            document.prependElement(PREPENDED_TAG);
        }
    }
}
