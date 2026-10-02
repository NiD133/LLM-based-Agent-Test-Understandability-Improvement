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
public class JsonFormat_ESTest_test77 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that a JsonFormat.Value created with a negative radix retains that radix
     * and reports no shape configured. getLenient() returns null (no leniency set)
     * because forRadix() only sets the radix field.
     */
    @Test(timeout = 4000)
    public void test77() throws Throwable {
        int negativeRadix = -2473;
        JsonFormat.Value valueWithNegativeRadix = JsonFormat.Value.forRadix(negativeRadix);

        // getLenient() returns null since forRadix() does not configure leniency
        valueWithNegativeRadix.getLenient();

        // forRadix() leaves shape as ANY, so hasShape() must be false
        assertFalse(valueWithNegativeRadix.hasShape());

        // the radix must be exactly the value passed to forRadix()
        assertEquals(negativeRadix, valueWithNegativeRadix.getRadix());
    }
}
