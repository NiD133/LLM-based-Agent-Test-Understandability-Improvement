package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test09 extends WordUtils_ESTest_scaffolding {

    // swapCase converts lowercase letters at the start or after whitespace to title case
    // and all other lowercase letters to uppercase; since all words here start with lowercase,
    // every character ends up uppercased.
    @Test(timeout = 4000)
    public void test_swapCase_allLowercase_convertsEachWordToUppercase() throws Throwable {
        String allLowercaseInput = "upper value is less than lower value";
        String result = WordUtils.swapCase(allLowercaseInput);
        assertEquals("UPPER VALUE IS LESS THAN LOWER VALUE", result);
    }
}
