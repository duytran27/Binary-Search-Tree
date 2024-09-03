package asgn3;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;

/**
 * A generic Binary Search Tree (BST) implementation.
 * 
 * @param <T> The type of elements stored in the BST, must implement Comparable interface.
 */
public class BST<T extends Comparable<T>> implements Iterable<T>
{
	
	private Comparator<T> compare; // Comparator for custom ordering
	private BSTNode root; // Root node of the BST
	private int size; // Number of elements in the BST
	
	/**
	  * Constructs an empty BST with natural ordering.
	  */
	public BST()
	{
		root = null;
		size = 0;
		compare = Comparator.naturalOrder();
	}
	/**
	  * Constructs an empty BST with custom ordering.
	  * 
	  * @param c Comparator for custom ordering
	  */
	public BST( Comparator<T> c)
	{
		root = null;
		size= 0;
		compare = c;
	}
	
	 /**
	    * Returns the number of elements in the BST.
	    * 
	    * @return The size of the BST
	    */
	public int size() {
		return size;
	}

	/**
	  * Returns the height of the BST.
	  * 
	  * @return The height of the BST
	  */
	public int height() {
		return height(root);
	}

	// Recursive helper method to calculate height
	private int height(BSTNode r) {

		if(r==null) {
			return -1;
		}
		
		int leftHeight = height(r.left);
		int rightHeight= height(r.right);
		
		return 1+Math.max(leftHeight, rightHeight);
	}

	/**
	  * Returns the minimum element in the BST.
	  * 
	  * @return The minimum element in the BST
	  */
	public T min() {
		// TODO Auto-generated method stub
		return min(root);
	}
	
	// Recursive helper method to find minimum element
	private T min(BSTNode r) {
		// TODO Auto-generated method stub
		if(r==null) {
			return null;
		}
		if(r.getLeft()!=null) {
			return min(r.getLeft());
		}
		return r.getData();
	}
	
	/**
	  * Adds a new element to the BST.
	  * 
	  * @param data The data to add to the BST
	  */
	public void add(T data) {
		BSTNode node = new BSTNode(data);
		
		if(root == null) {
			size++;
			root = node;
		}
		else {
			addNode(root,node);
		}
	}
	
	// Recursive helper method to add a node
	private void addNode(BSTNode root, BSTNode node) {
		// TODO Auto-generated method stub
		int comp = compare.compare(node.getData(), root.getData());
		
		if(comp < 0) {
			if(root.getLeft()==null) {
				root.setLeft(node);
				size++;
			}
			else {
				addNode(root.getLeft(),node);
			}
		}
		else if(comp>0) {
			if(root.getRight()==null) {
				root.setRight(node);
				size++;
			}
			else {
				addNode(root.getRight(), node);
			}
		}
		
	}
	
	 /**
	   * Finds an element in the BST.
	   * 
	   * @param data The data to search for
	   * @return The element found, or null if not found
	   */
	public T find(T data) {
		return findNode(data, root);
	}
	
	// Recursive helper method to find a node
	private T findNode(T data, BSTNode root) {
		
        if ( root == null)
            return null;
        int c = data.compareTo( root.getData());
        if ( c == 0)
            return root.getData();
        else if ( c < 0)
            return findNode( data, root.getLeft());
        else
            return findNode( data, root.getRight());
	}
	
	/**
	  * Deletes an element from the BST.
	  * 
	  * @param d The data to delete
	  */
    public void delete(T d) {
        root = delete(root,d);
        size --;
    }


    // Recursive helper method to delete a node
    private BSTNode delete(BSTNode r, T d) {

        if(r == null) {
            return null; 
        }
        if (compare.compare(r.getData(), d) == 0){
            if(r.isLeaf()) { 
                return null;
            } else if (r.getRight() == null && r.getLeft() != null) {
              
                return r.getLeft();
            } else if (r.getRight() != null && r.getLeft() == null) {
                
                return r.getRight();
            } else { 
                T min = min(r.getRight());
                r.setData(min);
                r.setRight(delete(r.getRight(), min));
            }
        }
        
        if (compare.compare(r.getData(), d) > 0) {
            r.setLeft(delete(r.getLeft(), d));
        } else {
            r.setRight(delete(r.getRight(), d));
        }
        return r;
    }
    
    /**
     * Checks if the BST is empty.
     * 
     * @return True if the BST is empty, false otherwise
     */
    public boolean isEmpty() {return root==null;}

    // Inner class representing a node in the BST
	private class BSTNode implements Comparable<BSTNode> {

        private T data;
        private BSTNode left;
        private BSTNode right;
        
        // Constructor
        public BSTNode( T d)
        {
            setLeft( null);
            setRight( null);
            setData( d);
        }
        
       // Getters and setters
        public T getData() { return data; }
        public void setData( T d) { data = d; }
        public void setLeft( BSTNode l) { left = l;}
        public void setRight( BSTNode r) { right = r;}
        public BSTNode getLeft()  { return left;}
        public BSTNode getRight()  { return right;}
       
        // Checks if the node is a leaf
        public boolean isLeaf()
        {
            return ( getLeft() == null) && ( getRight() == null);
        }
        
        // Compares nodes based on their data
        public int compareTo( BSTNode o)
        {
            return this.getData().compareTo( o.getData());
        }

    }

	 /**
	   * Returns an iterator over the elements in the BST in ascending order.
	   * 
	   * @return An iterator over the elements in the BST
	   */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
        	
        	// Queue to store elements in in-order traversal order
            Queue<T> inOrderQueue; {
            	// Initialization block to initialize the inOrderQueue with in-order traversal elements
            	inOrderQueue = new LinkedList<>();
                fillQueue(root); 	// Fill the queue with in-order traversal elements
            }

            // Helper method to recursively fill the queue with in-order traversal elements
            private void fillQueue(BSTNode r) {

                if ( r != null ) {
                    fillQueue(r.getLeft());	 // Traverse left subtree
                    visit(r);	// Visit current node
                    fillQueue(r.getRight());  // Traverse right subtree
                }
            }

           // Helper method to visit a node and add its data to the queue
            private void visit(BSTNode n) {
                inOrderQueue.add(n.getData());
            }

            /**
             * Checks if there are more elements to iterate over.
             * 
             * @return True if there are more elements, false otherwise
             */
            @Override
            public boolean hasNext() {

                return !inOrderQueue.isEmpty();
            }

            /**
             * Returns the next element in the iteration.
             * 
             * @return The next element in the iteration
             */
            @Override
            public T next() {

                return inOrderQueue.poll();
            }
        };
    }
}
