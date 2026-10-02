package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test06 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        CharRange rangeContainingOnlyCapitalO = CharRange.isIn('O', 'O');
        Object unrelatedObject = new Object();

        boolean equalsUnrelatedObject = rangeContainingOnlyCapitalO.equals(unrelatedObject);

        assertEquals('O', rangeContainingOnlyCapitalO.getStart());
        assertFalse(equalsUnrelatedObject);
        assertEquals('O', rangeContainingOnlyCapitalO.getEnd());
    }
}
