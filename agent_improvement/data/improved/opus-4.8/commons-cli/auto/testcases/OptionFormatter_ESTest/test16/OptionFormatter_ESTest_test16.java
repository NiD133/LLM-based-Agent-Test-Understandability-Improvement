package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.function.Function;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test16 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter.Builder#setDeprecatedFormatFunction(Function)}
     * follows the fluent builder pattern by returning the same builder instance it was
     * called on, allowing method calls to be chained.
     */
    @Test(timeout = 4000)
    public void setDeprecatedFormatFunctionReturnsSameBuilderForChaining() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();
        Function<Option, String> deprecatedFormatFunction = OptionFormatter.NO_DEPRECATED_FORMAT;

        OptionFormatter.Builder returnedBuilder = builder.setDeprecatedFormatFunction(deprecatedFormatFunction);

        assertSame(builder, returnedBuilder);
    }
}
