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
public class ArraySorter_ESTest_test03 extends ArraySorter_ESTest_scaffolding {

    /**
     * Verifies that sorting a null Object array returns null without throwing an exception.
     * ArraySorter.sort(T[]) is documented to accept null input and return it unchanged.
     */
    @Test(timeout = 4000)
    public void test03_sortNullObjectArray_returnsNull() throws Throwable {
        Object[] result = ArraySorter.sort((Object[]) null);

        assertNull(result);
    }
}
