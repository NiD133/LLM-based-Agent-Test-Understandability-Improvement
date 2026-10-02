package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test20 extends JacksonInject_ESTest_scaffolding {

    /**
     * {@link JacksonInject.Value#from(JacksonInject)} calls {@code src.useInput().asBoolean()}
     * on the annotation it receives. When {@code useInput()} returns {@code null}, dereferencing
     * it to call {@code asBoolean()} throws a {@link NullPointerException}.
     */
    @Test(timeout = 4000)
    public void fromAnnotationWithNullUseInputThrowsNullPointerException() throws Throwable {
        // Build an annotation stub whose useInput() and value() both yield null.
        JacksonInject annotationWithNullUseInput = mock(JacksonInject.class, CALLS_REAL_METHODS);
        doReturn((OptBoolean) null).when(annotationWithNullUseInput).useInput();
        doReturn((String) null).when(annotationWithNullUseInput).value();

        try {
            JacksonInject.Value.from(annotationWithNullUseInput);
            fail("Expected NullPointerException because useInput() returned null");
        } catch (NullPointerException e) {
            // Thrown from JacksonInject.Value while dereferencing the null useInput().
            verifyException("com.fasterxml.jackson.annotation.JacksonInject$Value", e);
        }
    }
}
