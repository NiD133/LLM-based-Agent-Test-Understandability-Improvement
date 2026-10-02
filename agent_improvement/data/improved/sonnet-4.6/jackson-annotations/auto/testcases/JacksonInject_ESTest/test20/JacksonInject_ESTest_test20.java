package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test20 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that {@link JacksonInject.Value#from(JacksonInject)} throws a
     * {@link NullPointerException} when the annotation's {@code useInput()} returns {@code null}.
     *
     * <p>Internally, {@code from()} calls {@code src.useInput().asBoolean()}, so a {@code null}
     * return from {@code useInput()} causes an NPE inside {@code JacksonInject$Value}.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Create a mock annotation whose useInput() and value() both return null,
        // simulating a malformed or partially-configured JacksonInject annotation.
        JacksonInject annotationWithNullUseInput = mock(JacksonInject.class, CALLS_REAL_METHODS);
        doReturn((OptBoolean) null).when(annotationWithNullUseInput).useInput();
        doReturn((String) null).when(annotationWithNullUseInput).value();

        // Value.from() internally calls src.useInput().asBoolean(), so a null OptBoolean
        // causes a NullPointerException inside JacksonInject$Value.
        try {
            JacksonInject.Value.from(annotationWithNullUseInput);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("com.fasterxml.jackson.annotation.JacksonInject$Value", e);
        }
    }
}
