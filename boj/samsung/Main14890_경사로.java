/**
 * @Date
 * 2019-08-22
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/14890
 *
 * @문제
 * 백준 14890. 경사로
 *
 * 크기가 N×N인 지도가 있다. 지도의 각 칸에는 그 곳의 높이가 적혀져 있다. 
 * 오늘은 이 지도에서 지나갈 수 있는 길이 몇 개 있는지 알아보려고 한다. 
 * 길이란 한 행 또는 한 열 전부를 나타내며, 한쪽 끝에서 다른쪽 끝까지 지나가는 것이다. 
 * 
 * 길을 지나갈 수 있으려면 길에 속한 모든 칸의 높이가 모두 같아야 한다. 또는, 경사로를 놓아서 지나갈 수 있는 길을 만들 수 있다. 경사로는 높이가 항상 1이며, 길이는 L이다. 또, 개수는 매우 많아 부족할 일이 없다. 경사로는 낮은 칸과 높은 칸을 연결하며, 아래와 같은 조건을 만족해야한다
 * 경사로는 낮은 칸에 놓으며, L개의 연속된 칸에 경사로의 바닥이 모두 접해야 한다.
 * 낮은 칸과 높은 칸의 높이 차이는 1이어야 한다.
 * 경사로를 놓을 낮은 칸의 높이는 모두 같아야 하고, L개의 칸이 연속되어 있어야 한다.
 * 
 * 경사로를 놓은 곳에 또 경사로를 놓는 경우
 * 낮은 칸과 높은 칸의 높이 차이가 1이 아닌 경우
 * 낮은 지점의 칸의 높이가 모두 같지 않거나, L개가 연속되지 않은 경우
 * 경사로를 놓다가 범위를 벗어나는 경우
 * 
 * 지도가 주어졌을 때, 지나갈 수 있는 길의 개수를 구하는 프로그램을 작성하시오.
 *
 * @입력값
 * 첫째 줄에 N (2 ≤ N ≤ 100)과 L (1 ≤ L ≤ N)이 주어진다. 
 * 둘째 줄부터 N개의 줄에 지도가 주어진다. 
 * 각 칸의 높이는 10보다 작거나 같은 자연수이다.
 *
 * @풀이방법
 * 1. 재귀를 이용하여 경사로를 놓을 수 있는 지 확인한다.
 * 2. 다음 위치의 높이가 같다면 계속해서 간다.
 * 3. 다음 위치가 작다면 다음 위치 부터 길이 L까지 경사로를 세울 수 있는지 확인
 * 4. 다음 위치가 크다면 현재 위치 부터 길이 L까지 경사로가 이미 설치되었거나 세울 수 있는지 확인
 * 
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main14890_경사로 {
	static final int MAP_MAX = 100;
	static int[][] map;
	static boolean[][] visited;
	static int N, L;
	static int answer;

	static boolean chkCol(int r, int c) {
		if (c == N - 1)
			return true;

		if (map[r][c] == map[r][c + 1])
			return chkCol(r, c + 1);

		else if (map[r][c] > map[r][c + 1]) {
			for (int i = 1; i <= L; ++i) {
				if (c + i >= N) // 범위를 벗어날 때
					return false;
				if (map[r][c + i] != map[r][c] - 1) // 높이 차이가 1이 아닐 때
					return false;
				visited[r][c + i] = true;
			}
			return chkCol(r, c + L);
		} else {
			for (int i = 0; i < L; ++i) {
				if (c - i < 0)
					return false;
				if (map[r][c - i] != map[r][c + 1] - 1)
					return false;
				if (visited[r][c - i]) // 이미 사용된 경사로일 경우
					return false;
			}
			return chkCol(r, c + 1);
		}
	}

	static boolean chkRow(int r, int c) {
		if (r == N - 1)
			return true;

		if (map[r][c] == map[r + 1][c])
			return chkRow(r + 1, c);

		else if (map[r][c] > map[r + 1][c]) {
			for (int i = 1; i <= L; ++i) {
				if (r + i >= N) // 범위를 벗어날 때
					return false;
				if (map[r + i][c] != map[r][c] - 1) // 같은 높이가 아닐 때
					return false;
				visited[r + i][c] = true;
			}
			return chkRow(r + L, c);
		} else {
			for (int i = 0; i < L; ++i) {
				if (r - i < 0)
					return false;
				if (map[r - i][c] != map[r + 1][c] - 1)
					return false;
				if (visited[r - i][c]) // 이미 사용된 경사로일 경우
					return false;
			}
			return chkRow(r + 1, c);
		}
	}

	static void solve() {
		for (int i = 0; i < N; ++i) {
			visited = new boolean[MAP_MAX][MAP_MAX];
			if (chkRow(0, i))
				answer++;

			visited = new boolean[MAP_MAX][MAP_MAX];
			if (chkCol(i, 0))
				answer++;
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input14890.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		map = new int[MAP_MAX][MAP_MAX];

		st = new StringTokenizer(br.readLine());
		N = new Integer(st.nextToken());
		L = new Integer(st.nextToken());

		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < N; ++j) {
				map[i][j] = new Integer(st.nextToken());
			}
		}

		solve();
		System.out.println(answer);

		br.close();
	}

}
