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
public class NodeIterator_ESTest_test1 extends NodeIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Parse a plain-text HTML string — the resulting document contains no <form> elements
        Document parsedDocument = Parser.parse("kv3=Q", "");
        // Clone the document within itself, producing an Element rooted in the same document
        Element clonedElement = parsedDocument.doClone(parsedDocument);

        // Build an iterator that only visits FormElement nodes within the cloned tree
        NodeIterator<FormElement> formElementIterator =
                new NodeIterator<FormElement>(clonedElement, FormElement.class);

        // Because the document has no <form> elements, the iterator should have nothing to return
        boolean hasFormElements = formElementIterator.hasNext();
        assertFalse(hasFormElements);
    }
}
