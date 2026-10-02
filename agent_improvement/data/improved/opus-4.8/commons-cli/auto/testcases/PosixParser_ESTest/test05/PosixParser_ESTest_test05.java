package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test05 extends PosixParser_ESTest_scaffolding {

    /**
     * Parsing an unknown long option ("--=") against an empty set of Options
     * must fail, because the parser does not recognize it. The thrown
     * exception originates from {@link org.apache.commons.cli.Parser}.
     */
    @Test(timeout = 4000)
    public void parsingUnrecognizedLongOptionThrowsException() throws Throwable {
        Options noKnownOptions = new Options();
        String[] arguments = { null, "--=" };
        PosixParser parser = new PosixParser();

        try {
            parser.parse(noKnownOptions, arguments);
            fail("Expected an exception for the unrecognized option: --=");
        } catch (Exception e) {
            // Unrecognized option: --=
            verifyException("org.apache.commons.cli.Parser", e);
        }
    }
}
