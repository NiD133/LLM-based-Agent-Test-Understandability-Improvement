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
public class DamerauLevenshteinDistance_ESTest_test08 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Integer zeroThreshold = new Integer(0);
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(zeroThreshold);
        SimilarityInput<Object> nullLeftInput = (SimilarityInput<Object>) null;
        SimilarityInput<Object> nullRightInput = (SimilarityInput<Object>) null;

        try {
            distance.apply(nullLeftInput, nullRightInput);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException("org.apache.commons.text.similarity.DamerauLevenshteinDistance", exception);
        }
    }
}
