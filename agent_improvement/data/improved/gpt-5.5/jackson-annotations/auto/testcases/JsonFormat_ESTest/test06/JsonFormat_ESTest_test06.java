package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test06 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        JsonFormat.Shape arrayShape = JsonFormat.Shape.ARRAY;
        JsonFormat.Value arrayShapeValue = JsonFormat.Value.forShape(arrayShape);
        JsonFormat.Value explicitNonLenientValue = JsonFormat.Value.forLeniency(false);

        boolean valuesAreEqual = arrayShapeValue.equals(explicitNonLenientValue);

        assertFalse(explicitNonLenientValue.isLenient());
        assertFalse(valuesAreEqual);
        assertEquals((-1), explicitNonLenientValue.getRadix());
        assertFalse(explicitNonLenientValue.hasShape());
        assertFalse(arrayShapeValue.hasNonDefaultRadix());
    }
}
