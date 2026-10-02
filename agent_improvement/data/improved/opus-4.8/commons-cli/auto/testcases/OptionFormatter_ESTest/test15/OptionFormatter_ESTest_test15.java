package org.apache.commons.cli.help;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test15 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter.Builder#setOptSeparator(String)} returns the same
     * builder instance it was called on, confirming the builder's fluent (chainable) API.
     */
    @Test(timeout = 4000)
    public void setOptSeparatorReturnsSameBuilderForChaining() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        OptionFormatter.Builder returnedBuilder = builder.setOptSeparator("");

        assertSame(builder, returnedBuilder);
    }
}
