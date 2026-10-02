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
public class OptionGroup_ESTest_test02 extends OptionGroup_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();
        Option shortOptionWithoutDescription = new Option("tkgJ", (String) null);

        optionGroup.addOption(shortOptionWithoutDescription);

        String groupDescription = optionGroup.toString();

        assertEquals("[-tkgJ]", groupDescription);
    }
}
