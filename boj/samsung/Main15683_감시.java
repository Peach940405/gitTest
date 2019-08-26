/**
 * @Date
 * 2019-08-26
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/15683
 *
 * @문제
 * 백준 15683. 감시
 *
 * 스타트링크의 사무실은 1×1크기의 정사각형으로 나누어져 있는 N×M 크기의 직사각형으로 나타낼 수 있다.
 * 사무실에는 총 K개의 CCTV가 설치되어져 있는데, CCTV는 5가지 종류가 있다.
 * 각 CCTV가 감시할 수 있는 방법은 다음과 같다.
 *
 *  1) 우
 *  2) 우좌
 *  3) 상우
 *  4) 상우좌
 *  5) 상우하좌
 *
 * CCTV는 감시할 수 있는 방향에 있는 칸 전체를 감시할 수 있다.
 * 사무실에는 벽이 있는데, CCTV는 벽을 통과할 수 없다.
 * CCTV가 감시할 수 없는 영역은 사각지대라고 한다.
 *
 * CCTV는 회전시킬 수 있는데, 회전은 항상 90도 방향으로 해야 하며, 감시하려고 하는 방향이 가로 또는 세로 방향이어야 한다.
 *
 * 사무실의 크기와 상태, 그리고 CCTV의 정보가 주어졌을 때, CCTV의 방향을 적절히 정해서, 사각 지대의 최소 크기를 구하는 프로그램을 작성하시오.
 *
 * @입력값
 * 첫째 줄에 사무실의 세로 크기 N과 가로 크기 M이 주어진다. (1 ≤ N, M ≤ 8)
 *
 * 둘째 줄부터 N개의 줄에는 사무실 각 칸의 정보가 주어진다.
 * 0은 빈 칸, 6은 벽, 1~5는 CCTV를 나타내고, 문제에서 설명한 CCTV의 종류이다.
 *
 * CCTV의 최대 개수는 8개를 넘지 않는다.
 *
 * @풀이방법
 * 1. 완전탐색을 통해서 모든 경우의 수를 보아야한다.
 * 2. 입력 받을 때 해당 CCTV 번호에 따라서 isPossible 배열을 만들어준다.
 * 3. 4방향을 탐색하기위해 4번 for문을 돌면서 함수에 들어서는 탐색할 수 있는 방향에 대해서 탐색한다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main15683_감시 {
    static final int MAX_NM = 8, MAX_CCTV = 8;
    static final int ROUTE = 0, WALL = -2, CCTV = -1;
    static final int[][] DIR = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 상, 우, 하, 좌
    static int[][] map;
    static CCTV[] cctvs;
    static int N, M, cctvCnt;
    static int leftCnt, answer;

    static class CCTV {
        int r, c, num;
        boolean[] isPossible;

        CCTV(int r, int c, int num) {
            this.r = r;
            this.c = c;
            this.num = num;

            switch (this.num) {
                case 1:
                    isPossible = new boolean[]{false, true, false, false};
                    break;
                case 2:
                    isPossible = new boolean[]{false, true, false, true};
                    break;
                case 3:
                    isPossible = new boolean[]{true, true, false, false};
                    break;
                case 4:
                    isPossible = new boolean[]{true, true, false, true};
                    break;
                case 5:
                    isPossible = new boolean[]{true, true, true, true};
            }
        }
    }

    static void search(int idx, int i, boolean flag) {
        CCTV cctv = cctvs[idx];
        int curD, nr, nc;

        for (int d = 0; d < DIR.length; ++d) {
            if (!cctv.isPossible[d])
                continue;

            curD = (d + i) % 4;

            for (int len = 1; ; ++len) {
                nr = cctv.r + DIR[curD][0] * len;
                nc = cctv.c + DIR[curD][1] * len;

                if (nr < 0 || nr >= N || nc < 0 || nc >= M)
                    break;

                if (map[nr][nc] == WALL)
                    break;

                if (flag) {
                    if (map[nr][nc] == ROUTE) {
                        map[nr][nc] = idx;
                        leftCnt--;
                    }
                } else {
                    if (map[nr][nc] == idx) {
                        map[nr][nc] = ROUTE;
                        leftCnt++;
                    }
                }
            }
        }
    }

    static void solve(int depth) {
        if (depth == cctvCnt + 1) {
            answer = answer < leftCnt ? answer : leftCnt;
            return;
        }

        for (int i = 0; i < DIR.length; ++i) {
            search(depth, i, true);
            solve(depth + 1);
            search(depth, i, false);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input15683.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAX_NM][MAX_NM];
        cctvs = new CCTV[MAX_CCTV + 1];

        st = new StringTokenizer(br.readLine());
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());

        int input;
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; ++j) {
                input = new Integer(st.nextToken());

                if (input == ROUTE) {
                    leftCnt++;
                } else if (input == 6) {
                    map[i][j] = WALL;
                } else {
                    map[i][j] = CCTV;
                    cctvs[++cctvCnt] = new CCTV(i, j, input);
                }
            }
        }

        answer = leftCnt;
        solve(1);

        System.out.println(answer);
        br.close();
    }
}