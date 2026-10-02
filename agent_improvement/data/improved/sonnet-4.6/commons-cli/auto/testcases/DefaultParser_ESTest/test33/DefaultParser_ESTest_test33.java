package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test33 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that DefaultParser.Builder.setDeprecatedHandler() returns the same builder
     * instance, enabling fluent (method-chaining) configuration.
     */
    @Test(timeout = 4000)
    public void test_setDeprecatedHandler_returnsTheSameBuilderInstance() throws Throwable {
        DefaultParser.Builder builder = DefaultParser.builder();
        Consumer<Option> deprecatedHandler = CommandLine.Builder.DEPRECATED_HANDLER;

        DefaultParser.Builder builderAfterSetting = builder.setDeprecatedHandler(deprecatedHandler);

        assertSame(builderAfterSetting, builder);
    }
}
