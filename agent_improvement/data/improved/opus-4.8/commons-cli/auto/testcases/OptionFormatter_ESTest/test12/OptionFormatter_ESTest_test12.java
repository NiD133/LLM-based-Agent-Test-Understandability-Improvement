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
     * Verifies that {@link OptionFormatter.Builder#setLongOptPrefix(String)} is a
     * fluent setter: it returns the very same Builder instance it was called on,
     * allowing setter calls to be chained.
     */
    @Test(timeout = 4000)
    public void setLongOptPrefixReturnsSameBuilderInstance() throws Throwable {
        Option option = new Option("arg", "arg", false, "arg");
        OptionFormatter formatter = OptionFormatter.from(option);
        OptionFormatter.Builder builder = new OptionFormatter.Builder(formatter);

        OptionFormatter.Builder returnedBuilder = builder.setLongOptPrefix("--");

        assertSame(builder, returnedBuilder);
    }
}
