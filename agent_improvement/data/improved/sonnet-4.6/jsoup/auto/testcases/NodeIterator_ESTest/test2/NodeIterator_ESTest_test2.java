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
     * Verifies that forEachRemaining completes without error when iterating a document
     * that has one child element appended to it, using a mock consumer.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Create a document and add one child element to it
        Document document = new Document("~I@)ny9j(:24`r@$", "org.jsoup.nodes.NodeIterator");
        document.appendElement("~I@)ny9j(:24`r@$");

        // Build an iterator that will traverse the document and all its descendants
        NodeIterator<Node> iterator = NodeIterator.from(document);

        // Use a mock consumer and confirm forEachRemaining traverses the tree without throwing
        Consumer<Object> consumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        iterator.forEachRemaining(consumer);
    }
}
