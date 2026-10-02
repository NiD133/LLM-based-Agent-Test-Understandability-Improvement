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
public class Entities_ESTest_test12 extends Entities_ESTest_scaffolding {

    // Bitmask that has ForText (0x1), Normalise (0x4), TrimLeading (0x8), and TrimTrailing (0x10) set,
    // along with several higher bits — matching the raw value used by the original generated test.
    private static final int NORMALIZE_AND_TRIM_OPTIONS = -67;

    private static final String SPACES_ONLY = "         ";

    @Test(timeout = 4000)
    public void test_escape_whitespaceOnlyString_doesNotAlterOutputSettingsSyntax() throws Throwable {
        // Wrap a mock file writer so escape() has somewhere to write output
        MockFileWriter fileWriter = new MockFileWriter(SPACES_ONLY);
        QuietAppendable output = QuietAppendable.wrap(fileWriter);

        // Default OutputSettings uses HTML syntax
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        // With Normalise+TrimLeading active, every leading space is skipped,
        // so the all-spaces input produces no output — the call must not throw.
        Entities.escape(output, SPACES_ONLY, outputSettings, NORMALIZE_AND_TRIM_OPTIONS);

        // Escaping should leave the OutputSettings syntax unchanged
        assertEquals(Document.OutputSettings.Syntax.html, outputSettings.syntax());
    }
}
