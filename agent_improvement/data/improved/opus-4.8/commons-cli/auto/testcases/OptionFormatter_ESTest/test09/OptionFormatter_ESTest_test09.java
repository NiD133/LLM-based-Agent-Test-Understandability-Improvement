package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test09 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter.Builder#setArgumentNameDelimiters(String, String)}
     * supports a fluent API by returning the same builder instance it was called on.
     */
    @Test(timeout = 4000)
    public void setArgumentNameDelimitersReturnsSameBuilderInstance() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        OptionFormatter.Builder returnedBuilder = builder.setArgumentNameDelimiters("", "");

        assertSame(builder, returnedBuilder);
    }
}
