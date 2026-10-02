package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test41 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that the full {@link JsonFormat.Value} constructor stores the
     * given shape and radix verbatim, so that {@code getShape()} and
     * {@code getRadix()} return exactly what was passed in.
     */
    @Test(timeout = 4000)
    public void constructorStoresShapeAndRadix() throws Throwable {
        JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        Locale locale = Locale.ITALIAN;
        TimeZone timeZone = TimeZone.getDefault();
        Boolean lenient = new Boolean((String) null); // parses to Boolean.FALSE
        int radix = -2562;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                (String) null,            // pattern
                shape,
                locale,
                timeZone,
                (JsonFormat.Features) null,
                lenient,
                radix);

        assertEquals(radix, formatValue.getRadix());
        assertEquals(JsonFormat.Shape.SCALAR, formatValue.getShape());
    }
}
