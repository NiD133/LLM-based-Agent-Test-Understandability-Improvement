package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test24 extends Entities_ESTest_scaffolding {

    /**
     * getByName should return an empty string when the supplied text is not a
     * recognised HTML entity name.
     */
    @Test(timeout = 4000)
    public void getByName_returnsEmptyString_forUnknownEntityName() throws Throwable {
        String unknownEntityName = "degGT=k&quot;]+G";

        String result = Entities.getByName(unknownEntityName);

        assertNotNull(result);
        assertEquals("", result);
    }
}
