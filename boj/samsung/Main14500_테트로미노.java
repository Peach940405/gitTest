/**
 * @Date
 * 2019-08-15
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/14500
 *
 * @문제
 * 백준 14500. 테르로미노
 *
 * 모양은 다음과 같은 5가지가 있다.
 * 1. - - - -
 * 2. - -
 *    - -
 * 3. -
 *    -
 *    - -
 * 4. -
 *    - -
 *      -
 * 5. - - -
 *      -
 *
 * 아름이는 크기가 N×M인 종이 위에 테트로미노 하나를 놓으려고 한다.
 * 종이는 1×1 크기의 칸으로 나누어져 있으며, 각각의 칸에는 정수가 하나 쓰여 있다.
 * 테트로미노 하나를 적절히 놓아서 테트로미노가 놓인 칸에 쓰여 있는 수들의 합을 최대로 하는 프로그램을 작성하시오.
 * 테트로미노는 반드시 한 정사각형이 정확히 하나의 칸을 포함하도록 놓아야 하며, 회전이나 대칭을 시켜도 된다.
 *
 *
 * @입력값
 * 첫째 줄에 종이의 세로 크기 N과 가로 크기 M이 주어진다. (4 ≤ N, M ≤ 500)
 *
 * 둘째 줄부터 N개의 줄에 종이에 쓰여 있는 수가 주어진다.
 * i번째 줄의 j번째 수는 위에서부터 i번째 칸, 왼쪽에서부터 j번째 칸에 쓰여 있는 수이다.
 * 입력으로 주어지는 수는 1,000을 넘지 않는 자연수이다.
 *
 *
 * @풀이방법
 * 1. 5번 모양을 제외한 나머지 모양들은 dfs를 이용하여 재귀적으로 모두 갈 수 있는 모양이므로 이를 확인하고
 * 2. 5번 모양을 따로 확인한다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main14500_테트로미노 {
    static final int SIZE = 4, MAP_MAX = 500;
    static final int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int answer, N, M;
    static int[][] map;
    static boolean[][] visited;

    static void solve(int r, int c) {
        for (int i = 0; i < SIZE; ++i) {
            int sum = map[r][c];
            boolean flag = true;

            for (int j = 0; j < SIZE - 1; ++j) {
                int nr = r + dir[(i + j) % SIZE][0];
                int nc = c + dir[(i + j) % SIZE][1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    flag = false;
                    break;
                } else
                    sum += map[nr][nc];
            }

            if (flag)
                answer = answer > sum ? answer : sum;
        }
    }

    static void solve(int depth, int res, int r, int c) {
        if (depth == SIZE - 1) {
            answer = answer > res ? answer : res;
            return;
        }

        visited[r][c] = true;
        for (int d = 0; d < dir.length; d++) {
            int nr = r + dir[d][0];
            int nc = c + dir[d][1];

            if (nr < 0 || nr >= N || nc < 0 || nc >= M)
                continue;

            if (visited[nr][nc])
                continue;

            solve(depth + 1, res + map[nr][nc], nr, nc);
        }
        visited[r][c] = false;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input14500.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());
        map = new int[MAP_MAX][MAP_MAX];
        visited = new boolean[MAP_MAX][MAP_MAX];

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; ++j)
                map[i][j] = new Integer(st.nextToken());
        }

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                // 1)
                solve(0, map[i][j], i, j);
                // 2)
                solve(i, j);
            }
        }

        System.out.println(answer);
        br.close();
    }
}
