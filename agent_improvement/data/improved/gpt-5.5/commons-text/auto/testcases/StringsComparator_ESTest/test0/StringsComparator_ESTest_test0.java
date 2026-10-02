package org.apache.commons.text.diff;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringsComparator_ESTest_test0 extends StringsComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testScriptModificationCountForDifferentClassNameStrings() throws Throwable {
        String leftClassName = "org.apache.commons.text.diff.StringsComparator";
        String rightClassName = "org.apache.commons.text.diff.DeleteCommand";

        StringsComparator comparator = new StringsComparator(leftClassName, rightClassName);
        EditScript<Character> editScript = comparator.getScript();

        assertEquals(20, editScript.getModifications());
    }
}
