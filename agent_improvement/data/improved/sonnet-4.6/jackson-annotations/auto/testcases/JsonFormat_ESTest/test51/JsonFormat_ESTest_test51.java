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
public class JsonFormat_ESTest_test51 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test51() throws Throwable {
        // Start with a default empty format value (no settings configured)
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        // Enable case-insensitive value matching
        JsonFormat.Feature caseInsensitiveValuesFeature = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES;
        JsonFormat.Value valueWithFeatureEnabled = emptyValue.withFeature(caseInsensitiveValuesFeature);

        // The feature should be explicitly enabled (true), not disabled (false) or unset (null)
        Boolean featureState = valueWithFeatureEnabled.getFeature(caseInsensitiveValuesFeature);
        assertNotNull(featureState);
        assertTrue(featureState);

        // The radix should remain at the default sentinel value (-1), since withFeature() only changes features
        assertEquals((-1), valueWithFeatureEnabled.getRadix());
    }
}
