/**
 * @Date 2019-09-05
 * @Author �ֺ���
 *
 * @��ó
 * http://www.jungol.co.kr/bbs/board.php?bo_table=pbank&wr_id=285&sca=3040
 *
 * @����
 * jungol 1006. �κ�
 *
 * ���� ���忡�� �κ��� �̿�ǰ� �ִ�. 
 * �츮 ������ �κ��� �ٶ󺸴� �������� �˵��� ���� �����̸� �����̴� ������ ��, ��, ��, �� ��� �ϳ��̴�. 
 * �κ��� �̵��� �����ϴ� ��ɾ�� ������ ���� �� �����̴�.
 * 
 * *��� 1. Go k
 * - k �� 1 2 �Ǵ� 3�� �� �ִ�. ���� ���ϰ� �ִ� �������� k ĭ��ŭ �����δ�. 
 * *��� 2. Turn dir
 * - dir �� left �Ǵ� right �̸� ���� ���� �Ǵ� ���������� 90�� ȸ���Ѵ�.
 * 
 * ���� �� �˵��� ��ġ�Ǿ� �ִ� ���°� �Ʒ��� ���� 0�� 1�� �̷���� ���簢�� ������� �κ����� �Էµȴ�.
 * 0�� �˵��� ��� �־� �κ��� �� �� �ִ� �����̰� 1�� �˵��� ���� �κ��� �� �� ���� �����̴�.
 * �κ��� (4, 2) �������� ������ ���ϰ� ���� ��
 * �� �κ��� (2, 4) �������� �������� ���ϵ��� �̵���Ű�� ���� �Ʒ��� ���� 9���� ������� �����ϴ�.
 *
 * @�Է°�
 * ù° �ٿ� ���� �� �˵� ��ġ ���¸� ��Ÿ���� ���簢���� ���� ���� M�� ���� ���� N�� ��ĭ�� ���̿� �ΰ� �־�����.
 * �� �� M�� N�� �� �� 100������ �ڿ����̴�.
 * �̾� M�ٿ� ���� �� �ٿ� N���� �� ������ �˵� ��ġ ���¸� ��Ÿ���� ���� 0 �Ǵ� 1�� ��ĭ�� ���̿� �ΰ� �־�����.
 * ���� �ٿ��� �κ��� ��� ������ ��ġ (��� ���� ��ȣ)�� �ٶ󺸴� ������ ��ĭ�� ���̿� �ΰ� �־�����.
 * ������ �ٿ��� �κ��� ���� ������ ��ġ (��� ���� ��ȣ)�� �ٶ󺸴� ������ ��ĭ�� ���̿� �ΰ� �־�����.
 * ������ ������ 1, ������ 2, ������ 3, ������ 4�� �־�����. ����������� �������������� �׻� �̵��� �����ϴ�.
 * 
 * @Ǯ�̹��
 * 1. ��������� �������� ������ ���� �����ϴ� ���� ������ �������� �ʴ´�.
 * 2. 3���� �迭�� ����� ���� ���� ���̻� ���� �ʵ��� �Ѵ�.
 * 3. �̵��ϴ� ���� ȸ���ϴ� ��츦 ���� �����Ͽ� q�� �־� bfs�� Ȱ���Ѵ�.
 *
 */

import java.io.*;
import java.util.*;

public class Main1006_�κ� {
	static final int MAX_NM = 100;
	static final int[][] DIR = { {}, { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
	static final int[] TURN_LEFT = { 0, 4, 3, 1, 2 };
	static final int[] TURN_RIGHT = { 0, 3, 4, 2, 1 };

	static int[][] map;
	static boolean[][][] visited;
	static int N, M;
	static int answer;

	static class Robot {
		int r, c, d;

		Robot(int r, int c, int d) {
			this.r = r;
			this.c = c;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		map = new int[MAX_NM][MAX_NM];
		visited = new boolean[MAX_NM][MAX_NM][5];

		st = new StringTokenizer(br.readLine());
		N = new Integer(st.nextToken());
		M = new Integer(st.nextToken());

		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < M; ++j) {
				map[i][j] = new Integer(st.nextToken());
			}
		}

		Queue<Robot> q = new LinkedList<>();
		int sr, sc, sd;
		int er, ec, ed;

		st = new StringTokenizer(br.readLine());
		sr = new Integer(st.nextToken()) - 1;
		sc = new Integer(st.nextToken()) - 1;
		sd = new Integer(st.nextToken());

		st = new StringTokenizer(br.readLine());
		er = new Integer(st.nextToken()) - 1;
		ec = new Integer(st.nextToken()) - 1;
		ed = new Integer(st.nextToken());

		q.offer(new Robot(sr, sc, sd));
		visited[sr][sc][sd] = true;

		int answer = 0;
		loop: while (!q.isEmpty()) {
			int qSize = q.size();
			answer++;

			for (int s = 0; s < qSize; ++s) {
				Robot robot = q.poll();

				if (robot.r == er && robot.c == ec && robot.d == ed) {
					break loop;
				}

				// go
				int nr, nc;
				for (int i = 1; i <= 3; ++i) {
					nr = robot.r + DIR[robot.d][0] * i;
					nc = robot.c + DIR[robot.d][1] * i;

					if (nr < 0 || nr >= N || nc < 0 || nc >= M)
						break;

					if (map[nr][nc] == 1)
						break;

					if (visited[nr][nc][robot.d])
						continue;

					visited[nr][nc][robot.d] = true;
					q.offer(new Robot(nr, nc, robot.d));
				}

				// turn
				if (!visited[robot.r][robot.c][TURN_LEFT[robot.d]]) {
					visited[robot.r][robot.c][TURN_LEFT[robot.d]] = true;
					q.offer(new Robot(robot.r, robot.c, TURN_LEFT[robot.d]));
				}

				if (!visited[robot.r][robot.c][TURN_RIGHT[robot.d]]) {
					visited[robot.r][robot.c][TURN_RIGHT[robot.d]] = true;
					q.offer(new Robot(robot.r, robot.c, TURN_RIGHT[robot.d]));
				}
			}
		}

		System.out.println(answer - 1);
		br.close();
	}
}
