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
public class DefaultParser_ESTest_test28 extends DefaultParser_ESTest_scaffolding {

    /**
     * When stopAtNonOption is true and the first argument is an unrecognized token
     * containing special characters, the parser should stop gracefully and return
     * a non-null CommandLine rather than throwing an exception.
     */
    @Test(timeout = 4000)
    public void test28() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // First argument is an unrecognized option-like token with special characters;
        // remaining 13 elements are null (unused).
        String[] args = new String[14];
        args[0] = "-s-#\"J";

        // stopAtNonOption=true: upon encountering the unrecognized token the parser
        // stops early but must still return a valid CommandLine object.
        CommandLine result = parser.parse(emptyOptions, args, true);
        assertNotNull(result);
    }
}
