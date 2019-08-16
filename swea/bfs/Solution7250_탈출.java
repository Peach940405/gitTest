/**
 * @Date
 * 2019-08-16
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/userProblem/userProblemDetail.do?contestProbId=AWlq-Cvq6joDFASP
 *
 * @문제
 * swea 7250. 탈출
 *
 * 스캇과 악당이 싸우고 있다.
 * N x M 크기의 공간에서 피해출구(E)에 도달해야 한다!
 * 스캇은 이동 가능한 길 A를 이동할 수 있고 K번의 W벽을 점프할 수 있다.
 * 악당은 불을 자유 자재로 다룰 수 있어 불도 통과할 수있지만 벽(W, X)는 통과할 수 없다.
 * 이때 스캇이 탈출구로 가는 최소의 수를 출력하라
 * 단, 스캇이 탈출하지 못하거나 악당이 탈출구로 가면 -1을 출력
 *
 * @입력값
 * 첫번쨰 줄에는 테스트케이스를 입력
 * 두번쨰 줄에는 배열의 크기 N(행) M(열) (3 < N, M <= 1000)
 * 벽을 지날 수 있는  줄인 몸을 유지하는 시간(K)를 입력 (1 <= K <= 10)
 * 세번째 줄 부터는 스캇(S), 악당(V), 탈출(E) 하나씩 있으며
 * 지날 갈 수 있는 길 (A), 벽(W), 지날 갈 수 없는 길(X), 불(F)이
 * 배열의 크기 만큼 있다.
 *
 *
 * @풀이방법
 * 1. bfs활용하여 문제를 접근한다.
 * 2. q의 사이즈를 활용하여 최소 단계의 값을 출력한다.
 * 3. 스캇과 악당 그리고 불의 큐를 만들어 각각의 위치를 담는다.
 * 4. 스캇의 큐에는 k 횟수를 적용하여 벽을 통과할 수 있는지 확인하자.
 *
 */

package D4;

import java.io.*;
import java.util.*;

public class Solution7250_탈출 {
    static final int MAP_MAX = 1000, K_MAX = 10;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static char[][] map;
    static boolean[][][] visited;
    static int answer, N, M, K;
    static Queue<Data> scottQ, villainQ, fireQ;

    static class Data {
        int r, c, k;

        Data(int r, int c) {
            this(r, c, 0);
        }

        Data(int r, int c, int k) {
            this.r = r;
            this.c = c;
            this.k = k;
        }
    }

    static boolean solve() {
        int scottQSize = 0, villainQSize = 0, fireQSize = 0;
        Data curData;

        while (!scottQ.isEmpty()) {
        	answer++;

            fireQSize = fireQ.size();
            for (int s = 0; s < fireQSize; ++s) {
                curData = fireQ.poll();

                for (int[] dir : DIR) {
                    int nr = curData.r + dir[0];
                    int nc = curData.c + dir[1];

                    if (nr < 0 || nc >= N || nc < 0 || nc >= M)
                        continue;

                    if (map[nr][nc] == 'W' || map[nr][nc] == 'X' || map[nr][nc] == 'F' || map[nr][nc] == 'E') // 벽에는 불이 안 붙는다.
                        continue;

                    map[nr][nc] = 'F';
                    fireQ.offer(new Data(nr, nc));
                }
            }

			villainQSize = villainQ.size();
			for (int s = 0; s < villainQSize; ++s) {
				curData = villainQ.poll();

				for (int[] dir : DIR) {
					int nr = curData.r + dir[0];
					int nc = curData.c + dir[1];

					if (nr < 0 || nc >= N || nc < 0 || nc >= M)
						continue;

					if (map[nr][nc] == 'A' || map[nr][nc] == 'F') {
						map[nr][nc] = 'V';
						villainQ.offer(new Data(nr, nc));
					} else if (map[nr][nc] == 'E')
						return false;
				}
			}

            scottQSize = scottQ.size();
            for (int s = 0; s < scottQSize; ++s) {
                curData = scottQ.poll();

                if (visited[curData.k][curData.r][curData.c])
                    continue;
                visited[curData.k][curData.r][curData.c] = true;

                for (int[] dir : DIR) {
                    int nr = curData.r + dir[0];
                    int nc = curData.c + dir[1];

                    if (nr < 0 || nc >= N || nc < 0 || nc >= M)
                        continue;

                    if (visited[curData.k][nr][nc] || map[nr][nc] == 'F' || map[nr][nc] == 'V')
                        continue;

                    if (map[nr][nc] == 'A') {
                        map[nr][nc] = 'S';
                        scottQ.offer(new Data(nr, nc)); // k가 0으로 초기화됨
                    } else if (map[nr][nc] == 'E')
                        return true;

                    if (curData.k < K && (map[nr][nc] == 'W' || map[nr][nc] == 'S')) {
                        map[nr][nc] = 'S';
                        scottQ.offer(new Data(nr, nc, curData.k + 1));
                    }
                }
            }
        }

        return false;
    }

    static void init() {
        visited = new boolean[K + 1][N + 1][M + 1];
        scottQ = new LinkedList<>();
        villainQ = new LinkedList<>();
        fireQ = new LinkedList<>();
        answer = 0;
    }

    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("res/input7250.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb;

        map = new char[MAP_MAX + 1][MAP_MAX + 1];
        sb = new StringBuilder();

        int T = new Integer(br.readLine());
        for (int tc = 1; tc <= T; ++tc) {
			st = new StringTokenizer(br.readLine());
			N = new Integer(st.nextToken());
			M = new Integer(st.nextToken());
			K = new Integer(st.nextToken());

			init();
			String input = "";
            char val;
            Data data;

            for (int i = 0; i < N; ++i) {
                input = br.readLine();

                for (int j = 0; j < M; ++j) {
                    map[i][j] = val = input.charAt(j);

                    data = new Data(i, j);
                    if (val == 'S')
                        scottQ.offer(data);
                    else if (val == 'V')
                        villainQ.offer(data);
                    else if (val == 'F')
                        fireQ.offer(data);
                }
            }

            if (!solve())
                answer = -1;

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}