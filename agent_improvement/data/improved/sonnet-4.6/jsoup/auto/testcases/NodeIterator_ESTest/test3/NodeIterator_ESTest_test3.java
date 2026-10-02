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
public class NodeIterator_ESTest_test3 extends NodeIterator_ESTest_scaffolding {

    /**
     * Verifies that calling remove() on the iterator's current node (removing it from the tree)
     * and then calling forEachRemaining() completes without error when no matching nodes remain.
     * The iterator detects the structural change (removed current node) and recovers gracefully.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Build a document with a single child element to serve as the iterator's start node
        Document doc = new Document("Gz-OTe\"24", "Wo8Lgsblf?");
        Element startElement = doc.appendElement("org.jsoup.nodes.NodeIterator");

        // Create an iterator that filters for FormElement nodes, starting at the child element
        Class<FormElement> formElementClass = FormElement.class;
        NodeIterator<FormElement> iterator = new NodeIterator<FormElement>(startElement, formElementClass);

        // Remove the current node (the start element) from the document tree mid-iteration
        iterator.remove();

        // After the structural removal, forEachRemaining should detect there are no matching
        // nodes left and invoke the consumer zero times without throwing
        Consumer<Object> mockConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        iterator.forEachRemaining(mockConsumer);
    }
}
