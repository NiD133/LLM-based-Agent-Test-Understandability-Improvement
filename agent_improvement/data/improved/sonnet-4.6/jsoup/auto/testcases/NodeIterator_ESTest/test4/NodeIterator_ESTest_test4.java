package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test4 extends NodeIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // A document with no FormElement descendants: calling next() without a
        // matching node must throw NoSuchElementException.
        Document emptyDocument = new Document("Gz-OTe\"24", "Wo8Lgsblf?");
        NodeIterator<FormElement> formIterator = new NodeIterator<FormElement>(emptyDocument, FormElement.class);

        try {
            formIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.jsoup.nodes.NodeIterator", e);
        }
    }
}
