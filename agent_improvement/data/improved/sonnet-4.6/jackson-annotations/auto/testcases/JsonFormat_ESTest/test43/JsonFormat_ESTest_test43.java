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
public class JsonFormat_ESTest_test43 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that JsonFormat.Value correctly stores and exposes the shape and radix
     * when constructed with null pattern/locale/timezone strings, an empty Features set,
     * and an explicit non-default radix.
     * Boolean.valueOf(null) evaluates to false, which is used as the lenient flag.
     */
    @Test(timeout = 4000)
    public void test43() throws Throwable {
        JsonFormat.Shape booleanShape = JsonFormat.Shape.BOOLEAN;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.valueOf((String) null); // Boolean.valueOf(null) == false
        int customRadix = 1695;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                (String) null,   // pattern
                booleanShape,
                (String) null,   // locale
                (String) null,   // timezone
                emptyFeatures,
                lenient,
                customRadix
        );

        assertEquals(JsonFormat.Shape.BOOLEAN, formatValue.getShape());
        assertEquals(1695, formatValue.getRadix());
    }
}
