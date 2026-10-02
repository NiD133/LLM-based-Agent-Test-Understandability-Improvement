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
public class JsonFormat_ESTest_test76 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that a Value created via forShape(ARRAY):
     * - returns null locale (no locale was configured)
     * - reports hasShape() as true (shape was explicitly set)
     * - reports hasNonDefaultRadix() as false (no custom radix was set)
     */
    @Test(timeout = 4000)
    public void test76() throws Throwable {
        JsonFormat.Value arrayShapeValue = JsonFormat.Value.forShape(JsonFormat.Shape.ARRAY);

        arrayShapeValue.getLocale(); // locale is null for a shape-only value; must not throw

        assertFalse(arrayShapeValue.hasNonDefaultRadix());
        assertTrue(arrayShapeValue.hasShape());
    }
}
