/**
 * @Date
 * 2019-10-08
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17471
 *
 * @문제
 * 백준 17471. 게리맨더링
 *
 * 백준시의 시장 최백준은 지난 몇 년간 게리맨더링을 통해서 자신의 당에게 유리하게 선거구를 획정했다.
 * 견제할 권력이 없어진 최백준은 권력을 매우 부당하게 행사했고, 심지어는 시의 이름도 백준시로 변경했다.
 * 이번 선거에서는 최대한 공평하게 선거구를 획정하려고 한다.
 * 
 * 백준시는 N개의 구역으로 나누어져 있고, 구역은 1번부터 N번까지 번호가 매겨져 있다.
 * 구역을 두 개의 선거구로 나눠야 하고, 각 구역은 두 선거구 중 하나에 포함되어야 한다.
 * 선거구는 구역을 적어도 하나 포함해야 하고, 한 선거구에 포함되어 있는 구역은 모두 연결되어 있어야 한다.
 * 구역 A에서 인접한 구역을 통해서 구역 B로 갈 수 있을 때, 두 구역은 연결되어 있다고 한다.
 * 중간에 통하는 인접한 구역은 0개 이상이어야 하고, 모두 같은 선거구에 포함된 구역이어야 한다.
 * 
 * 공평하게 선거구를 나누기 위해 두 선거구에 포함된 인구의 차이를 최소로 하려고 한다.
 * 백준시의 정보가 주어졌을 때, 인구 차이의 최솟값을 구해보자.
 *
 * @입력값
 * 첫째 줄에 구역의 개수 N이 주어진다. (2 <= N <= 10)
 * 둘째 줄에 구역의 인구가 1번 구역부터 N번 구역까지 순서대로 주어진다. 인구는 공백으로 구분되어져 있다.
 * 셋째 줄부터 N개의 줄에 각 구역과 인접한 구역의 정보가 주어진다.
 * 각 정보의 첫 번째 정수는 그 구역과 인접한 구역의 수이고, 이후 인접한 구역의 번호가 주어진다.
 * 모든 값은 정수로 구분되어져 있다.
 * 구역 A가 구역 B와 인접하면 구역 B도 구역 A와 인접하다. 인접한 구역이 없을 수도 있다.
 *
 * @풀이방법
 * 1. 완전탐색을 이용하여 구역을 나눈다.
 * 2. 구역을 나눈 후 chk함수를 이용하여 마을 간에 서로 이동이 가능한지 확인한다.
 * 3. chk 함수에서 선택된 첫 구역을 q에 넣고 탐색을 시작한다.
 * 4. q에 구역이 없어질 때까지 갈 수 있는 경로가 있다면 q에 넣어준다.
 * 5. 두 구역다 이동이 가능하다면 계산 결과를 최소값으로 갱신한다.
 *
 */

package algo.samsung;

import java.io.*;
import java.util.*;

public class Main17471_게리맨더링 {
	static final int INF = 987654321, MAX_N = 101;

	static int[] personCnt;
	static int[][] graph;
	static boolean[] area;
	static int N;
	static int answer;

	static int calRes() {
		int cntA = 0, cntB = 0;

		for (int i = 1; i <= N; ++i) {
			if (area[i]) {
				cntA += personCnt[i];
			} else {
				cntB += personCnt[i];
			}
		}

		return Math.abs(cntA - cntB);
	}

	static boolean chk(boolean areaState) {
		boolean[] visited = new boolean[N + 1];
		Queue<Integer> q = new LinkedList<>();

		for (int i = 1; i <= N; ++i) {
			// 같은 지역중 1개를 q에 넣자.
			if (area[i] != areaState) {
				continue;
			}

			q.offer(i);
			visited[i] = true;
			break;
		}

		int cur;

		while (!q.isEmpty()) {
			cur = q.poll();

			// 현재 구역에서 갈 수 있는 곳을 확인하자.
			for (int i = 1; i <= N; ++i) {
				// 이미 방문 했거나 선택된 구역이 아니거나 갈 수 없는 경로라면 넘어간다.
				if (visited[i] || area[i] != areaState || graph[cur][i] == 0) {
					continue;
				}

				q.offer(i);
				visited[i] = true;
			}
		}

		for (int i = 1; i <= N; ++i) {
			if (area[i] != areaState) {
				continue;
			}

			// 선택된 구역중에서 갈 수 없는 곳이 있다면 불가능하다.
			if (!visited[i]) {
				return false;
			}
		}

		return true;
	}

	static void solve(int depth) {
		if (depth == N + 1) {
			if (chk(true) && chk(false)) {
				answer = Math.min(answer, calRes());
			}

			return;
		}

		area[depth] = true;
		solve(depth + 1);
		area[depth] = false;
		solve(depth + 1);
	}

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input17471.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = new Integer(br.readLine());
		personCnt = new int[MAX_N];
		graph = new int[MAX_N][MAX_N];
		area = new boolean[N + 1];
		answer = INF;

		st = new StringTokenizer(br.readLine());

		for (int i = 1; i <= N; ++i) {
			personCnt[i] = new Integer(st.nextToken());
		}

		int cnt, vertex;

		for (int i = 1; i <= N; ++i) {
			st = new StringTokenizer(br.readLine());

			cnt = new Integer(st.nextToken());

			for (int j = 0; j < cnt; ++j) {
				vertex = new Integer(st.nextToken());

				graph[i][vertex] = graph[vertex][i] = 1;
			}
		}

		solve(1);

		System.out.println(answer == INF ? -1 : answer);

		br.close();
	}
}
