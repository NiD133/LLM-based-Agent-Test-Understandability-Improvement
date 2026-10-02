package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test16 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_setScalable_returnsFluentBuilderAndMaxWidthDefaultsToUnset() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Builder returnedBuilder = builder.setScalable(true);

        // setScalable returns the same builder instance (fluent API)
        assertTrue(builder.isScalable());

        // maxWidth defaults to Integer.MAX_VALUE (UNSET_MAX_WIDTH) and is unaffected by setScalable
        assertEquals(Integer.MAX_VALUE, returnedBuilder.getMaxWidth());
    }
}
