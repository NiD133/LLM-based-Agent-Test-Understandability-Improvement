package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test01 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * The default (no-threshold) instance should return the unlimited edit distance.
     * Turning "@" into "*@" requires a single insertion of '*', so the distance is 1.
     */
    @Test(timeout = 4000)
    public void apply_singleInsertion_returnsDistanceOne() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();

        Integer result = distance.apply((CharSequence) "@", (CharSequence) "*@");

        assertEquals(1, (int) result);
    }
}
