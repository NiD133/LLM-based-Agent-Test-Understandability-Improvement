package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test23 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        // Register two options: short "d" with long name "d", and a required option with long name "-=R};SP'-".
        // Because "-=R};SP'-" is itself a long option name starting with "-", passing it as a command-line
        // token causes the parser to extract "-" as the option prefix and find multiple partial matches,
        // resulting in an AmbiguousOptionException.
        Options options = new Options();
        options.addOption("d", "d", false, "-=R};SP'-");
        options.addRequiredOption("d", "-=R};SP'-", false, "d");

        DefaultParser parser = new DefaultParser();

        // Only index 4 is set; all other slots remain null and are skipped by the parser.
        String[] args = new String[36];
        args[4] = "-=R};SP'-";

        try {
            parser.parse(options, args, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Ambiguous option: '-'  (could be: 'd', '-=R};SP'-')
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
