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

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        String[] arguments = new String[2];
        Properties defaultProperties = new Properties();
        defaultProperties.put("OYj", "OYj");

        DefaultParser parser = new DefaultParser();
        Options noDefinedOptions = new Options();

        try {
            parser.parse(noDefinedOptions, arguments, defaultProperties);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // The default property names option "OYj", but the Options instance is empty.
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
