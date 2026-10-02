package org.apache.commons.text.diff;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringsComparator_ESTest_test1 extends StringsComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_getScript_shortStringVsLongString_returnsExpectedModificationCount() throws Throwable {
        // Comparing a short string against a long, dissimilar string produces many modifications.
        // The two strings share no common characters in positions that would reduce the edit distance,
        // so the modification count equals the combined length of both unique portions (43).
        String shortInput = "yU3P";
        String longInput = "org.apache.commons.text.diff.EditScript";
        StringsComparator comparator = new StringsComparator(shortInput, longInput);

        EditScript<Character> editScript = comparator.getScript();

        assertEquals(43, editScript.getModifications());
    }
}
