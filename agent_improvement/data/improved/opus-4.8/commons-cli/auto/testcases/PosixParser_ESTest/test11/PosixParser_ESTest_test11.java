package org.apache.commons.cli;

import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;

import java.util.Properties;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test11 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that {@link PosixParser#burstToken(String, boolean)} can be invoked after a
     * previous parse has initialized the parser's {@code options} field.
     *
     * <p>The flow is:</p>
     * <ol>
     *   <li>Parse an (empty) argument set so the parser's internal {@code options} reference is
     *       populated (otherwise {@code burstToken} would dereference {@code null}).</li>
     *   <li>Register an option {@code "Z"} that accepts an argument.</li>
     *   <li>Burst the token {@code "wZ"}: bursting skips the first character {@code 'w'}, which is
     *       not a registered option, so with {@code stopAtNonOption == true} the remainder is
     *       handled as a non-option token and bursting stops.</li>
     * </ol>
     */
    @Test(timeout = 4000)
    public void burstTokenAfterParseHandlesUnknownLeadingCharacter() throws Throwable {
        PosixParser parser = new PosixParser();
        Options options = new Options();

        // Six null arguments; parsing them initializes the parser's options reference.
        String[] arguments = new String[6];
        Properties resolverProperties = new Properties();
        boolean stopAtNonOption = true;
        parser.parse(options, arguments, resolverProperties, stopAtNonOption);

        // Register option "Z" which requires an argument value.
        Option optionZ = new Option("Z", true, "");
        options.addOption(optionZ);

        // Burst "wZ": 'w' is not a known option, so bursting stops at the non-option character.
        parser.burstToken("wZ", stopAtNonOption);
    }
}
