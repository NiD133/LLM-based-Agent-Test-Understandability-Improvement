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

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // Create a minimal document shell with a double-quote as the base URI
        Document shellDocument = Document.createShell("\"");

        // Modify the static PreserveWhitespace flag to a non-default value to verify
        // that the pretty printer still serializes the empty document structure correctly
        Tag.PreserveWhitespace = 3;

        String serializedHtml = shellDocument.toString();

        assertEquals("<html><head></head><body></body></html>", serializedHtml);
    }
}
