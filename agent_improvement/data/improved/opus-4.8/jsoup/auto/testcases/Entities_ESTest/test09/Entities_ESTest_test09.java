package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test09 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that {@link Entities#escape(String, Document.OutputSettings)} replaces
     * the HTML-significant characters {@code '} and {@code &} with their named entities
     * ({@code &apos;} and {@code &amp;}), while leaving ordinary characters unchanged.
     */
    @Test(timeout = 4000)
    public void escapeReplacesAposAndAmpersandWithNamedEntities() throws Throwable {
        Document.OutputSettings outputSettings = new Document.OutputSettings();

        String escaped = Entities.escape("K'?wQt&", outputSettings);

        assertEquals("K&apos;?wQt&amp;", escaped);
    }
}
