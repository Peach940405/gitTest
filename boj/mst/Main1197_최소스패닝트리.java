/**
 * @Date
 * 2019-10-08
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/1197
 *
 * @문제
 * 백준 1197. 최소 스패닝 트리
 *
 * 그래프가 주어졌을 때, 그 그래프의 최소 스패닝 트리를 구하는 프로그램을 작성하시오.
 * 
 * 최소 스패닝 트리는, 주어진 그래프의 모든 정점들을 연결하는 부분 그래프 중에서 
 * 그 가중치의 합이 최소인 트리를 말한다.
 *
 * @풀이방법
 * 1. mst문제이다. 크루스칼 방법을 이용하자.
 * 2. union-find 를 활용하여 가장 최소의 방법을 선택하자.
 *
 */

package algo.mst;

import java.io.*;
import java.util.*;

public class Main1197_최소스패닝트리 {
	static final int MAX_V = 10001, MAX_E = 100001;

	static List<Edge> edges;
	static int[] root;
	static int V, E;
	static int answer;

	static class Edge implements Comparable<Edge> {
		int u, v, weight;

		Edge(int u, int v, int weight) {
			this.u = u;
			this.v = v;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			return this.weight - o.weight;
		}
	}

	static void union(int x, int y) {
		root[y] = x;
	}

	static int find(int x) {
		if (root[x] == x) {
			return x;
		}

		return root[x] = find(root[x]);
	}

	static void solve() {
		Collections.sort(edges);

		for (int i = 1; i <= V; ++i) {
			root[i] = i;
		}

		int u, v;

		for (Edge edge : edges) {
			u = find(edge.u);
			v = find(edge.v);

			if (u == v) {
				continue;
			}

			union(u, v);
			answer += edge.weight;
		}
	}

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input1197.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		edges = new ArrayList<>();
		root = new int[MAX_V];

		st = new StringTokenizer(br.readLine());
		V = new Integer(st.nextToken());
		E = new Integer(st.nextToken());

		int u, v, weight;

		for (int i = 0; i < E; ++i) {
			st = new StringTokenizer(br.readLine());
			u = new Integer(st.nextToken());
			v = new Integer(st.nextToken());
			weight = new Integer(st.nextToken());

			edges.add(new Edge(u, v, weight));
		}

		solve();

		System.out.println(answer);
		
		br.close();
	}
}
