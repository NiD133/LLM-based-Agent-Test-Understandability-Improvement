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
public class NodeIterator_ESTest_test2 extends NodeIterator_ESTest_scaffolding {

    /**
     * Verifies that forEachRemaining on a NodeIterator traverses a document
     * with one child element without throwing an exception.
     */
    @Test(timeout = 4000)
    public void test_forEachRemaining_traversesDocumentWithChildElement() throws Throwable {
        // Build a document with a single child element
        Document document = new Document("~I@)ny9j(:24`r@$", "org.jsoup.nodes.NodeIterator");
        document.appendElement("~I@)ny9j(:24`r@$");

        // Create an iterator that visits all nodes in the document tree
        NodeIterator<Node> nodeIterator = NodeIterator.from(document);

        // Use a mock consumer to absorb each node visit without side effects
        Consumer<Object> mockConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        nodeIterator.forEachRemaining(mockConsumer);
    }
}
