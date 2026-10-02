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
public class DamerauLevenshteinDistance_ESTest_test03 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03_distanceBetweenTwoEmptyStringsIsZero() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();

        Integer result = distance.apply((CharSequence) "", (CharSequence) "");

        assertEquals("Distance between two empty strings should be 0", 0, (int) result);
    }
}
