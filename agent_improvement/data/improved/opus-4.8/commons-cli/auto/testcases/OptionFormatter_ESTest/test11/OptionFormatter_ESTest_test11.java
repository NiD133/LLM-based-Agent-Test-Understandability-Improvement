package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test11 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter.Builder#setDefaultArgName(String)} follows the
     * fluent-builder contract by returning the same builder instance it was called on,
     * allowing setter calls to be chained.
     */
    @Test(timeout = 4000)
    public void setDefaultArgNameReturnsSameBuilderInstance() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        OptionFormatter.Builder returnedBuilder = builder.setDefaultArgName("");

        assertSame(builder, returnedBuilder);
    }
}
