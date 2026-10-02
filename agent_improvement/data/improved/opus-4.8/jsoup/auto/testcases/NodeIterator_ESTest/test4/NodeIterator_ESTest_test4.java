package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test4 extends NodeIterator_ESTest_scaffolding {

    /**
     * A NodeIterator filtered to FormElement nodes, starting from a plain Document
     * that contains no form elements, has nothing to yield. Calling next() on such
     * an empty iteration must therefore throw NoSuchElementException.
     */
    @Test(timeout = 4000)
    public void nextOnIteratorWithNoMatchingNodesThrows() throws Throwable {
        Document document = new Document("Gz-OTe\"24", "Wo8Lgsblf?");
        NodeIterator<FormElement> formElementIterator =
                new NodeIterator<FormElement>(document, FormElement.class);

        try {
            formElementIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // next() throws NoSuchElementException when no further node matches the filter type
            verifyException("org.jsoup.nodes.NodeIterator", e);
        }
    }
}
