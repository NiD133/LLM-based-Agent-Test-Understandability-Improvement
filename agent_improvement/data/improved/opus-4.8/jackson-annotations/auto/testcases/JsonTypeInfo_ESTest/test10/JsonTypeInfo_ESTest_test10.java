package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test10 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that the default/empty {@link JsonTypeInfo.Value} renders all of its
     * fields with their default values in its {@code toString()} representation.
     */
    @Test(timeout = 4000)
    public void emptyValueToStringShowsAllDefaultFields() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        String description = emptyValue.toString();

        assertEquals(
                "JsonTypeInfo.Value(idType=NONE,includeAs=NOTHING,propertyName=null,defaultImpl=NULL,"
                        + "idVisible=false,requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)",
                description);
    }
}
