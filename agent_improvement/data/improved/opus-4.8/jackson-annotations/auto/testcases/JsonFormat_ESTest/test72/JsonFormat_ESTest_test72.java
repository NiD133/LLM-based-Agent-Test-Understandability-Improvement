package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test72 extends JsonFormat_ESTest_scaffolding {

    /**
     * The {@code Value(JsonFormat)} constructor immediately dereferences the
     * annotation (e.g. {@code ann.pattern()}), so passing a {@code null}
     * annotation must fail with a {@link NullPointerException}.
     */
    @Test(timeout = 4000)
    public void constructingValueFromNullAnnotationThrowsNullPointerException() throws Throwable {
        JsonFormat nullAnnotation = null;
        try {
            new JsonFormat.Value(nullAnnotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // Thrown when the constructor reads fields off the null annotation;
            // the exception itself carries no message.
            verifyException("com.fasterxml.jackson.annotation.JsonFormat$Value", e);
        }
    }
}
