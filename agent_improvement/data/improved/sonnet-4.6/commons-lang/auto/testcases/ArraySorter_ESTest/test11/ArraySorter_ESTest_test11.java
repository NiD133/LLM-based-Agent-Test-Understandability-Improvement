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
public class ArraySorter_ESTest_test11 extends ArraySorter_ESTest_scaffolding {

    // Sorting a null float array should return null without throwing an exception.
    @Test(timeout = 4000)
    public void test_sortNullFloatArray_returnsNull() throws Throwable {
        float[] result = ArraySorter.sort((float[]) null);
        assertNull(result);
    }
}
