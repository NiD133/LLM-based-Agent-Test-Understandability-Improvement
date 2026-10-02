package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test15 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_setMinWidth_persistsValueInBuilder() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        builder.setMinWidth(3329);
        assertEquals("Builder should return the minimum width that was set", 3329, builder.getMinWidth());
    }
}
