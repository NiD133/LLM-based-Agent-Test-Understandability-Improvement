package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test06 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that building a new OptionFormatter from a copy of an existing formatter's
     * Builder produces a distinct object, not the same instance.
     */
    @Test(timeout = 4000)
    public void test06_builderFromExistingFormatterProducesNewInstance() throws Throwable {
        // Create an option with short opt "arKg" and description "arKg"
        Option option = new Option("arKg", "arKg");

        // Build a formatter using the default settings
        OptionFormatter originalFormatter = OptionFormatter.from(option);

        // Copy the formatter's settings into a new Builder and build a second formatter
        OptionFormatter.Builder builderFromCopy = new OptionFormatter.Builder(originalFormatter);
        OptionFormatter newFormatter = builderFromCopy.build(option);

        // The two formatters should be separate instances even though they share the same settings
        assertNotSame(originalFormatter, newFormatter);
    }
}
