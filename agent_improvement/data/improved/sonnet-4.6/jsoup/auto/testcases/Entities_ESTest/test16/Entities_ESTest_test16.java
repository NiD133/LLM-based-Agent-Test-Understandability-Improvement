package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test16 extends Entities_ESTest_scaffolding {

    /**
     * An empty string is not a valid HTML entity name, so codepointsForName should
     * return 0 (meaning no codepoints were resolved) and leave the output array unchanged.
     */
    @Test(timeout = 4000)
    public void test_codepointsForName_returnsZero_whenEntityNameIsEmpty() throws Throwable {
        int[] codepoints = new int[5];

        int resolvedCount = Entities.codepointsForName("", codepoints);

        assertEquals(0, resolvedCount);
    }
}
