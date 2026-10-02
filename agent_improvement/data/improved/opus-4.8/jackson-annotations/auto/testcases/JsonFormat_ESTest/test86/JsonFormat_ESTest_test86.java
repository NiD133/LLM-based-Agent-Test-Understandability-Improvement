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
public class JsonFormat_ESTest_test86 extends JsonFormat_ESTest_scaffolding {

    /**
     * {@link JsonFormat.Value#from(JsonFormat)} builds the value by calling
     * {@code JsonFormat.Features.construct(ann)}, which in turn iterates over the
     * arrays returned by {@code with()} and {@code without()}. When those accessors
     * return {@code null} instead of an array, the iteration dereferences null and
     * the construction fails with an (undeclared) NullPointerException originating
     * from {@code JsonFormat$Features}.
     */
    @Test(timeout = 4000)
    public void from_whenFeatureArraysAreNull_throwsNullPointerException() throws Throwable {
        // Build a JsonFormat whose accessors return null, including the
        // with()/without() feature arrays that Features.construct() iterates over.
        JsonFormat annotationWithNullFeatures = mock(JsonFormat.class, CALLS_REAL_METHODS);
        doReturn((String) null).when(annotationWithNullFeatures).locale();
        doReturn((String) null).when(annotationWithNullFeatures).pattern();
        doReturn((JsonFormat.Shape) null).when(annotationWithNullFeatures).shape();
        doReturn((String) null).when(annotationWithNullFeatures).timezone();
        doReturn((JsonFormat.Feature[]) null).when(annotationWithNullFeatures).with();
        doReturn((JsonFormat.Feature[]) null).when(annotationWithNullFeatures).without();

        try {
            JsonFormat.Value.from(annotationWithNullFeatures);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // No message; the NPE is raised while iterating the null feature arrays.
            verifyException("com.fasterxml.jackson.annotation.JsonFormat$Features", e);
        }
    }
}
