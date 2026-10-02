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

    /**
     * The String-based constructor accepts any mapping string without validation,
     * so even an arbitrary string containing non-letter characters is accepted
     * and yields a usable instance.
     */
    @Test(timeout = 4000)
    public void constructorWithCustomMappingStringCreatesInstance() throws Throwable {
        String customMapping = "p)p5OA";

        RefinedSoundex refinedSoundex = new RefinedSoundex(customMapping);

        assertNotNull(refinedSoundex);
    }
}
