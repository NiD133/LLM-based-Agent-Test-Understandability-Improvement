package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test09 extends Entities_ESTest_scaffolding {

    private static final String TEXT_WITH_APOSTROPHE_AND_AMPERSAND = "K'?wQt&";
    private static final String ESCAPED_TEXT = "K&apos;?wQt&amp;";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Document.OutputSettings defaultOutputSettings = new Document.OutputSettings();

        String escapedText = Entities.escape(TEXT_WITH_APOSTROPHE_AND_AMPERSAND, defaultOutputSettings);

        assertEquals(ESCAPED_TEXT, escapedText);
    }
}
