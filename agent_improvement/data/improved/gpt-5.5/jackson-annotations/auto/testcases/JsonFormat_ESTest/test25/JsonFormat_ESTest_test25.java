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
public class JsonFormat_ESTest_test25 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        JsonFormat.Shape numberFloatShape = JsonFormat.Shape.NUMBER_FLOAT;
        TimeZone resolvedTimeZone = TimeZone.getTimeZone("-d`");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenientFlag = Boolean.valueOf("com.fasterxml.jackson.annotation.JsonFormat$Features");

        JsonFormat.Value value = new JsonFormat.Value(
                "GTN#0o7A|nNN5e%^;@",
                numberFloatShape,
                (Locale) null,
                resolvedTimeZone,
                emptyFeatures,
                lenientFlag,
                1932);

        TimeZone storedTimeZone = value.getTimeZone();

        assertEquals(1932, value.getRadix());
        assertTrue(value.hasShape());
        assertNotNull(storedTimeZone);
        assertEquals("GTN#0o7A|nNN5e%^;@", value.getPattern());
    }
}
