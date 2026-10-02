package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test00 extends Frequency_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00_twoEmptyFrequencyObjectsAreEqual() throws Throwable {
        Frequency<Integer> firstEmptyFrequency = new Frequency<Integer>();
        Frequency<Integer> secondEmptyFrequency = new Frequency<Integer>();

        boolean areEqual = firstEmptyFrequency.equals(secondEmptyFrequency);

        assertTrue(areEqual);
    }
}
