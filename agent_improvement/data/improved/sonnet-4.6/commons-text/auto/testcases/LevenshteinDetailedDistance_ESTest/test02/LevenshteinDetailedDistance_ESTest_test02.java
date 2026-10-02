package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test02 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02_comparingTwoEmptyStringsYieldsZeroEditOperations() throws Throwable {
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        LevenshteinResults results = distance.apply((CharSequence) "", (CharSequence) "");

        assertEquals("No insertions expected when both inputs are empty", 0, (int) results.getInsertCount());
        assertEquals("No substitutions expected when both inputs are empty", 0, (int) results.getSubstituteCount());
        assertEquals("No deletions expected when both inputs are empty", 0, (int) results.getDeleteCount());
    }
}
