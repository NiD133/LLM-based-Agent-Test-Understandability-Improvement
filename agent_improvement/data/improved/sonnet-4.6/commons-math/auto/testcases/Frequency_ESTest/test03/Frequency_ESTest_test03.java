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
public class Frequency_ESTest_test03 extends Frequency_ESTest_scaffolding {

    // Verifies that hashCode() completes without throwing on a newly constructed, empty Frequency instance.
    @Test(timeout = 4000)
    public void test03_hashCodeOnEmptyFrequencyDoesNotThrow() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();
        emptyFrequency.hashCode();
    }
}
