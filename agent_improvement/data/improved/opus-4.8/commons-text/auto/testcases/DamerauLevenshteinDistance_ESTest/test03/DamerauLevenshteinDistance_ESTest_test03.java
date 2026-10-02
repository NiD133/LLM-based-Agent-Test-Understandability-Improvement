package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test03 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * The distance between two empty strings is zero, since no edits are
     * required to turn one into the other.
     */
    @Test(timeout = 4000)
    public void distanceBetweenTwoEmptyStringsIsZero() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();

        Integer result = distance.apply((CharSequence) "", (CharSequence) "");

        assertEquals(0, (int) result);
    }
}
