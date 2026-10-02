package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test65 extends JsonFormat_ESTest_scaffolding {

    /**
     * The OBJECT shape is one of the structured shapes (OBJECT, ARRAY, POJO),
     * so the static {@code isStructured} check should report it as structured.
     */
    @Test(timeout = 4000)
    public void isStructured_forObjectShape_returnsTrue() throws Throwable {
        boolean structured = JsonFormat.Shape.isStructured(JsonFormat.Shape.OBJECT);

        assertTrue("OBJECT shape should be considered structured", structured);
    }
}
