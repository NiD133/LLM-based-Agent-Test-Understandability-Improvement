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
     * Verifies that {@link OptionGroup#toString()} renders each option using its
     * long-option form (prefixed with "--") when the short option is {@code null}.
     */
    @Test(timeout = 4000)
    public void toStringUsesLongOptPrefixWhenShortOptIsNull() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        // Both options have a null short opt, so toString() falls back to the long opt.
        Option optionWithNullLongOpt = new Option((String) null, (String) null);
        optionGroup.addOption(optionWithNullLongOpt);

        Option optionWithBracketLongOpt = new Option((String) null, "[]", true, (String) null);
        optionGroup.addOption(optionWithBracketLongOpt);

        String rendered = optionGroup.toString();

        assertEquals("[--null, --[]]", rendered);
    }
}
