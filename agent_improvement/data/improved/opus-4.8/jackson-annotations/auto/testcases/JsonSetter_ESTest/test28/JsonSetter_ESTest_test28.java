package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test28 extends JsonSetter_ESTest_scaffolding {

    /**
     * A Value built only from a content-nulls setting should leave its
     * value-nulls at {@link Nulls#DEFAULT}, and must not be considered equal
     * to the EMPTY Value (which has both nulls set to DEFAULT).
     */
    @Test(timeout = 4000)
    public void contentNullsValueIsNotEqualToEmpty() throws Throwable {
        JsonSetter.Value contentNullsOnly = JsonSetter.Value.forContentNulls(Nulls.AS_EMPTY);
        JsonSetter.Value emptyValue = JsonSetter.Value.EMPTY;

        // value-nulls were never set, so they remain at the default
        assertEquals(Nulls.DEFAULT, contentNullsOnly.getValueNulls());

        // differs from EMPTY because its content-nulls is AS_EMPTY, not DEFAULT
        assertFalse(emptyValue.equals(contentNullsOnly));
    }
}
