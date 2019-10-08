/**
 * @Date
 * 2019-10-08
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
 * 섬은 연결된 땅이 상하좌우로 붙어있는 덩어리를 말한다.
 * 
 * 다리는 바다에만 건설할 수 있고, 다리의 길이는 다리가 격자에서 차지하는 칸의 수이다. 
 * 다리를 연결해서 모든 섬을 연결하려고 한다. 
 * 섬 A에서 다리를 통해 섬 B로 갈 수 있을 때, 섬 A와 B를 연결되었다고 한다. 
 * 다리의 양 끝은 섬과 인접한 바다 위에 있어야 하고, 한 다리의 방향이 중간에 바뀌면 안된다. 
 * 또, 다리의 길이는 2 이상이어야 한다.
 * 다리의 방향이 중간에 바뀌면 안되기 때문에, 다리의 방향은 가로 또는
 * 세로인 다리는 다리의 양 끝이 세로 방향으로 섬과 인접해야 한다.
 * 
 * 나라의 정보가 주어졌을 때, 모든 섬을 연결하는 다리 길이의 최솟값을 구해보자.
 *
 * @풀이방법
 * 1. 완전탐색을 이용하여 인접한 섬 번호를 매겨준다.
 * 2. 번호를 매겨주는 동시에 주위가 바다와 인접해 있다면 좌표를 저장해준다.
 * 3. 섬을 처음 부터 돌면서 좌표를 모두 비교한다.
 * 4. 좌표에서 다음 섬으로 갈 수 있다면 다리를 생성한다. (길이는 2 이상)
 * 5. 모든 다리를 길이 순으로 오름차순 정렬한다.
 * 6. kruskal 방법을 활용하여 mst를 완성한다.
 *
 */

package algo.samsung;

import java.io.*;
import java.util.*;

public class Main17472_다리만들기2 {
	static final int MAX_NM = 10, MAX_ISLAND = 7;
	static final int[][] DIR = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	static List<Bridge> bridges;
	static Island[] islands;
	static int[][] map;
	static int[] root;
	static int N, M, islandIdx;
	static int answer;

	static class Pos {
		int r, c;

		Pos(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

	static class Island {
		List<Pos> positions;

		Island() {
			positions = new ArrayList<>();
		}
	}

	static class Bridge implements Comparable<Bridge> {
		int u, v, weight;

		Bridge(int u, int v, int weight) {
			this.u = u;
			this.v = v;
			this.weight = weight;
		}

		@Override
		public int compareTo(Bridge o) {
			return this.weight - o.weight;
		}
	}

	static void union(int u, int v) {
		root[v] = u;
	}

	static int find(int v) {
		if (root[v] == v) {
			return v;
		}

		return root[v] = find(root[v]);
	}

	static boolean chk(Pos pos) {
		return pos.r >= 0 && pos.r < N && pos.c >= 0 && pos.c < M;
	}

	static void setBridge(Pos pos, int islandNum) {
		Pos curPos;
		int len = 0, dest = 0;

		for (int[] dir : DIR) {
			curPos = new Pos(pos.r, pos.c);
			len = dest = -1;

			do {
				curPos.r += dir[0];
				curPos.c += dir[1];
				++len;
			} while (chk(curPos) && map[curPos.r][curPos.c] == 0);

			if (chk(curPos)) {
				dest = map[curPos.r][curPos.c];
			}

			// 다리의 길이가 2 이상이어야한다.
			if (len < 2 || dest == -1) {
				continue;
			}

			bridges.add(new Bridge(islandNum, dest, len));
		}
	}

	static void setIsland(Pos pos) {
		map[pos.r][pos.c] = islandIdx;

		int nr, nc;
		int len = 0;

		for (int[] dir : DIR) {
			nr = pos.r + dir[0];
			nc = pos.c + dir[1];

			if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
				continue;
			}

			if (map[nr][nc] == -1) {
				setIsland(new Pos(nr, nc));
			} else if (map[nr][nc] == 0) {
				++len;
			}
		}

		if (len > 0) {
			islands[islandIdx].positions.add(pos);
		}
	}

	static void solve() {
		// 1. 섬 구별하기
		islandIdx = 1; // 섬은 1번 부터

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				if (map[i][j] == -1) {
					setIsland(new Pos(i, j));
					root[islandIdx] = islandIdx++;
				}
			}
		}

		// 2. 다리 이어주기
		for (int i = 1; i < islandIdx; ++i) {
			for (Pos pos : islands[i].positions) {
				setBridge(pos, i);
			}
		}

		// 3. kruskal 방법을 활용하여 최소 간선 선택하기
		Collections.sort(bridges);
		int u, v;

		for (Bridge bridge : bridges) {
			u = find(bridge.u);
			v = find(bridge.v);

			if (u == v) {
				continue;
			}

			union(u, v);
			answer += bridge.weight;
		}

		// 4. 모든 다리가 연결되어 있는지 확인
		int cmp = find(1);
		for (int i = 2; i < islandIdx; ++i) {
			if (cmp == find(i)) {
				continue;
			}

			answer = -1;
			break;
		}
	}

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input17472.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		bridges = new ArrayList<>();
		map = new int[MAX_NM][MAX_NM];
		islands = new Island[MAX_ISLAND];
		root = new int[MAX_ISLAND];

		for (int i = 1; i < MAX_ISLAND; ++i) {
			islands[i] = new Island();
		}

		st = new StringTokenizer(br.readLine());
		N = new Integer(st.nextToken());
		M = new Integer(st.nextToken());

		int input;

		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < M; ++j) {
				input = new Integer(st.nextToken());

				if (input == 0) {
					continue;
				}

				map[i][j] = -1;
			}
		}

		solve();

		System.out.println(answer);

		br.close();
	}
}
