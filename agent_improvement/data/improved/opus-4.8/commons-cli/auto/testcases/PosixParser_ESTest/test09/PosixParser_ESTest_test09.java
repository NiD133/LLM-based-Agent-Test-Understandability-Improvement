package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test09 extends PosixParser_ESTest_scaffolding {

    /**
     * Flattening an argument list that contains a single unknown short-style token
     * (here "-Z&=") together with several null entries should yield exactly one
     * processed token.
     *
     * The null entries are ignored during flattening. The "-Z&=" token starts with
     * "-" and is longer than two characters, so it is "burst": its first character
     * after the hyphen ('Z') is not a known Option and, because stopAtNonOption is
     * false, the whole token is kept verbatim. That leaves a single flattened token.
     */
    @Test(timeout = 4000)
    public void flattenKeepsSingleUnknownTokenAndIgnoresNulls() throws Throwable {
        PosixParser parser = new PosixParser();
        Options noOptions = new Options();

        // Six-element array; only index 4 holds a token, the rest stay null.
        String[] arguments = new String[6];
        arguments[4] = "-Z&=";

        String[] flattened = parser.flatten(noOptions, arguments, false);

        assertEquals(1, flattened.length);
    }
}
