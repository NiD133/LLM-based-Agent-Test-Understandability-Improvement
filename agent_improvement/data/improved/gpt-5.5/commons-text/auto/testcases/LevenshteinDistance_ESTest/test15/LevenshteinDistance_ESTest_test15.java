package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test15 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Integer configuredThreshold = new Integer(1403);
        LevenshteinDistance distanceWithThreshold = new LevenshteinDistance(configuredThreshold);

        Integer actualThreshold = distanceWithThreshold.getThreshold();

        assertEquals(1403, (int) actualThreshold);
    }
}
