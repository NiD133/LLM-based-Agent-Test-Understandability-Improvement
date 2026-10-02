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
        String[] stringArray0 = new String[38];
        stringArray0[17] = "---";
        Options options0 = new Options();
        Option option0 = new Option(stringArray0[27], "---", false, stringArray0[1]);
        Options options1 = options0.addOption(option0);
        options0.addRequiredOption("nz6YwG7", "--", false, "---");
        PosixParser posixParser0 = new PosixParser();
        try {
            posixParser0.parse(options1, stringArray0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Ambiguous option: '---'  (could be: '---', '--')
            //
            verifyException("org.apache.commons.cli.PosixParser", e);
        }
    }
}
