package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test06 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Create a Value configured with Shape=ARRAY
        JsonFormat.Shape arrayShape = JsonFormat.Shape.ARRAY;
        JsonFormat.Value shapeValue = JsonFormat.Value.forShape(arrayShape);

        // Create a Value configured with strict (non-lenient) parsing
        JsonFormat.Value strictLeniencyValue = JsonFormat.Value.forLeniency(false);

        // Two Values with different settings (shape vs leniency) must not be equal
        boolean valuesAreEqual = shapeValue.equals(strictLeniencyValue);
        assertFalse(valuesAreEqual);

        // isLenient() returns true only when leniency is explicitly TRUE;
        // setting leniency to false results in isLenient() returning false
        assertFalse(strictLeniencyValue.isLenient());

        // A Value created via forLeniency() has no explicit shape set
        assertFalse(strictLeniencyValue.hasShape());

        // Both Values use the default radix (-1 means no custom radix configured)
        assertEquals((-1), strictLeniencyValue.getRadix());
        assertFalse(shapeValue.hasNonDefaultRadix());
    }
}
