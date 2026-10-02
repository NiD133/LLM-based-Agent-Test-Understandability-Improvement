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

    @Test(timeout = 4000)
    public void test_getDescription_returnsOptionDescription() throws Throwable {
        Option option = new Option("arg", "arg", false, "arg");
        OptionFormatter formatter = OptionFormatter.from(option);
        String description = formatter.getDescription();
        assertEquals("arg", description);
    }
}
