package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test11 extends OptionFormatter_ESTest_scaffolding {

    // Verifies that setDefaultArgName returns the same Builder instance (fluent API)
    @Test(timeout = 4000)
    public void test11_setDefaultArgName_emptyString_returnsSameBuilderInstance() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();
        OptionFormatter.Builder builderAfterSet = builder.setDefaultArgName("");
        assertSame(builder, builderAfterSet);
    }
}
