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
public class TagSet_ESTest_test09 extends TagSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        TagSet tagSet0 = new TagSet();
        Tag tag0 = tagSet0.valueOf("~m&2\"v*M>Y$C[<", "~m&2\"v*M>Y$C[<");
        assertEquals("~m&2\"v*m>y$c[<", tag0.normalName());
        assertEquals("~m&2\"v*M>Y$C[<", tag0.toString());
        assertEquals("~m&2\"v*M>Y$C[<", tag0.namespace());
        assertNotNull(tag0);
        TagSet tagSet1 = new TagSet(tagSet0);
        assertTrue(tagSet1.equals((Object) tagSet0));
    }
}
