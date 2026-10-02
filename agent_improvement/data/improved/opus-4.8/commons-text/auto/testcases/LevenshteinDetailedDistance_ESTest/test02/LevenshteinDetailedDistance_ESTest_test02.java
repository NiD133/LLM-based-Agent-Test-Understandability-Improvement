package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test02 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Comparing two empty strings requires no edits, so every individual
     * operation count (insert, substitute, delete) must be zero.
     */
    @Test(timeout = 4000)
    public void applyToTwoEmptyStringsReportsZeroEdits() throws Throwable {
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        LevenshteinResults results = distance.apply((CharSequence) "", (CharSequence) "");

        assertEquals("no inserts expected for two empty strings", 0, (int) results.getInsertCount());
        assertEquals("no substitutions expected for two empty strings", 0, (int) results.getSubstituteCount());
        assertEquals("no deletes expected for two empty strings", 0, (int) results.getDeleteCount());
    }
}
