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
public class Entities_ESTest_test26 extends Entities_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // Create a file-backed appendable as the escape destination
        MockFileWriter fileWriter = new MockFileWriter("e\nY]<]>");
        QuietAppendable output = QuietAppendable.wrap(fileWriter);

        // Use default output settings (indentAmount defaults to 1)
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // Escape the string using options 76 = Normalise(4) | TrimLeading(8) | 64
        Entities.escape(output, "v{}++=\"D1mJ", outputSettings, 76);

        // Verify that calling escape does not mutate the OutputSettings object
        assertEquals(1, outputSettings.indentAmount());
    }
}
