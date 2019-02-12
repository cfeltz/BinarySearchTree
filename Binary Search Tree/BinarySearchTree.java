import java.util.*;

/**
 * This BinarySearchTree object defines a reference based binary search tree
 * 
 * @author  Chris F.
 * @version 1.0
 */

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T>
{
    protected BinaryNode<T> root;      // reference to the root of this BST

    // Creates an empty Binary Search Tree object
    public BinarySearchTree()
    {
        root = null;
    }

    // Returns true if this BST is empty; otherwise, returns false.
    public boolean isEmpty()
    {
        return root == null;
    }

    // Returns the number of elements in this BST.
    public int size()
    {
        return size(root);
    }

    private int size(BinaryNode<T> n)
    {
        if(n == null)
            return 0;
        else
            return 1 + size(n.getLeft()) + size(n.getRight());
    }

    // Adds element to this BST. The tree retains its BST property.
    public void add (T element)
    {
        root = add(element, root);
    }

    private BinaryNode<T> add(T element, BinaryNode<T> tree)
    {
        if(tree == null)
            tree = new BinaryNode<T>(element);
        else if(element.compareTo(tree.getInfo()) <= 0)
        {
            tree.setLeft(add(element, tree.getLeft())); //add in left subtree
        }
        else
            tree.setRight(add(element, tree.getRight()));//add in right subtree
        return tree;
    }

    // Returns true if this BST contains an element e such that 
    // e.compareTo(element) == 0; otherwise, returns false.
    public boolean contains (T element)
    {
        return contains(element, root);
    }

    private boolean contains(T element, BinaryNode<T> tree)
    {
        if(tree == null)
            return false;
        else if(element.compareTo(tree.getInfo()) < 0)
            return contains(element,tree.getLeft());
        else if(element.compareTo(tree.getInfo()) > 0)
            return contains(element,tree.getRight());
        else
            return true;
    }

    // Returns a graphical representation of the tree
    public String toString()
    {
        return toString(root, 0);
    }

    private String toString(BinaryNode<T> tree, int level)
    {
        String str = "";
        if (tree != null)
        {
            str += toString(tree.getRight(), level + 1);
            for (int i = 1; i <= level; ++i)
                str = str + "| ";
            str += tree.getInfo().toString() + "\n";
            str += toString(tree.getLeft(), level + 1);
        }
        return str;
    }

    //Returns a list of elements from a preorder traversal
    public List<T> preorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        preorderTraverse(root,list);
        return list;
    }

    private void preorderTraverse(BinaryNode<T> tree, List<T> list)
    {
        if(tree != null)
        {
            list.add(tree.getInfo());
            preorderTraverse(tree.getLeft(), list);
            preorderTraverse(tree.getRight(), list);
        }
    }

    // Returns a list of elements from an inorder traversal
    public List<T> inorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        inorderTraverse(root, list);
        return list;
    }

    private void inorderTraverse(BinaryNode<T> tree, List<T> list)
    {
        if(tree != null)
        {
            inorderTraverse(tree.getLeft(), list);
            list.add(tree.getInfo());
            inorderTraverse(tree.getRight(),list);
        }
    }

    // Returns a list of elements from a postorder traversal
    public List<T> postorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        postorderTraverse(root,list);
        return list;
    }

    public void postorderTraverse(BinaryNode<T> tree, List<T> list)
    {
        if(tree != null)
        {
            postorderTraverse(tree.getLeft(), list);
            postorderTraverse(tree.getRight(), list);
            list.add(tree.getInfo());
        }
    }

    // Removes an element e from this BST such that e.compareTo(element) == 0
    public void remove (T element)
    {
        root = remove(element, root);
    }

    public BinaryNode<T> remove(T element, BinaryNode<T> tree)
    {
        if(element.compareTo(tree.getInfo()) < 0)//if is is less than one then go to the left.
            tree.setLeft(remove(element,tree.getLeft()));
        else if(element.compareTo(tree.getInfo()) > 0)//if this is greater than one then go to right.
            tree.setRight(remove(element,tree.getRight()));
        else//this is the one that we are going to be removing
        {
            tree = removeNode(tree);
        }
        return tree;
    }

    public BinaryNode<T> removeNode(BinaryNode<T> tree)
    {
        if((tree.getLeft() == null) && (tree.getRight() == null))// there are no children then return null
            return null;
        else if((tree.getLeft() == null))//there is not one on the left so return the right
            return tree.getRight();
        else if((tree.getRight() == null))//there is not one on the right so return the left
            return tree.getLeft();
        else//otherwise start to remove things
        {
            T info = getPredecessor(tree);//makes a copy of the node that will be replacing tree
            tree.setInfo(info); //sets the info to the last one
            tree.setLeft(remove(getPredecessor(tree), tree.getLeft())); //this would take care of if the predecessor has any children on the left
        }
        return tree;
    }

    public T getPredecessor(BinaryNode<T> tree)
    {   
        
        while(tree.getRight() != null)
        {
            tree = tree.getRight();
        }
        return tree.getInfo();
    }
    
    // Restructures this BST to be optimally balanced
    public void balance()
    {
        List<T> list = inorderTraverse();
        root = null;
        refillTree(0, list.size() - 1, list);
    }

    private void refillTree(int low, int high, List<T> list)
    {
        if(low == high)
        {
            add(list.get(low));
        }
        else if((low + 1) == high)
        {
            add(list.get(low));
            add(list.get(high));
        }
        else
        {
            int mid = (low + high) / 2;
            add(list.get(mid));
            refillTree(low, mid - 1, list);
            refillTree(mid+ 1, high, list);
        }
    }
}