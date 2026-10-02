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
public class DefaultParser_ESTest_test31 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        Options emptyOptions = new Options();
        String[] argumentsWithUnrecognizedOption = new String[7];
        argumentsWithUnrecognizedOption[3] = "--kfS14:mS:6|k\u007fIq HbB";

        DefaultParser.Builder parserBuilder = DefaultParser.builder();
        DefaultParser.Builder parserBuilderWithoutPartialMatching = parserBuilder.setAllowPartialMatching(false);
        DefaultParser parser = parserBuilderWithoutPartialMatching.get();
        Properties defaultProperties = new Properties();

        try {
            parser.parse(emptyOptions, argumentsWithUnrecognizedOption, defaultProperties);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: --kfS14:mS:6|kIq HbB
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
