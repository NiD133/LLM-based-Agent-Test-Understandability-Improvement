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
public class DamerauLevenshteinDistance_ESTest_test06 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        CharBuffer shorterEmptyInput = CharBuffer.allocate(15);
        Integer maximumAllowedDistance = new Integer(15);
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(maximumAllowedDistance);
        CharBuffer longerEmptyInput = CharBuffer.allocate(332);

        Integer actualDistance = distance.apply((CharSequence) shorterEmptyInput, (CharSequence) longerEmptyInput);

        assertEquals((-1), (int) actualDistance);
    }
}
