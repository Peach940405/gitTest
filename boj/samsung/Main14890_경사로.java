/**
 * @Date
 * 2019-08-22
 *
 * @Author
 * �ֺ���
 *
 * @��ó
 * https://www.acmicpc.net/problem/14890
 *
 * @����
 * ���� 14890. ����
 *
 * ũ�Ⱑ N��N�� ������ �ִ�. ������ �� ĭ���� �� ���� ���̰� ������ �ִ�. 
 * ������ �� �������� ������ �� �ִ� ���� �� �� �ִ��� �˾ƺ����� �Ѵ�. 
 * ���̶� �� �� �Ǵ� �� �� ���θ� ��Ÿ����, ���� ������ �ٸ��� ������ �������� ���̴�. 
 * 
 * ���� ������ �� �������� �濡 ���� ��� ĭ�� ���̰� ��� ���ƾ� �Ѵ�. �Ǵ�, ���θ� ���Ƽ� ������ �� �ִ� ���� ���� �� �ִ�. ���δ� ���̰� �׻� 1�̸�, ���̴� L�̴�. ��, ������ �ſ� ���� ������ ���� ����. ���δ� ���� ĭ�� ���� ĭ�� �����ϸ�, �Ʒ��� ���� ������ �����ؾ��Ѵ�
 * ���δ� ���� ĭ�� ������, L���� ���ӵ� ĭ�� ������ �ٴ��� ��� ���ؾ� �Ѵ�.
 * ���� ĭ�� ���� ĭ�� ���� ���̴� 1�̾�� �Ѵ�.
 * ���θ� ���� ���� ĭ�� ���̴� ��� ���ƾ� �ϰ�, L���� ĭ�� ���ӵǾ� �־�� �Ѵ�.
 * 
 * ���θ� ���� ���� �� ���θ� ���� ���
 * ���� ĭ�� ���� ĭ�� ���� ���̰� 1�� �ƴ� ���
 * ���� ������ ĭ�� ���̰� ��� ���� �ʰų�, L���� ���ӵ��� ���� ���
 * ���θ� ���ٰ� ������ ����� ���
 * 
 * ������ �־����� ��, ������ �� �ִ� ���� ������ ���ϴ� ���α׷��� �ۼ��Ͻÿ�.
 *
 * @�Է°�
 * ù° �ٿ� N (2 �� N �� 100)�� L (1 �� L �� N)�� �־�����. 
 * ��° �ٺ��� N���� �ٿ� ������ �־�����. 
 * �� ĭ�� ���̴� 10���� �۰ų� ���� �ڿ����̴�.
 *
 * @Ǯ�̹��
 * 1. ��͸� �̿��Ͽ� ���θ� ���� �� �ִ� �� Ȯ���Ѵ�.
 * 2. ���� ��ġ�� ���̰� ���ٸ� ����ؼ� ����.
 * 3. ���� ��ġ�� �۴ٸ� ���� ��ġ ���� ���� L���� ���θ� ���� �� �ִ��� Ȯ��
 * 4. ���� ��ġ�� ũ�ٸ� ���� ��ġ ���� ���� L���� ���ΰ� �̹� ��ġ�Ǿ��ų� ���� �� �ִ��� Ȯ��
 * 
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main14890_���� {
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
				if (c + i >= N) // ������ ��� ��
					return false;
				if (map[r][c + i] != map[r][c] - 1) // ���� ���̰� 1�� �ƴ� ��
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
				if (visited[r][c - i]) // �̹� ���� ������ ���
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
				if (r + i >= N) // ������ ��� ��
					return false;
				if (map[r + i][c] != map[r][c] - 1) // ���� ���̰� �ƴ� ��
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
				if (visited[r - i][c]) // �̹� ���� ������ ���
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
