package org.apache.commons.text.diff;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringsComparator_ESTest_test1 extends StringsComparator_ESTest_scaffolding {

    /**
     * Comparing two very different strings should yield an edit script whose
     * modification count reflects every delete and insert required to turn the
     * left string into the right one. Only the few characters they share (such
     * as the trailing 'P') are kept, so almost all positions count as
     * modifications.
     */
    @Test(timeout = 4000)
    public void scriptCountsAllModificationsBetweenDissimilarStrings() throws Throwable {
        String left = "yU3P";
        String right = "org.apache.commons.text.diff.EditScript";

        StringsComparator comparator = new StringsComparator(left, right);
        EditScript<Character> editScript = comparator.getScript();

        assertEquals(43, editScript.getModifications());
    }
}
