/**
 * @Date
 * 2019-08-14
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/2468
 *
 * @문제
 * 백준 2468. 안전 영역
 *
 * 어떤 지역의 높이 정보는 행과 열의 크기가 각각 N인 2차원 배열 형태로 주어지며 배열의 각 원소는 해당 지점의 높이를 표시하는 자연수이다.
 * 장마철에 내리는 비의 양에 따라서 물에 잠기지 않는 안전한 영역의 개수는 다르게 된다.
 * 지역에서 내리는 비의 양에 따른 모든 경우를 다 조사해 보면서 물에 잠기지 않는 안전한 영역의 최대 값을 구하라
 *
 * @입력값
 * 첫째 줄에는 어떤 지역을 나타내는 2차원 배열의 행과 열의 개수를 나타내는 수 N이 입력된다. (2 <= N <= 100)
 * 둘째 줄부터 N개의 각 줄에는 2차원 배열의 첫 번째 행부터 N번째 행까지 순서대로 한 행씩 높이 정보가 입력된다.
 * 각 줄에는 각 행의 첫 번째 열부터 N번째 열까지 N개의 높이 정보를 나타내는 자연수가 빈 칸을 사이에 두고 입력된다. (1 <= 높이 <= 100)
 *
 *
 * @풀이방법
 * 1. 최소 높이와 최대 높이를 알고 있어서 이 만큼만 탐색한다.
 * 2. dfs를 이용하여 갈 수 있는 범위내로 탐색을 시작한다.
 * 3. 다음 좌표가 높이보다 커야하고 방문하지 않은 곳일 때 탐색을 진행할 수 있다.
 * 4. 탐색이 시작된 횟수 res와 answer를 비교하여 최대값을 구하자.
 *
 */

package algo.dfs;

import java.io.*;
import java.util.*;

public class Main2468_안전영역 {
    static final int N_MAX = 100, HEIGHT_MAX = 100, INF = 987654321;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int[][] map;
    static int N, minHeight, maxHeight, answer;
    static boolean[][][] visited;

    static void solve(int r, int c, int h) {
        visited[h][r][c] = true;

        for (int[] dir : DIR) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (nr < 0 || nr >= N || nc < 0 || nc >= N)
                continue;

            if (map[nr][nc] <= h || visited[h][nr][nc])
                continue;

            solve(nr, nc, h);
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input2468.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[N_MAX][N_MAX];
        visited = new boolean[HEIGHT_MAX][N_MAX][N_MAX];
        minHeight = INF;
        maxHeight = -INF;
        N = new Integer(br.readLine());

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; ++j) {
                int data = new Integer(st.nextToken());

                map[i][j] = data;
                // 1)
                minHeight = minHeight < data ? minHeight : data;
                maxHeight = maxHeight > data ? maxHeight : data;
            }
        }

        answer = 1;
        int res = 0;
        for (int h = minHeight; h < maxHeight; ++h) {
            res = 0;

            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    if (map[i][j] <= h || visited[h][i][j]) // 3
                        continue;

                    solve(i, j, h); // 2)
                    res++;
                }
            }

            // 4)
            answer = answer > res ? answer : res;
        }

        System.out.println(answer);
        br.close();
    }
}
