package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test01 extends OptionGroup_ESTest_scaffolding {

    /**
     * Verifies that {@link OptionGroup#toString()} renders an option that only has a
     * long option (no short option) using the long-option prefix ("--"), followed by
     * the option's description.
     *
     * Here the option is created with a null short name and a null long name, so its
     * key resolves to the literal "null"; after setting an empty description, the
     * rendered group is "[--null ]" (note the trailing space before the closing
     * bracket, which separates the long option from its empty description).
     */
    @Test(timeout = 4000)
    public void toStringRendersLongOptionWithDescription() throws Throwable {
        // Build an option with no short name and no long name, then give it an empty description.
        Option optionWithoutNames = new Option((String) null, (String) null);
        optionWithoutNames.setDescription("");

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(optionWithoutNames);

        String rendered = optionGroup.toString();

        assertEquals("[--null ]", rendered);
    }
}
