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
     * Verifies that Entities.escape() encodes an apostrophe as &apos; and an
     * ampersand as &amp; when using default OutputSettings.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Document.OutputSettings defaultOutputSettings = new Document.OutputSettings();
        String escaped = Entities.escape("K'?wQt&", defaultOutputSettings);
        assertEquals("K&apos;?wQt&amp;", escaped);
    }
}
