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
public class DefaultParser_ESTest_test31 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing an argument that looks like a long option ("--...") but is not
     * declared in the (empty) {@link Options} must fail with an
     * {@link UnrecognizedOptionException} thrown from {@link DefaultParser}.
     */
    @Test(timeout = 4000)
    public void parsingUndeclaredLongOptionThrowsUnrecognizedOption() throws Throwable {
        Options emptyOptions = new Options();

        // Only one of the seven arguments is a (long-looking) option token;
        // the rest are null and are skipped during parsing.
        String[] arguments = new String[7];
        String unrecognizedLongOption = "--kfS14:mS:6|kIq HbB";
        arguments[3] = unrecognizedLongOption;

        DefaultParser parser = DefaultParser.builder()
                .setAllowPartialMatching(false)
                .get();
        Properties noProperties = new Properties();

        try {
            parser.parse(emptyOptions, arguments, noProperties);
            fail("Expected an exception for unrecognized option: " + unrecognizedLongOption);
        } catch (Exception e) {
            // Unrecognized option: --kfS14:mS:6|kIq HbB
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
