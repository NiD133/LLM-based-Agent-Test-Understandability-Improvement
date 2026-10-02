package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test28 extends JacksonInject_ESTest_scaffolding {

    /**
     * A Value created via {@link JacksonInject.Value#forId(Object)} with a
     * non-null id should report that it holds an id.
     */
    @Test(timeout = 4000)
    public void forIdWithNonNullId_reportsThatItHasId() throws Throwable {
        Object injectionId = new Object();

        JacksonInject.Value value = JacksonInject.Value.forId(injectionId);

        // Exercise the annotation-type accessor (always returns JacksonInject.class).
        value.valueFor();

        // The id we supplied is non-null, so the Value reports that it holds an id.
        assertTrue(value.hasId());
    }
}
