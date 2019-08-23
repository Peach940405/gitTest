/**
 * @Date
 * 2019-08-23
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV597vbqAH0DFAVl&categoryId=AV597vbqAH0DFAVl&categoryType=CODE
 *
 * @문제
 * swea 2382. [모의 sw 역량테스트] 미생물 격리
 *
 * 정사각형 구역 안에 K개의 미생물 군집이 있다.
 * 
 * 이 구역은 가로 N개, 세로 N개, 총 N * N 개의 동일한 크기의 정사각형 셀들로 이루어져 있다.
 * 미생물들이 구역을 벗어나는걸 방지하기 위해, 가장 바깥쪽 가장자리 부분에 위치한 셀들에는 특수한 약품이 칠해져 있다.
 * 가장자리의 빨간 셀은 약품이 칠해져 있는 셀이다.
 * 
 * ① 최초 각 미생물 군집의 위치와 군집 내 미생물의 수, 이동 방향이 주어진다. 약품이 칠해진 부분에는 미생물이 배치되어 있지 않다. 이동방향은 상, 하, 좌, 우 네 방향 중 하나이다.
 * 
 * ② 각 군집들은 1시간마다 이동방향에 있는 다음 셀로 이동한다.
 * 
 * ③ 미생물 군집이 이동 후 약품이 칠해진 셀에 도착하면 군집 내 미생물의 절반이 죽고, 이동방향이 반대로 바뀐다. 
 * 미생물 수가 홀수인 경우 반으로 나누어 떨어지지 않으므로, 다음과 같이 정의한다.
 * 살아남은 미생물 수 = 원래 미생물 수를 2로 나눈 후 소수점 이하를 버림 한 값
 * 따라서 군집에 미생물이 한 마리 있는 경우 살아남은 미생물 수가 0이 되기 때문에, 군집이 사라지게 된다,
 *
 * ④ 이동 후 두 개 이상의 군집이 한 셀에 모이는 경우 군집들이 합쳐지게 된다. 
 * 합쳐 진 군집의 미생물 수는 군집들의 미생물 수의 합이며, 이동 방향은 군집들 중 미생물 수가 가장 많은 군집의 이동방향이 된다. 
 * 합쳐지는 군집의 미생물 수가 같은 경우는 주어지지 않으므로 고려하지 않아도 된다.
 * 
 * M 시간 동안 이 미생물 군집들을 격리하였다. M시간 후 남아 있는 미생물 수의 총합을 구하여라.
 *
 *
 * @입력값
 * 입력의 맨 첫 줄에는 총 테스트 케이스의 개수 T가 주어지고, 그 다음 줄부터 T개의 테스트 케이스가 주어진다.
 * 셀의 개수 N (5 <= N <= 100)
 * 격리 시간 M (1 <= M <= 1,000)
 * 군집의 개수 K (5 <= K <= 1,000)
 * K 개의 군집 정보
 *
 *
 * @풀이방법
 * 1. 배열에 군집 정보들을 모두 저장한다.
 * 2. 먼자 배열을 끝까지 다돌면서 이동처리를 한 후 개수가 0인 미생물들을 제거한다.
 * 3. 이중 for문을 이용하여 미생물 끼리 비교를 하며 같은 위치의 미생물이 존재한다면 조건에 맞게 처리한다. 
 *
 */

package codebattle;

import java.io.*;
import java.util.*;

public class Solution2382_미생물격리 {
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
						microorganism = nextMicroorganism; // 제일 큰애로 기준점을 바꾼다.
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