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
public class JsonFormat_ESTest_test29 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that calling withLenient(null) on a Value that already has no
     * lenient setting returns the same instance (identity preserved) and that
     * the original radix and shape settings are unchanged.
     */
    @Test(timeout = 4000)
    public void test29() throws Throwable {
        // Create a Value with radix=1 and no shape or lenient setting
        JsonFormat.Value valueWithRadix = JsonFormat.Value.forRadix(1);

        // withLenient(null) on a Value whose lenient is already null should return the same instance
        JsonFormat.Value valueAfterSettingNullLenient = valueWithRadix.withLenient((Boolean) null);

        assertEquals(1, valueAfterSettingNullLenient.getRadix());
        assertFalse(valueAfterSettingNullLenient.hasShape());
        assertSame(valueAfterSettingNullLenient, valueWithRadix);
    }
}
