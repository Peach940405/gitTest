/**
 * @Date
 * 2019-08-11
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17135
 *
 * @문제
 * 백준 17135. 캐슬 디펜스
 *
 * 성을 향해 몰려오는 적을 잡는 게임이다. 게임이 진행되는 곳은 N * M인 격자판이다.
 * N번행 바로아래 성이 있다. 성에는 궁수 최대 3명이 있을 수 있다.
 * 모든 궁수는 동시에 공격하여 거리가 D이하인 적중에 가장 가까운 적이고, 그러한 적이 여러한 경우일 때는 가장 왼쪽적을 공격한다.
 * 공격이 끝나면 적들은 아래로 한칸 이동한다.
 * 궁수의 위치를 달리 하였을 때, 궁수의 공격으로 제거할 수 있는 최대의 적을 구하라.
 *
 *
 * @입력값
 * 첫째 줄에 격자판 행의 수 N, 열의 수 M, 궁수의 공격 거리 제한 D가 주어진다.
 * 3 <= N, M <= 5
 * 1 <= D <= 10
 * 둘째 줄부터 N개의 줄에는 격자판의 상태가 주어진다. 0은 빈 칸, 1은 적이 있는 칸이다.
 *
 *
 * @풀이방법
 * 1. 3중 for문을 이용하여 각 궁수의 좌표마다 테스트케이스를 solve한다.
 * 2. bfs를 이용하여 q에 궁수의 좌표를 추가한 후 공격(shoot)하여 공격당한 적이 있는지 확인한다.
 * 3. shoot 함수에서는 다음의 좌표 nr, nc를 구하여 범위를 확인하여 공격할 수 있는 적을 shootedQ에 넣는다.
 * 4. shoot 함수가 끝난 후 적들이 내려 올 수 있다면(궁수가 위로 올라갈 수 있다면) q에 다시 궁수의 좌표를 갱신해서 넣어준다.
 * 5. 적들이 모두 성들을 통과했다면 res를 answer와 비교하여 최대값을 구해준다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main17135_캐슬디펜스 {
    static int MAP_MAX = 15;
    static int[][] map, check;
    static int answer, res, N, M, D, checkNum;
    static Point[] shooter;

    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static void shoot(int r, int c, Queue<Point> q) {
        for (int d = 1; d <= D; d++) { // 거리가 가장 작은 순 부터
            for (int dc = 1 - d; dc <= d - 1; ++dc) { // 왼쪽 좌표부터 본다.
                int dr = d - Math.abs(dc);
                int nr = r - dr;
                int nc = c + dc;

                if (nr < 0 || nc < 0 || nc >= M)
                    continue;

                if (map[nr][nc] == 0 || check[nr][nc] == checkNum) // 적이 없거나, 이전에 죽인 적이라면
                    continue;

                q.offer(new Point(nr, nc));
                return;
            }
        }
    }

    static void solve(int c1, int c2, int c3) {
        Queue<Point> q = new LinkedList<>(), shootedQ = new LinkedList<>();
        q.offer(new Point(N, c1));
        q.offer(new Point(N, c2));
        q.offer(new Point(N, c3));

        // 2)
        while (!q.isEmpty()) {
            for (int s = 0; s < 3; ++s) { // 궁수는 최대 3명
                Point curShooter = q.poll();
                // 3)
                shoot(curShooter.r, curShooter.c, shootedQ);

                // 4)
                if (curShooter.r - 1 > 0) // 적들을 내릴 수 있다면
                    q.offer(new Point(curShooter.r - 1, curShooter.c));
            }

            while (!shootedQ.isEmpty()) {
                Point curEnemy = shootedQ.poll();

                if (check[curEnemy.r][curEnemy.c] == checkNum) // 이미 죽인 적이라면(여러명의 궁수가 한명의 적을 쏠 수 도 있음)
                    continue;

                check[curEnemy.r][curEnemy.c] = checkNum;
                res++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input17135.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAP_MAX][MAP_MAX];
        check = new int[MAP_MAX][MAP_MAX];

        st = new StringTokenizer(br.readLine());
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());
        D = new Integer(st.nextToken());

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; ++j) {
                int data = new Integer(st.nextToken());
                if (data == 0)
                    continue;

                map[i][j] = data;
            }
        }

        answer = 0;
        shooter = new Point[3]; // 최대 궁수 3명까지 가능

		// 1)
        for (int i = 0; i < M - 2; ++i) {
            for (int j = i + 1; j < M - 1; ++j) {
                for (int k = j + 1; k < M; ++k) {
                    ++checkNum;
                    res = 0;
                    solve(i, j, k);
                    // 5)
                    answer = answer > res ? answer : res;
                }
            }
        }

        System.out.println(answer);
        br.close();
    }
}
