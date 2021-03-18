import java.util.Arrays;

/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
	
	
	   enum OP {
		   INSERT,
		   DELETE;
	   }

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
	private IAVLNode root = new notRealAVLNode(null);
	private IAVLNode min;
	private IAVLNode max;
	private IAVLNode temp;

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
	  if(root.isRealNode()) return null;
	  temp = root;
	  while(temp.isRealNode()) {
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
	   if(!root.isRealNode()) {
		   root = new AVLNode(k ,i);
		   min = root;
		   max = root;
		   return 0;
	   }
	   IAVLNode temp = root;
	   
	   while(temp.isRealNode()) {
		   if(k == temp.getKey()) {
			   return -1;
		   }  
		   if(k < temp.getKey()) {
			   temp = temp.getLeft();
		   }
		   else if(k > temp.getKey()) {
			   temp = temp.getRight();
		   }
	   } 
		   AVLNode node = new AVLNode(k , i);
		   if(k < temp.getParent().getKey()) {
			   temp.getParent().setLeft(node);
			   node.setParent(temp.getParent());
			   updateSize(node);
		   }
		   else {
			   temp.getParent().setRight(node);
			   node.setParent(temp.getParent());
			   updateSize(node);
		   }
		 
	updateMin();
	updateMax();
	return balance(node.getParent(), OP.INSERT);
	//return 0;
	   
	   
	   /*
	   if (search(k)!=null) return -1;
	   AVLNode node = new AVLNode(k,i);
	   if(min.getKey()>k) min=node;
	   if(max.getKey()<k) max=node;
	   int count_reb=0;
	   return count_reb;
	   */
   }



	/**
	 * private int balance(IAVLNode node, int mode)
	 * 
	 * This method balances the tree after insertion or deletion.
	 * For insertion use mode = INSERT
	 * For deletion use mode = DELETE
	 * node is the parent of the node which was inserted
	 * or the parent of the node which was truly deleted.
	 * 
	 * It returns the number of rotations that were applied when
	 * LL & RR are considered 1 rotation while LR & RL are two rotations.
	 * 
	 * Time complexity: O(logn)
	 */
	private int balance(IAVLNode node, OP op) {

		IAVLNode ptr = node;
		int rotations = 0;

		// Apply the balancing algorithm
		while (ptr != null) {

			int bf = ptr.calculateBF();
			int newHeight = ptr.calculateHeight();
			if (Math.abs(bf) < 2 && newHeight == ptr.getHeight())
				return rotations;
			if (Math.abs(bf) < 2 && newHeight != ptr.getHeight()) {
				ptr.setHeight(newHeight);
				ptr = ptr.getParent();
				continue;
			}

			ptr.setHeight(newHeight);
			
			// remember ptr's parent
			IAVLNode parent = ptr.getParent();
			//rotate
			rotations += rotate(ptr);

			if (op == OP.INSERT)
				return rotations;
			
			if (op == OP.DELETE) {
				ptr = parent;
				continue;
			}
		}

		return rotations;
	}
   


	/**
	 * private int rotate(IAVLNode node)
	 * 
	 * This method calls the appropriate rotate according to the BF values.
	 * It returns the number of rotations that were applied:
	 * 2 for RL & LR
	 * 1 for LL & RR
	 * 
	 * Time complexity: O(1)
	 */
	private int rotate(IAVLNode node) {

		int bf = node.calculateBF();

		if (bf == 2) {
			if (node.getLeft().calculateBF() == 1 || node.getLeft().calculateBF() == 0) {
				LL(node);
				return 1;
			}
			if (node.getLeft().calculateBF() == -1) {
				LR(node);
				return 2;
			}
		}

		if (bf == -2) {
			if (node.getRight().calculateBF() == 1) {
				RL(node);
				return 2;
			}
			if (node.getRight().calculateBF() == -1 || node.getRight().calculateBF() == 0) {
				RR(node);
				return 1;
			}
		}
		
		// should NOT reach this point
		return 0;
	}
	

	/**
	 * private void LL(IAVLNode node)
	 * 
	 * Apply Left-Left rotation
	 * 
	 * Time complexity: O(1)
	 */
	private void LL(IAVLNode node) {
		rightRotate(node);
	}
	
	/**
	 * private void RR(IAVLNode node)
	 * 
	 * Apply Right-Right rotation
	 * 
	 * Time complexity: O(1)
	 */
	private void RR(IAVLNode node) {
		leftRotate(node);
	}
	
	/**
	 * private void LR(IAVLNode node)
	 * 
	 * Apply Left-Right rotation
	 * 
	 * Time complexity: O(1)
	 */
	private void LR(IAVLNode node) {
		leftRotate(node.getLeft());
		rightRotate(node);
	}

	/**
	 * private void RL(IAVLNode node)
	 * 
	 * Apply Right-Left rotation
	 * 
	 * Time complexity: O(1)
	 */
	private void RL(IAVLNode node) {
		rightRotate(node.getRight());
		leftRotate(node);
	}
	
	
	/**
	 * private void rightRotate(IAVLNode a)
	 * 
	 * This method Applies right rotation as follows:
	 * 
	 *            a                  b
	 *           / \                / \
	 *          b   c      =>      d   a  
	 *         / \                    / \
	 *        d   e                  e   c
	 * 
	 * Time complexity: O(1)
	 */
	private void rightRotate(IAVLNode a) {

		IAVLNode b = a.getLeft();
		IAVLNode e = b.getRight();
		// if a is NOT the root then b should be connected to a's parent
		if (a != root) {
			if (isRight(a))
				a.getParent().setRight(b);
			else // a is a left child
				a.getParent().setLeft(b);
		}

		// connect nodes
		a.setLeft(e);
		b.setRight(a);
		b.setParent(a.getParent());
		a.setParent(b);
		e.setParent(a);

		// if a was the root then b should replace it
		if (root == a) {
			root = b;
			root.setParent(null);
		}

		// update high, size and keys' sum for a
		updateHeightSize(a);
		a.setKeysSum(a.calculateKeysSum());
		// update high, size and keys' sum for b
		updateHeightSize(b);
		b.setKeysSum(b.calculateKeysSum());

	}

	/**
	 * private void leftRotate(IAVLNode a)
	 * 
	 * This method Applies left rotation as follows:
	 * 
	 *            a					 b
	 *           / \			    / \
	 *          c   b      =>      a   e
	 *             / \			  / \
	 *            d   e          c   d
	 * 
	 * Time complexity: O(1)
	 */
	private void leftRotate(IAVLNode a) {

		IAVLNode b = a.getRight();
		IAVLNode bLeft = b.getLeft();
		// if a is NOT the root then b should be connected to a's parent
		if (a != root) {
			if (isRight(a))
				a.getParent().setRight(b);
			else // a is a left child
				a.getParent().setLeft(b);
		}

		// connect nodes
		a.setRight(bLeft);
		b.setLeft(a);
		b.setParent(a.getParent());
		a.setParent(b);
		bLeft.setParent(a);

		// if a was the root then b should replace it
		if (root == a) {
			root = b;
			root.setParent(null);
		}

		// update high, size and keys' sum for a
		updateHeightSize(a);
		a.setKeysSum(a.calculateKeysSum());
		// update high, size and keys' sum for b
		updateHeightSize(b);
		b.setKeysSum(b.calculateKeysSum());

	}
	
	

	/**
	 * private boolean isRight(IAVLNode node)
	 * 
	 * precondition: node is NOT the root
	 * 
	 * return value :
	 * true  : if node is a right child
	 * false : if node is a left child
	 * 
	 * Time complexity: O(1)
	 */
	private boolean isRight(IAVLNode node) {
		return node.getParent().getRight().getKey() == node.getKey();
	}
	

	/**
	 * private void updateHeightSize(IAVLNode node)
	 * 
	 * This method updates the height and size of a given node.
	 * 
	 * Time complexity: O(1)
	 */
	private void updateHeightSize(IAVLNode node) {
		node.setHeight(node.calculateHeight());
		node.setSubtreeSize(node.calculateSize());
	}

	
	/**
	 * private void updateSizesKeysSumButtomUp(IAVLNode node)
	 * 
	 * Buttom-up update of size and keys' sum starting from node.
	 * 
	 * Time complexity: O(logn) 
	 */
	private void updateSizesKeysSumButtomUp(IAVLNode node) {

		IAVLNode ptr = node;

		while (ptr != null) {
			ptr.setSubtreeSize(ptr.calculateSize());
			ptr.setKeysSum(ptr.calculateKeysSum());
			ptr = ptr.getParent();
		}

	}
	
	
	/**
	 * private void updateMin()
	 * 
	 * update min so it points to the node with the minimal key
	 * or null if the tree is empty.
	 * 
	 * Time complexity: O(logn)
	 */
	private void updateMin() {
		
		if (empty()) {
			min = null;
			return;
		}
		
		IAVLNode node = root;
		
		while(node.isRealNode())
			node = node.getLeft();
		
		min = node.getParent();
		
	}

	/**
	 * private void updateMax()
	 * 
	 * update max so it points to the node with the maximal key
	 * or null if the tree is empty.
	 * 
	 * Time complexity: O(logn)
	 */
	private void updateMax() {
		
		if (empty()) {
			max = null;
			return;
		}
		
		IAVLNode node = root;
		
		while(node.isRealNode())
			node = node.getRight();
		
		max = node.getParent();
		
	}

   
   
   
   
   
   private void updateSize(IAVLNode node) {
	   while(node.getKey() != root.getKey()) {
		   node = node.getParent();
		   node.setSize(node.getSize() + 1);
	   }
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
private IAVLNode Successor(IAVLNode x) {
	if(!root.isRealNode()) {
		return null;
	}
	if(x.getKey() == max.getKey()) {
		return null;
	}
	
	if (x.getRight().isRealNode()) {
		   IAVLNode temp=x.getRight();
		   while(temp.getLeft().isRealNode()) {
			   temp=temp.getLeft();
		   }
		   return temp;
	 }
	 IAVLNode temp=x.getParent();
	 IAVLNode temp1=x;
	 while (temp!=null && temp.getRight().equals(temp1)) {
		   temp1=temp;
		   temp=temp.getParent();
	 } 
	 return temp;
   } 

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()// ME
  {
	  if(!root.isRealNode()) return new int[0];
      int[] arr = new int[root.getSize()];
      arr[0]=min.getKey();
      IAVLNode temp=min;
      for(int i=1;i<arr.length;i++) {
    	  IAVLNode temp1=Successor(temp);
    	  arr[i]=temp1.getKey();
    	  temp=temp1;
    	  
      }
        
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
	  if(!root.isRealNode()) return new String[0];
      String[] arr = new String[root.getSize()];
      arr[0]=min.getValue();
      IAVLNode temp=min;
      for(int i=1;i<arr.length;i++) {
    	  IAVLNode temp1=Successor(temp);
    	  arr[i]=temp1.getValue();
    	  temp=temp1;
    	  
      }
        
      return arr;  }

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
	   return root.getSize(); // to be replaced by student code
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
   public AVLTree[] split(int x)// ME
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
		public void setSubtreeSize(int calculateSize);
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
    	public int getSize();
    	public void setSize(int size);
    	public int calculateBF();
    	public int calculateHeight();
    	public int calculateKeysSum(); // calculates keys' sum
    	public void setKeysSum(int keysSum); // sets keys' sum
    	public int getSubtreeSize(); // Returns the number of real nodes in this node's subtree (Should be// implemented in O(1))
    	public int calculateSize(); // Calculate the size of a the subtree starting from the current node
		public int getKeysSum();
		public boolean equals (IAVLNode x);
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
	
	
	public class notRealAVLNode implements IAVLNode{
		private IAVLNode parent;
		private boolean isReal = false;
		
		public notRealAVLNode(IAVLNode parent) {
			this.parent = parent;
		}
		
	@Override
	public int getKey() {
		return 0;
	}

	@Override
	public String getValue() {
		return "[NOTREAL]";
	}

	@Override
	public void setLeft(IAVLNode node) {
	}

	@Override
	public IAVLNode getLeft() {
		return null;
	}

	@Override
	public void setRight(IAVLNode node) {	
	}

	@Override
	public IAVLNode getRight() {
		return null;
	}

	@Override
	public void setParent(IAVLNode node) {		
	}

	@Override
	public IAVLNode getParent() {
		return parent;
	}

	@Override
	public boolean isRealNode() {
		return isReal;
	}

	@Override
	public void setHeight(int height) {
	
		
	}

	@Override
	public int getHeight() {
		return -1;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSize(int size) {
		// TODO Auto-generated method stub
		
	}
	public int calculateBF() {
		return 0;
	}

	@Override
	public int calculateHeight() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void setSubtreeSize(int calculateSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int calculateKeysSum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setKeysSum(int keysSum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSubtreeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int calculateSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getKeysSum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(IAVLNode x) {
		// TODO Auto-generated method stub
		return x.getKey()==-1;
	}
	
		
	}
	
	
	
  public class AVLNode implements IAVLNode{
	private int key;
	private String info;
	private IAVLNode left = new notRealAVLNode(this);
	private IAVLNode right = new notRealAVLNode(this);
	private IAVLNode parent;
	private int height;
	private int size;
	private boolean isReal = true;
	private int keysSum = 0;
	
	
	public AVLNode(int k,String s) {
		  this.key=k;
		  this.info=s;
		  this.isReal = true;
		  this.size = 1;
	  	}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
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
		public void setParent(IAVLNode node){
			this.parent=node;
		}
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
		
		
		public int calculateBF() {
			return left.getHeight() - right.getHeight();
		}

		public int calculateHeight() {
			int rHeight = right.getHeight();
			int lHeight = left.getHeight();
			return Math.max(rHeight, lHeight) + 1;
		}
		public int calculateKeysSum() {
			return key + left.getKeysSum() + right.getKeysSum();
		}
		public void setSubtreeSize(int size) {
			this.size = size;
		}
		public int getKeysSum() {
			return keysSum;
		}
		public void setKeyssSum(int keysSum) {
			this.keysSum = keysSum;
		}
		public void setKeysSum(int keysSum) {
			this.keysSum = keysSum;
		}
		public int calculateSize() {
			int rSize = right.getSubtreeSize();
			int lSize = left.getSubtreeSize();
			return rSize + lSize + 1;
		}
		@Override
		public int getSubtreeSize() {
			// TODO Auto-generated method stub
			return size;
		}
		@Override
		public boolean equals(IAVLNode x) {
			// TODO Auto-generated method stub
			return x.getKey()==key;
		}
		
  }

  
  public static void main(String[] args) {
	  AVLTree tree = new AVLTree();
	  tree.insert(3, "hi");
	  tree.insert(2, "bye");
	  tree.insert(1, "lol");
	  tree.insert(0, "yazan");
	  tree.insert(-1, "beisan");
	  //AVLTree.AVLNode node=tree.new AVLNode(3, "lol");
	  //System.out.println();
	  //System.out.println(node.getValue());
	  //System.out.println(tree.Successor(node).getValue());
	  System.out.println(Arrays.toString(tree.keysToArray()));
	  System.out.println(tree.root.getLeft().getValue());
	  System.out.println(tree.root.getRight().getValue());
  }
}
  

