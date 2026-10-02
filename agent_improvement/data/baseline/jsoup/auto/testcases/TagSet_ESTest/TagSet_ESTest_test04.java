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
public class TagSet_ESTest_test04 extends TagSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        TagSet tagSet0 = new TagSet();
        Tag tag0 = tagSet0.valueOf("v;;K-", "~m&2\"v*M>Y$C[<", "", false);
        assertEquals("~m&2\"v*M>Y$C[<", tag0.localName());
        Tag tag1 = tagSet0.valueOf("J", "~m&2\"v*M>Y$C[<", "", true);
        assertEquals("", tag1.namespace());
        assertEquals("~m&2\"v*M>Y$C[<", tag1.normalName());
        assertEquals("J", tag1.name());
    }
}
