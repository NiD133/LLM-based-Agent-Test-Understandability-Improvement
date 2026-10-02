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

    /**
     * Verifies that {@link JsonIncludeProperties.Value#from} throws a NullPointerException
     * when the annotation's {@code order()} method returns {@code null}, because the
     * implementation calls {@code order().asBoolean()} without a null guard.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JsonIncludeProperties mockAnnotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn((OptBoolean) null).when(mockAnnotation).order();
        doReturn((String[]) null).when(mockAnnotation).value();

        try {
            JsonIncludeProperties.Value.from(mockAnnotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("com.fasterxml.jackson.annotation.JsonIncludeProperties$Value", e);
        }
    }
}
