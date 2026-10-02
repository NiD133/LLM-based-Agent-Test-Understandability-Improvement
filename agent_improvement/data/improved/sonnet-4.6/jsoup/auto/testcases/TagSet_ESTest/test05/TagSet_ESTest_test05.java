package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test05 extends TagSet_ESTest_scaffolding {

    /**
     * "kbd" is a standard HTML inline tag defined in the default HTML TagSet,
     * so resolving it via Tag.valueOf should return a tag that is recognized as known.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Tag kbdTag = Tag.valueOf("kbd");
        assertTrue("'kbd' is a pre-defined HTML inline tag and should be recognized as a known tag",
            kbdTag.isKnownTag());
    }
}
