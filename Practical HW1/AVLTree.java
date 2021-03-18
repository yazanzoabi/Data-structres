/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
	private IAVLNode root;
	private IAVLNode min;
	private IAVLNode max;

  public boolean empty() {
    return root==null;
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
	  if(root==null) return null;
	  IAVLNode temp=root;
	  while(temp!=null) {
		  if(temp.getKey()==k) return temp.getValue();
		  if(root.getKey()>k) temp=temp.getRight();
		  if(root.getKey()<k) temp=temp.getLeft();
	  }
	return null; 
  }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   if (search(k)!=null) return -1;
	   AVLNode node = new AVLNode(k,i);
	   if(min.getKey()>k) min=node;
	   if(max.getKey()<k) max=node;
	   int count_reb=0;
	   return count_reb;
   }

  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   if (search(k)==null) return -1;
	   int count_reb=0;
	   
	   return count_reb;
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   if (root==null) return null;
	   return min.getValue(); // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   if (root==null) return null;
	   return max.getValue(); // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
	  if(root==null) return new int[0];
      int[] arr = new int[root.getHeight()];
        
      return arr;
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
	  if(root==null) return new String[0];
      String[] arr = new String[42]; // to be replaced by student code
      return arr;                    // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {
	   return root.getHeight(); // to be replaced by student code
   }
   
     /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    */
   public IAVLNode getRoot()
   {
	   return root;
   }
     /**
    * public string split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	  * precondition: search(x) != null
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   return null; 
   }
   /**
    * public join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (rank difference between the tree and t)
	  * precondition: keys(x,t) < keys() or keys(x,t) > keys()
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   return 0; 
   }

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
    	public void setHeight(int height); // sets the height of the node
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
  public class AVLNode implements IAVLNode{
	  private int key;
	private String info;
	private IAVLNode left;
	private IAVLNode right;
	private IAVLNode parent;
	private int height;
	public AVLNode(int k,String s) {
		  this.key=k;
		  this.info=s;
	  }
		public int getKey()
		{
			return key;
		}
		public String getValue()
		{
			return info;
		}
		public void setLeft(IAVLNode node)
		{
			this.left=node;
		}
		public IAVLNode getLeft()
		{
			return left;
		}
		public void setRight(IAVLNode node)
		{
			 this.right=node;
		}
		public IAVLNode getRight()
		{
			return right;
		}
		public void setParent(IAVLNode node)
		{
			this.parent=node;		}
		public IAVLNode getParent()
		{
			return parent; // to be replaced by student code
		}
		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode()
		{
			return true; // to be replaced by student code
		}
    public void setHeight(int height)
    {
      this.height=height; // to be replaced by student code
    }
    public int getHeight()
    {
      return height; // to be replaced by student code
    }
  }

  
  public static void main(String[] args) {
	  System.out.println("fuck");
  }
}
  

