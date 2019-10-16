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
