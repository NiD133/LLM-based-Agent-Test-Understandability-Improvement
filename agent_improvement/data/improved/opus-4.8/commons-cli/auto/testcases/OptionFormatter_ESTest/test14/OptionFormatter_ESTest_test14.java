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
     *
     * The builder's {@code get()} method is an unimplemented {@link java.util.function.Supplier}
     * stub that always returns {@code null}, regardless of how the builder was created.
     * Here the builder is seeded from an existing OptionFormatter, yet {@code get()} still
     * yields {@code null}.
     */
    @Test(timeout = 4000)
    public void getReturnsNull() throws Throwable {
        Option option = new Option("arKg", "arKg");
        OptionFormatter sourceFormatter = OptionFormatter.from(option);
        OptionFormatter.Builder builder = new OptionFormatter.Builder(sourceFormatter);

        OptionFormatter result = builder.get();

        assertNull(result);
    }
}
