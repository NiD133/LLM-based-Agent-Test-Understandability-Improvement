package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest_test01 extends OptionGroup_ESTest_scaffolding {

    /**
     * Verifies that OptionGroup.toString() renders an option with a null short-opt
     * (triggering the long-opt prefix "--") and a null long-opt (printed as "null"),
     * followed by a space when the description is an empty string (non-null).
     *
     * Expected output: "[--null ]"
     *   - "[" / "]"     : group delimiters
     *   - "--"          : DEFAULT_LONG_OPT_PREFIX, used because getOpt() == null
     *   - "null"        : getLongOpt() returns null, rendered via String.valueOf as "null"
     *   - " "           : space separator before description (added because description != null)
     *   - ""            : the empty description itself
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        OptionGroup optionGroup = new OptionGroup();

        // Option created with null short-opt and null long-opt; description defaults to null
        Option optionWithNullOpts = new Option((String) null, (String) null);
        optionGroup.addOption(optionWithNullOpts);

        // Setting description to empty string (not null) causes a trailing space in toString()
        optionWithNullOpts.setDescription("");

        String result = optionGroup.toString();

        assertEquals("[--null ]", result);
    }
}
