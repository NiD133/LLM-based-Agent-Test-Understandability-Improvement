package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test08 extends OptionFormatter_ESTest_scaffolding {

    /**
     * setOptionalDelimiters should accept null delimiters and return the same
     * Builder instance so that configuration calls can be chained fluently.
     */
    @Test(timeout = 4000)
    public void setOptionalDelimitersWithNullsReturnsSameBuilder() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        OptionFormatter.Builder returnedBuilder = builder.setOptionalDelimiters((String) null, (String) null);

        assertSame(builder, returnedBuilder);
    }
}
