package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests that {@link JsonFormat.Value#from(JsonFormat)} throws NullPointerException
 * when the annotation's {@code with()} or {@code without()} feature arrays return null.
 * <p>
 * Internally, {@code Value.from()} delegates to {@code Features.construct(Feature[], Feature[])},
 * which iterates over those arrays using enhanced-for. A null array reference causes an NPE
 * inside {@code JsonFormat$Features}.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test86 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@code JsonFormat.Value.from()} propagates a NullPointerException
     * when the annotation mock returns {@code null} for the {@code with()} and
     * {@code without()} feature arrays (instead of the default empty arrays).
     */
    @Test(timeout = 4000)
    public void test86() throws Throwable {
        // Create a partial mock of the @JsonFormat annotation that delegates to real methods
        // unless explicitly stubbed. All annotation attributes are stubbed to return null,
        // which mimics a pathological annotation instance where feature arrays are absent.
        JsonFormat annotationWithNullFeatures = mock(JsonFormat.class, CALLS_REAL_METHODS);
        doReturn((String) null).when(annotationWithNullFeatures).locale();
        doReturn((String) null).when(annotationWithNullFeatures).pattern();
        doReturn((JsonFormat.Shape) null).when(annotationWithNullFeatures).shape();
        doReturn((String) null).when(annotationWithNullFeatures).timezone();
        // Returning null here (instead of an empty Feature[]) triggers NPE in Features.construct()
        doReturn((JsonFormat.Feature[]) null).when(annotationWithNullFeatures).with();
        doReturn((JsonFormat.Feature[]) null).when(annotationWithNullFeatures).without();

        // Calling Value.from() must throw NullPointerException because Features.construct()
        // tries to iterate over the null feature arrays using an enhanced-for loop.
        try {
            JsonFormat.Value.from(annotationWithNullFeatures);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The NPE originates inside JsonFormat$Features (the Features.construct method),
            // not in the caller — verify the exception comes from that class.
            verifyException("com.fasterxml.jackson.annotation.JsonFormat$Features", e);
        }
    }
}
