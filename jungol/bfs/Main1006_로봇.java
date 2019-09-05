/**
 * @Date 2019-09-05
 * @Author 최병길
 *
 * @출처
 * http://www.jungol.co.kr/bbs/board.php?bo_table=pbank&wr_id=285&sca=3040
 *
 * @문제
 * jungol 1006. 로봇
 *
 * 많은 공장에서 로봇이 이용되고 있다. 
 * 우리 공장의 로봇은 바라보는 방향으로 궤도를 따라 움직이며 움직이는 방향은 동, 서, 남, 북 가운데 하나이다. 
 * 로봇의 이동을 제어하는 명령어는 다음과 같이 두 가지이다.
 * 
 * *명령 1. Go k
 * - k 는 1 2 또는 3일 수 있다. 현재 향하고 있는 방향으로 k 칸만큼 움직인다. 
 * *명령 2. Turn dir
 * - dir 은 left 또는 right 이며 각각 왼쪽 또는 오른쪽으로 90° 회전한다.
 * 
 * 공장 내 궤도가 설치되어 있는 상태가 아래와 같이 0과 1로 이루어진 직사각형 모양으로 로봇에게 입력된다.
 * 0은 궤도가 깔려 있어 로봇이 갈 수 있는 지점이고 1은 궤도가 없어 로봇이 갈 수 없는 지점이다.
 * 로봇이 (4, 2) 지점에서 남쪽을 향하고 있을 때
 * 이 로봇을 (2, 4) 지점에서 동쪽으로 향하도록 이동시키는 것은 아래와 같이 9번의 명령으로 가능하다.
 *
 * @입력값
 * 첫째 줄에 공장 내 궤도 설치 상태를 나타내는 직사각형의 세로 길이 M과 가로 길이 N이 빈칸을 사이에 두고 주어진다.
 * 이 때 M과 N은 둘 다 100이하의 자연수이다.
 * 이어 M줄에 걸쳐 한 줄에 N개씩 각 지점의 궤도 설치 상태를 나타내는 숫자 0 또는 1이 빈칸을 사이에 두고 주어진다.
 * 다음 줄에는 로봇의 출발 지점의 위치 (행과 열의 번호)와 바라보는 방향이 빈칸을 사이에 두고 주어진다.
 * 마지막 줄에는 로봇의 도착 지점의 위치 (행과 열의 번호)와 바라보는 방향이 빈칸을 사이에 두고 주어진다.
 * 방향은 동쪽이 1, 서쪽이 2, 남쪽이 3, 북쪽이 4로 주어진다. 출발지점에서 도착지점까지는 항상 이동이 가능하다.
 * 
 * @풀이방법
 * 1. 출발점에서 도착점에 무조건 도착 가능하니 예외 변수는 생각하지 않는다.
 * 2. 3차원 배열을 만들어 같은 곳은 더이상 가지 않도록 한다.
 * 3. 이동하는 경우와 회전하는 경우를 따로 생각하여 q에 넣어 bfs를 활용한다.
 *
 */

import java.io.*;
import java.util.*;

public class Main1006_로봇 {
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
