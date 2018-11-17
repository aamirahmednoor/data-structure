package binary_tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import tree.NodeBT;
import utils.Common;

public class BinaryTree {

	public NodeBT root;

	public static void main(String[] args) {
		NodeBT root = initialize();

		Common.println("\n------------------------------Traversals------------------------------------ ");
		inorder(root);
		Common.println();
		preorder(root);
		Common.println();
		postorder(root);

		Common.println("\nLevel Order Traversal :  ");
		levelOrderTraversal(root);
		Common.println("\nLevel By Level  Traversal :  ");
		levelByLevelTraversal(root);

		Common.println("\nBoundary  Traversal :  ");
		boundaryTraversal(root);

		Common.println("\n------------------------------searching and path------------------------------------ ");
		// searching and path
		int search = 1233;
		Common.println("\nSearching :  " + searchNode(root, search));
		LinkedList<Integer> path = new LinkedList<>();
		searchPath(root, search, path);
		Common.println("\nSearching pATH :  " + path);

		Common.println("\nSum Tree  :  ");

		root = initialize();
		root.right.left = new NodeBT(15);
		// Sum tree
		sumTree(root);
		inorder(root);
		// mirroring
		root = initialize();
		// root.right.right.right = new NodeBT(19);
		Common.println("\nMirror (Inorder):  ");
		mirror(root);
		inorder(root);

		Common.println("\nIs Balanced:  " + isBalanced(root, new Height()));

		Common.println("\nPrint K Distant nodes:  ");
		printKDistant(root, 2);
		// isBalanced(root);
	}

	private static NodeBT initialize() {
		NodeBT root = new NodeBT(10);
		root.left = new NodeBT(8);
		root.right = new NodeBT(2);
		root.left.left = new NodeBT(3);
		root.left.right = new NodeBT(5);
		root.right.right = new NodeBT(12);

		return root;
	}

	private static void printKDistant(NodeBT root, int k) {

		if (root == null)
			return;

		if (k == 0) {
			Common.print(root.data + " , ");
			return;
		}

		printKDistant(root.left, k - 1);
		printKDistant(root.right, k - 1);
	}

	public static void mirror(NodeBT root) {
		if (root == null)
			return;
		NodeBT left = root.left;
		NodeBT right = root.right;
		root.right = left;
		root.left = right;
		mirror(root.left);
		mirror(root.right);
	}

	// O(n^2) Worst case occurs in case of skewed tree.
	public static boolean isBalanced(NodeBT root) {
		if (root == null)
			return true;
		int lh = height(root.left);
		int rh = height(root.right);

		if (Math.abs(lh - rh) <= 1 && isBalanced(root.left) && isBalanced(root.right)) {
			return true;
		}
		return false;
	}

	// O(n) Worst case
	public static boolean isBalanced(NodeBT root, Height height) {
		if (root == null) {
			height.h = 0;
			return true;
		}
		Height lheight = new Height(), rheight = new Height();

		boolean lbal = isBalanced(root.left, lheight);
		boolean rBal = isBalanced(root.right, rheight);

		int lh = lheight.h;
		int rh = rheight.h;

		// Height of current node is max of heights of left and right subtrees plus 1
		height.h = Math.max(lh, rh) + 1;

		if (Math.abs(lh - rh) >= 2) {
			return false;
		}
		return lbal & rBal;
	}

	/**
	 * it has 3 parts:
	 * <li>left size all</li>
	 * <li>leaf nodes</li>
	 * <li>right size</li>
	 */
	public static void boundaryTraversal(NodeBT root) {
		Common.print(" " + root.data);
		printBoundryLeft(root.left);
		printLeaves(root);
		printBoundryRight(root.right);
	}

	/**
	 * The solution involves a simple traversal of the given tree. So the time
	 * complexity is O(n) where n is the number of nodes in the given Binary Tree.
	 */
	public static int sumTree(NodeBT node) {
		if (node == null) {
			return 0;
		}
		int oldVal = node.data;
		node.data = sumTree(node.left) + sumTree(node.right);
		return node.data + oldVal;
	}

	private static void printLeaves(NodeBT node) {
		if (node == null) {
			return;
		}
		printLeaves(node.left);
		// Print it if it is a leaf node
		if (node.left == null && node.right == null)
			System.out.print(" " + node.data);
		printLeaves(node.right);
	}

	private static void printBoundryLeft(NodeBT leftNode) {
		if (leftNode == null) {
			return;
		}

		if (leftNode.left != null) {
			Common.print(" " + leftNode.data);
			printBoundryLeft(leftNode.left);
		} else if (leftNode.right != null) {
			Common.print(" " + leftNode.data);
			printBoundryLeft(leftNode.right);
		}
	}

	private static void printBoundryRight(NodeBT rightNode) {
		if (rightNode == null) {
			return;
		}
		if (rightNode.right != null) {
			printBoundryRight(rightNode.right);
			Common.print(" " + rightNode.data);
		} else if (rightNode.left != null) {
			printBoundryRight(rightNode.left);
			Common.print(" " + rightNode.data);
		}

	}

	public static void levelByLevelTraversal(NodeBT root) {

		Queue<NodeBT> queue = new LinkedList<>();
		queue.add(root);

		while (true) {
			int nodeCount = queue.size();
			if (nodeCount == 0)
				break;
			Common.println("--------------");
			while (nodeCount > 0) {

				NodeBT node = queue.remove();

				Common.print(":" + node.data);
				if (node.left != null)
					queue.add(node.left);

				if (node.right != null)
					queue.add(node.right);

				nodeCount--;
			}
		}
	}

	public static boolean searchNode(NodeBT root, int element) {
		if (root == null) {
			return false;
		}
		Common.print(" " + root.data);
		if (element == root.data) {
			return true;
		}
		return searchNode(root.left, element) || searchNode(root.right, element);
	}

	/**
	 * Use Queue or this
	 * 
	 */
	public static void levelOrderTraversal(NodeBT root) {
		int height = height(root);

		for (int l = 1; l <= height; l++) {
			printLevel(root, l);
		}

	}

	private static void printLevel(NodeBT root, int level) {
		if (level <= 1 && root != null) {
			Common.print(root.data + " , ");
		}
		if (level > 1) {
			printLevel(root.left, level - 1);
			printLevel(root.right, level - 1);
		}
	}

	public static boolean searchPath(NodeBT root, int element, List<Integer> path) {
		if (root == null) {
			return false;
		}
		if (element == root.data) {
			path.add(root.data);
			return true;
		}
		if (searchPath(root.left, element, path) || searchPath(root.right, element, path)) {
			path.add(root.data);
			return true;
		}
		return false;
	}

	public static void inorder(NodeBT root) {
		if (root == null) {
			return;
		}
		inorder(root.left);
		Common.print(" " + root.data);
		inorder(root.right);
	}

	public static void postorder(NodeBT root) {
		if (root == null) {
			return;
		}
		postorder(root.left);
		postorder(root.right);
		Common.print(" " + root.data);
	}

	public static void preorder(NodeBT root) {
		if (root == null) {
			return;
		}
		Common.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	public static int height(NodeBT node) {
		if (node == null)
			return 0;
		return 1 + Common.max(height(node.left), height(node.right));
	}

}
