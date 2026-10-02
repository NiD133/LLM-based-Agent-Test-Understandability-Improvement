package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test83 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test83_forRadixCreatesValueWithSpecifiedRadixAndNoExplicitShape() throws Throwable {
        JsonFormat.Value formatValue = JsonFormat.Value.forRadix(1);

        int radix = formatValue.getRadix();
        assertEquals(1, radix);
        assertFalse(formatValue.hasShape());
    }
}
