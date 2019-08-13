/**
 * @Date
 * 2019-08-12
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17406
 *
 * @문제
 * 백준 17406. 배열돌리기4
 *
 * 크기가 NxM 크기인 배열이 있을 때, 배열 A의 값은 모든 수의 합 중 최솟값을 의미한다.
 * 배열 A가 아래와 같은 경우 1행의 합은 6, 2행의 합은 4, 3행의 합은 15이다.
 * 따라서 배열 A의 값은 4이다.
 * 1 2 3
 * 2 1 1
 * 4 5 6
 *
 * 회전연산은 (r, c, s)로 이루어져 있고, 가장 왼쪽 윗 칸이 (r-s, c-s), 가장 오른쪽 칸이(r+s, c+s)인
 * 정사각형을 시계 방향으로 한 칸씩 돌린다는 의미이다.
 * 회전 연산은 모두 한번씩 한번 씩 사용해야하며 순서는 임의로 정해도 될 때, A의 최솟값을 구하라.
 *
 * @입력값
 * 배열 A의 크기 N, M (3 <= N, M <= 50), 회전 연산의 수 K (1 <= K <= 6)
 * 둘째 줄부터 N개의 줄에 배열 A에 들어있는 수 A[i][j]가 주어지고, 다음 K개의 줄에 회전 연산의 정보 r, c, s가 주어진다.
 *
 *
 * @풀이방법
 * 1) 회전의 순서가 상관없으므로 재귀를 이용하여 순열을 돌려 모든 경우의 수를 탐색한다.
 * 2) 해당 순서대로 주어진 배열을 회전시키는데 끝점이 시작점보다 같거나 작아질때까지 회전한다.
 *
 */

import java.io.*;
import java.util.*;

public class Main17406_배열돌리기4 {
    static final int N_MAX = 50, M_MAX = 50, INF = 987654321;
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final int[] REV = {2, 3, 0, 1}, CHECK = {1, 1};
    static int[][] map, tmpMap;
    static Cal[] cal;
    static int[] tmp;
    static boolean[] visited;
    static int N, M, K, answer;

    static class Cal {
        int r, c, s;

        Cal(int r, int c, int s) {
            this.r = r;
            this.c = c;
            this.s = s;
        }
    }

    static void getAnswer() {
        int min = INF;
        int sum = 0;

        for (int i = 0; i < N; ++i) {
            sum = 0;
            for (int j = 0; j < M; ++j) {
                sum += tmpMap[i][j];
            }

            min = min < sum ? min : sum;
        }

        answer = answer < min ? answer : min;
    }

    static void arrayCopy(int[][] a, int[][] b) {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                a[i][j] = b[i][j];
            }
        }
    }

    static void rotate(int r, int c, int sr, int sc, int R, int C, int d, int tmp) {
        if (r == sr && c == sc) {
            tmpMap[r][c + 1] = tmp;
            return;
        }

        tmpMap[r + DIR[REV[d]][0]][c + DIR[REV[d]][1]] = tmpMap[r][c];
        if ((r == R && c == sc) || (r == R && c == C) || (r == sr && c == C))
            d++;

        rotate(r + DIR[d][0], c + DIR[d][1], sr, sc, R, C, d, tmp);
    }

    static void rotate(int depth) {
        if (depth == K)
            return;

        Cal curCal = cal[tmp[depth]];
        for (int k = 0; ; k++) {
            int sr = curCal.r - curCal.s + k * CHECK[0];
            int sc = curCal.c - curCal.s + k * CHECK[1];
            int r = curCal.r + curCal.s - k * CHECK[0];
            int c = curCal.c + curCal.s - k * CHECK[1];

            // 2)
            // 끝자리가 시작자리와 같거나 시작자리가 끝자리를 넘어가면 탈출
            if (sr >= r && sc >= c) {
                break;
            }

            rotate(sr + 1, sc, sr, sc, r, c, 0, tmpMap[sr][sc]);
        }

        rotate(depth + 1);
    }

    static void solve(int depth) {
        // 1)
        if (depth == K) {
            arrayCopy(tmpMap, map);
            rotate(0);
            getAnswer();
            return;
        }

        for (int i = 0; i < K; ++i) {
            if (visited[i])
                continue;

            visited[i] = true;
            tmp[depth] = i;
            solve(depth + 1);
            visited[i] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[N_MAX][M_MAX];
        tmpMap = new int[N_MAX][M_MAX];
        st = new StringTokenizer(br.readLine());
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());
        K = new Integer(st.nextToken());
        cal = new Cal[K];
        visited = new boolean[K];
        tmp = new int[K];

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; ++j) {
                map[i][j] = new Integer(st.nextToken());
            }
        }

        for (int i = 0; i < K; ++i) {
            st = new StringTokenizer(br.readLine());
            cal[i] = new Cal(new Integer(st.nextToken()) - 1, new Integer(st.nextToken()) - 1, new Integer(st.nextToken()));
        }

        answer = INF;
        solve(0);
        System.out.println(answer);
        br.close();
    }
}
