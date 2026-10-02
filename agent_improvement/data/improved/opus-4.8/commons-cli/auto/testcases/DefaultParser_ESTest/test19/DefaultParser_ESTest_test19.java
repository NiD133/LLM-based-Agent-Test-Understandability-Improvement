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
public class DefaultParser_ESTest_test19 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing must fail when the supplied default properties reference an option
     * that was never declared in the {@link Options}. Here the property key
     * "OYj" has no matching option, so {@code parse} is expected to throw an
     * exception ("Default option wasn't defined") from DefaultParser.
     */
    @Test(timeout = 4000)
    public void parseWithUndefinedDefaultPropertyThrowsException() throws Throwable {
        String[] noArguments = new String[2];

        Properties defaultProperties = new Properties();
        defaultProperties.put("OYj", "OYj");

        DefaultParser parser = new DefaultParser();
        Options optionsWithoutOYj = new Options();

        try {
            parser.parse(optionsWithoutOYj, noArguments, defaultProperties);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // "OYj" is not a defined option, so the default option wasn't defined.
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
