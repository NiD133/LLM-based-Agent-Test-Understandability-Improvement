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
public class JsonFormat_ESTest_test27 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that calling withRadix with the DEFAULT_RADIX sentinel value (-1)
     * on a Value whose radix is already DEFAULT_RADIX returns the same instance,
     * i.e. withRadix short-circuits and avoids creating a new object when the
     * requested radix equals the current radix.
     */
    @Test(timeout = 4000)
    public void test_withRadix_returnsThisWhenRadixUnchanged() throws Throwable {
        // A default-constructed Value has _radix == DEFAULT_RADIX (-1)
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Applying the same DEFAULT_RADIX should trigger the identity short-circuit
        JsonFormat.Value resultValue = defaultValue.withRadix(JsonFormat.DEFAULT_RADIX);

        // Expect the exact same instance back (no new object created)
        assertSame(resultValue, defaultValue);
    }
}
