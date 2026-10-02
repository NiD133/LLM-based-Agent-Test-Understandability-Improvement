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
public class JsonFormat_ESTest_test09 extends JsonFormat_ESTest_scaffolding {

    private static final int DEFAULT_RADIX_MARKER = -1;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        JsonFormat.Value defaultFormat = new JsonFormat.Value();

        boolean equalsItself = defaultFormat.equals(defaultFormat);

        assertTrue(equalsItself);
        assertEquals(DEFAULT_RADIX_MARKER, defaultFormat.getRadix());
    }
}
