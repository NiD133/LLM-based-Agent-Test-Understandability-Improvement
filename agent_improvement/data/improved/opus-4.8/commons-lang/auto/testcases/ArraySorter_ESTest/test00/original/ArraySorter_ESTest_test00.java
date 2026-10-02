package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Comparator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ArraySorter_ESTest_test00 extends ArraySorter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Object[] objectArray0 = new Object[3];
        Comparator<Object> comparator0 = (Comparator<Object>) mock(Comparator.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0).when(comparator0).compare(any(), any());
        Object[] objectArray1 = ArraySorter.sort(objectArray0, (Comparator<? super Object>) comparator0);
        assertSame(objectArray0, objectArray1);
    }
}
