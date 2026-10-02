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
    public void test02_toStringFormatsShortOptionWithDashPrefixInBrackets() throws Throwable {
        OptionGroup group = new OptionGroup();
        // Option with short name "tkgJ" and no description
        Option shortOption = new Option("tkgJ", (String) null);
        group.addOption(shortOption);

        // toString() should prefix the short opt with "-" and wrap the list in "[]"
        String result = group.toString();
        assertEquals("[-tkgJ]", result);
    }
}
