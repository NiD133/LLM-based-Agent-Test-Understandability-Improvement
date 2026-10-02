package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test10 extends OptionFormatter_ESTest_scaffolding {

    /**
     * A builder with default settings uses '<' and '>' as the argument name
     * delimiters, so formatting an argument name simply wraps it in those
     * delimiters.
     */
    @Test(timeout = 4000)
    public void toArgName_withDefaultDelimiters_wrapsArgNameInAngleBrackets() throws Throwable {
        OptionFormatter.Builder builder = OptionFormatter.builder();

        String formattedArgName = builder.toArgName(" ");

        assertEquals("< >", formattedArgName);
    }
}
