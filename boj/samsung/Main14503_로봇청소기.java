/**
 * @Date
 * 2019-08-22
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/14503
 *
 * @문제
 * 백준 14503. 로봇청소기
 *
 * 로봇 청소기가 주어졌을 때, 청소하는 영역의 개수를 구하는 프로그램을 작성하시오.
 *
 * 로봇 청소기가 있는 장소는 N×M 크기의 직사각형으로 나타낼 수 있으며, 1×1크기의 정사각형 칸으로 나누어져 있다. 각각의 칸은 벽 또는 빈 칸이다. 청소기는 바라보는 방향이 있으며, 이 방향은 동, 서, 남, 북중 하나이다. 지도의 각 칸은 (r, c)로 나타낼 수 있고, r은 북쪽으로부터 떨어진 칸의 개수, c는 서쪽으로 부터 떨어진 칸의 개수이다.
 *
 * 로봇 청소기는 다음과 같이 작동한다.
 *
 * 1. 현재 위치를 청소한다.
 * 2. 현재 위치에서 현재 방향을 기준으로 왼쪽방향부터 차례대로 탐색을 진행한다.
 *      a. 왼쪽 방향에 아직 청소하지 않은 공간이 존재한다면, 그 방향으로 회전한 다음 한 칸을 전진하고 1번부터 진행한다.
 *      b. 왼쪽 방향에 청소할 공간이 없다면, 그 방향으로 회전하고 2번으로 돌아간다.
 *      c. 네 방향 모두 청소가 이미 되어있거나 벽인 경우에는, 바라보는 방향을 유지한 채로 한 칸 후진을 하고 2번으로 돌아간다.
 *      d. 네 방향 모두 청소가 이미 되어있거나 벽이면서, 뒤쪽 방향이 벽이라 후진도 할 수 없는 경우에는 작동을 멈춘다.
 * 로봇 청소기는 이미 청소되어있는 칸을 또 청소하지 않으며, 벽을 통과할 수 없다.
 *
 * @입력값
 * 첫째 줄에 세로 크기 N과 가로 크기 M이 주어진다. (3 ≤ N, M ≤ 50)
 *
 * 둘째 줄에 로봇 청소기가 있는 칸의 좌표 (r, c)와 바라보는 방향 d가 주어진다.
 * d가 0인 경우에는 북쪽을, 1인 경우에는 동쪽을, 2인 경우에는 남쪽을, 3인 경우에는 서쪽을 바라보고 있는 것이다.
 *
 * 셋째 줄부터 N개의 줄에 장소의 상태가 북쪽부터 남쪽 순서대로, 각 줄은 서쪽부터 동쪽 순서대로 주어진다.
 * 빈 칸은 0, 벽은 1로 주어진다. 장소의 모든 외곽은 벽이다.
 *
 * 로봇 청소기가 있는 칸의 상태는 항상 빈 칸이다.
 *
 * @풀이방법
 * 1. 시뮬레이션 문제로서 요구사항을 순서대로 구현한다.
 * 2. bfs를 이용하자.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main14503_로봇청소기 {
    static final int CLEANING = -1, WALL = 1, NOT_CLAEN = 0;
    static final int[][] DIR = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static final int[] TURN = {3, 0, 1, 2};
    static final int[] BACK = {2, 3, 0, 1};
    static final int MAP_MAX = 50;
    static int[][] map;
    static int N, M;
    static int answer;

    static class Vacuum {
        int r, c, d;

        Vacuum(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    static void solve(Vacuum vacuum) {
        Queue<Vacuum> q = new LinkedList<>();
        q.offer(vacuum);

        // 1. 현재 위치를 청소
        map[vacuum.r][vacuum.c] = CLEANING;

        while (!q.isEmpty()) {
            Vacuum curVacuum = q.poll();

            boolean flag = false;
            int nr, nc;
            int nd = curVacuum.d;

            // 2. 현재 위치에서 현재 방향을 기준으로 왼쪽 방향 부터 차례대로 탐색을 진행
            for (int d = 0; d < DIR.length; ++d) {
                nd = TURN[nd];
                nr = curVacuum.r + DIR[nd][0];
                nc = curVacuum.c + DIR[nd][1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M)
                    continue;

                // 2.a 왼쪽 방향에 청소하지 않은 공간이 존재하면
                if (map[nr][nc] == NOT_CLAEN) {
                    // 그 방향으로 회전한 다음 한 칸을 전진하고 1번 부터 실행(청소를 진행)
                    answer++;
                    map[nr][nc] = CLEANING;
                    q.offer(new Vacuum(nr, nc, nd));
                    flag = true; // 네 방향 모두 청소 안되있다
                    break;
                }
                // 2.b  왼쪽방향에 청소할 공간이 없다면 회전하고 2번으로 돌아감
            }

            // 네 방향 모두 청소가 이미 되어있을 때
            if (!flag) {
                nd = BACK[nd];
                nr = curVacuum.r + DIR[nd][0];
                nc = curVacuum.c + DIR[nd][1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M)
                    continue;

                // 뒤쪽 방향이 벽이 아니면 바라보는 방향을 유지한 채로 한 칸 후진을 하고 2번으로 돌아감
                if (map[nr][nc] != WALL) {
                    map[nr][nc] = CLEANING;
                    q.offer(new Vacuum(nr, nc, curVacuum.d));
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input14503.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAP_MAX][MAP_MAX];

        st = new StringTokenizer(br.readLine());
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());

        st = new StringTokenizer(br.readLine());
        Vacuum vacuum = new Vacuum(new Integer(st.nextToken()), new Integer(st.nextToken()), new Integer(st.nextToken()));

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; ++j) {
                map[i][j] = new Integer(st.nextToken());
            }
        }

        solve(vacuum);
        System.out.println(answer + 1);

        br.close();
    }
}