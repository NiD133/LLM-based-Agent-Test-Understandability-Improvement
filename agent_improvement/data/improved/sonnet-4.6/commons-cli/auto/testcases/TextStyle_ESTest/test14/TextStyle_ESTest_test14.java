package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test14 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_builderDefaultValues_indentZeroScalableTrueMaxWidthUnset() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        int indent = builder.getIndent();
        assertTrue(builder.isScalable());
        assertEquals(0, indent);
        assertEquals(TextStyle.UNSET_MAX_WIDTH, builder.getMaxWidth());
    }
}
