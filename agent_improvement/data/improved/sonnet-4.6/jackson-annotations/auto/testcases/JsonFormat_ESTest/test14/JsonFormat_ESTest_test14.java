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
public class JsonFormat_ESTest_test14 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that a default-constructed JsonFormat.Value has no lenient setting
     * and uses the default radix of -1.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        JsonFormat.Value defaultFormatValue = new JsonFormat.Value();

        boolean hasLenientSetting = defaultFormatValue.hasLenient();
        assertFalse(hasLenientSetting);

        assertEquals((-1), defaultFormatValue.getRadix());
    }
}
