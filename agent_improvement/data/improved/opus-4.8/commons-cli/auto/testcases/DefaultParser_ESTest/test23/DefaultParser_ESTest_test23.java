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

    /**
     * When an argument token resolves to more than one possible long option, the
     * parser must reject it with an ambiguous-option failure.
     *
     * Two long options are registered whose names both begin with the same
     * leading-hyphen text ("-=R};SP'-"), so the token "-=R};SP'-" cannot be
     * matched to a single option and parsing fails.
     */
    @Test(timeout = 4000)
    public void parseAmbiguousLongOptionThrowsException() throws Throwable {
        final String ambiguousLongName = "-=R};SP'-";

        // Register two long options whose names collide on a common prefix.
        Options options = new Options();
        options.addOption("d", "d", false, ambiguousLongName);
        options.addRequiredOption("d", ambiguousLongName, false, "d");

        // Place the ambiguous token somewhere in the argument list.
        String[] arguments = new String[36];
        arguments[4] = ambiguousLongName;

        DefaultParser parser = new DefaultParser();
        try {
            parser.parse(options, arguments, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Ambiguous option: '-' (could be: 'd', '-=R};SP'-')
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
