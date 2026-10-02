package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test17 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void setLeftPad_storesTheValueAndGetLeftPadReturnsIt() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        builder.setLeftPad(1132);
        assertEquals(1132, builder.getLeftPad());
    }
}
