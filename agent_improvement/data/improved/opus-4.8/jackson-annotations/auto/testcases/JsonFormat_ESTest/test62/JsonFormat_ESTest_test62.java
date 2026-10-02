package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test62 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonFormat.Shape#isStructured(JsonFormat.Shape)}
     * reports POJO as a structured shape (POJO maps to a JSON object).
     */
    @Test(timeout = 4000)
    public void isStructured_returnsTrue_forPojoShape() throws Throwable {
        boolean structured = JsonFormat.Shape.isStructured(JsonFormat.Shape.POJO);

        assertTrue("POJO should be reported as a structured shape", structured);
    }
}
