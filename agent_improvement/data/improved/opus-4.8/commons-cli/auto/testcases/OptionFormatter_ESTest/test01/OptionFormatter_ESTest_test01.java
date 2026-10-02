package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test01 extends OptionFormatter_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionFormatter#getDescription()} returns the
     * description of the underlying option verbatim when the option is not
     * deprecated.
     */
    @Test(timeout = 4000)
    public void getDescription_returnsOptionDescription_whenNotDeprecated() throws Throwable {
        // Build an (non-deprecated) option whose description is "arg".
        Option option = new Option("arg", "arg", false, "arg");
        OptionFormatter formatter = OptionFormatter.from(option);

        String description = formatter.getDescription();

        assertEquals("arg", description);
    }
}
