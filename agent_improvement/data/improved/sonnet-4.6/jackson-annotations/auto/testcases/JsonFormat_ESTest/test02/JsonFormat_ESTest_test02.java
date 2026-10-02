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
public class JsonFormat_ESTest_test02 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that when the same feature appears in both the enabled and disabled arrays,
     * the disabled state takes precedence (get() returns Boolean.FALSE, not Boolean.TRUE).
     * Also verifies that a Value constructed with a null timezone string has no timezone,
     * and that the specified radix is preserved.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Build a Features object where ADJUST_DATES_TO_CONTEXT_TIME_ZONE is listed
        // in both the enabled[] and disabled[] arrays. The disabled check wins in get().
        JsonFormat.Feature adjustDatesToContextTimeZone = JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
        JsonFormat.Feature[] featuresEnabledAndDisabled = new JsonFormat.Feature[3];
        featuresEnabledAndDisabled[0] = adjustDatesToContextTimeZone;
        featuresEnabledAndDisabled[1] = featuresEnabledAndDisabled[0];
        featuresEnabledAndDisabled[2] = featuresEnabledAndDisabled[0];

        JsonFormat.Features features = JsonFormat.Features.construct(featuresEnabledAndDisabled, featuresEnabledAndDisabled);

        // Disabled check in Features.get() runs before enabled, so the result is Boolean.FALSE
        Boolean featureState = features.get(adjustDatesToContextTimeZone);
        assertFalse(featureState);

        // Construct a Value with: a non-default pattern, BINARY shape, a non-standard locale string,
        // null timezone (so hasTimeZone() should return false), and radix=3
        JsonFormat.Shape binaryShape = JsonFormat.Shape.BINARY;
        String patternAndLocale = "0(hYGYeE_-#<Q!B";
        String nullTimezone = null;
        int radix = 3;
        JsonFormat.Value value = new JsonFormat.Value(
                patternAndLocale, binaryShape, patternAndLocale, nullTimezone,
                features, featureState, radix);

        // Null timezone string means no timezone is set
        assertFalse(value.hasTimeZone());
        // Radix must be exactly what was passed to the constructor
        assertEquals(3, value.getRadix());
    }
}
