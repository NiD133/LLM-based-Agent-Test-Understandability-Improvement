package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test18 extends OptionFormatter_ESTest_scaffolding {

    /**
     * An Option with neither a short opt name nor a description produces an empty
     * syntax string: there is no opt token to render and, being optional with no
     * content, the result wraps to an empty string.
     */
    @Test(timeout = 4000)
    public void toSyntaxOptionReturnsEmptyForOptionWithoutNameOrDescription() throws Throwable {
        Option optionWithNoNameOrDescription = new Option(null, null);
        OptionFormatter formatter = OptionFormatter.from(optionWithNoNameOrDescription);

        String syntax = formatter.toSyntaxOption();

        assertEquals("", syntax);
    }
}
