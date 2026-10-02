package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test16 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE", JsonFormat.Shape.SCALAR, "FALSE", "O", emptyFeatures, Boolean.TRUE, 10);

        boolean hasTimeZone = value.hasTimeZone();

        assertEquals("FALSE", value.getPattern());
        assertEquals(10, value.getRadix());
        assertEquals("O", value.timeZoneAsString());
        assertTrue(value.hasShape());
        assertTrue(hasTimeZone);
    }
}
