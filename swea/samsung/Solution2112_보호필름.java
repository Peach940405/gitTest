
/**
 * @Date
 * 2019-09-05
 *
 * @Author
 * �ֺ���
 *
 * @��ó
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5V1SYKAaUDFAWu
 *
 * @����
 * swea 2112. [���� SW �����׽�Ʈ] ��ȣ�ʸ�
 *
 * ������ ����� ��ȣ �ʸ��� �����Ϸ��� �Ѵ�.
 * ��ȣ �ʸ��� [Fig. 1]�� ���� ���� ������ ���� D�� �׾Ƽ� ���۵ȴ�.
 * ���� [Fig. 1]�� ���� ������ ũ�⸦ ���� ��(bar) ����� ������ ���� �������� W�� �ٿ��� ���������.
 * �̷��� ���۵� �ʸ��� �β� D, ���� ũ�� W�� ��ȣ �ʸ��̶�� �Ѵ�.
 * 
 * ��ȣ �ʸ��� ������ �˻��ϱ� ���� �հݱ��� K��� ���� ����Ѵ�.
 * ����� ��ȣ �ʸ� �ܸ��� ���� �������� �������Ƿ�, ���� ���� ������ Ư���� �߿��ϴ�.
 * �ܸ��� ��� ���ι��⿡ ���ؼ� ������ Ư���� ������ K�� �̻� ���������� �ִ� ��쿡�� ���ɰ˻縦 ����ϰ� �ȴ�.
 * 
 * ���ɰ˻翡 ����ϱ� ���ؼ� ��ǰ�� ����Ͽ��� �Ѵ�.
 * ��ǰ�� �� ���� ������ �� ������ �� ��� �����ϴ� ���� ��� ������ �ϳ��� Ư������ ����ȴ�.
 * Ư�� ���� ��ǰ A�� �����ϸ� �� ���� ��� ������ Ư�� A�� ����Ǹ�, ��ǰ B�� �ְ� �Ǹ� Ư���� ��� Ư�� B�� ����ȴ�.
 *
 * �β� D, ����ũ�� W�� ��ȣ �ʸ� �ܸ��� ������ �հݱ��� K�� �־����� ��, ��ǰ ���� Ƚ���� �ּҷ� �Ͽ� ���ɰ˻縦 ����� �� �ִ� ����� ã��,
 * �̶��� ��ǰ ���� Ƚ���� ����϶�.
 * ��ǰ�� �������� �ʰ� ���ɰ˻縦 ����ϴ� ��쿡�� 0�� ����Ѵ�.
 * 
 * @�Է°�
 * ��ȣ �ʸ��� �β� D (3<= D <= 13), ����ũ�� W (1 <= W <= 10), �հݱ��� K ( 1 <= K <= D)
 * ���� D�ٿ� ��ȣ �ʸ� �ܸ��� ������ �־�����. �� �ٿ��� ������ Ư�� W���� �־�����. (Ư��A�� 0, Ư��B�� 1�� ǥ�õȴ�.)
 *
 * @Ǯ�̹��
 * 1. ��͸� �̿��Ͽ� ��� ��츦 �����Ѵ�.
 * 2. visited�� �̿��Ͽ� true ���� ��ǰ�� �������ش�.
 * 3. 0�׽�Ʈ �� 1�� �׽�Ʈ �ϰ� ������ �迭�� ������.
 * 4. �˻簡 ok �ȴٸ� �׶��� �ּڰ��� ���Ѵ�.
 *
 */

import java.io.*;
import java.util.*;

public class Solution2112_��ȣ�ʸ� {
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
		// 0 �׽�Ʈ
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

		// 1 �׽�Ʈ
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
