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

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        final Integer zeroEditThreshold = new Integer(0);
        final DamerauLevenshteinDistance distanceWithZeroThreshold = new DamerauLevenshteinDistance(zeroEditThreshold);
        final CharBuffer emptyLeftInput = CharBuffer.allocate(0);

        final Integer computedDistance = distanceWithZeroThreshold.apply(
                (CharSequence) emptyLeftInput,
                (CharSequence) "DN{S5$O|Wi*p/ZbT");

        assertEquals((-1), (int) computedDistance);
    }
}
