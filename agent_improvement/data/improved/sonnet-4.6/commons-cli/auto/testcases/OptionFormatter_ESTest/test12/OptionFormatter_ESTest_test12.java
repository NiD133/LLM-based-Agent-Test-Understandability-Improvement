package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test12 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that Builder.setLongOptPrefix() returns the same Builder instance,
     * confirming the fluent (method-chaining) API contract.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Arrange: create an option and derive a formatter and builder from it
        Option option = new Option("arg", "arg", false, "arg");
        OptionFormatter formatter = OptionFormatter.from(option);
        OptionFormatter.Builder builder = new OptionFormatter.Builder(formatter);

        // Act: set the long option prefix on the builder
        OptionFormatter.Builder returnedBuilder = builder.setLongOptPrefix("--");

        // Assert: the builder returns itself to support method chaining
        assertSame(builder, returnedBuilder);
    }
}
