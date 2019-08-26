/**
 * @Date
 * 2019-08-26
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5VwAr6APYDFAWu
 *
 * @문제
 * swea 2105. [모의 SW 역량테스트] 디저트 카페
 *
 * 한 변의 길이가 N인 정사각형 모양을 가진 지역에 디저트 카페가 모여 있다.
 * 원 안의 숫자는 해당 디저트 카페에서 팔고 있는 디저트의 종류를 의미하고
 * 카페들 사이에는 대각선 방향으로 움직일 수 있는 길들이 있다.
 * 디저트 카페 투어는 어느 한 카페에서 출발하여
 * 대각선 방향으로 움직이고 사각형 모양을 그리며 출발한 카페로 돌아와야 한다.
 * 디저트 카페 투어를 하는 도중 해당 지역을 벗어나면 안 된다.
 * 또한, 친구들은 같은 종류의 디저트를 다시 먹는 것을 싫어한다.
 * 친구들과 디저트를 되도록 많이 먹으려고 한다.
 * 디저트 가게가 모여있는 지역의 한 변의 길이 N과 디저트 카페의 디저트 종류가 입력으로 주어질 때,
 * 임의의 한 카페에서 출발하여 대각선 방향으로 움직이고
 * 서로 다른 디저트를 먹으면서 사각형 모양을 그리며 다시 출발점으로 돌아오는 경우,
 * 디저트를 가장 많이 먹을 수 있는 경로를 찾고, 그 때의 디저트 수를 정답으로 출력하는 프로그램을 작성하라.
 * 만약, 디저트를 먹을 수 없는 경우 -1을 출력한다.
 *
 * @입력값
 * 입력의 맨 첫 줄에는 총 테스트 케이스의 개수 T가 주어지고, 그 다음 줄부터 T개의 테스트 케이스가 주어진다.
 *
 * 각 테스트 케이스의 첫 번째 줄에는 디저트 카페가 모여있는 지역의 한 변의 길이 N이 주어진다. (4 <= N <= 20)
 *
 * 그 다음 N 줄에는 N * N 크기의 디저트 카페에서 팔고 있는 디저트 종류에 대한 정보가 주어진다.
 *
 * 1 <= 디저트 종류 <= 100
 *
 * @풀이방법
 * 1. 완전탐색을 이용하여 모든 경우를 탐색한다.
 * 2. 각 방향에 대해서 확인하며 처음과 같은 좌표에 도착하면 최대 값을 구한다.
 *
 */

package SWTest;

import java.io.*;
import java.util.*;

public class Solution2105_디저트카페 {
    static final int MAX_N = 20, MAX_DESSERT = 100;
    static final int[][] DIR = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
    static int[][] map;
    static boolean[] dessert;
    static int N, sr, sc;
    static int answer;

    static void solve(int r, int c, int d, int res) {
        if (d == DIR.length)
            return;

        int nr = r + DIR[d][0];
        int nc = c + DIR[d][1];

        if (nr < 0 || nr >= N || nc < 0 || nc >= N)
            return;

        if (nr == sr && nc == sc) {
            answer = answer > res ? answer : res;
            return;
        }

        if (dessert[map[nr][nc]])
            return;

        dessert[map[nr][nc]] = true;
        solve(nr, nc, d + 1, res + 1);
        solve(nr, nc, d, res + 1);
        dessert[map[nr][nc]] = false;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input2105.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAX_N][MAX_N];
        dessert = new boolean[MAX_DESSERT + 1];

        StringBuilder sb = new StringBuilder();
        int T = new Integer(br.readLine());

        for (int tc = 1; tc <= T; ++tc) {
            N = new Integer(br.readLine());

            for (int i = 0; i < N; ++i) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < N; ++j) {
                    map[i][j] = new Integer(st.nextToken());
                }
            }

            answer = -1;

            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    sr = i;
                    sc = j;
                    dessert[map[i][j]] = true;
                    solve(i, j, 0, 1);
                    dessert[map[i][j]] = false;
                }
            }

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
