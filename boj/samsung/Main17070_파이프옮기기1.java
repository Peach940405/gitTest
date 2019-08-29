/**
 * @Date
 * 2019-08-29
 *
 * @Author
 * �ֺ���
 *
 * @��ó
 * https://www.acmicpc.net/problem/17070
 *
 * @����
 * ���� 17070. �������ű��1
 *
 * �����̰� �� ������ �̻��ߴ�. 
 * �� ���� ũ��� N��N�� ���������� ��Ÿ�� �� �ְ�, 1��1ũ���� ���簢�� ĭ���� �������� �ִ�.
 * ������ ĭ�� (r, c)�� ��Ÿ�� �� �ִ�. 
 * ���⼭ r�� ���� ��ȣ, c�� ���� ��ȣ�̰�, ��� ���� ��ȣ�� 1���� �����Ѵ�. ������ ĭ�� �� ĭ�̰ų� ���̴�.
 * 
 * ������ �� ������ ���ؼ� ������ �ϳ��� �ű���� �Ѵ�. �������� �Ʒ��� ���� �����̰�, 2���� ���ӵ� ĭ�� �����ϴ� ũ���̴�.
 * 
 * �������� �ſ� ���̱� ������, �����̴� �������� �о �̵���Ű���� �Ѵ�. 
 * ������ ���ο� ������ �߶��� ������, �������� ���� ������ �� �ȴ�. ��, �������� �׻� �� ĭ�� �����ؾ� �Ѵ�.
 * 
 * �������� �� �� �ִ� ������ �� 3������ ������, ��, ��, �� �����̴�. 
 * �������� �и鼭 ȸ����ų �� �ִ�. ȸ���� 45���� ȸ����ų �� ������, �̴� ������ ������, �Ʒ�, �Ǵ� ������ �Ʒ� �밢�� �����̾�� �Ѵ�.
 * 
 * �������� ���η� ������ ��쿡 ������ �̵� ����� �� 2����, ���η� ������ ��쿡�� 2����, �밢�� �������� ������ ��쿡�� 3������ �ִ�.
 * 
 * �Ʒ� �׸��� �������� ������ ���⿡ ���� �̵��� �� �ִ� ����� ��� ��Ÿ�� ���̰�, �� �� ĭ�̾�� �ϴ� ���� ������ ǥ�õǾ��� �ִ�.
 * 
 * ���� ó���� �������� (1, 1)�� (1, 2)�� �����ϰ� �ְ�, ������ �����̴�. �������� ���� ���� (N, N)�� �̵���Ű�� ����� ������ ���غ���.
 *
 * @�Է°�
 * ù° �ٿ� ���� ũ�� N(3 �� N �� 16)�� �־�����. 
 * ��° �ٺ��� N���� �ٿ��� ���� ���°� �־�����. 
 * �� ĭ�� 0, ���� 1�� �־�����. (1, 1)�� (1, 2)�� �׻� �� ĭ�̴�.
 *
 * ��° �ٿ��� �� �����忡 �ִ� �������� �� Ai (1 �� Ai �� 1,000,000)�� �־�����.
 *
 * ��° �ٿ��� B�� C�� �־�����. (1 �� B, C �� 1,000,000)
 *
 * @Ǯ�̹��
 * 1. ó�� ��ġ�� 0, 1���� ���� 3������ �� �� �ִ��� Ȯ���Ѵ�.
 * 2. ���ǿ� �°� �� �� �ִٸ� ��͸� �̿��Ͽ� ��� Ž���Ѵ�.
 *
 */

import java.io.*;
import java.util.*;

public class Main17070_�������ű��1 {
	static final int MAX_N = 16, WALL = 1;
	static final int[][] DIR = { { 0, 1 }, { 1, 1 }, { 1, 0 } }; // ����, �밢��, �Ʒ�
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

			// ������ �ʰ��� ��
			if (nr < 0 || nr >= N || nc < 0 || nc >= N)
				continue;

			// ������ ���̶��
			if (map[nr][nc] == WALL)
				continue;

			// �밢���� �� �������� Ȯ��
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
