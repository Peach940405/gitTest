/**
 * @Date
 * 2019-08-31
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/16236
 *
 * @문제
 * 백준 16236. 아기 상어
 *
 * N×N 크기의 공간에 물고기 M마리와 아기 상어 1마리가 있다. 공간은 1×1 크기의 정사각형 칸으로 나누어져 있다.
 * 한 칸에는 물고기가 최대 1마리 존재한다.
 *
 * 아기 상어와 물고기는 모두 크기를 가지고 있고, 이 크기는 자연수이다.
 * 가장 처음에 아기 상어의 크기는 2이고, 아기 상어는 1초에 상하좌우로 인접한 한 칸씩 이동한다.
 *
 * 아기 상어는 자신의 크기보다 큰 물고기가 있는 칸은 지나갈 수 없고, 나머지 칸은 모두 지나갈 수 있다.
 * 아기 상어는 자신의 크기보다 작은 물고기만 먹을 수 있다.
 * 따라서, 크기가 같은 물고기는 먹을 수 없지만, 그 물고기가 있는 칸은 지나갈 수 있다.
 *
 * 아기 상어가 어디로 이동할지 결정하는 방법은 아래와 같다.
 *
 * 더 이상 먹을 수 있는 물고기가 공간에 없다면 아기 상어는 엄마 상어에게 도움을 요청한다.
 * 먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 간다.
 * 먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기를 먹으러 간다.
 * 거리는 아기 상어가 있는 칸에서 물고기가 있는 칸으로 이동할 때, 지나야하는 칸의 개수의 최솟값이다.
 * 거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면, 가장 왼쪽에 있는 물고기를 먹는다.
 * 아기 상어의 이동은 1초 걸리고, 물고기를 먹는데 걸리는 시간은 없다고 가정한다.
 * 즉, 아기 상어가 먹을 수 있는 물고기가 있는 칸으로 이동했다면, 이동과 동시에 물고기를 먹는다. 물고기를 먹으면, 그 칸은 빈 칸이 된다.
 *
 * 아기 상어는 자신의 크기와 같은 수의 물고기를 먹을 때 마다 크기가 1 증가한다. 예를 들어, 크기가 2인 아기 상어는 물고기를 2마리 먹으면 크기가 3이 된다.
 *
 * 공간의 상태가 주어졌을 때, 아기 상어가 몇 초 동안 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는지 구하는 프로그램을 작성하시오.
 *
 * @입력값
 * 첫째 줄에 공간의 크기 N(2 ≤ N ≤ 20)이 주어진다.
 *
 * 둘째 줄부터 N개의 줄에 공간의 상태가 주어진다.
 * 공간의 상태는 0, 1, 2, 3, 4, 5, 6, 9로 이루어져 있고, 아래와 같은 의미를 가진다.
 *
 *  0: 빈 칸
 *  1, 2, 3, 4, 5, 6: 칸에 있는 물고기의 크기
 *  9: 아기 상어의 위치
 *
 * 아기 상어는 공간에 한 마리 있다.
 *
 * @풀이방법
 * 1. 상어의 위치를 기억해 두고 엄마상어에게 도움을 요청할 때까지 while문을 돌린다.
 * 2. 상어의 처음 위치를 큐에 넣고 q의 사이즈 만큼 bfs를 계속 돌린다.
 * 3. 상어가 이동할 수 있다면 dist에 값을 1 증가 시키고 이동한다.
 * 4. 물고기를 잡을 수 있다면 조건에 맡게 우선순위를 정해 물고기 위치를 계속 갱신한다.
 * 5. 물고기를 먹었다면 상어의 위치와 사이즈를 갱신하고 답을 더해준다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main16236_아기상어 {
    static final int MAX_N = 20;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int[][] map;
    static int N, fishCnt, cnt;
    static Shark shark;
    static int answer;

    static class Coordinate {
        int r, c;

        Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Shark {
        int r, c, size;

        Shark(int r, int c) {
            this.r = r;
            this.c = c;
            this.size = 2;
        }
    }

    static boolean solve() {
        Queue<Coordinate> q = new LinkedList<>();
        int[][] dist = new int[MAX_N][MAX_N];
        int fishR = N + 1, fishC = N + 1;
        boolean isEat = false;

        q.offer(new Coordinate(shark.r, shark.c));
        dist[shark.r][shark.c] = 1;

        int qSize, nr, nc;
        Coordinate coordinate;

        while (!q.isEmpty()) {
            qSize = q.size();

            for (int s = 0; s < qSize; ++s) {
                coordinate = q.poll();

                for (int[] dir : DIR) {
                    nr = coordinate.r + dir[0];
                    nc = coordinate.c + dir[1];

                    if (nr < 0 || nr >= N || nc < 0 || nc >= N)
                        continue;

                    // 이미 지나간 곳이라면
                    if (dist[nr][nc] != 0)
                        continue;

                    // 상어는 자신의 크기보다 작거나 캍은곳을 이동가능
                    if (map[nr][nc] > shark.size)
                        continue;

                    // 이동시키자.
                    dist[nr][nc] = dist[coordinate.r][coordinate.c] + 1;
                    // 잡을 수 있는 물고기 우선순위 정하기
                    if (map[nr][nc] > 0 && map[nr][nc] < shark.size) {
                        if (fishR > nr) { // 가장 위에 있으면서
                            fishR = nr;
                            fishC = nc;
                        } else if (fishR == nr && fishC > nc) { // 가장 왼쪽에 있는 물고기
                            fishR = nr;
                            fishC = nc;
                        }
                        isEat = true;
                    }
                    q.offer(new Coordinate(nr, nc));
                }
            }

            // 잡은 물고기 있을 때
            if (isEat) {
                // 상어 위치 갱신
                shark.r = fishR;
                shark.c = fishC;

                // 상어 사이즈 갱신
                if (++fishCnt == shark.size) {
                    ++shark.size;
                    fishCnt = 0;
                }

                // 물고기를 먹으면 빈칸된
                map[fishR][fishC] = 0;
                cnt = dist[fishR][fishC] - 1;

                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input16236.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAX_N][MAX_N];
        N = new Integer(br.readLine());

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; ++j) {
                map[i][j] = new Integer(st.nextToken());

                if (map[i][j] == 9) {
                    shark = new Shark(i, j);
                    map[i][j] = 0;
                }
            }
        }

        while (solve())
            answer += cnt;

        System.out.println(answer);
        br.close();
    }
}
