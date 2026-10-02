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
public class DefaultParser_ESTest_test29 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test29() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options options = new Options();
        Options optionsWithShortS = options.addOption("s", true, "-s");
        String[] arguments = new String[5];

        parser.parse(optionsWithShortS, arguments, true);
        parser.handleConcatenatedOptions("-s");
    }
}
