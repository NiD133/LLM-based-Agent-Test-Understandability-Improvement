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
public class LevenshteinDistance_ESTest_test03 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing two empty strings returns a Levenshtein distance of 0,
     * since no edits are needed to transform one empty string into another.
     */
    @Test(timeout = 4000)
    public void test_distanceBetweenTwoEmptyStringsIsZero() throws Throwable {
        LevenshteinDistance defaultInstance = LevenshteinDistance.getDefaultInstance();
        Integer distance = defaultInstance.apply((CharSequence) "", (CharSequence) "");
        assertEquals(0, (int) distance);
    }
}
