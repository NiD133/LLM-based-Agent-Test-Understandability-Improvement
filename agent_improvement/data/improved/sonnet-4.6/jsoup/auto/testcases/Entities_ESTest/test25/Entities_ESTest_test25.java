package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test25 extends Entities_ESTest_scaffolding {

    //   is the non-breaking space character; HTML escape mode maps it to &nbsp;
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        Document.OutputSettings defaultOutputSettings = new Document.OutputSettings();
        String escapedResult = Entities.escape("yen ", defaultOutputSettings);
        assertEquals("yen&nbsp;", escapedResult);
    }
}
