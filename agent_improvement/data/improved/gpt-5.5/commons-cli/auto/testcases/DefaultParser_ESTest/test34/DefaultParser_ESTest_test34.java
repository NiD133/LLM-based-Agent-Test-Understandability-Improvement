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
public class DefaultParser_ESTest_test34 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test34() throws Throwable {
        Options optionsRequiringSArgument = new Options();
        DefaultParser parser = new DefaultParser();
        optionsRequiringSArgument.addOption("s", true, "s");

        String[] argumentsWithMissingSValue = new String[7];
        argumentsWithMissingSValue[0] = "-s";
        argumentsWithMissingSValue[2] = "-s#";

        try {
            parser.parse(optionsRequiringSArgument, argumentsWithMissingSValue, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing argument for option: s
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
