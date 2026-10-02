package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test14 extends Options_ESTest_scaffolding {

    // getOptionGroup returns null when the option has not been added to any OptionGroup
    @Test(timeout = 4000)
    public void test_getOptionGroup_returnsNull_whenOptionNotInAnyGroup() throws Throwable {
        Options options = new Options();
        Option optionWithNullKey = new Option((String) null, true, (String) null);

        OptionGroup result = options.getOptionGroup(optionWithNullKey);

        assertNull(result);
    }
}
