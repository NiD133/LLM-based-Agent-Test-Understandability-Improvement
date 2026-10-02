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
public class JsonIncludeProperties_ESTest_test01 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Value.from(...) calls {@code src.order().asBoolean()} on the annotation.
     * When the annotation's order() returns null, dereferencing it to call
     * asBoolean() throws a NullPointerException.
     */
    @Test(timeout = 4000)
    public void fromAnnotationWithNullOrderThrowsNullPointerException() throws Throwable {
        // Annotation whose order() and value() both return null.
        JsonIncludeProperties annotationWithNullOrder = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn((OptBoolean) null).when(annotationWithNullOrder).order();
        doReturn((String[]) null).when(annotationWithNullOrder).value();

        try {
            JsonIncludeProperties.Value.from(annotationWithNullOrder);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // NPE originates from JsonIncludeProperties.Value.from when calling
            // order().asBoolean() on the null order value; it carries no message.
            verifyException("com.fasterxml.jackson.annotation.JsonIncludeProperties$Value", e);
        }
    }
}
