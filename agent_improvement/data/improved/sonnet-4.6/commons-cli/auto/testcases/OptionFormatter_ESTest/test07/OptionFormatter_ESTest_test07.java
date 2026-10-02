package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test07 extends OptionFormatter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07_setOptArgSeparator_returnsTheSameBuilderInstance() throws Throwable {
        // Verify that setOptArgSeparator follows the fluent builder pattern by returning the same Builder instance
        OptionFormatter.Builder builder = OptionFormatter.builder();
        OptionFormatter.Builder builderAfterSetting = builder.setOptArgSeparator("");
        assertSame(builderAfterSetting, builder);
    }
}
