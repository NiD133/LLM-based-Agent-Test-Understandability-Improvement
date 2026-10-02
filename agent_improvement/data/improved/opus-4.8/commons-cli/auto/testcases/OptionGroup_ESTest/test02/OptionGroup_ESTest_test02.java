package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test02 extends OptionGroup_ESTest_scaffolding {

    /**
     * Verifies that toString() renders a group containing a single short option
     * as the option's key prefixed with "-" and wrapped in square brackets.
     */
    @Test(timeout = 4000)
    public void toStringRendersSingleShortOptionInBrackets() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();
        Option shortOptionWithoutDescription = new Option("tkgJ", (String) null);
        optionGroup.addOption(shortOptionWithoutDescription);

        String rendered = optionGroup.toString();

        assertEquals("[-tkgJ]", rendered);
    }
}
