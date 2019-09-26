/**
 * @Date
 * 2019-09-26
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/2357
 *
 * @문제
 * 백준 2357. 최솟값과 최댓값
 *
 * @입력값
 * 
 * N(1 ≤ N ≤ 100,000)개의 정수들이 있을 때, a번째 정수부터 b번째 정수까지 중에서 제일 작은 정수, 
 * 또는 제일 큰 정수를 찾는 것은 어려운 일이 아니다. 
 * 하지만 이와 같은 a, b의 쌍이 M(1 ≤ M ≤ 100,000)개 주어졌을 때는 어려운 문제가 된다. 
 * 여기서 a번째라는 것은 입력되는 순서로 a번째라는 이야기이다. 
 * 예를 들어 a=1, b=3이라면 입력된 순서대로 1번, 2번, 3번 정수 중에서 최소, 최댓값을 찾아야 한다. 
 * 각각의 정수들은 1이상 1,000,000,000이하의 값을 갖는다.
 * 
 * @풀이방법
 * 1. 구간의 최소와 최대를 구해야하므로 세그먼트 트리를 이용한다.
 * 2. 최소와 최대 각각 저장하는 세그먼트 트리를 만들어 초기화한다.
 * 3. 입력되는 구간 만큼 최소와 최대를 각 트리에서 구해주자.
 *
 */

package segmenttree;

import java.io.*;
import java.util.*;

public class Main2357_최솟값과최댓값 {
	static int INF = 1987654321;

	static int[] minSegmentTree, maxSegmentTree, ia;
	static int N, M;

	static int max(int node, int start, int end, int left, int right) {
		if (left > end || right < start) {
			return -INF;
		}

		if (left <= start && right >= end) {
			return maxSegmentTree[node];
		}

		int mid = (start + end) >> 1;

		return getMax(max(node * 2, start, mid, left, right), max(node * 2 + 1, mid + 1, end, left, right));
	}

	static int min(int node, int start, int end, int left, int right) {
		if (left > end || right < start) {
			return INF;
		}

		if (left <= start && right >= end) {
			return minSegmentTree[node];
		}

		int mid = (start + end) >> 1;

		return getMin(min(node * 2, start, mid, left, right), min(node * 2 + 1, mid + 1, end, left, right));
	}

	static int getMax(int a, int b) {
		return a > b ? a : b;
	}

	static int getMin(int a, int b) {
		return a < b ? a : b;
	}

	static int maxInit(int node, int start, int end) {
		if (start == end)
			return maxSegmentTree[node] = ia[start];

		int mid = (start + end) >> 1;

		return maxSegmentTree[node] = getMax(maxInit(node * 2, start, mid), maxInit(node * 2 + 1, mid + 1, end));
	}

	static int minInit(int node, int start, int end) {
		if (start == end)
			return minSegmentTree[node] = ia[start];

		int mid = (start + end) >> 1;

		return minSegmentTree[node] = getMin(minInit(node * 2, start, mid), minInit(node * 2 + 1, mid + 1, end));
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input2357.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = new Integer(st.nextToken());
		M = new Integer(st.nextToken());

		ia = new int[N];
		minSegmentTree = new int[N << 2];
		maxSegmentTree = new int[N << 2];

		for (int i = 0; i < N; ++i) {
			ia[i] = new Integer(br.readLine());
		}

		minInit(1, 0, N - 1);
		maxInit(1, 0, N - 1);
		StringBuilder sb = new StringBuilder();
		int left, right;

		while (M-- > 0) {
			st = new StringTokenizer(br.readLine());
			left = new Integer(st.nextToken()) - 1;
			right = new Integer(st.nextToken()) - 1;

			sb.append(min(1, 0, N - 1, left, right));
			sb.append(" ");
			sb.append(max(1, 0, N - 1, left, right));
			sb.append("\n");
		}

		System.out.println(sb.toString());
		br.close();
	}
}
