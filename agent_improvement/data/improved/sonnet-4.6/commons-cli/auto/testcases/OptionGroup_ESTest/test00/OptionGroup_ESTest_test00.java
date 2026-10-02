package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test00 extends OptionGroup_ESTest_scaffolding {

    /**
     * Verifies that toString() formats a group of options with null opt names correctly.
     *
     * When an Option has no short name (opt == null), toString() falls back to using
     * the long option prefix ("--") followed by the long opt value.
     * The two options added here both have null short names, so the expected string
     * uses "--null" for the first (null long-opt) and "--[]" for the second ("[]" long-opt).
     */
    @Test(timeout = 4000)
    public void test00_toStringWithNullOptNamesFallsBackToLongOptPrefix() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        // Option with both short name (opt) and long name (longOpt) set to null
        Option optionWithNullNames = new Option((String) null, (String) null);
        optionGroup.addOption(optionWithNullNames);

        // Option with null short name, "[]" as long name, accepting an argument, and null description
        Option optionWithBracketLongName = new Option((String) null, "[]", true, (String) null);
        optionGroup.addOption(optionWithBracketLongName);

        String result = optionGroup.toString();

        assertEquals("[--null, --[]]", result);
    }
}
