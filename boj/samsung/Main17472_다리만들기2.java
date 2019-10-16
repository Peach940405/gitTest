/**
 * @Date
 * 2019-10-16
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17472
 *
 * @문제
 * 백준 17472. 다리만들기2
 *
 * 섬으로 이루어진 나라가 있고, 모든 섬을 다리로 연결하려고 한다.
 * 이 나라의 지도는 N×M 크기의 이차원 격자로 나타낼 수 있고, 격자의 각 칸은 땅이거나 바다이다.
 * 섬은 연결된 땅이 상하좌우로 붙어있는 덩어리를 말하고, 아래 그림은 네 개의 섬으로 이루어진 나라이다. 색칠되어있는 칸은 땅이다.
 * 
 * 다리는 바다에만 건설할 수 있고, 다리의 길이는 다리가 격자에서 차지하는 칸의 수이다. 
 * 다리를 연결해서 모든 섬을 연결하려고 한다. 섬 A에서 다리를 통해 섬 B로 갈 수 있을 때, 섬 A와 B를 연결되었다고 한다. 
 * 다리의 양 끝은 섬과 인접한 바다 위에 있어야 하고, 한 다리의 방향이 중간에 바뀌면 안된다. 
 * 또, 다리의 길이는 2 이상이어야 한다.
 * 다리의 방향이 중간에 바뀌면 안되기 때문에, 다리의 방향은 가로 또는 세로가 될 수 밖에 없다. 
 * 방향이 가로인 다리는 다리의 양 끝이 가로 방향으로 섬과 인접해야 하고, 
 * 방향이 세로인 다리는 다리의 양 끝이 세로 방향으로 섬과 인접해야 한다.
 * 섬 A와 B를 연결하는 다리가 중간에 섬 C와 인접한 바다를 지나가는 경우에 섬 C는 A, B와 연결되어있는 것이 아니다. 
 * 
 * 나라의 정보가 주어졌을 때, 모든 섬을 연결하는 다리 길이의 최솟값을 구해보자. 
 * 
 * @입력값
 * 첫째 줄에 지도의 세로 크기 N과 가로 크기 M이 주어진다. 
 * 둘째 줄부터 N개의 줄에 지도의 정보가 주어진다. 
 * 각 줄은 M개의 수로 이루어져 있으며, 수는 0 또는 1이다. 0은 바다, 1은 땅을 의미한다.
 *
 * @풀이방법
 * 1. 섬을 구별하기 위해 재귀를 돌면서 섬 번호를 매겨준다.
 * 2. map을 전체 돌면서 섬이 나오면 다리를 만들 수 있는지 확인한다.
 * 3. mst를 이용하여 다리를 연결한다.(kruskal 이용)
 *
 */

import java.io.*;
import java.util.*;

class Bridge implements Comparable<Bridge> {
	int u, v, weight;

	public Bridge(int u, int v, int weight) {
		this.u = u;
		this.v = v;
		this.weight = weight;
	}

	@Override
	public int compareTo(Bridge o) {
		return this.weight - o.weight;
	}
}

public class Main17472_다리만들기2 {
	static final int MAX_NM = 12, MAX_ISLAND = 7;
	static final int[][] DIR = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	static int[][] map;
	static int[] island;
	static int N, M, islandCnt, bridgeCnt;
	static int answer;

	static boolean union(int u, int v) {
		u = find(u);
		v = find(v);

		if (u == v) {
			return false;
		}

		island[v] = u;
		return true;
	}

	static int find(int v) {
		if (island[v] == v) {
			return v;
		}

		return island[v] = find(island[v]);
	}

	static void setIsland(int r, int c) {
		int nr, nc;
		map[r][c] = islandCnt;

		for (int[] dir : DIR) {
			nr = r + dir[0];
			nc = c + dir[1];

			if (map[nr][nc] != -1) {
				continue;
			}

			setIsland(nr, nc);
		}
	}

	static boolean chk(int r, int c) {
		return r >= 1 && r <= N && c >= 1 && c <= M;
	}

	static void solve() {
		// 1. 섬 세팅

		for (int i = 1; i <= N; ++i) {
			for (int j = 1; j <= M; ++j) {
				if (map[i][j] >= 0) {
					continue;
				}

				// 섬 갯수가 증가되고 mst를 위해 island 초기화
				island[++islandCnt] = islandCnt;
				setIsland(i, j);
			}
		}

		// 2. 다리 만들기
		Queue<Bridge> pQ = new PriorityQueue<>();
		int curIsland, r, c, weight;

		for (int i = 1; i <= N; ++i) {
			for (int j = 1; j <= M; ++j) {
				if (map[i][j] == 0) {
					continue;
				}

				curIsland = map[i][j];
				
				for (int[] dir : DIR) {
					r = i + dir[0];
					c = j + dir[1];
					weight = 0;

					while (chk(r, c) && map[r][c] == 0) {
						r += dir[0];
						c += dir[1];
						++weight;
					}

					// 다리 길이가 2 이하거나 범위 초과거나 현재 다리끼리 연결시
					if (weight < 2 || map[r][c] == 0 || curIsland == map[r][c]) {
						continue;
					}

					pQ.offer(new Bridge(curIsland, map[r][c], weight));
				}
			}
		}

		// 3. mst를 이용해 다리 연결하기
		Bridge bridge;

		while (!pQ.isEmpty()) {
			bridge = pQ.poll();

			if (!union(bridge.u, bridge.v)) {
				continue;
			}

			++bridgeCnt;
			answer += bridge.weight;
		}
	}

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input17472.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		map = new int[MAX_NM][MAX_NM];
		island = new int[MAX_ISLAND];

		st = new StringTokenizer(br.readLine());
		N = new Integer(st.nextToken());
		M = new Integer(st.nextToken());
		int input;

		for (int i = 1; i <= N; ++i) {
			st = new StringTokenizer(br.readLine());
			
			for (int j = 1; j <= M; ++j) {
				input = new Integer(st.nextToken());
				
				if (input == 0) {
					continue;
				}
				
				map[i][j] = -1;
			}
		}

		solve();
		
		System.out.println(bridgeCnt == islandCnt - 1 ? answer : -1);
		br.close();
	}
}
