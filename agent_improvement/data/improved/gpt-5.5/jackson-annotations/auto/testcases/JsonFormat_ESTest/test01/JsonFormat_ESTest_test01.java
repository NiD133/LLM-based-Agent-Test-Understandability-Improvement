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
public class JsonFormat_ESTest_test01 extends JsonFormat_ESTest_scaffolding {

    private static final String PATTERN = "FALSE";
    private static final JsonFormat.Shape SHAPE = JsonFormat.Shape.OBJECT;
    private static final String LOCALE = "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1639)";
    private static final String TIME_ZONE = "8e.";
    private static final int RADIX = -1639;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value(
                PATTERN,
                SHAPE,
                LOCALE,
                TIME_ZONE,
                (JsonFormat.Features) null,
                (Boolean) null,
                RADIX);

        assertEquals(TIME_ZONE, value.timeZoneAsString());
        assertEquals(RADIX, value.getRadix());
        assertEquals(PATTERN, value.getPattern());
        assertEquals(SHAPE, value.getShape());
    }
}
