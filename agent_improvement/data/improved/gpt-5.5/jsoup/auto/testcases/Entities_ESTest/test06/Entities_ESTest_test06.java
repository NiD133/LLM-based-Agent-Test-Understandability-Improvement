package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test06 extends Entities_ESTest_scaffolding {

    private static final String MOCK_OUTPUT_FILE = "20,HVe0[Tl&apos;l&gt;\u007FTR";
    private static final String TEXT_TO_ESCAPE = "20,HVe0[Tl'l>\u007FTR";
    private static final int EVOSUITE_ESCAPE_OPTIONS = -991;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        MockFileWriter outputWriter = new MockFileWriter(MOCK_OUTPUT_FILE);
        QuietAppendable output = QuietAppendable.wrap(outputWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        Entities.escape(output, TEXT_TO_ESCAPE, outputSettings, EVOSUITE_ESCAPE_OPTIONS);

        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }
}
