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
public class JsonFormat_ESTest_test86 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test86() throws Throwable {
        JsonFormat jsonFormat0 = mock(JsonFormat.class, CALLS_REAL_METHODS);
        doReturn((String) null).when(jsonFormat0).locale();
        doReturn((String) null).when(jsonFormat0).pattern();
        doReturn((JsonFormat.Shape) null).when(jsonFormat0).shape();
        doReturn((String) null).when(jsonFormat0).timezone();
        doReturn((JsonFormat.Feature[]) null).when(jsonFormat0).with();
        doReturn((JsonFormat.Feature[]) null).when(jsonFormat0).without();
        // Undeclared exception!
        try {
            JsonFormat.Value.from(jsonFormat0);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("com.fasterxml.jackson.annotation.JsonFormat$Features", e);
        }
    }
}
