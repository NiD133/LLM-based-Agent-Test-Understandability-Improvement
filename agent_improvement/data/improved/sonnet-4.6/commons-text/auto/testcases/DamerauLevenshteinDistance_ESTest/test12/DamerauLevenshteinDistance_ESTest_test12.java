package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test12 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * When constructed with the no-arg constructor, the distance instance has no threshold,
     * so getThreshold() must return null (unlimited distance mode).
     */
    @Test(timeout = 4000)
    public void test_defaultConstructor_hasNullThreshold() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();
        Integer threshold = distance.getThreshold();
        assertNull(threshold);
    }
}
