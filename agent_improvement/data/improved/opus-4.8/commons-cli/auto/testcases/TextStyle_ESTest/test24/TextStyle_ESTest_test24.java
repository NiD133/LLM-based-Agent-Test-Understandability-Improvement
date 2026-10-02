package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test24 extends TextStyle_ESTest_scaffolding {

    /**
     * The DEFAULT TextStyle should use LEFT alignment, matching the
     * Builder's documented default of {@code alignment = LEFT}.
     */
    @Test(timeout = 4000)
    public void defaultTextStyleUsesLeftAlignment() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;

        TextStyle.Alignment alignment = defaultStyle.getAlignment();

        assertEquals(TextStyle.Alignment.LEFT, alignment);
    }
}
