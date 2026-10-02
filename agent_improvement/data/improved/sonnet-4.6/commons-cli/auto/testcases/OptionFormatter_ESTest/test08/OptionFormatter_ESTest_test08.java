package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test08 extends OptionFormatter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08_setOptionalDelimitersWithNullsReturnsSameBuilderInstance() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        // setOptionalDelimiters should support method chaining by returning the same builder instance
        OptionFormatter.Builder returnedBuilder = builder.setOptionalDelimiters(null, null);

        assertSame(builder, returnedBuilder);
    }
}
