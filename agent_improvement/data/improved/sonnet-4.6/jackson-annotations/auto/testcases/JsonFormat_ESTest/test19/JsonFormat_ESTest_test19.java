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
public class JsonFormat_ESTest_test19 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_forRadix_setsOnlyRadix_patternAndShapeRemainAbsent() throws Throwable {
        // forRadix() should produce a Value whose radix is set to the given value,
        // while pattern and shape are left at their defaults (empty/ANY).
        JsonFormat.Value valueWithNegativeRadix = JsonFormat.Value.forRadix(-1639);

        boolean hasPattern = valueWithNegativeRadix.hasPattern();

        assertEquals(-1639, valueWithNegativeRadix.getRadix());
        assertFalse(valueWithNegativeRadix.hasShape());
        assertFalse(hasPattern);
    }
}
