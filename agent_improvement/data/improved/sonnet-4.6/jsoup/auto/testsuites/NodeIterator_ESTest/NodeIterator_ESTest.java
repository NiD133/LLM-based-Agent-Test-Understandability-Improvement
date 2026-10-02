/*
 * Improved version of the EvoSuite-generated NodeIterator test.
 * Original was auto-generated; this version retains identical runtime
 * behaviour while using descriptive names and comments for clarity.
 */

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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeIterator;
import org.jsoup.parser.Parser;

import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(
    mockJVMNonDeterminism = true,
    useVFS = true,
    useVNET = true,
    resetStaticState = true,
    separateClassLoader = false
)
public class NodeIterator_ESTest extends NodeIterator_ESTest_scaffolding {

    /**
     * A document parsed from arbitrary text and then extended with an appended
     * element contains no FormElement nodes, so hasNext() must return false
     * when the iterator is typed to FormElement.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        Document document = Parser.parse("g==i<Tb", "g==i<Tb");
        document.appendElement("g==i<Tb");

        NodeIterator<FormElement> iterator = new NodeIterator<>(document, FormElement.class);

        boolean hasFormElement = iterator.hasNext();
        assertFalse(hasFormElement);
    }

    /**
     * Cloning a document (using itself as the parent context) produces an
     * Element tree that still contains no FormElement nodes, so a
     * FormElement-typed iterator reports hasNext() == false.
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Document document = Parser.parse("kv3=Q", "");
        // doClone with the document as its own parent creates a structural clone
        Element clonedElement = document.doClone(document);

        NodeIterator<FormElement> iterator = new NodeIterator<>(clonedElement, FormElement.class);

        boolean hasFormElement = iterator.hasNext();
        assertFalse(hasFormElement);
    }

    /**
     * NodeIterator.from() creates an untyped (Node) iterator over the whole
     * subtree. Calling forEachRemaining() on a document that has one child
     * element visits every node; the mock consumer silently absorbs each call.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        Document document = new Document("~I@)ny9j(:24`r@$", "org.jsoup.nodes.NodeIterator");
        document.appendElement("~I@)ny9j(:24`r@$");

        NodeIterator<Node> iterator = NodeIterator.from(document);

        @SuppressWarnings("unchecked")
        Consumer<Object> consumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        iterator.forEachRemaining(consumer);
    }

    /**
     * Calling remove() before any next() removes the iterator's current node
     * (which is the start node). After that removal, forEachRemaining() finds
     * no FormElement descendants and the mock consumer is never invoked.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        Document document = new Document("Gz-OTe\"24", "Wo8Lgsblf?");
        Element element = document.appendElement("org.jsoup.nodes.NodeIterator");

        NodeIterator<FormElement> iterator = new NodeIterator<>(element, FormElement.class);
        // remove() acts on the current node (the start element) before any next() call
        iterator.remove();

        @SuppressWarnings("unchecked")
        Consumer<Object> consumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        iterator.forEachRemaining(consumer);
    }

    /**
     * Calling next() on an iterator whose document contains no FormElement
     * nodes must throw NoSuchElementException immediately.
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        Document document = new Document("Gz-OTe\"24", "Wo8Lgsblf?");

        NodeIterator<FormElement> iterator = new NodeIterator<>(document, FormElement.class);

        try {
            iterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.jsoup.nodes.NodeIterator", e);
        }
    }
}
