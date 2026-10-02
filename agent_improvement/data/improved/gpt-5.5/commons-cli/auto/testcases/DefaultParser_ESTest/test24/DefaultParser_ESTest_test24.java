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
public class DefaultParser_ESTest_test24 extends DefaultParser_ESTest_scaffolding {

    private static final boolean DISABLE_PARTIAL_MATCHING = false;
    private static final int ARGUMENT_COUNT = 9;
    private static final int UNRECOGNIZED_OPTION_INDEX = 1;
    private static final String UNRECOGNIZED_OPTION = "---fo=9EbdgIv{'d^Zj";

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        DefaultParser parser = new DefaultParser(DISABLE_PARTIAL_MATCHING);
        Options optionsWithoutDefinitions = new Options();
        String[] arguments = new String[ARGUMENT_COUNT];
        arguments[UNRECOGNIZED_OPTION_INDEX] = UNRECOGNIZED_OPTION;

        try {
            parser.parse(optionsWithoutDefinitions, arguments, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: ---fo=9EbdgIv{'d^Zj
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
