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
public class OptionGroup_ESTest_test04 extends OptionGroup_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        final OptionGroup optionGroup = new OptionGroup();
        final String noShortOption = null;
        final String longOption = "[]";
        final boolean hasArgument = true;
        final String noDescription = null;
        final Option selectedOption = new Option(noShortOption, longOption, hasArgument, noDescription);

        optionGroup.setSelected(selectedOption);
        optionGroup.setSelected(selectedOption);

        assertEquals('\u0000', selectedOption.getValueSeparator());
    }
}
