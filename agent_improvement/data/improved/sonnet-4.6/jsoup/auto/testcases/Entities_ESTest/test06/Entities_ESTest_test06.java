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
public class Entities_ESTest_test06 extends Entities_ESTest_scaffolding {

    // Bitmask with no recognised escape flags set (ForText=0x1, ForAttribute=0x2, Normalise=0x4, etc.)
    // -991 has only the ForText bit (0x1) set among the low five bits, so single-quotes are NOT escaped.
    private static final int OPTIONS_FOR_TEXT_ONLY = -991;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // The filename mirrors the pre-escaped form of the input string we will escape.
        MockFileWriter writer = new MockFileWriter("20,HVe0[Tl&apos;l&gt;TR");
        QuietAppendable appendable = QuietAppendable.wrap(writer);

        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // Escape the raw string (containing a literal apostrophe and '>') into the appendable.
        // With OPTIONS_FOR_TEXT_ONLY the apostrophe is left as-is; '>' is always encoded as &gt;.
        Entities.escape(appendable, "20,HVe0[Tl'l>TR", outputSettings, OPTIONS_FOR_TEXT_ONLY);

        // The default OutputSettings syntax must remain HTML (escape() must not mutate it).
        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }
}
