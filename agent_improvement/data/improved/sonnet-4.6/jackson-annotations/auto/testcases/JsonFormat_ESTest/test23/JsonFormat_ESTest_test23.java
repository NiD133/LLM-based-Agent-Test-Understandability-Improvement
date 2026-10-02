package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test23 extends JsonFormat_ESTest_scaffolding {

    // Java's TimeZone.getTimeZone() silently falls back to GMT for any unrecognized timezone ID
    private static final String UNRECOGNIZED_TZ_STRING = "<PKr<%Q@RL3(`H";

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();

        JsonFormat.Value value = new JsonFormat.Value(
            UNRECOGNIZED_TZ_STRING,    // pattern
            JsonFormat.Shape.OBJECT,   // shape
            UNRECOGNIZED_TZ_STRING,    // locale string (unrecognized → null locale)
            UNRECOGNIZED_TZ_STRING,    // timezone string (non-empty, so hasTimeZone() == true)
            emptyFeatures,
            Boolean.TRUE,              // lenient
            (-3138)                    // radix
        );

        // Lazily resolve the timezone string; invalid IDs are mapped to GMT by the JDK
        value.getTimeZone();

        boolean hasTimeZone = value.hasTimeZone();
        // After lazy resolution, timeZoneAsString() returns the resolved timezone's ID
        assertEquals("GMT", value.timeZoneAsString());
        assertTrue(hasTimeZone);
    }
}
