package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test26 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // A tag created with empty name and empty namespace should have no parser options set
        // and should not be considered a known tag (not registered in any TagSet).
        Tag emptyTag = new Tag("", "");

        boolean hasRcDataParserOption = emptyTag.hasParserOption(Tag.RcData);

        assertFalse(emptyTag.isKnownTag());
        assertFalse(hasRcDataParserOption);
    }
}
