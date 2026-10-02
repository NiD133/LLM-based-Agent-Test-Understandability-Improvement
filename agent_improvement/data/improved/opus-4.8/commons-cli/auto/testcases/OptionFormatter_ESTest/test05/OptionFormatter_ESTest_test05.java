package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.cli.Option;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionFormatter_ESTest_test05 extends OptionFormatter_ESTest_scaffolding {

    /**
     * An option that takes an argument but has no name or description should be
     * rendered in syntax form using the default argument name "arg", wrapped in
     * the default delimiters ("<" and ">") and preceded by the opt/arg separator
     * (a single space). When rendered as required, no optional brackets are added.
     */
    @Test(timeout = 4000)
    public void requiredArgumentOptionUsesDefaultArgNameInSyntax() throws Throwable {
        // Option with no opt/long-opt, that takes an argument, with no description.
        Option optionWithArg = new Option(null, true, null);
        OptionFormatter formatter = OptionFormatter.from(optionWithArg);

        String syntax = formatter.toSyntaxOption(true);

        assertEquals(" <arg>", syntax);
    }
}
