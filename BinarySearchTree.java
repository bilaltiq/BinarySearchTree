import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree {

    static Node root;

    public void add(int data) {
        Node newNode = new Node(data);
        if (root == null) {
            root = newNode;
        } else {
            Node focusNode = root;
            Node parent;

            while (true) {
                parent = focusNode;
                if (data < focusNode.data) {
                    focusNode = focusNode.left;

                    if (focusNode == null) {
                        parent.left = newNode;
                        return;
                    }

                } else {
                    focusNode = focusNode.right;

                    if (focusNode == null) {
                        parent.right = newNode;
                        return;
                    }
                }
            }

        }
    }

    public boolean find(int data) {
        Node focusNode = root;
        Node parent;

        while (true) {
            parent = focusNode;

            if (data < focusNode.data) {
                focusNode = focusNode.left;

                if (focusNode == null) {
                    return false;
                }
            } else if (data > focusNode.data) {
                focusNode = focusNode.right;
                if (focusNode == null) {
                    return false;
                }

            } else if (data == focusNode.data) {
                return true;
            }
        }
    }

    public Node helperReplacement(Node replacedNode) {
        Node replacementParent = replacedNode;
        Node replacement = replacedNode;
        Node focusNode = replacedNode.right;

        while (focusNode != null) {
            replacementParent = replacement;
            replacement = focusNode;
            focusNode = focusNode.left;
        }

        if (replacement != replacedNode.right) {
            replacementParent.left = replacement.right;
            replacement.right = replacedNode.right;
        }

        return replacement;
    }

    public boolean remove(int data) {
        Node focusNode = root;
        Node parent = root;
        boolean isLeft = true;

        while (focusNode != null && focusNode.data != data) {
            parent = focusNode;

            if (data < focusNode.data) {
                isLeft = true;
                focusNode = focusNode.left;
            } else {
                isLeft = false;
                focusNode = focusNode.right;
            }

            if (focusNode == null) {
                return false; // Node not found
            }
        }

        if (focusNode.left == null && focusNode.right == null) { // Case 1: No children
            if (focusNode == root) {
                root = null;
            } else if (isLeft) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (focusNode.right == null) { // Case 2: Only has left child
            if (focusNode == root) {
                root = focusNode.left;
            } else if (isLeft) {
                parent.left = focusNode.left;
            } else {
                parent.right = focusNode.left;
            }
        } else if (focusNode.left == null) { // Case 3: Only has right child
            if (focusNode == root) {
                root = focusNode.right;
            } else if (isLeft) {
                parent.left = focusNode.right;
            } else {
                parent.right = focusNode.right;
            }
        } else { // Case 4: Has both left and right children
            Node replacement = helperReplacement(focusNode);

            if (focusNode == root) {
                root = replacement;
            } else if (isLeft) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
            replacement.left = focusNode.left;
        }

        return true;
    }

    public Node deepest(Node node) {
        if (node == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        Node deepest = null;

        while (!queue.isEmpty()) {
            deepest = queue.poll();

            if (deepest.left != null) {
                queue.add(deepest.left);
            }

            if (deepest.right != null) {
                queue.add(deepest.right);
            }
        }

        return deepest;

    }

    public int total(Node node) {
        if (node == null)
            return 0;

        return node.data + total(node.left) + total(node.right);
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        int[] toAdd = { 20, 3, 2, 5, 9, 100, 50, 21, 200, 8, 7, 10 };

        for (int i = 0; i < toAdd.length; i++) {
            tree.add(toAdd[i]);
        }

        // Deepest:
        System.out.println();

        Node deepest1 = tree.deepest(root);
        System.out.println(deepest1.data);

        // Total:
        System.out.println();
        System.out.println(tree.total(root));

        int[] toFind = { 5, 9, 10, 12, 20, 100, 150, 200, 300 };

        for (int i = 0; i < toFind.length; i++) {
            if (tree.find(toFind[i]) == false) {
                System.out.println(toFind[i] + " was not found");
            } else {
                System.out.println(toFind[i] + " was found");
            }
        }

        int[] toRemove = { 20, 2, 9, 100, 300 };
        for (int i = 0; i < toRemove.length; i++) {
            boolean removed = tree.remove(toRemove[i]);
        }

        System.out.println();

        for (int i = 0; i < toFind.length; i++) {
            if (tree.find(toFind[i]) == false) {
                System.out.println(toFind[i] + " was not found");
            } else {
                System.out.println(toFind[i] + " was found");
            }
        }

        // Deepest:
        System.out.println();

        Node deepest2 = tree.deepest(root);
        System.out.println(deepest2.data);

        // Total:
        System.out.println();
        System.out.println(tree.total(root));

    }

}
