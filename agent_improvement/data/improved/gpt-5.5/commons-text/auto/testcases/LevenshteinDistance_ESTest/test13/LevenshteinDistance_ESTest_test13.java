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
public class LevenshteinDistance_ESTest_test13 extends LevenshteinDistance_ESTest_scaffolding {

    private static final String NON_EMPTY_RIGHT_SEQUENCE = "&D/r2c/;WL~$DxE.m";

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Integer zeroThreshold = new Integer(0);
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(zeroThreshold);
        CharBuffer emptyLeftSequence = CharBuffer.allocate(0);

        Integer distance = distanceWithZeroThreshold.apply((CharSequence) emptyLeftSequence,
                (CharSequence) NON_EMPTY_RIGHT_SEQUENCE);

        assertEquals((-1), (int) distance);
    }
}
