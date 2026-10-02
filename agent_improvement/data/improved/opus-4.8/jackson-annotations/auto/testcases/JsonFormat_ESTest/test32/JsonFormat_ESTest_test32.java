package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test32 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that applying a shape via {@code withShape(...)} sets that shape
     * on the resulting Value, while leaving the radix at its (unspecified) default.
     */
    @Test(timeout = 4000)
    public void withShape_setsShape_andLeavesRadixAtDefault() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        JsonFormat.Value valueWithBinaryShape = emptyValue.withShape(JsonFormat.Shape.BINARY);

        assertEquals(JsonFormat.Shape.BINARY, valueWithBinaryShape.getShape());
        assertFalse(valueWithBinaryShape.hasNonDefaultRadix());
    }
}
