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
public class JsonFormat_ESTest_test42 extends JsonFormat_ESTest_scaffolding {

    // The pattern "##default" is the DEFAULT_LOCALE sentinel, but it is non-empty so hasPattern() returns true.
    // The timezone string "##default" equals DEFAULT_TIMEZONE, which the constructor maps to null,
    // so hasTimeZone() returns false.
    @Test(timeout = 4000)
    public void test42() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        int customRadix = -1880944581;

        JsonFormat.Value value = new JsonFormat.Value(
                JsonFormat.DEFAULT_LOCALE,    // pattern: non-empty sentinel → hasPattern() == true
                objectShape,
                JsonFormat.DEFAULT_LOCALE,    // locale string: sentinel → stored as null
                JsonFormat.DEFAULT_TIMEZONE,  // timezone string: sentinel → stored as null → hasTimeZone() == false
                (JsonFormat.Features) null,
                (Boolean) null,
                customRadix);

        assertFalse(value.hasTimeZone());
        assertTrue(value.hasPattern());
        assertEquals(customRadix, value.getRadix());
        assertEquals(JsonFormat.Shape.OBJECT, value.getShape());
    }
}
