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
public class JsonFormat_ESTest_test30 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        // Create a default Value with no lenient setting and default radix
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Apply lenient=true to get a new Value; the original is unmodified
        JsonFormat.Value lenientValue = defaultValue.withLenient(Boolean.TRUE);

        // Neither value should have a non-default radix since none was specified
        assertFalse(lenientValue.hasNonDefaultRadix());
        assertFalse(defaultValue.hasNonDefaultRadix());

        // The new Value must report leniency as explicitly enabled
        assertTrue(lenientValue.isLenient());
    }
}
