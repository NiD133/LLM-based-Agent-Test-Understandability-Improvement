package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test24 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing an argument that looks like an option (starts with hyphens) but is
     * not defined in the {@link Options} must fail. With {@code stopAtNonOption}
     * set to {@code false}, the parser throws an exception for the unrecognized
     * token instead of treating it as a plain argument.
     */
    @Test(timeout = 4000)
    public void parseRejectsUnrecognizedOption() throws Throwable {
        DefaultParser parser = new DefaultParser(false);
        Options noOptionsDefined = new Options();

        // The second token is an option-like string that matches none of the
        // (empty) set of defined options.
        String[] arguments = new String[9];
        arguments[1] = "---fo=9EbdgIv{'d^Zj";

        boolean stopAtNonOption = false;
        try {
            parser.parse(noOptionsDefined, arguments, stopAtNonOption);
            fail("Expecting exception for unrecognized option: ---fo=9EbdgIv{'d^Zj");
        } catch (Exception e) {
            // Unrecognized option: ---fo=9EbdgIv{'d^Zj
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
