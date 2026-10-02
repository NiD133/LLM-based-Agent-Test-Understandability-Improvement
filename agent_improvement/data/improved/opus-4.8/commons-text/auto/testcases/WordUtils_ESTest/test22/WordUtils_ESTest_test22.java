package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test22 extends WordUtils_ESTest_scaffolding {

    /**
     * containsAllWords returns false when the search array contains a null element.
     * Here only the first slot holds a word; the remaining three slots are null,
     * and a null (blank) search word causes the method to short-circuit to false.
     */
    @Test(timeout = 4000)
    public void containsAllWordsReturnsFalseWhenSearchArrayHasNullElement() throws Throwable {
        CharSequence[] searchWords = new CharSequence[4];
        searchWords[0] = "The validated object is null";
        // searchWords[1..3] remain null

        boolean allWordsFound =
            WordUtils.containsAllWords("The Validated Object Is Null", searchWords);

        assertFalse(allWordsFound);
    }
}
