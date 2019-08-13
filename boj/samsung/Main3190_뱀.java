/**
 * @Date
 * 2019-08-13
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/3190
 *
 * @문제
 * 백준 3190. 뱀
 *
 * 'Dummy' 라는 도스게임이 있다. 이 게임에는 뱀이 나와서 기어다니는데, 사과를 먹으면 뱀 길이가 늘어난다.
 * 뱀이 이리저리 기어다니다가 벽 또는 자기자신의 몸과 부딪히면 게임이 끝난다.
 * 게임은 NxN 정사각 보드위에서 진행되고, 몇몇 칸에는 사과가 놓여져 있다.
 * 보드의 상하좌우 끝에 벽이 있다.
 * 게임이 시작할때 뱀은 맨위 맨좌측에 위치하고 뱀의 길이는 1 이다. 뱀은 처음에 오른쪽을 향한다.
 *
 * 뱀은 매 초마다 이동을 하는데 다음과 같은 규칙을 따른다.
 *
 * 먼저 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
 * 만약 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
 * 만약 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.
 * 사과의 위치와 뱀의 이동경로가 주어질 때 이 게임이 몇 초에 끝나는지 계산하라.
 *
 * @입력값
 * 첫째 줄에 보드의 크기 N이 주어진다. (2 ≤ N ≤ 100) 다음 줄에 사과의 개수 K가 주어진다. (0 ≤ K ≤ 100)
 * 다음 K개의 줄에는 사과의 위치가 주어지는데, 첫 번째 정수는 행, 두 번째 정수는 열 위치를 의미한다.
 * 사과의 위치는 모두 다르며, 맨 위 맨 좌측 (1행 1열) 에는 사과가 없다.
 * 다음 줄에는 뱀의 방향 변환 횟수 L 이 주어진다. (1 ≤ L ≤ 100)
 * 다음 L개의 줄에는 뱀의 방향 변환 정보가 주어지는데,  정수 X와 문자 C로 이루어져 있으며. 게임 시작 시간으로부터 X초가 끝난 뒤에 왼쪽(C가 'L') 또는 오른쪽(C가 'D')로 90도 방향을 회전시킨다는 뜻이다. X는 10,000 이하의 양의 정수이며, 방향 전환 정보는 X가 증가하는 순으로 주어진다.첫째 줄에 격자판 행의 수 N, 열의 수 M, 궁수의 공격 거리 제한 D가 주어진다.
 * 3 <= N, M <= 5
 * 1 <= D <= 10
 * 둘째 줄부터 N개의 줄에는 격자판의 상태가 주어진다. 0은 빈 칸, 1은 적이 있는 칸이다.
 *
 * @풀이방법
 * 1. 방향 변환 정보를 저장할 때 해당 게임 시작 인덱스에 방향 변화 정보를 저장해두자.
 * 2. 덱(디큐)를 이용해 뱀의 머리를 확인하여 다음 데이터에 따라서 방법을 처리하자.
 * 3. 다음 좌표가 뱀이거나 범위를 초과하면 게임이 끝난다.
 * 4. 먼저 다음 좌표에 뱀의 머리를 이동시킨 후(덱의 앞에 자료를 추가한 후) 다음 좌표가 사과가 아니라면 꼬리를 없애자.
 *
 */

import java.io.*;
import java.util.*;

public class Main3190_뱀 {
    static final int N_MAX = 100, L_MAX = 10000, APPLE = -1, SNAKE = 1, ROUTE = 0;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int[][] map;
    static int[] change;
    static int N, K, L, answer;
    static Snake snake;

    static class Snake {
        int r, c, d;

        Snake(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    static int solve() {
        int depth = 0;
        // 2)
        Deque<Snake> q = new ArrayDeque<>();

        q.offerLast(new Snake(0, 0, 0));
        map[0][0] = SNAKE;

        while (!q.isEmpty()) {
            Snake snake = q.peekFirst();

            if (change[depth] != 0)
                snake.d = (snake.d + change[depth] + 4) % 4;

            int nr = snake.r + DIR[snake.d][0];
            int nc = snake.c + DIR[snake.d][1];

            // 3)
            if (nr < 0 || nr >= N || nc < 0 || nc >= N || map[nr][nc] == SNAKE)
                return depth + 1;

            // 4)
            q.offerFirst(new Snake(nr, nc, snake.d));
            if (map[nr][nc] == ROUTE) {
                Snake tail = q.pollLast();
                map[tail.r][tail.c] = ROUTE;
            }

            map[nr][nc] = SNAKE;
            depth++;
        }

        return depth + 1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[N_MAX][N_MAX];
        N = new Integer(br.readLine());
        K = new Integer(br.readLine());

        for (int i = 0; i < K; ++i) {
            st = new StringTokenizer(br.readLine());
            map[new Integer(st.nextToken()) - 1][new Integer(st.nextToken()) - 1] = APPLE;
        }

        change = new int[L_MAX + 1];
        L = new Integer(br.readLine());
        for (int i = 0; i < L; ++i) {
            st = new StringTokenizer(br.readLine());

            // 1)
            int idx = new Integer(st.nextToken());
            switch (st.nextToken().charAt(0)) {
                case 'L':
                    change[idx] = -1; // 왼쪽으로 90도 회전
                    break;
                case 'D':
                    change[idx] = 1; // 오른쪽 90도 회전
            }
        }

        System.out.println(solve());
        br.close();
    }
}
