/**
 * @Date
 * 2019-08-27
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/15685
 *
 * @문제
 * 백준 15685. 드래곤커브
 *
 * 드래곤 커브는 다음과 같은 세 가지 속성으로 이루어져 있으며, 이차원 좌표 평면 위에서 정의된다. 좌표 평면의 x축은 → 방향, y축은 ↓ 방향이다.
 *
 *  1) 시작 점
 *  2) 시작 방향
 *  3) 세대
 *
 * 즉, K(K > 1)세대 드래곤 커브는 K-1세대 드래곤 커브를 끝 점을 기준으로 90도 시계 방향 회전 시킨 다음, 그것을 끝 점에 붙인 것이다.
 *
 * 크기가 100×100인 격자 위에 드래곤 커브가 N개 있다.
 * 이때, 크기가 1×1인 정사각형의 네 꼭짓점이 모두 드래곤 커브의 일부인 정사각형의 개수를 구하는 프로그램을 작성하시오.
 * 격자의 좌표는 (x, y)로 나타내며, 0 ≤ x ≤ 100, 0 ≤ y ≤ 100만 유효한 좌표이다.
 *
 * @입력
 * 첫째 줄에 드래곤 커브의 개수 N(1 ≤ N ≤ 20)이 주어진다.
 * 둘째 줄부터 N개의 줄에는 드래곤 커브의 정보가 주어진다.
 * 드래곤 커브의 정보는 네 정수 x, y, d, g로 이루어져 있다.
 * x와 y는 드래곤 커브의 시작 점, d는 시작 방향, g는 세대이다. (0 ≤ x, y ≤ 100, 0 ≤ d ≤ 3, 0 ≤ g ≤ 10)
 *
 * 입력으로 주어지는 드래곤 커브는 격자 밖으로 벗어나지 않는다. 드래곤 커브는 서로 겹칠 수 있다.
 *
 * 방향은 0, 1, 2, 3 중 하나이고, 다음을 의미한다.
 *
 * 0: x좌표가 증가하는 방향 (→)
 * 1: y좌표가 감소하는 방향 (↑)
 * 2: x좌표가 감소하는 방향 (←)
 * 3: y좌표가 증가하는 방향 (↓)
 *
 * @풀이방법
 * 1. 드래곤 커브의 규칙은 다음세대의 방향은 이전세대 끝부터 시작까지 역순으로 1증가하는 것과 같다.
 * 2. 모든 세대 까지 규칙을 저장해둔 후 이동을 시작한다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main15685_드래곤커브 {
    static final int MAX_MAP = 100, MAX_G = 10;
    static final int[][] DIR = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    static boolean[][] map;
    static int N;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input15685.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new boolean[MAX_MAP + 1][MAX_MAP + 1];
        N = new Integer(br.readLine());

        int r, c, d, g;
        int[] curve = new int[2 << MAX_G];

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            c = new Integer(st.nextToken()); // x
            r = new Integer(st.nextToken()); // y
            d = new Integer(st.nextToken()); // d(방향)
            g = new Integer(st.nextToken()); // g(세대)

            int idx = 0;
            curve[idx++] = d;

            for (int j = 0; j < g; ++j) {
                for (int k = idx - 1; k >= 0; --k) { // 다음 세대는 이전세대의 역방향 순으로 1 증가한다.
                    curve[idx++] = (curve[k] + 1) % DIR.length;
                }
            }

            map[r][c] = true;

            for (int j = 0; j < idx; ++j) {
                r += DIR[curve[j]][0];
                c += DIR[curve[j]][1];

                map[r][c] = true;
            }
        }

        int answer = 0;
        for (int i = 0; i < MAX_MAP; ++i) {
            for (int j = 0; j < MAX_MAP; ++j) {
                if (map[i][j] && map[i + 1][j] && map[i][j + 1] && map[i + 1][j + 1])
                    answer++;
            }
        }

        System.out.println(answer);
        br.close();
    }
}
