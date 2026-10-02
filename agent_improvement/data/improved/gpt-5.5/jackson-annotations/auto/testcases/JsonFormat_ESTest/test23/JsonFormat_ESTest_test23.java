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
public class JsonFormat_ESTest_test23 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        String nonStandardFormatText = "<PKr<%Q@RL3(`H";
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                nonStandardFormatText,
                objectShape,
                nonStandardFormatText,
                nonStandardFormatText,
                emptyFeatures,
                lenient,
                (-3138));

        formatValue.getTimeZone();

        boolean hasExplicitTimeZone = formatValue.hasTimeZone();
        assertEquals("GMT", formatValue.timeZoneAsString());
        assertTrue(hasExplicitTimeZone);
    }
}
