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
public class DamerauLevenshteinDistance_ESTest_test09 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    private static final String DISTANT_RIGHT_INPUT = "DN{S5$O|Wi*p/ZbT";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Integer zeroThreshold = new Integer(0);
        DamerauLevenshteinDistance distanceWithZeroThreshold = new DamerauLevenshteinDistance(zeroThreshold);
        CharBuffer emptyLeftInput = CharBuffer.allocate(0);

        Integer actualDistance = distanceWithZeroThreshold.apply((CharSequence) emptyLeftInput, (CharSequence) DISTANT_RIGHT_INPUT);

        assertEquals((-1), (int) actualDistance);
    }
}
