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
public class JsonTypeInfo_ESTest_test00 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@code JsonTypeInfo.Value.from()} throws NullPointerException
     * when {@code writeTypeIdForDefaultImpl()} returns null.
     *
     * {@code Value.from()} unconditionally calls {@code .asBoolean()} on the result of
     * {@code writeTypeIdForDefaultImpl()}, so returning null causes a NullPointerException
     * inside {@code JsonTypeInfo$Value}.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.SIMPLE_NAME;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.WRAPPER_OBJECT;
        Class<Object> defaultImplClass = Object.class;
        OptBoolean requireTypeId = OptBoolean.TRUE;

        JsonTypeInfo annotation = mock(JsonTypeInfo.class, CALLS_REAL_METHODS);
        doReturn(defaultImplClass).when(annotation).defaultImpl();
        doReturn(inclusionType).when(annotation).include();
        doReturn("$d$_(=!").when(annotation).property();
        doReturn(requireTypeId).when(annotation).requireTypeIdForSubtypes();
        doReturn(idType).when(annotation).use();
        doReturn(true).when(annotation).visible();
        // Returning null here triggers NPE: Value.from() calls writeTypeIdForDefaultImpl().asBoolean()
        doReturn((OptBoolean) null).when(annotation).writeTypeIdForDefaultImpl();

        try {
            JsonTypeInfo.Value.from(annotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("com.fasterxml.jackson.annotation.JsonTypeInfo$Value", e);
        }
    }
}
