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
public class CharRange_ESTest_test08 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        CharRange yOnlyRange = CharRange.isIn('y', 'y');
        CharRange dOnlyRange = CharRange.is('D');

        boolean containsDOnlyRange = yOnlyRange.contains(dOnlyRange);

        assertFalse(dOnlyRange.isNegated());
        assertEquals('y', yOnlyRange.getEnd());
        assertEquals('D', dOnlyRange.getStart());
        assertFalse(containsDOnlyRange);
        assertEquals('y', yOnlyRange.getStart());
        assertEquals('D', dOnlyRange.getEnd());
    }
}
