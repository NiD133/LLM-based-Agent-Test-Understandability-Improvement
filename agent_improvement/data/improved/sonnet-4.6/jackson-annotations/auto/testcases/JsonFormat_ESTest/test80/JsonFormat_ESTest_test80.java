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
public class JsonFormat_ESTest_test80 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that a Value created via forRadix(0) correctly stores radix=0,
     * reports no shape override (hasShape is false), and identifies its annotation
     * type as JsonFormat via valueFor().
     */
    @Test(timeout = 4000)
    public void test_forRadix_withZeroRadix_hasNoShapeAndCorrectRadix() throws Throwable {
        JsonFormat.Value formatValue = JsonFormat.Value.forRadix(0);

        assertEquals(JsonFormat.class, formatValue.valueFor());
        assertEquals(0, formatValue.getRadix());
        assertFalse(formatValue.hasShape());
    }
}
