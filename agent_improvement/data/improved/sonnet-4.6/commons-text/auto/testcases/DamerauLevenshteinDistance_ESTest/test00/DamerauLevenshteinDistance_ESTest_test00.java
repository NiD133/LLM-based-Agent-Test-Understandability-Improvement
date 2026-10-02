package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test00 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // A negative threshold is not allowed; the constructor must reject it with IllegalArgumentException
        Integer negativeThreshold = new Integer((-17));
        DamerauLevenshteinDistance distance = null;
        try {
            distance = new DamerauLevenshteinDistance(negativeThreshold);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Threshold must not be negative
            //
            verifyException("org.apache.commons.text.similarity.DamerauLevenshteinDistance", e);
        }
    }
}
