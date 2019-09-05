
/**
 * @Date
 * 2019-09-05
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5V1SYKAaUDFAWu
 *
 * @문제
 * swea 2112. [모의 SW 역량테스트] 보호필름
 *
 * 성능이 우수한 보호 필름을 제작하려고 한다.
 * 보호 필름은 [Fig. 1]와 같은 엷은 투명한 막을 D장 쌓아서 제작된다.
 * 막은 [Fig. 1]과 같이 동일한 크기를 가진 바(bar) 모양의 셀들이 가로 방향으로 W개 붙여서 만들어진다.
 * 이렇게 제작된 필름은 두께 D, 가로 크기 W의 보호 필름이라고 한다.
 * 
 * 보호 필름의 성능을 검사하기 위해 합격기준 K라는 값을 사용한다.
 * 충격은 보호 필름 단면의 세로 방향으로 가해지므로, 세로 방향 셀들의 특성이 중요하다.
 * 단면의 모든 세로방향에 대해서 동일한 특성의 셀들이 K개 이상 연속적으로 있는 경우에만 성능검사를 통과하게 된다.
 * 
 * 성능검사에 통과하기 위해서 약품을 사용하여야 한다.
 * 약품은 막 별로 투입할 수 있으며 이 경우 투입하는 막의 모든 셀들은 하나의 특성으로 변경된다.
 * 특정 막에 약품 A를 투입하면 막 내의 모든 셀들이 특성 A로 변경되며, 약품 B를 넣게 되면 특성이 모두 특성 B로 변경된다.
 *
 * 두께 D, 가로크기 W인 보호 필름 단면의 정보와 합격기준 K가 주어졌을 때, 약품 투입 횟수를 최소로 하여 성능검사를 통과할 수 있는 방법을 찾고,
 * 이때의 약품 투입 횟수를 출력하라.
 * 약품을 투입하지 않고도 성능검사를 통과하는 경우에는 0을 출력한다.
 * 
 * @입력값
 * 보호 필름의 두께 D (3<= D <= 13), 가로크기 W (1 <= W <= 10), 합격기준 K ( 1 <= K <= D)
 * 다음 D줄에 보호 필름 단면의 정보가 주어진다. 각 줄에는 셀들의 특성 W개가 주어진다. (특성A는 0, 특성B는 1로 표시된다.)
 *
 * @풀이방법
 * 1. 재귀를 이용하여 모든 경우를 봐야한다.
 * 2. visited를 이용하여 true 곳을 약품을 변경해준다.
 * 3. 0테스트 후 1을 테스트 하고 원래의 배열로 돌린다.
 * 4. 검사가 ok 된다면 그때의 최솟값을 구한다.
 *
 */

import java.io.*;
import java.util.*;

public class Solution2112_보호필름 {
	static final int MAX_D = 13, MAX_W = 20;

	static int[][] map, tmpMap;
	static boolean[] visited;
	static int D, W, K;
	static int answer;

	static boolean testOk() {
		int n, cnt;

		loop: for (int c = 0; c < W; ++c) {
			for (int r = 0; r < D - K + 1; ++r) {
				cnt = 1;
				n = map[r][c];

				for (int i = 1; i < K; ++i) {
					if (map[r + i][c] == n)
						++cnt;
				}

				if (cnt == K)
					continue loop;

				if (r == D - K)
					return false;
			}
		}

		return true;
	}

	static void change(int r, int n) {
		for (int c = 0; c < W; ++c) {
			map[r][c] = n;
		}
	}

	static void arrayCopy(int[][] a, int[][] b) {
		for (int i = 0; i < D; ++i) {
			for (int j = 0; j < W; ++j) {
				a[i][j] = b[i][j];
			}
		}
	}

	static void test() {
		arrayCopy(tmpMap, map);
		// 0 테스트
		for (int i = 0; i < D; ++i) {
			if (visited[i])
				change(i, 0);
		}

		if (testOk()) {
			int cnt = 0;

			for (int i = 0; i < D; ++i) {
				if (visited[i])
					cnt++;
			}

			answer = answer < cnt ? answer : cnt;
		}

		// 1 테스트
		for (int i = 0; i < D; ++i) {
			if (visited[i])
				change(i, 1);
		}

		if (testOk()) {
			int cnt = 0;

			for (int i = 0; i < D; ++i) {
				if (visited[i])
					cnt++;
			}

			answer = answer < cnt ? answer : cnt;
		}
		arrayCopy(map, tmpMap);
	}

	static void solve(int depth, int idx) {
		if (depth == D) {
			test();
			return;
		}

		visited[depth] = true;
		solve(depth + 1, idx + 1);
		visited[depth] = false;
		solve(depth + 1, idx);
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = new Integer(br.readLine());

		map = new int[MAX_D][MAX_W];
		tmpMap = new int[MAX_D][MAX_W];

		for (int tc = 1; tc <= T; ++tc) {
			st = new StringTokenizer(br.readLine());
			D = new Integer(st.nextToken());
			W = new Integer(st.nextToken());
			K = new Integer(st.nextToken());

			for (int i = 0; i < D; ++i) {
				st = new StringTokenizer(br.readLine());

				for (int j = 0; j < W; ++j) {
					map[i][j] = new Integer(st.nextToken());
				}
			}

			visited = new boolean[D];
			answer = 987654321;
			if (testOk())
				answer = 0;
			else
				solve(0, 0);

			System.out.println("#" + tc + " " + answer);
		}

		br.close();
	}
}
