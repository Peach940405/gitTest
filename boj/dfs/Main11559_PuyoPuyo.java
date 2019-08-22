/**
 * @Date
 * 2019-08-22
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/11559
 *
 * @문제
 * 백준 11559. Puyo Puyo
 *
 * 뿌요뿌요의 룰은 다음과 같다.

 * 필드에 여러 가지 색깔의 뿌요를 놓는다. 
 * 뿌요는 중력의 영향을 받아 아래에 바닥이나 다른 뿌요가 나올 때까지 아래로 떨어진다.

 * 1) 뿌요를 놓고 난 후, 같은 색 뿌요가 4개 이상 상하좌우로 연결되어 있으면 연결된 같은 색 뿌요들이 한꺼번에 없어진다.
 * 2) 뿌요들이 없어지고 나서 위에 다른 뿌요들이 있다면, 역시 중력의 영향을 받아 차례대로 아래로 떨어지게 된다.
 * 3) 아래로 떨어지고 나서 다시 같은 색의 뿌요들이 4개 이상 모이게 되면 또 터지게 되는데, 터진 후 뿌요들이 내려오고 다시 터짐을 반복할 때마다 1연쇄씩 늘어난다.
 * 4) 터질 수 있는 뿌요가 여러 그룹이 있다면 동시에 터져야 하고 여러 그룹이 터지더라도 한번의 연쇄가 추가된다.

 * 연쇄가 몇 번 연속으로 일어날지 계산하라.
 *
 *
 * @입력값
 * 12*6의 문자가 주어진다.
 * 이때 .은 빈공간이고 .이 아닌것은 각각의 색깔의 뿌요를 나타낸다.
 * R은 빨강, G는 초록, B는 파랑, P는 보라, Y는 노랑이다.(모두 대문자로 주어진다.)
 * 입력으로 주어지는 필드는 뿌요들이 전부 아래로 떨어진 뒤의 상태(즉 뿌요 아래에 빈 칸이 있는 경우는 없음) 이다.
 *
 *
 * @풀이방법
 * 1. 배열을 밑에서 부터 돌면서 4개 연속으로 같은 색이 나오는지 확인한다.
 * 2. 4개 이상의 색이 나온적이 있다면 flag를 true로 바꾸고 터트린다.
 * 3. flag가 true라면 drop을 시켜 계속해서 진행한다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;

public class Main11559_PuyoPuyo {
	static final int R = 12, C = 6, DOWN = 1;
	static final int[][] DIR = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
	static char[][] map;
	static boolean[][] visited;
	static int answer;

	static class Point {
		int r, c;

		Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

	static void dropPuyo(int r, int c) {
		int nr = r + DIR[DOWN][0];
		int nc = c + DIR[DOWN][1];

		if (nr < 0 || nr >= R || nc < 0 || nc >= C)
			return;

		if (map[nr][nc] != '.')
			return;

		map[nr][nc] = map[r][c];
		map[r][c] = '.';
		dropPuyo(nr, nc);
	}

	static void dropPuyo() {
		for (int i = R - 2; i >= 0; --i) {
			for (int j = 0; j < C; ++j) {
				if (map[i][j] != '.')
					dropPuyo(i, j);
			}
		}
	}

	static void removePuyo() {
		for (int i = 0; i < R; ++i) {
			for (int j = 0; j < C; ++j) {
				if (visited[i][j])
					map[i][j] = '.';
			}
		}
	}

	static int chk(int r, int c) {
		int cnt = 1;
		visited[r][c] = true;

		for (int[] dir : DIR) {
			int nr = r + dir[0];
			int nc = c + dir[1];

			if (nr < 0 || nr >= R || nc < 0 || nc >= C)
				continue;

			if (visited[nr][nc])
				continue;

			if (map[nr][nc] != map[r][c])
				continue;

			cnt += chk(nr, nc);
		}

		return cnt;
	}

	static void solve() {
		boolean flag;

		while (true) {
			flag = false;
			
			for (int i = R - 1; i >= 0; --i) {
				for (int j = 0; j < C; ++j) {
					if (map[i][j] == '.')
						continue;

					if (chk(i, j) >= 4) {
						flag = true;
						removePuyo();
					}

					visited = new boolean[R][C];
				}
			}

			if (flag) {
				answer++;
				dropPuyo();
			} else
				break;
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input11559.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		map = new char[R][C];
		visited = new boolean[R][C];

		for (int i = 0; i < R; ++i) {
			String input = br.readLine();

			for (int j = 0; j < C; ++j) {
				map[i][j] = input.charAt(j);
			}
		}

		solve();
		System.out.println(answer);

		br.close();
	}
}
