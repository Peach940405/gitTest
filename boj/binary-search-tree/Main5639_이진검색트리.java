/**
 * @Date
 * 2019-08-16
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/5639
 *
 * @문제
 * 백준 5639.
 *
 * 전위 순회 (루트-왼쪽-오른쪽)은 루트를 방문하고, 왼쪽 서브트리, 오른쪽 서브 트리를 순서대로 방문하면서 노드의 키를 출력한다.
 * 후위 순회 (왼쪽-오른쪽-루트)는 왼쪽 서브트리, 오른쪽 서브트리, 루트 노드 순서대로 키를 출력한다.
 * 예를 들어, 위의 이진 검색 트리의 전위 순회 결과는 50 30 24 5 28 45 98 52 60 이고, 후위 순회 결과는 5 28 24 45 30 60 52 98 50 이다.
 * 이진 검색 트리를 전위 순회한 결과가 주어졌을 때, 이 트리를 후위 순회한 결과를 구하는 프로그램을 작성하시오.
 *
 *
 * @입력값
 * 트리를 전위 순회한 결과가 주어진다.
 * 노드에 들어있는 키의 값은 106보다 작은 양의 정수이다.
 * 모든 값은 한 줄에 하나씩 주어지며, 노드의 수는 10,000개 이하이다. 같은 키를 가지는 노드는 없다.
 *
 *
 * @풀이방법
 * 1. BST 클래스를 작성하여 insert함수와 postOrder 함수를 만들자.
 *
 */

package algo.tree;

import java.io.*;

public class Main5639_이진검색트리 {
    static BST bst;

    static class BST {
        Node root;
        StringBuilder sb;

        void insert(int data) {
            root = insert(root, data);
        }

        Node insert(Node root, int data) {
            if (root == null) {
                root = new Node(data);
                return root;
            }

            if (data < root.data)
                root.left = insert(root.left, data);
            else if (data > root.data)
                root.right = insert(root.right, data);

            return root;
        }

        void postOrder() {
            sb = new StringBuilder();
            postOrder(root);
            System.out.println(sb.toString());
        }

        void postOrder(Node root) {
            if (root != null) {
                postOrder(root.left);
                postOrder(root.right);
                sb.append(root.data).append("\n");
            }
        }

        class Node {
            int data;
            Node left, right;

            Node(int data) {
                this.data = data;
            }

            @Override
            public String toString() {
                return String.valueOf(data);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input5639.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        bst = new BST();
        while (br.ready())
            bst.insert(new Integer(br.readLine()));

        bst.postOrder();

        br.close();
    }
}
