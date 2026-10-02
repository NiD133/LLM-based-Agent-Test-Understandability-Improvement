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
public class JsonTypeInfo_ESTest_test31 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that when a null propertyName is passed to Value.construct(),
     * the resulting Value uses the Id's default property name ("@c" for MINIMAL_CLASS),
     * that idVisible is preserved, and that shouldWriteTypeIdForDefaultImpl() returns
     * true when writeTypeIdForDefaultImpl is null (null means "default to true").
     */
    @Test(timeout = 4000)
    public void test31() throws Throwable {
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.MINIMAL_CLASS;           // default property name is "@c"
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.NOTHING;
        Class<Integer> defaultImpl = Integer.class;
        Boolean requireTypeIdForSubtypes = Boolean.valueOf(false);
        Boolean writeTypeIdForDefaultImpl = (Boolean) null;               // null means "default to true"

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                idType,
                inclusionType,
                (String) null,                                            // null -> falls back to idType's default name
                defaultImpl,
                true,                                                     // idVisible
                requireTypeIdForSubtypes,
                writeTypeIdForDefaultImpl);

        assertEquals("@c", value.getPropertyName());                      // MINIMAL_CLASS default property name
        assertTrue(value.getIdVisible());
        assertTrue(value.shouldWriteTypeIdForDefaultImpl());              // null writeTypeIdForDefaultImpl defaults to true
    }
}
