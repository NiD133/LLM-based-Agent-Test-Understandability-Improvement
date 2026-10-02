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
public class Options_ESTest_test06 extends Options_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Options options = new Options();
        String shortOption = "j";
        String longOptionWithDeleteCharacter = "N\u007FR1W*T";

        options.addRequiredOption(shortOption, longOptionWithDeleteCharacter, false, shortOption);
        List<String> matchesForEmptyPrefix = options.getMatchingOptions("");

        assertFalse(matchesForEmptyPrefix.contains(""));
        assertFalse(matchesForEmptyPrefix.isEmpty());
    }
}
