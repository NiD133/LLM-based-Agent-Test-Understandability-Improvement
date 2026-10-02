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
public class Entities_ESTest_test05 extends Entities_ESTest_scaffolding {

    private static final String TEXT_WITH_APOSTROPHE_GREATER_THAN_AND_DELETE =
            "20,HVe0[Tl'l>\u007FTR";
    private static final int GENERATED_ESCAPE_OPTIONS = -1814;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(pipedOutputStream);
        MockPrintStream printStream = new MockPrintStream(bufferedOutputStream, false);
        QuietAppendable appendable = QuietAppendable.wrap(printStream);

        Entities.escape(
                appendable,
                TEXT_WITH_APOSTROPHE_GREATER_THAN_AND_DELETE,
                outputSettings,
                GENERATED_ESCAPE_OPTIONS);

        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }
}
