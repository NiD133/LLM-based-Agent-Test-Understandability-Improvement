package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test33 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that {@link DefaultParser.Builder#setDeprecatedHandler(Consumer)}
     * follows the fluent builder contract by returning the same builder instance,
     * allowing calls to be chained.
     */
    @Test(timeout = 4000)
    public void setDeprecatedHandlerReturnsSameBuilderForChaining() throws Throwable {
        DefaultParser.Builder builder = DefaultParser.builder();
        Consumer<Option> deprecatedHandler = CommandLine.Builder.DEPRECATED_HANDLER;

        DefaultParser.Builder returnedBuilder = builder.setDeprecatedHandler(deprecatedHandler);

        assertSame(builder, returnedBuilder);
    }
}
