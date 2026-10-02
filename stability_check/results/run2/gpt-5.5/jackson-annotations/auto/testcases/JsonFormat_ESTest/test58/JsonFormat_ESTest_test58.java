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
        JsonFormat.Feature featureToEnable = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value featureEnabledFormat = defaultFormat.withFeature(featureToEnable);
        JsonFormat.Value mergedFormat = defaultFormat.withOverrides(featureEnabledFormat);

        assertFalse(featureEnabledFormat.equals((Object) defaultFormat));
        assertEquals((-1), defaultFormat.getRadix());
        assertNotSame(mergedFormat, defaultFormat);
        assertEquals((-1), featureEnabledFormat.getRadix());
        assertTrue(mergedFormat.equals((Object) featureEnabledFormat));
    }
}
