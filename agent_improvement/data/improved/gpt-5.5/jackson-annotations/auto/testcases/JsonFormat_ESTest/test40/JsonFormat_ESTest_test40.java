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

    @Test(timeout = 4000)
    public void test40() throws Throwable {
        Locale configuredLocale = Locale.PRC;
        SimpleTimeZone configuredTimeZone = new SimpleTimeZone(2893, "&v1N4|W0j)B");
        JsonFormat.Features configuredFeatures = JsonFormat.Features.empty();

        String pattern = "$6\"RBn+pQ?N*brK";
        int radix = 1453;
        JsonFormat.Value formatValue = new JsonFormat.Value(
                pattern,
                (JsonFormat.Shape) null,
                configuredLocale,
                configuredTimeZone,
                configuredFeatures,
                (Boolean) null,
                radix);

        assertFalse(formatValue.hasShape());
        assertEquals(radix, formatValue.getRadix());
        assertEquals(pattern, formatValue.getPattern());
    }
}
