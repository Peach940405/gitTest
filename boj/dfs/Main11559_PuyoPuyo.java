/**
 * @Date
 * 2019-08-22
 *
 * @Author
 * �ֺ���
 *
 * @��ó
 * https://www.acmicpc.net/problem/11559
 *
 * @����
 * ���� 11559. Puyo Puyo
 *
 * �ѿ�ѿ��� ���� ������ ����.

 * �ʵ忡 ���� ���� ������ �ѿ並 ���´�. 
 * �ѿ�� �߷��� ������ �޾� �Ʒ��� �ٴ��̳� �ٸ� �ѿ䰡 ���� ������ �Ʒ��� ��������.

 * 1) �ѿ並 ���� �� ��, ���� �� �ѿ䰡 4�� �̻� �����¿�� ����Ǿ� ������ ����� ���� �� �ѿ���� �Ѳ����� ��������.
 * 2) �ѿ���� �������� ���� ���� �ٸ� �ѿ���� �ִٸ�, ���� �߷��� ������ �޾� ���ʴ�� �Ʒ��� �������� �ȴ�.
 * 3) �Ʒ��� �������� ���� �ٽ� ���� ���� �ѿ���� 4�� �̻� ���̰� �Ǹ� �� ������ �Ǵµ�, ���� �� �ѿ���� �������� �ٽ� ������ �ݺ��� ������ 1���⾿ �þ��.
 * 4) ���� �� �ִ� �ѿ䰡 ���� �׷��� �ִٸ� ���ÿ� ������ �ϰ� ���� �׷��� �������� �ѹ��� ���Ⱑ �߰��ȴ�.

 * ���Ⱑ �� �� �������� �Ͼ�� ����϶�.
 *
 *
 * @�Է°�
 * 12*6�� ���ڰ� �־�����.
 * �̶� .�� ������̰� .�� �ƴѰ��� ������ ������ �ѿ並 ��Ÿ����.
 * R�� ����, G�� �ʷ�, B�� �Ķ�, P�� ����, Y�� ����̴�.(��� �빮�ڷ� �־�����.)
 * �Է����� �־����� �ʵ�� �ѿ���� ���� �Ʒ��� ������ ���� ����(�� �ѿ� �Ʒ��� �� ĭ�� �ִ� ���� ����) �̴�.
 *
 *
 * @Ǯ�̹��
 * 1. �迭�� �ؿ��� ���� ���鼭 4�� �������� ���� ���� �������� Ȯ���Ѵ�.
 * 2. 4�� �̻��� ���� �������� �ִٸ� flag�� true�� �ٲٰ� ��Ʈ����.
 * 3. flag�� true��� drop�� ���� ����ؼ� �����Ѵ�.
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
