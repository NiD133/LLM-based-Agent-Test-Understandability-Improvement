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
public class JsonFormat_ESTest_test56 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test56() throws Throwable {
        JsonFormat.Value emptyFormat = JsonFormat.Value.empty();
        JsonFormat.Feature unknownEnumAsNull = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;

        JsonFormat.Value formatWithFeature = emptyFormat.withFeature(unknownEnumAsNull);
        JsonFormat.Value formatWithoutFeature = emptyFormat.withoutFeature(unknownEnumAsNull);
        JsonFormat.Value overriddenFormat = formatWithoutFeature.withOverrides(formatWithFeature);

        assertFalse(formatWithoutFeature.equals((Object) emptyFormat));
        assertTrue(overriddenFormat.equals((Object) formatWithFeature));
        assertNotSame(overriddenFormat, formatWithFeature);
        assertEquals((-1), formatWithFeature.getRadix());
    }
}
