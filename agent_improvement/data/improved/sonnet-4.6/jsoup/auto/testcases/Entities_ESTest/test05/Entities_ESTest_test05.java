package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test05 extends Entities_ESTest_scaffolding {

    // Bitmask combining ForAttribute (0x2), TrimLeading (0x8), TrimTrailing (0x10),
    // plus additional high bits; negative value exercises atypical flag combinations.
    private static final int ESCAPE_OPTIONS = -1814;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Default output settings use HTML syntax
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // Build a piped stream chain that Entities.escape can write into
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(pipedOutputStream);
        MockPrintStream printStream = new MockPrintStream(bufferedOutputStream, false);
        QuietAppendable destination = QuietAppendable.wrap(printStream);

        // Escape a string that contains special HTML characters (apostrophe and '>')
        Entities.escape(destination, "20,HVe0[Tl'l>TR", outputSettings, ESCAPE_OPTIONS);

        // Escaping should never mutate the OutputSettings; syntax must still be HTML
        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }
}
