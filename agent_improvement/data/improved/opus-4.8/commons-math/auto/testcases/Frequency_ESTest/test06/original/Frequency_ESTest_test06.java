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
public class Frequency_ESTest_test06 extends Frequency_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Frequency<Integer> frequency0 = new Frequency<Integer>();
        Integer integer0 = new Integer(0);
        double double0 = frequency0.getCumPct(integer0);
        assertEquals(Double.NaN, double0, 0.01);
    }
}
