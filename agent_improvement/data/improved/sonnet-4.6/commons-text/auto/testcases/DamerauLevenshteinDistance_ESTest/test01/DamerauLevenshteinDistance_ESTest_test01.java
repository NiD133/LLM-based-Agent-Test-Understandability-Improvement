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
public class DamerauLevenshteinDistance_ESTest_test01 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that inserting one character at the start ("@" -> "*@") yields a distance of 1.
     * DamerauLevenshteinDistance with no threshold uses the unlimited algorithm.
     */
    @Test(timeout = 4000)
    public void test01_singleInsertionYieldsDistanceOne() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();
        // "@" and "*@" differ by one insertion, so the edit distance should be 1
        Integer result = distance.apply((CharSequence) "@", (CharSequence) "*@");
        assertEquals(1, (int) result);
    }
}
