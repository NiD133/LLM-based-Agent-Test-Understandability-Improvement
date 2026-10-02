package org.apache.commons.text.diff;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringsComparator_ESTest_test0 extends StringsComparator_ESTest_scaffolding {

    /**
     * Verifies that the edit script transforming one string into another reports
     * the expected number of modifications (insert/delete commands, excluding kept
     * characters).
     */
    @Test(timeout = 4000)
    public void getScriptReportsExpectedNumberOfModifications() throws Throwable {
        String leftSequence = "org.apache.commons.text.diff.StringsComparator";
        String rightSequence = "org.apache.commons.text.diff.DeleteCommand";

        StringsComparator comparator = new StringsComparator(leftSequence, rightSequence);
        EditScript<Character> editScript = comparator.getScript();

        assertEquals(20, editScript.getModifications());
    }
}
