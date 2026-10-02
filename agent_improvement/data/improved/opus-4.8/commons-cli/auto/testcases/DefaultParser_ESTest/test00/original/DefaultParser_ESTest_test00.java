package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test00 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        DefaultParser defaultParser0 = new DefaultParser();
        Options options0 = new Options();
        OptionGroup optionGroup0 = new OptionGroup();
        Option option0 = new Option("s", "s");
        optionGroup0.setRequired(true);
        OptionGroup optionGroup1 = optionGroup0.addOption(option0);
        Options options1 = options0.addOptionGroup(optionGroup1);
        String[] stringArray0 = new String[4];
        stringArray0[0] = "-s";
        Properties properties0 = new Properties();
        defaultParser0.parse(options1, stringArray0, properties0, true);
        assertTrue(optionGroup1.isSelected());
        assertTrue(optionGroup0.isSelected());
    }
}
