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
public class JsonFormat_ESTest_test17 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        JsonFormat.Value emptyFormat = JsonFormat.Value.empty();
        Locale germanLocale = Locale.GERMAN;

        JsonFormat.Value formatWithGermanLocale = emptyFormat.withLocale(germanLocale);
        boolean hasConfiguredLocale = formatWithGermanLocale.hasLocale();

        assertEquals((-1), formatWithGermanLocale.getRadix());
        assertTrue(hasConfiguredLocale);
    }
}
