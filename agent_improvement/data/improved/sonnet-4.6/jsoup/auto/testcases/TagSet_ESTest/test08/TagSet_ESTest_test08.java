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
public class TagSet_ESTest_test08 extends TagSet_ESTest_scaffolding {

    /**
     * A TagSet copy constructed from an Html TagSet should equal the original,
     * because TagSet.equals compares only the tags map and both have an empty
     * tags map at construction time (tags are resolved lazily from the source).
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        TagSet originalTagSet = TagSet.Html();
        TagSet copiedTagSet = new TagSet(originalTagSet);
        assertTrue(copiedTagSet.equals((Object) originalTagSet));
    }
}
