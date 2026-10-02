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
public class JsonFormat_ESTest_test58 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test58() throws Throwable {
        JsonFormat.Value defaultFormat = new JsonFormat.Value();
        JsonFormat.Feature enabledFeature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value formatWithFeature = defaultFormat.withFeature(enabledFeature);
        JsonFormat.Value mergedFormat = defaultFormat.withOverrides(formatWithFeature);

        assertFalse("Enabling a feature should make the value differ from the default",
                formatWithFeature.equals((Object) defaultFormat));
        assertEquals("Default format keeps the default radix", (-1), defaultFormat.getRadix());
        assertNotSame("Merging a feature override creates a separate value instance",
                mergedFormat, defaultFormat);
        assertEquals("Enabling a feature should not change the radix", (-1),
                formatWithFeature.getRadix());
        assertTrue("Merged value should match the feature-enabled override",
                mergedFormat.equals((Object) formatWithFeature));
    }
}
