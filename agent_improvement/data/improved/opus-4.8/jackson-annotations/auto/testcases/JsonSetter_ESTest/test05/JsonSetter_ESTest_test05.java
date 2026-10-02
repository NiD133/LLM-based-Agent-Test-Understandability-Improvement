package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test05 extends JsonSetter_ESTest_scaffolding {

    /**
     * The EMPTY value has both null-handling settings left at {@link Nulls#DEFAULT}.
     * Since contentNulls is DEFAULT, nonDefaultContentNulls() should report "no
     * explicit setting" by returning null rather than the DEFAULT constant.
     */
    @Test(timeout = 4000)
    public void nonDefaultContentNulls_returnsNull_whenContentNullsIsDefault() throws Throwable {
        JsonSetter.Value emptyValue = JsonSetter.Value.EMPTY;

        Nulls contentNulls = emptyValue.nonDefaultContentNulls();

        assertNull(contentNulls);
    }
}
