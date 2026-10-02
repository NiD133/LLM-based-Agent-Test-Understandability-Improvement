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
public class JsonFormat_ESTest_test55 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test55() throws Throwable {
        JsonFormat.Value emptyFormat = JsonFormat.Value.empty();

        JsonFormat.Feature enabledFeature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value formatWithEnabledFeature = emptyFormat.withFeature(enabledFeature);

        JsonFormat.Feature disabledFeature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        JsonFormat.Value formatWithDisabledFeature = emptyFormat.withoutFeature(disabledFeature);

        JsonFormat.Value mergedFormat = formatWithEnabledFeature.withOverrides(formatWithDisabledFeature);

        assertFalse(mergedFormat.equals((Object) formatWithDisabledFeature));
        assertNotSame(mergedFormat, formatWithDisabledFeature);
        assertEquals((-1), mergedFormat.getRadix());
        assertNotSame(mergedFormat, formatWithEnabledFeature);
        assertFalse(mergedFormat.equals((Object) formatWithEnabledFeature));
        assertFalse(formatWithDisabledFeature.hasNonDefaultRadix());
    }
}
