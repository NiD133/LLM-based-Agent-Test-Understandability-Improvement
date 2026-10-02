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
public class JsonTypeInfo_ESTest_test00 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonTypeInfo.Value#from(JsonTypeInfo)} fails with a
     * {@link NullPointerException} when the source annotation's
     * {@code writeTypeIdForDefaultImpl()} returns {@code null}.
     *
     * <p>Internally {@code from(...)} calls
     * {@code src.writeTypeIdForDefaultImpl().asBoolean()}; when that accessor
     * returns {@code null}, dereferencing it to call {@code asBoolean()} throws.
     */
    @Test(timeout = 4000)
    public void from_throwsNullPointerException_whenWriteTypeIdForDefaultImplIsNull() throws Throwable {
        // Build a JsonTypeInfo whose accessors return valid values, EXCEPT
        // writeTypeIdForDefaultImpl() which is deliberately stubbed to null.
        JsonTypeInfo sourceAnnotation = mock(JsonTypeInfo.class, CALLS_REAL_METHODS);
        doReturn(JsonTypeInfo.Id.SIMPLE_NAME).when(sourceAnnotation).use();
        doReturn(JsonTypeInfo.As.WRAPPER_OBJECT).when(sourceAnnotation).include();
        doReturn("$d$_(=!").when(sourceAnnotation).property();
        doReturn(Object.class).when(sourceAnnotation).defaultImpl();
        doReturn(true).when(sourceAnnotation).visible();
        doReturn(OptBoolean.TRUE).when(sourceAnnotation).requireTypeIdForSubtypes();
        doReturn((OptBoolean) null).when(sourceAnnotation).writeTypeIdForDefaultImpl();

        try {
            JsonTypeInfo.Value.from(sourceAnnotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // Exception originates from JsonTypeInfo.Value (no message expected).
            verifyException("com.fasterxml.jackson.annotation.JsonTypeInfo$Value", e);
        }
    }
}
