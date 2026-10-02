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
public class JsonFormat_ESTest_test40 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that constructing a JsonFormat.Value with a null Shape defaults to Shape.ANY
     * (so hasShape() returns false), and that the provided pattern and radix are retained as-is.
     */
    @Test(timeout = 4000)
    public void test40() throws Throwable {
        Locale prcLocale = Locale.PRC;
        SimpleTimeZone customTimeZone = new SimpleTimeZone(2893, "&v1N4|W0j)B");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();

        // null shape should be stored as Shape.ANY internally
        JsonFormat.Value formatValue = new JsonFormat.Value(
                "$6\"RBn+pQ?N*brK",
                (JsonFormat.Shape) null,
                prcLocale,
                customTimeZone,
                emptyFeatures,
                (Boolean) null,
                1453);

        // Shape.ANY means no specific shape was configured, so hasShape() must be false
        assertFalse(formatValue.hasShape());
        assertEquals(1453, formatValue.getRadix());
        assertEquals("$6\"RBn+pQ?N*brK", formatValue.getPattern());
    }
}
