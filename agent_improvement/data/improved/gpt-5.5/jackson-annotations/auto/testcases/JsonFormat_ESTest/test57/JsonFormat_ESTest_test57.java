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
public class JsonFormat_ESTest_test57 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test57() throws Throwable {
        JsonFormat.Value defaultFormat = new JsonFormat.Value();
        JsonFormat.Feature enumNullFeature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;

        JsonFormat.Value formatWithFeature = defaultFormat.withFeature(enumNullFeature);
        JsonFormat.Value formatWithSameFeatureAgain = formatWithFeature.withFeature(enumNullFeature);
        JsonFormat.Value mergedWithDefault = JsonFormat.Value.merge(formatWithFeature, defaultFormat);
        JsonFormat.Value overriddenByMergedValue = formatWithSameFeatureAgain.withOverrides(mergedWithDefault);

        assertNotSame(mergedWithDefault, formatWithSameFeatureAgain);
        assertTrue(overriddenByMergedValue.equals((Object) formatWithSameFeatureAgain));
        assertTrue(mergedWithDefault.equals((Object) formatWithFeature));
        assertFalse(formatWithSameFeatureAgain.hasNonDefaultRadix());
        assertSame(formatWithSameFeatureAgain, formatWithFeature);
    }
}
