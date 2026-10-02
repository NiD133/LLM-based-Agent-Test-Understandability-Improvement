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
public class JsonFormat_ESTest_test05 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // A default Value has no leniency, shape, pattern, locale, or timezone set
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // A lenient-only Value explicitly sets leniency to true, leaving all other fields at defaults
        JsonFormat.Value lenientValue = JsonFormat.Value.forLeniency(true);

        // The two values differ only in their leniency setting, so they must not be equal
        boolean areEqual = defaultValue.equals(lenientValue);
        assertFalse(areEqual);

        // forLeniency sets only leniency; radix remains at the default sentinel (-1 = DEFAULT_RADIX)
        assertEquals((-1), lenientValue.getRadix());

        // forLeniency does not set a specific shape, so hasShape() must return false
        assertFalse(lenientValue.hasShape());

        // The leniency was explicitly set to true, so isLenient() must return true
        assertTrue(lenientValue.isLenient());
    }
}
