package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test64 extends JsonFormat_ESTest_scaffolding {

    /**
     * SCALAR is explicitly a non-structural shape, so the static
     * {@link JsonFormat.Shape#isStructured(JsonFormat.Shape)} helper
     * should report it as not structured.
     */
    @Test(timeout = 4000)
    public void scalarShapeIsNotStructured() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;

        boolean structured = JsonFormat.Shape.isStructured(scalarShape);

        assertFalse(structured);
    }
}
