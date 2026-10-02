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
public class PosixParser_ESTest_test03 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        String[] argumentsWithAmbiguousToken = new String[38];
        argumentsWithAmbiguousToken[17] = "---";

        Options options = new Options();
        Option tripleDashLongOption = new Option(argumentsWithAmbiguousToken[27], "---", false, argumentsWithAmbiguousToken[1]);
        Options optionsWithTripleDash = options.addOption(tripleDashLongOption);
        options.addRequiredOption("nz6YwG7", "--", false, "---");

        PosixParser parser = new PosixParser();
        try {
            parser.parse(optionsWithTripleDash, argumentsWithAmbiguousToken);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Ambiguous option: '---'  (could be: '---', '--')
            //
            verifyException("org.apache.commons.cli.PosixParser", e);
        }
    }
}
