package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test5 extends RefinedSoundex_ESTest_scaffolding {

    // The String constructor accepts any character sequence as a mapping table,
    // including strings with non-alphabetic characters like "p)p5OA".
    @Test(timeout = 4000)
    public void test_constructorWithCustomMappingString_doesNotThrow() throws Throwable {
        RefinedSoundex refinedSoundex0 = new RefinedSoundex("p)p5OA");
    }
}
