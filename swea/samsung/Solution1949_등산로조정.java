/**
 * @Date
 * 2019-08-31
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5PoOKKAPIDFAUq
 *
 * @문제
 * swea 1949. [모의 SW 역량테스트] 등산로 조정
 *
 * 등산로를 조성하려고 한다.
 * 등산로를 만들기 위한 부지는 N * N 크기를 가지고 있으며, 이곳에 최대한 긴 등산로를 만들 계획이다.
 * 등산로 부지는 아래 [Fig. 1]과 같이 숫자가 표시된 지도로 주어지며, 각 숫자는 지형의 높이를 나타낸다.
 *
 * 등산로를 만드는 규칙은 다음과 같다.
 *    ① 등산로는 가장 높은 봉우리에서 시작해야 한다.
 *    ② 등산로는 산으로 올라갈 수 있도록 반드시 높은 지형에서 낮은 지형으로 가로 또는 세로 방향으로 연결이 되어야 한다.
 *        즉, 높이가 같은 곳 혹은 낮은 지형이나, 대각선 방향의 연결은 불가능하다.
 *    ③ 긴 등산로를 만들기 위해 딱 한 곳을 정해서 최대 K 깊이만큼 지형을 깎는 공사를 할 수 있다.
 * N * N 크기의 지도가 주어지고, 최대 공사 가능 깊이 K가 주어진다.
 *
 * 이때 만들 수 있는 가장 긴 등산로를 찾아 그 길이를 출력하는 프로그램을 작성하라.
 *
 * @입력값
 * 3 <= N <= 8
 * N * N 지도의 정보
 *
 *
 * @풀이방법
 * 1. 지도에서 가장 큰 봉우리의 정보들을 q에 담아둔다.
 * 2. q가 빌 때 까지 재귀를 통해 문제를 해결한다.
 * 3. 다음 봉우리가 현재보다 작다면 무조건 간다.
 * 4. 그렇지 않다면 이미 깎은 등산로는 건너 뛰고 1 부터 K 까지 봉우리를 깎을 수 있는지 확인하고 isCut을 자른 표시로 재귀를 돌린다.
 *
 */

package SWTest;

import java.io.*;
import java.util.*;

public class Solution1949_등산로조정 {
    static final int MAX_N = 8, INF = 987654321;
    static final int[][] DIR = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    static int[][] map;
    static boolean[][] visited;
    static int N, K;
    static int answer;

    static class Coordinate {
        int r, c;

        Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static void solve(int r, int c, int height, int roadLen, boolean isCut) {
        answer = answer > roadLen ? answer : roadLen;
        visited[r][c] = true;
        int nr, nc;

        for (int[] dir : DIR) {
            nr = r + dir[0];
            nc = c + dir[1];

            if (nr < 0 || nr >= N || nc < 0 || nc >= N)
                continue;

            if (visited[nr][nc])
                continue;

            // 다음 곳이 작은 경우
            if (map[nr][nc] < height)
                solve(nr, nc, map[nr][nc], roadLen + 1, isCut);
            else {
                // 이미 자른 경로인 경우
                if (isCut)
                    continue;

                // 공사 가능깊이 K확인
                for (int i = 1; i <= K; ++i) {
                    if (map[nr][nc] - i >= height)
                        continue;

                    solve(nr, nc, map[nr][nc] - i, roadLen + 1, true);
                }
            }
        }

        visited[r][c] = false;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input1949.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAX_N][MAX_N];
        visited = new boolean[MAX_N][MAX_N];

        Queue<Coordinate> q;
        Coordinate coordinate;
        int max;
        int T = new Integer(br.readLine());

        for (int tc = 1; tc <= T; ++tc) {
            st = new StringTokenizer(br.readLine());
            N = new Integer(st.nextToken());
            K = new Integer(st.nextToken());
            q = new LinkedList<>();

            max = -INF;

            for (int i = 0; i < N; ++i) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < N; ++j) {
                    map[i][j] = new Integer(st.nextToken());

                    if (map[i][j] == max)
                        q.offer(new Coordinate(i, j));
                    else if (map[i][j] > max) {
                        max = map[i][j];
                        q.clear();
                        q.offer(new Coordinate(i, j));
                    }
                }
            }

            answer = 0;
            while (!q.isEmpty()) {
                coordinate = q.poll();
                solve(coordinate.r, coordinate.c, map[coordinate.r][coordinate.c], 1, false);
            }

            System.out.println("#" + tc + " " + answer);
        }


        br.close();
    }
}
