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
     * An empty entity name does not match any known entity, so
     * {@link Entities#codepointsForName(String, int[])} should report that
     * zero codepoints were written into the output array.
     */
    @Test(timeout = 4000)
    public void codepointsForName_withEmptyName_returnsZero() throws Throwable {
        int[] codepoints = new int[5];

        int matchedCount = Entities.codepointsForName("", codepoints);

        assertEquals(0, matchedCount);
    }
}
