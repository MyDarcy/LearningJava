package ctci5th.chapter8.section4;

import java.util.*;

/**
 * Author by darcy
 * Date on 17-7-8 下午12:07.
 * Description:
 */

class Node {
    public int value;
    public Node left;
    public Node right;

    public Node(int v) {
        this.value = v;
        this.left = null;
        this.right = null;
    }

}

class BinaryTreeTraversal {
    /**
     * @param root 树根节点
     * 递归先序遍历
     */
    public static void preOrderRecursive(Node root) {
        if (root != null) {
            System.out.println(root.value);
            preOrderRecursive(root.left);
            preOrderRecursive(root.right);
        }
    }

    /**
     * @param root 树根节点
     * 递归中序遍历
     */
    public static void inOrderRecursive(Node root) {
        if (root != null) {
            inOrderRecursive(root.left);
            System.out.println(root.value);
            inOrderRecursive(root.right);
        }
    }

    /**
     * @param root 树根节点
     *             递归后序遍历
     */
    public static void postOrderRecursive(Node root) {
        if (root != null) {
            postOrderRecursive(root.left);
            postOrderRecursive(root.right);
            System.out.println(root.value);
        }
    }

    /**
     * @param root 树根节点
     *
     * 思路:类似于图的深度优先遍历（DFS）
     * 维护一个栈，将根节点入栈，然后只要栈不为空，出栈并访问，接着依次将访问节点的右节点、左节点入栈。
     * 这种方式应该是对先序遍历的一种特殊实现（看上去简单明了），但是不具备很好的扩展性，在中序和后序方式中不适用
     */
    public static void preOrderStack1(Node root) {
        if (root == null) return;
        Stack<Node> s = new Stack<Node>();
        s.push(root);
        while (!s.isEmpty()) {
            Node temp = s.pop();
            System.out.println(temp.value);
            if (temp.right != null) s.push(temp.right);
            if (temp.left != null) s.push(temp.left);
        }
    }

    /**
     * @param root 树的根节点
     * 思路:利用栈模拟递归过程实现循环先序遍历二叉树
     * 这种方式具备扩展性，它模拟递归的过程，将左子树点不断的压入栈，直到null，然后处理栈顶节点的右子树
     */
    public static void preOrderStack2(Node root) {
        if (root == null) return;
        Stack<Node> s = new Stack<Node>();
        while (root != null || !s.isEmpty()) {
            // 这个过程中已经访问了根节点和左节点.
            while (root != null) {
                //先访问再入栈
                System.out.println(root.value);
                s.push(root);
                root = root.left;
            }
            root = s.pop();
            root = root.right;//如果是null，出栈并处理右子树
        }
    }

    /**
     * @param root 树根节点
     * 思路:利用栈模拟递归过程实现循环中序遍历二叉树
     * 思想和上面的preOrderStack_2相同，只是访问的时间是在左子树都处理完直到null的时候出栈并访问。
     */
    public static void inOrderStack(Node root) {
        if (root == null) return;
        Stack<Node> s = new Stack<Node>();
        while (root != null || !s.isEmpty()) {
            while (root != null) {
                s.push(root);
                root = root.left;
            }
            // 先访问了最左根节点才会回退去其父节点访问.
            root = s.pop();
            System.out.println(root.value);
            root = root.right;//如果是null，出栈并处理右子树
        }
    }

    /**
     * @param root 树根节点
     * 思路:后序遍历不同于先序和中序，它是要先处理完左右子树，然后再处理根(回溯)，
     * 所以需要一个记录哪些节点已经被访问的结构(可以在树结构里面加一个标记)，这里可以用map实现
     */
    public static void postOrderStack(Node root) {
        if (root == null) return;
        Stack<Node> s = new Stack<Node>();
        Map<Node, Boolean> map = new HashMap<Node, Boolean>();
        s.push(root);
        while (!s.isEmpty()) {
            Node temp = s.peek();
            if (temp.left != null && !map.containsKey(temp.left)) {
                temp = temp.left;
                while (temp != null) {
                    if (map.containsKey(temp)) break;
                    else s.push(temp);
                    temp = temp.left;
                }
                // 到最左子节点就会退出,进入下一轮循环.
                continue;
            }
            if (temp.right != null && !map.containsKey(temp.right)) {
                s.push(temp.right);
                continue;
            }
            Node t = s.pop();
            map.put(t, true);
            System.out.println(t.value);
        }
    }

    /**
     * ref: http://blog.csdn.net/pi9nc/article/details/13008511
     * @param root
     */
    public static void postOrderStack2(Node root) {
        Stack<Node> stack = new Stack<>();
        Node currentNode = null;
        Node previousNode = null;
        stack.push(root);
        while (!stack.isEmpty()) {
            currentNode = stack.peek();
            // 如果当前节点没有左右孩子节点, 或者有已经访问过的孩子节点,那么直接输出该节点.
            // 同时将其出栈,并将其设置为上一个访问的节点.
            if (currentNode.left == null
                    && currentNode.right == null
                    || (previousNode != null && (currentNode.left == previousNode || currentNode.right == previousNode))) {
                System.out.println(currentNode.value);
                stack.pop();
                previousNode = currentNode;
            } else {
                if (currentNode.right != null) {
                    stack.push(currentNode.right);
                }
                if (currentNode.left != null) {
                    stack.push(currentNode.left);
                }
            }
        }
    }

    /**
     * @param root 树根节点
     * 思路:层序遍历二叉树，用队列实现，先将根节点入队列，只要队列不为空，然后出队列，并访问，
     * 接着将访问节点的左右子树依次入队列
     */
    public static void levelTravel(Node root) {
        if (root == null) return;
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        while (!q.isEmpty()) {
            Node temp = q.poll();
            System.out.println(temp.value);
            if (temp.left != null) q.add(temp.left);
            if (temp.right != null) q.add(temp.right);
        }
    }
}

public class P840_TreeTraversal2 {

}