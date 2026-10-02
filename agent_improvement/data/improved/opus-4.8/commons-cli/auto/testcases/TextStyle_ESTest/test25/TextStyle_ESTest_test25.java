package org.apache.commons.cli.help;

import static org.junit.Assert.assertTrue;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test25 extends TextStyle_ESTest_scaffolding {

    /**
     * The DEFAULT TextStyle is built from a default Builder, whose scalable flag
     * defaults to {@code true}. Verify that DEFAULT therefore reports as scalable.
     */
    @Test(timeout = 4000)
    public void defaultTextStyleIsScalable() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;

        boolean scalable = defaultStyle.isScalable();

        assertTrue("DEFAULT TextStyle should be scalable", scalable);
    }
}
