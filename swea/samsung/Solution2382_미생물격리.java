/**
 * @Date
 * 2019-08-23
 *
 * @Author
 * �ֺ���
 *
 * @��ó
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV597vbqAH0DFAVl&categoryId=AV597vbqAH0DFAVl&categoryType=CODE
 *
 * @����
 * swea 2382. [���� sw �����׽�Ʈ] �̻��� �ݸ�
 *
 * ���簢�� ���� �ȿ� K���� �̻��� ������ �ִ�.
 * 
 * �� ������ ���� N��, ���� N��, �� N * N ���� ������ ũ���� ���簢�� ����� �̷���� �ִ�.
 * �̻������� ������ ����°� �����ϱ� ����, ���� �ٱ��� �����ڸ� �κп� ��ġ�� ���鿡�� Ư���� ��ǰ�� ĥ���� �ִ�.
 * �����ڸ��� ���� ���� ��ǰ�� ĥ���� �ִ� ���̴�.
 * 
 * �� ���� �� �̻��� ������ ��ġ�� ���� �� �̻����� ��, �̵� ������ �־�����. ��ǰ�� ĥ���� �κп��� �̻����� ��ġ�Ǿ� ���� �ʴ�. �̵������� ��, ��, ��, �� �� ���� �� �ϳ��̴�.
 * 
 * �� �� �������� 1�ð����� �̵����⿡ �ִ� ���� ���� �̵��Ѵ�.
 * 
 * �� �̻��� ������ �̵� �� ��ǰ�� ĥ���� ���� �����ϸ� ���� �� �̻����� ������ �װ�, �̵������� �ݴ�� �ٲ��. 
 * �̻��� ���� Ȧ���� ��� ������ ������ �������� �����Ƿ�, ������ ���� �����Ѵ�.
 * ��Ƴ��� �̻��� �� = ���� �̻��� ���� 2�� ���� �� �Ҽ��� ���ϸ� ���� �� ��
 * ���� ������ �̻����� �� ���� �ִ� ��� ��Ƴ��� �̻��� ���� 0�� �Ǳ� ������, ������ ������� �ȴ�,
 *
 * �� �̵� �� �� �� �̻��� ������ �� ���� ���̴� ��� �������� �������� �ȴ�. 
 * ���� �� ������ �̻��� ���� �������� �̻��� ���� ���̸�, �̵� ������ ������ �� �̻��� ���� ���� ���� ������ �̵������� �ȴ�. 
 * �������� ������ �̻��� ���� ���� ���� �־����� �����Ƿ� ������� �ʾƵ� �ȴ�.
 * 
 * M �ð� ���� �� �̻��� �������� �ݸ��Ͽ���. M�ð� �� ���� �ִ� �̻��� ���� ������ ���Ͽ���.
 *
 *
 * @�Է°�
 * �Է��� �� ù �ٿ��� �� �׽�Ʈ ���̽��� ���� T�� �־�����, �� ���� �ٺ��� T���� �׽�Ʈ ���̽��� �־�����.
 * ���� ���� N (5 <= N <= 100)
 * �ݸ� �ð� M (1 <= M <= 1,000)
 * ������ ���� K (5 <= K <= 1,000)
 * K ���� ���� ����
 *
 *
 * @Ǯ�̹��
 * 1. �迭�� ���� �������� ��� �����Ѵ�.
 * 2. ���� �迭�� ������ �ٵ��鼭 �̵�ó���� �� �� ������ 0�� �̻������� �����Ѵ�.
 * 3. ���� for���� �̿��Ͽ� �̻��� ���� �񱳸� �ϸ� ���� ��ġ�� �̻����� �����Ѵٸ� ���ǿ� �°� ó���Ѵ�. 
 *
 */

package codebattle;

import java.io.*;
import java.util.*;

public class Solution2382_�̻����ݸ� {
	static final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;
	static final int[] REV = { 0, 2, 1, 4, 3 };
	static int N, M, K;
	static List<Microorganism> microorganisms;

	static class Microorganism {
		int r, c, cnt, d;

		Microorganism(int r, int c, int cnt, int d) {
			this.r = r;
			this.c = c;
			this.cnt = cnt;
			this.d = d;
		}
	}

	static void update() {
		for (int i = 0; i < microorganisms.size() - 1; ++i) {
			Microorganism microorganism = microorganisms.get(i);

			if (microorganism.cnt == 0)
				continue;

			int tmpCnt = 0;
			
			for (int j = i + 1; j < microorganisms.size(); ++j) {
				Microorganism nextMicroorganism = microorganisms.get(j);

				if (nextMicroorganism.cnt == 0)
					continue;

				if ((microorganism.r == nextMicroorganism.r) && (microorganism.c == nextMicroorganism.c)) {
					if (microorganism.cnt > nextMicroorganism.cnt) {
						tmpCnt += nextMicroorganism.cnt;
						nextMicroorganism.cnt = 0;
					} else {
						tmpCnt += microorganism.cnt;
						microorganism.cnt = 0;
						microorganism = nextMicroorganism; // ���� ū�ַ� �������� �ٲ۴�.
					}
				}
			}
			microorganism.cnt += tmpCnt;
		}

		remove();
	}

	static void remove() {
		for (int i = 0; i < microorganisms.size(); ++i) {
			if (microorganisms.get(i).cnt == 0)
				microorganisms.remove(i);
		}
	}

	static void move() {
		for (Microorganism microorganism : microorganisms) {
			switch (microorganism.d) {
			case UP:
				if (--microorganism.r == 0) {
					microorganism.d = REV[microorganism.d];
					microorganism.cnt >>= 1;
				}
				break;
			case DOWN:
				if (++microorganism.r == N - 1) {
					microorganism.d = REV[microorganism.d];
					microorganism.cnt >>= 1;
				}
				break;
			case LEFT:
				if (--microorganism.c == 0) {
					microorganism.d = REV[microorganism.d];
					microorganism.cnt >>= 1;
				}
				break;
			case RIGHT:
				if (++microorganism.c == N - 1) {
					microorganism.d = REV[microorganism.d];
					microorganism.cnt >>= 1;
				}
			}
		}

		remove();
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input2382.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int r, c, cnt, d;
		int answer = 0;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; ++tc) {
			st = new StringTokenizer(br.readLine());
			N = new Integer(st.nextToken());
			M = new Integer(st.nextToken());
			K = new Integer(st.nextToken());

			microorganisms = new ArrayList<>();

			for (int i = 0; i < K; ++i) {
				st = new StringTokenizer(br.readLine());

				r = new Integer(st.nextToken());
				c = new Integer(st.nextToken());
				cnt = new Integer(st.nextToken());
				d = new Integer(st.nextToken());

				microorganisms.add(new Microorganism(r, c, cnt, d));
			}

			for (int i = 0; i < M; ++i) {
				move();
				update();
			}

			answer = 0;
			for (Microorganism microorganism : microorganisms)
				answer += microorganism.cnt;

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.print(sb.toString());
	}
}