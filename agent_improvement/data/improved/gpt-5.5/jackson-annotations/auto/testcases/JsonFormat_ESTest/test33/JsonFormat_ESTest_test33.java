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
public class JsonFormat_ESTest_test33 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        JsonFormat.Shape numericIntegerShape = JsonFormat.Shape.NUMBER_INT;
        JsonFormat.Value baseFormat = JsonFormat.Value.forPattern("");
        SimpleTimeZone explicitTimeZone = new SimpleTimeZone((-5461), "");
        JsonFormat.Features noFeatureOverrides = JsonFormat.Features.empty();
        Boolean explicitLeniency = Boolean.FALSE;

        JsonFormat.Value overrideFormat = new JsonFormat.Value(
                "",
                numericIntegerShape,
                (Locale) null,
                "",
                explicitTimeZone,
                noFeatureOverrides,
                explicitLeniency,
                (-1685));
        JsonFormat.Value mergedFormat = baseFormat.withOverrides(overrideFormat);

        assertEquals((-1685), mergedFormat.getRadix());
        assertTrue(mergedFormat.hasLenient());
        assertFalse(baseFormat.hasNonDefaultRadix());
        assertTrue(mergedFormat.hasShape());
        assertFalse(mergedFormat.equals((Object) overrideFormat));
        assertFalse(mergedFormat.hasTimeZone());
        assertTrue(overrideFormat.hasShape());
    }
}
