package algo.heap;

import java.io.*;

public class Main11279_최대힙 {
	static class Heap {
		int size;
		int[] heap;

		Heap(int n) {
			size = 0;
			heap = new int[n << 2];
		}

		boolean isEmpty() {
			return size == 0;
		}

		void push(int n) {
			heap[++size] = n;

			int cur = size;
			int par = cur >> 1;
			int tmp;

			while (cur > 1 && heap[cur] > heap[par]) {
				tmp = heap[cur];
				heap[cur] = heap[par];
				heap[par] = tmp;

				cur = par;
				par = cur >> 1;
			}
		}

		int pop() {
			if (isEmpty()) {
				return 0;
			}

			int n = heap[1];
			heap[1] = heap[size--];
			int cur = 1, next, tmp;

			while (true) {
				next = cur << 1;

				if (next < size && heap[next] < heap[next + 1]) {
					++next;
				}

				if (next > size || heap[cur] > heap[next]) {
					break;
				}

				tmp = heap[cur];
				heap[cur] = heap[next];
				heap[next] = tmp;

				cur = next;
			}

			return n;
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input11279.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = new Integer(br.readLine());
		Heap heap = new Heap(N);
		int x;
		StringBuilder sb = new StringBuilder();

		while (N-- > 0) {
			x = new Integer(br.readLine());

			switch (x) {
			case 0:
				sb.append(heap.pop()).append("\n");
				break;
			default:
				heap.push(x);
			}
		}

		System.out.println(sb.toString());
		br.close();
	}
}
