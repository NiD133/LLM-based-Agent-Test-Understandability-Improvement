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
        Locale locale0 = Locale.PRC;
        SimpleTimeZone simpleTimeZone0 = new SimpleTimeZone(2893, "&v1N4|W0j)B");
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.empty();
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("$6\"RBn+pQ?N*brK", (JsonFormat.Shape) null, locale0, simpleTimeZone0, jsonFormat_Features0, (Boolean) null, 1453);
        assertFalse(jsonFormat_Value0.hasShape());
        assertEquals(1453, jsonFormat_Value0.getRadix());
        assertEquals("$6\"RBn+pQ?N*brK", jsonFormat_Value0.getPattern());
    }
}
