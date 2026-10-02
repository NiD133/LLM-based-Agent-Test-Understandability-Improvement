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
public class JsonFormat_ESTest_test49 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that enabling a feature via withFeature() produces a Value that is not equal
     * to the original default Value, and that neither instance uses a non-default radix.
     */
    @Test(timeout = 4000)
    public void test49() throws Throwable {
        // A default Value has no features enabled and uses the default radix
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Enabling WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS produces a distinct Value
        JsonFormat.Value valueWithNanosFeature = defaultValue.withFeature(
                JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        // The two instances are not equal because their feature sets differ
        assertFalse(valueWithNanosFeature.equals(defaultValue));
        assertFalse(defaultValue.equals((Object) valueWithNanosFeature));

        // Neither instance has overridden the radix from its default
        assertFalse(valueWithNanosFeature.hasNonDefaultRadix());
        assertFalse(defaultValue.hasNonDefaultRadix());
    }
}
