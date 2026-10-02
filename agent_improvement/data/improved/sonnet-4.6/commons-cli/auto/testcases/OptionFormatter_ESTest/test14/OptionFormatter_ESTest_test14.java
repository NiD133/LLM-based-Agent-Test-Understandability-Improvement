package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test14 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter.Builder#get()} returns {@code null}.
     * <p>
     * The {@code Builder} implements {@link java.util.function.Supplier}, but its
     * {@code get()} method is an unimplemented stub that always returns {@code null}.
     * Creating a builder from an existing {@link OptionFormatter} does not change
     * this behaviour — calling {@code get()} still yields {@code null}.
     */
    @Test(timeout = 4000)
    public void test_builderGetReturnsNullWhenBuilderCopiedFromExistingFormatter() throws Throwable {
        Option option = new Option("arKg", "arKg");
        OptionFormatter sourceFormatter = OptionFormatter.from(option);

        OptionFormatter.Builder builder = new OptionFormatter.Builder(sourceFormatter);
        OptionFormatter result = builder.get();

        assertNull(result);
    }
}
