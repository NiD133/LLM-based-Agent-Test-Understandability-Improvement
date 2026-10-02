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
public class DefaultParser_ESTest_test19 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing fails when the default properties contain a key
     * ("OYj") that has no matching option defined in the Options object.
     * DefaultParser.handleProperties() throws UnrecognizedOptionException
     * with the message "Default option wasn't defined" in this case.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // Two null entries act as the command-line arguments (neither triggers a parse error on its own)
        String[] args = new String[2];

        // Property key "OYj" is intentionally absent from the Options below
        Properties defaultProperties = new Properties();
        defaultProperties.put("OYj", "OYj");

        DefaultParser parser = new DefaultParser();
        Options options = new Options(); // no options defined, so "OYj" is unrecognised

        try {
            parser.parse(options, args, defaultProperties);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Default option wasn't defined
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
