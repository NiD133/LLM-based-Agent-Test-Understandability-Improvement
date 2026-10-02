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
public class JsonFormat_ESTest_test71 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonFormat.Value#forRadix(int)} correctly stores a negative radix
     * and that the resulting Value has no shape configured (shape defaults to ANY).
     */
    @Test(timeout = 4000)
    public void test71() throws Throwable {
        // Create a Value configured only with a custom (negative) radix
        JsonFormat.Value formatValue = JsonFormat.Value.forRadix(-1639);

        // getFeatures() must be callable without throwing (returns default empty features)
        formatValue.getFeatures();

        // The exact radix value must be preserved, even when negative
        assertEquals(-1639, formatValue.getRadix());

        // forRadix() leaves shape at its default (ANY), so hasShape() must return false
        assertFalse(formatValue.hasShape());
    }
}
