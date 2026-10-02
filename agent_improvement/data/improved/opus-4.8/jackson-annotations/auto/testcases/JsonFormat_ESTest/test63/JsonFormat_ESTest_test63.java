package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test63 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that the static {@code Shape.isStructured(Shape)} helper recognizes
     * the ARRAY shape as structured (as opposed to a scalar shape).
     */
    @Test(timeout = 4000)
    public void isStructured_returnsTrue_forArrayShape() throws Throwable {
        boolean structured = JsonFormat.Shape.isStructured(JsonFormat.Shape.ARRAY);

        assertTrue("ARRAY shape should be reported as structured", structured);
    }
}
