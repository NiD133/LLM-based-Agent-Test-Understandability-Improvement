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
     * Verifies that {@link Entities#escape(String, Document.OutputSettings)} escapes the
     * characters that are reserved in HTML while leaving ordinary characters untouched.
     * With the default output settings, a double quote becomes {@code &quot;} and a
     * less-than sign becomes {@code &lt;}, while the surrounding text stays as-is.
     */
    @Test(timeout = 4000)
    public void escapeReplacesReservedHtmlCharacters() throws Throwable {
        Document.OutputSettings defaultOutputSettings = new Document.OutputSettings();

        String escaped = Entities.escape("^du^X\"w<", defaultOutputSettings);

        assertEquals("^du^X&quot;w&lt;", escaped);
    }
}
