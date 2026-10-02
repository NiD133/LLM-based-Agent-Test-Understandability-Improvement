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
public class Frequency_ESTest_test11 extends Frequency_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Comparator<Integer> comparator0 = (Comparator<Integer>) mock(Comparator.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(comparator0).compare(anyInt(), anyInt());
        Frequency<Integer> frequency0 = new Frequency<Integer>(comparator0);
        Integer integer0 = new Integer(46);
        frequency0.addValue(integer0);
        String string0 = frequency0.toString();
        assertEquals("Value \t Freq. \t Pct. \t Cum Pct. \n46\t1\t100%\t100%\n", string0);
    }
}
