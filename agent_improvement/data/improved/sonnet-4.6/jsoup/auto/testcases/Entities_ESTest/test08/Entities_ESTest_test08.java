package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test08 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that Entities.escape escapes HTML special characters (double-quote and
     * less-than) while leaving all other characters in the input unchanged, using
     * default OutputSettings (base escape mode, UTF-8 charset).
     *
     * Input:    "^du^X\"w<"
     * Expected: "^du^X&quot;w&lt;"  — " → &quot;, < → &lt;, rest unchanged
     */
    @Test(timeout = 4000)
    public void test08_escapeHtmlSpecialCharsWithDefaultOutputSettings() throws Throwable {
        Document.OutputSettings defaultOutputSettings = new Document.OutputSettings();

        // Input contains a double-quote (") and a less-than sign (<) alongside plain chars
        String inputWithHtmlSpecialChars = "^du^X\"w<";

        String escaped = Entities.escape(inputWithHtmlSpecialChars, defaultOutputSettings);

        // " must be encoded as &quot; and < must be encoded as &lt;
        assertEquals("^du^X&quot;w&lt;", escaped);
    }
}
