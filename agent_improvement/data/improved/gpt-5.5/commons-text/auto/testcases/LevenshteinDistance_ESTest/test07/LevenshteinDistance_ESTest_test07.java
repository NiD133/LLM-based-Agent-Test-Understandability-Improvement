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
public class LevenshteinDistance_ESTest_test07 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Integer maximumThreshold = new Integer(Integer.MAX_VALUE);
        LevenshteinDistance distanceWithMaximumThreshold = new LevenshteinDistance(maximumThreshold);

        CharSequence identicalLeftInput = (CharSequence) "3{sMd^PVa";
        CharSequence identicalRightInput = (CharSequence) "3{sMd^PVa";
        Integer distanceBetweenIdenticalInputs = distanceWithMaximumThreshold.apply(identicalLeftInput, identicalRightInput);

        assertEquals(0, (int) distanceBetweenIdenticalInputs);
    }
}
