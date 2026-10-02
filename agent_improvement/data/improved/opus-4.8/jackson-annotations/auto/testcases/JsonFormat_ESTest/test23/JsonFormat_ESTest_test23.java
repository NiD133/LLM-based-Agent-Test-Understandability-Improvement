package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test23 extends JsonFormat_ESTest_scaffolding {

    /**
     * When a JsonFormat.Value is built with a non-empty timezone string that is
     * not a recognized timezone id, the value still reports that it has a timezone.
     * Resolving it via getTimeZone() falls back to "GMT" (TimeZone.getTimeZone's
     * behavior for unknown ids), and timeZoneAsString() then returns that id.
     */
    @Test(timeout = 4000)
    public void valueWithUnknownTimeZoneStringResolvesToGmt() throws Throwable {
        String unknownTimeZoneId = "<PKr<%Q@RL3(`H";

        JsonFormat.Value value = new JsonFormat.Value(
                unknownTimeZoneId,            // pattern
                JsonFormat.Shape.OBJECT,      // shape
                unknownTimeZoneId,            // locale string
                unknownTimeZoneId,            // timezone string (unrecognized)
                JsonFormat.Features.empty(),  // features
                Boolean.TRUE,                 // lenient
                -3138);                       // radix

        // Resolve the timezone; an unknown id falls back to GMT.
        value.getTimeZone();

        assertTrue("non-empty timezone string counts as having a timezone",
                value.hasTimeZone());
        assertEquals("unknown timezone id resolves to GMT",
                "GMT", value.timeZoneAsString());
    }
}
