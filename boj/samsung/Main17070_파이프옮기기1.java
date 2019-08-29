/**
 * @Date
 * 2019-08-29
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17070
 *
 * @문제
 * 백준 17070. 파이프옮기기1
 *
 * 유현이가 새 집으로 이사했다. 
 * 새 집의 크기는 N×N의 격자판으로 나타낼 수 있고, 1×1크기의 정사각형 칸으로 나누어져 있다.
 * 각각의 칸은 (r, c)로 나타낼 수 있다. 
 * 여기서 r은 행의 번호, c는 열의 번호이고, 행과 열의 번호는 1부터 시작한다. 각각의 칸은 빈 칸이거나 벽이다.
 * 
 * 오늘은 집 수리를 위해서 파이프 하나를 옮기려고 한다. 파이프는 아래와 같은 형태이고, 2개의 연속된 칸을 차지하는 크기이다.
 * 
 * 파이프는 매우 무겁기 때문에, 유현이는 파이프를 밀어서 이동시키려고 한다. 
 * 벽에는 새로운 벽지를 발랐기 때문에, 파이프가 벽을 긁으면 안 된다. 즉, 파이프는 항상 빈 칸만 차지해야 한다.
 * 
 * 파이프를 밀 수 있는 방향은 총 3가지가 있으며, →, ↘, ↓ 방향이다. 
 * 파이프는 밀면서 회전시킬 수 있다. 회전은 45도만 회전시킬 수 있으며, 미는 방향은 오른쪽, 아래, 또는 오른쪽 아래 대각선 방향이어야 한다.
 * 
 * 파이프가 가로로 놓여진 경우에 가능한 이동 방법은 총 2가지, 세로로 놓여진 경우에는 2가지, 대각선 방향으로 놓여진 경우에는 3가지가 있다.
 * 
 * 아래 그림은 파이프가 놓여진 방향에 따라서 이동할 수 있는 방법을 모두 나타낸 것이고, 꼭 빈 칸이어야 하는 곳은 색으로 표시되어져 있다.
 * 
 * 가장 처음에 파이프는 (1, 1)와 (1, 2)를 차지하고 있고, 방향은 가로이다. 파이프의 한쪽 끝을 (N, N)로 이동시키는 방법의 개수를 구해보자.
 *
 * @입력값
 * 첫째 줄에 집의 크기 N(3 ≤ N ≤ 16)이 주어진다. 
 * 둘째 줄부터 N개의 줄에는 집의 상태가 주어진다. 
 * 빈 칸은 0, 벽은 1로 주어진다. (1, 1)과 (1, 2)는 항상 빈 칸이다.
 *
 * 둘째 줄에는 각 시험장에 있는 응시자의 수 Ai (1 ≤ Ai ≤ 1,000,000)가 주어진다.
 *
 * 셋째 줄에는 B와 C가 주어진다. (1 ≤ B, C ≤ 1,000,000)
 *
 * @풀이방법
 * 1. 처음 위치인 0, 1에서 부터 3방향을 갈 수 있는지 확인한다.
 * 2. 조건에 맞게 갈 수 있다면 재귀를 이용하여 계속 탐색한다.
 *
 */

import java.io.*;
import java.util.*;

public class Main17070_파이프옮기기1 {
	static final int MAX_N = 16, WALL = 1;
	static final int[][] DIR = { { 0, 1 }, { 1, 1 }, { 1, 0 } }; // 우측, 대각선, 아래
	static final boolean[][] PIPE_ROUTE = { { true, true, false }, { true, true, true }, { false, true, true } };
	static int[][] map;
	static int N;
	static int answer;

	static void solve(int r, int c, int type) {
		if (r == N - 1 && c == N - 1) {
			answer++;
			return;
		}

		int nr, nc;

		for (int d = 0; d < DIR.length; ++d) {
			if (!PIPE_ROUTE[type][d])
				continue;

			nr = r + DIR[d][0];
			nc = c + DIR[d][1];

			// 범위를 초과할 때
			if (nr < 0 || nr >= N || nc < 0 || nc >= N)
				continue;

			// 다음이 벽이라면
			if (map[nr][nc] == WALL)
				continue;

			// 대각선일 때 가능한지 확인
			if (d == 1 && (map[nr][nc - 1] == WALL || map[nr - 1][nc] == WALL))
				continue;

			solve(nr, nc, d);
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input17070.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		map = new int[MAX_N][MAX_N];
		N = new Integer(br.readLine());

		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < N; ++j) {
				map[i][j] = new Integer(st.nextToken());
			}
		}

		solve(0, 1, 0);

		System.out.println(answer);
		br.close();
	}
}
