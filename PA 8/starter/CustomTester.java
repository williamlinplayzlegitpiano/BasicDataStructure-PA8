import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomTester {
    
    MyBST<Integer, Integer> tree;

    @Before
    public void setup() {
        MyBST.MyBSTNode<Integer, Integer> root =
                new MyBST.MyBSTNode<>(4, 1, null);
        MyBST.MyBSTNode<Integer, Integer> two =
                new MyBST.MyBSTNode<>(2, 1, root);
        MyBST.MyBSTNode<Integer, Integer> six =
                new MyBST.MyBSTNode<>(6, 1, root);
        MyBST.MyBSTNode<Integer, Integer> one =
                new MyBST.MyBSTNode<>(1, 2, two);
        MyBST.MyBSTNode<Integer, Integer> three =
                new MyBST.MyBSTNode<>(3, 30, two);  
        MyBST.MyBSTNode<Integer, Integer> five =
                new MyBST.MyBSTNode<>(5, 50, six);

        this.tree = new MyBST<>();
        this.tree.root = root;
        root.setLeft(two);
        root.setRight(six);
        two.setLeft(one);
        two.setRight(three);
        six.setLeft(five);
        tree.size = 6;
    }

    @Test
    public void testSuccessor() {
        MyBST<Integer, String> test = new MyBST<>();
        test.insert(5, "left");
        test.insert(3, "left,left");
        test.insert(7, "right");
        test.insert(6, "root");
        test.insert(8, "right, right");

        MyBST.MyBSTNode<Integer, String> rootNode = test.root;
        MyBST.MyBSTNode<Integer, String> successor = rootNode.successor();

        assertEquals(6, (int) successor.getKey());
        assertEquals("root", successor.getValue());

        MyBST<Integer, String> test2 = new MyBST<>();
        test2.insert(50, "root");
        test2.insert(30, "left");
        test2.insert(70, "right");
        test2.insert(20, "left,left");
        test2.insert(40, "left,right");
        

        MyBST.MyBSTNode<Integer, String> leafNode = test2.root.getLeft().getLeft();
        MyBST.MyBSTNode<Integer, String> successor2 = leafNode.successor();

        assertEquals(30, (int) successor2.getKey());
        assertEquals("left", successor2.getValue());

        MyBST<Integer, String> test3 = new MyBST<>();
        test3.insert(50, "root");
        test3.insert(30, "left");
        test3.insert(70, "right");
        test3.insert(20, "left,left");
        test3.insert(40, "left,right");

        MyBST.MyBSTNode<Integer, String> Node = test3.root.getLeft();
        MyBST.MyBSTNode<Integer, String> successor3 = Node.successor();

        assertEquals(40, (int) successor3.getKey());
        assertEquals("left,right", successor3.getValue());

        MyBST<Integer, String> test4 = new MyBST<>();
        test4.insert(50, "root");
        test4.insert(30, "left");
        test4.insert(70, "right");
        test4.insert(60, "right,left");
        test4.insert(80, "right,right");

        MyBST.MyBSTNode<Integer, String> Node2 = test4.root.getRight();
        MyBST.MyBSTNode<Integer, String> successor4 = Node2.successor();

        assertEquals(80, (int) successor4.getKey());
        assertEquals("right,right", successor4.getValue());

    }

    @Test
    public void testInsert() {
        MyBST<Integer, String> nullTest = new MyBST<>();
        assertThrows(NullPointerException.class,
            () -> nullTest.insert(null, "test for Null"));

        MyBST<Integer, String> emptyTree = new MyBST<>();
        assertNull(emptyTree.insert(5, "root"));
        //returns old value
        assertEquals("root", emptyTree.insert(5, "testReplaceValue"));
        assertNull(emptyTree.insert(3, "leaf,left"));
        assertNull(emptyTree.insert(7, "leaf,right"));

        assertEquals(5, (int) emptyTree.root.getKey());
        //value should be new value
        assertEquals("testReplaceValue", emptyTree.root.getValue());
        assertEquals(3, (int) emptyTree.root.getLeft().getKey());
        assertEquals("leaf,left", emptyTree.root.getLeft().getValue());
        assertEquals(7, (int) emptyTree.root.getRight().getKey());
        assertEquals("leaf,right", emptyTree.root.getRight().getValue());
        assertEquals(3, emptyTree.size());

    }

    @Test
    public void testSearch() {
        assertEquals((Integer) 1, tree.search(2));
        assertEquals((Integer) 1, tree.search(4));
        assertEquals((Integer) 1, tree.search(6));
        assertEquals((Integer) 2, tree.search(1));
        assertEquals((Integer) 30, tree.search(3));
        assertEquals((Integer) 50, tree.search(5));
        assertNull(tree.search(8));
    }

    @Test
    public void testRemove() {

        assertEquals((Integer) 1, tree.remove(6));
        assertEquals(5, tree.size());
        assertNull(tree.search(6));

        assertEquals((Integer) 1, tree.remove(2));
        assertEquals(4, tree.size());
        assertNull(tree.search(2));

        assertEquals((Integer) 30, tree.remove(3));
        assertEquals(3, tree.size());
        assertNull(tree.search(3));

        assertEquals((Integer) 2, tree.remove(1));
        assertEquals(2, tree.size());
        assertNull(tree.search(1));

        assertEquals((Integer) 1, tree.remove(4));
        assertEquals(1, tree.size());
        assertNull(tree.search(4));

        assertEquals((Integer) 50, tree.remove(5));
        assertEquals(0, tree.size());
        assertNull(tree.search(5));

        assertNull(tree.remove(20));

    }


    @Test
    public void testInorder() {
        MyBST<Integer, String> emptyTree = new MyBST<>();
        ArrayList<MyBST.MyBSTNode<Integer, String>> emptyTreeInOrder = emptyTree.inorder();
        assertTrue(emptyTreeInOrder.isEmpty());

        ArrayList<MyBST.MyBSTNode<Integer, Integer>> treeInOrder = tree.inorder();
        assertEquals(6, treeInOrder.size());

        assertEquals((Integer) 1, treeInOrder.get(0).getKey());
        assertEquals((Integer) 2, treeInOrder.get(1).getKey());
        assertEquals((Integer) 3, treeInOrder.get(2).getKey());
        assertEquals((Integer) 4, treeInOrder.get(3).getKey());
        assertEquals((Integer) 5, treeInOrder.get(4).getKey());
        assertEquals((Integer) 6, treeInOrder.get(5).getKey());

    }

    
    @Test
    public void testCopy() {

        MyBST<Integer, String> emptyTree = new MyBST<>();
        MyBST<Integer, String> nullCopy = emptyTree.copy();

        assertNotNull(nullCopy);
        assertNull(nullCopy.root);
        assertEquals(0, nullCopy.size());


        MyBST<Integer, Integer> treeCopy = tree.copy();

        assertEquals(tree.size(), treeCopy.size());
        assertNotSame(tree.root, treeCopy.root);

        ArrayList<MyBST.MyBSTNode<Integer,Integer>> treeInOrder = tree.inorder();
        ArrayList<MyBST.MyBSTNode<Integer,Integer>> treeCopyInOrder = treeCopy.inorder();

        assertEquals(treeInOrder.size(), treeCopyInOrder.size());

        for (int i = 0; i < treeInOrder.size(); i++) {
            assertEquals(treeInOrder.get(i).getKey(), treeCopyInOrder.get(i).getKey());
            assertEquals(treeInOrder.get(i).getValue(), treeCopyInOrder.get(i).getValue());
            assertNotSame(treeInOrder.get(i), treeCopyInOrder.get(i));
        }


    }

}
