package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test02 extends JsonSetter_ESTest_scaffolding {

    /**
     * JsonSetter.Value.equals(null) must return false, as mandated by the
     * general contract of Object.equals.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForNull() throws Throwable {
        JsonSetter.Value emptyValue = JsonSetter.Value.EMPTY;

        boolean isEqualToNull = emptyValue.equals((Object) null);

        assertFalse(isEqualToNull);
    }
}
