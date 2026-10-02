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
public class OptionGroup_ESTest_test01 extends OptionGroup_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        OptionGroup optionGroup0 = new OptionGroup();
        Option option0 = new Option((String) null, (String) null);
        optionGroup0.addOption(option0);
        option0.setDescription("");
        String string0 = optionGroup0.toString();
        assertEquals("[--null ]", string0);
    }
}
