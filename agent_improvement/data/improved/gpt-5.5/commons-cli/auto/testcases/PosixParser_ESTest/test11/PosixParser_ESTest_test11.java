package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test11 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        PosixParser parser = new PosixParser();
        Options options = new Options();
        String[] emptyArguments = new String[6];
        Properties defaultProperties = new Properties();

        parser.parse(options, emptyArguments, defaultProperties, true);

        // Register the option after parsing so burstToken exercises the same parser state as the generated test.
        Option optionWithArgument = new Option("Z", true, "");
        options.addOption(optionWithArgument);

        parser.burstToken("wZ", true);
    }
}
