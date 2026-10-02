package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test19 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        Tag tag0 = new Tag("'k7*}2H9fkf;cif_ ", "'k7*}2H9fkf;cif_ ");
        boolean boolean0 = tag0.isInline();
        assertEquals("'k7*}2H9fkf;cif_ ", tag0.namespace());
        assertEquals("'k7*}2h9fkf;cif_", tag0.normalName());
        assertFalse(tag0.isKnownTag());
        assertTrue(boolean0);
    }
}
