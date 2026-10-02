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
public class PosixParser_ESTest_test08 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that flatten bursts "-ZA" into ["-Z", "--", "A"] plus one trailing null
     * (from the 7-element array) when stopAtNonOption is true.
     *
     * "-Z" matches the registered option, so it is emitted as "-Z".
     * "A" does not match any option and stopAtNonOption is true, so "--" is
     * inserted before "A" and all remaining arguments are consumed as-is.
     * The sole remaining element (index 6) is null, giving a total of 4 tokens.
     */
    @Test(timeout = 4000)
    public void testFlattenBurstsKnownOptionFollowedByUnknownTokenWithStopAtNonOption() throws Throwable {
        PosixParser parser = new PosixParser();

        Options options = new Options();
        Option optionZ = new Option("Z", "Z");
        options.addOption(optionZ);

        // 7-element array; only index 5 carries a value — all others are null and are skipped
        String[] args = new String[7];
        args[5] = "-ZA";

        // Bursting "-ZA": "-Z" is a known option; "A" is unknown so, with
        // stopAtNonOption=true, "--" is prepended and the rest is consumed,
        // yielding ["-Z", "--", "A", null] — length 4
        String[] flattenedArgs = parser.flatten(options, args, true);
        assertEquals(4, flattenedArgs.length);
    }
}
