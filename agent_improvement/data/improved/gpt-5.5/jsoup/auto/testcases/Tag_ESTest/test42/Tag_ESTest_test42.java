package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test42 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test42() throws Throwable {
        String emptyTagName = "";
        String emptyNamespace = "";
        Tag emptyTag = new Tag(emptyTagName, emptyNamespace);

        String actualName = emptyTag.name();

        assertNotNull(actualName);
        assertFalse(emptyTag.isKnownTag());
    }
}
