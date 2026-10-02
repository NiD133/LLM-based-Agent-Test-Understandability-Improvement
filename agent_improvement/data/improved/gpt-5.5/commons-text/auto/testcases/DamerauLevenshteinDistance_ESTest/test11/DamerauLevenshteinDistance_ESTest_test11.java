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
public class DamerauLevenshteinDistance_ESTest_test11 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    private static final String IDENTICAL_INPUT = "org.apache.commons.text.similarity.DamerauLevenshteinDistance";

    @Test(timeout = 4000)
    public void identicalInputsHaveZeroDistance() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();

        Integer actualDistance = distance.apply((CharSequence) IDENTICAL_INPUT, (CharSequence) IDENTICAL_INPUT);

        assertEquals(0, (int) actualDistance);
    }
}
