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

    /**
     * Verifies that a NodeIterator filtered to FormElement returns no results
     * when iterating a document that contains no form elements.
     */
    @Test(timeout = 4000)
    public void test_hasNext_returnsFalse_whenNoFormElementsExistInDocument() throws Throwable {
        // Parse a simple text string that produces no form elements
        Document parsedDocument = Parser.parse("kv3=Q", "");

        // Clone the document into a new element to serve as the iteration root
        Element clonedRootElement = parsedDocument.doClone(parsedDocument);

        // Create an iterator that only yields FormElement nodes
        Class<FormElement> formElementType = FormElement.class;
        NodeIterator<FormElement> formElementIterator = new NodeIterator<FormElement>(clonedRootElement, formElementType);

        // The document has no form elements, so hasNext() should be false
        boolean hasFormElement = formElementIterator.hasNext();
        assertFalse(hasFormElement);
    }
}
