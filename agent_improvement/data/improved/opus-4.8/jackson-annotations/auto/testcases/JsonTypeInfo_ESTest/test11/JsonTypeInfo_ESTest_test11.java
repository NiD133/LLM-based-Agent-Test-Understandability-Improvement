package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test11 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@code toString()} reflects a default-implementation class set
     * via {@link JsonTypeInfo.Value#withDefaultImpl(Class)} while every other field
     * keeps the default value carried by {@link JsonTypeInfo.Value#EMPTY}.
     */
    @Test(timeout = 4000)
    public void toStringReportsDefaultImplWithOtherFieldsAtDefaults() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value valueWithDefaultImpl = emptyValue.withDefaultImpl(Object.class);

        String description = valueWithDefaultImpl.toString();
        assertEquals(
                "JsonTypeInfo.Value(idType=NONE,includeAs=NOTHING,propertyName=null,"
                        + "defaultImpl=java.lang.Object,idVisible=false,"
                        + "requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)",
                description);
    }
}
