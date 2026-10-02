package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test24 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that getByName returns an empty string (never null) when the
     * input is not a recognized HTML entity name.
     */
    @Test(timeout = 4000)
    public void getByName_unknownEntityName_returnsEmptyString() throws Throwable {
        String unknownEntityName = "degGT=k&quot;]+G";

        String result = Entities.getByName(unknownEntityName);

        assertNotNull(result);
        assertEquals("", result);
    }
}
