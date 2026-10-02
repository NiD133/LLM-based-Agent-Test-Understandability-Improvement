package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test11 extends Entities_ESTest_scaffolding {

    private static final String INPUT_TEXT = "Zect     _ @          ";

    @Test(timeout = 4000)
    public void test11_escapeIntoWriterDoesNotMutateOutputSettings() throws Throwable {
        MockFileWriter fileWriter = new MockFileWriter(INPUT_TEXT);
        QuietAppendable output = QuietAppendable.wrap(fileWriter);
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // options = -20 enables ForAttribute and Normalise/TrimTrailing bits
        Entities.escape(output, INPUT_TEXT, outputSettings, (-20));

        // escape() must not change the OutputSettings state; outline defaults to false
        assertFalse(outputSettings.outline());
    }
}
