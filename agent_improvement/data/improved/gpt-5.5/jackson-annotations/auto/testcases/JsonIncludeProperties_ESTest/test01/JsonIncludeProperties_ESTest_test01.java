package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test01 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JsonIncludeProperties annotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn((OptBoolean) null).when(annotation).order();
        doReturn((String[]) null).when(annotation).value();

        try {
            JsonIncludeProperties.Value.from(annotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // A null order() result is dereferenced by Value.from after value() is read.
            verifyException("com.fasterxml.jackson.annotation.JsonIncludeProperties$Value", e);
        }
    }
}
