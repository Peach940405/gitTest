/**
 * @Date 2019-08-16
 * @Author 최병길
 * @출처 https://www.acmicpc.net/problem/13460
 * @문제 백준 13460.
 * <p>
 * 스타트링크에서 판매하는 어린이용 장난감 중에서 가장 인기가 많은 제품은 구슬 탈출이다.
 * 구슬 탈출은 직사각형 보드에 빨간 구슬과 파란 구슬을 하나씩 넣은 다음, 빨간 구슬을 구멍을 통해 빼내는 게임이다.
 * <p>
 * 보드의 세로 크기는 N, 가로 크기는 M이고, 편의상 1×1크기의 칸으로 나누어져 있다.
 * 가장 바깥 행과 열은 모두 막혀져 있고, 보드에는 구멍이 하나 있다.
 * 빨간 구슬과 파란 구슬의 크기는 보드에서 1×1크기의 칸을 가득 채우는 사이즈이고, 각각 하나씩 들어가 있다.
 * 게임의 목표는 빨간 구슬을 구멍을 통해서 빼내는 것이다. 이때, 파란 구슬이 구멍에 들어가면 안 된다.
 * <p>
 * 이때, 구슬을 손으로 건드릴 수는 없고, 중력을 이용해서 이리 저리 굴려야 한다.
 * 왼쪽으로 기울이기, 오른쪽으로 기울이기, 위쪽으로 기울이기, 아래쪽으로 기울이기와 같은 네 가지 동작이 가능하다.
 * <p>
 * 각각의 동작에서 공은 동시에 움직인다.
 * 빨간 구슬이 구멍에 빠지면 성공이지만, 파란 구슬이 구멍에 빠지면 실패이다.
 * 빨간 구슬과 파란 구슬이 동시에 구멍에 빠져도 실패이다.
 * 빨간 구슬과 파란 구슬은 동시에 같은 칸에 있을 수 없다. 또, 빨간 구슬과 파란 구슬의 크기는 한 칸을 모두 차지한다.
 * 기울이는 동작을 그만하는 것은 더 이상 구슬이 움직이지 않을 때 까지이다.
 * <p>
 * 보드의 상태가 주어졌을 때, 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 구하는 프로그램을 작성하시오.
 * @입력값 첫 번째 줄에는 보드의 세로, 가로 크기를 의미하는 두 정수 N, M (3 ≤ N, M ≤ 10)이 주어진다.
 * 다음 N개의 줄에 보드의 모양을 나타내는 길이 M의 문자열이 주어진다.
 * 이 문자열은 '.', '#', 'O', 'R', 'B' 로 이루어져 있다.
 * '.'은 빈 칸을 의미하고, '#'은 공이 이동할 수 없는 장애물 또는 벽을 의미하며, 'O'는 구멍의 위치를 의미한다.
 * 'R'은 빨간 구슬의 위치, 'B'는 파란 구슬의 위치이다.
 * <p>
 * 입력되는 모든 보드의 가장자리에는 모두 '#'이 있다. 구멍의 개수는 한 개 이며, 빨간 구슬과 파란 구슬은 항상 1개가 주어진다.
 * @풀이방법 1. bfs를 이용하여 최단 시간으로 통과하는 시간을 찾는다.
 * 2. 원래의 빨간 구슬과 파란구슬 좌표를 저장한 후 구슬 탈출을 시작한다.
 * 3. 파란 구슬이 나온다면 continue 시켜 다른 구슬 상황을 보고, 빨간 구슬만 나왔다면 그때의 depth를 리턴시킨다.
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main13460_구슬탈출2 {
    static final int MAP_MAX = 10, RED_MARBLE = 'R', BLUE_MARBLE = 'B', HOLE = 'O', ROUTE = '.', WALL = '#';
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static char[][] map;
    static boolean[][][][] visited;
    static int N, M;
    static Marble marble;

    static class Marble {
        int redR, redC, blueR, blueC;

        Marble() {
            this(0, 0, 0, 0);
        }

        Marble(int redR, int redC, int blueR, int blueC) {
            this.redR = redR;
            this.redC = redC;
            this.blueR = blueR;
            this.blueC = blueC;
        }
    }

    static int solve() {
        int depth = -1;
        Marble curMarble;
        Queue<Marble> q = new LinkedList<>();

        q.offer(marble);
        visited[marble.redR][marble.redC][marble.blueR][marble.blueC] = true;

        while (!q.isEmpty()) {
            if (++depth > 10) // 10번 이하로 움직여서 빨간 구슬 못 빼내면 -1
                return -1;

            int qSize = q.size();
            for (int s = 0; s < qSize; ++s) {
                curMarble = q.poll();

                if (map[curMarble.blueR][curMarble.blueC] == HOLE)
                    continue;
                if (map[curMarble.redR][curMarble.redC] == HOLE)
                    return depth;

                for (int d = 0; d < DIR.length; ++d) {
                    // 빨간 구슬 이동
                    int redR = curMarble.redR;
                    int redC = curMarble.redC;

                    while (true) {
                        redR += DIR[d][0];
                        redC += DIR[d][1];

                        if (map[redR][redC] == HOLE) {
                            break;
                        } else if (map[redR][redC] == WALL) { // WALL 일경우
                            redR -= DIR[d][0];
                            redC -= DIR[d][1];
                            break;
                        }
                    }

                    // 파란 구슬 이동
                    int blueR = curMarble.blueR;
                    int blueC = curMarble.blueC;

                    while (true) {
                        blueR += DIR[d][0];
                        blueC += DIR[d][1];

                        if (map[blueR][blueC] == HOLE) {
                            break;
                        } else if (map[blueR][blueC] == WALL) { // WALL 일경우
                            blueR -= DIR[d][0];
                            blueC -= DIR[d][1];
                            break;
                        }
                    }

                    // 빨간 구슬과 파란 구슬이 겹치는 경우는 예외처리를 해야한다. (HOLE일 경우는 제외)
                    if (redR == blueR && redC == blueC && map[redR][redC] != HOLE) {
                        switch (d) {
                            case 0: // 동쪽으로 가는 경우
                                if (curMarble.redC > curMarble.blueC) // 처음에 빨간 구슬이 더 오른쪽에 있었다면
                                    blueC--;
                                else // 처음에 파란 구슬이 더 오른쪽에 있었다면
                                    redC--;
                                break;
                            case 1: // 남쪽으로 가는 경우
                                if (curMarble.redR > curMarble.blueR) // 처음에 빨간 구슬이 더 아래에 있었다면
                                    blueR--;
                                else // 처음에 파란 구슬이 더 아래에 있었다면
                                    redR--;
                                break;
                            case 2: // 서쪽으로 가는 경우
                                if (curMarble.redC > curMarble.blueC) // 처음에 파란 구슬이 더 왼쪽에 있었다면
                                    redC++;
                                else // 처음에 빨간 구슬이 더 왼쪽에 있었다면
                                    blueC++;
                                break;
                            case 3: // 북쪽으로 가는 경우
                                if (curMarble.redR > curMarble.blueR) // 처음에 파란 구슬이 더 아래에 있었다면
                                    redR++;
                                else // 처음에 빨간 구슬이 더 아래에 있었다면
                                    blueR++;
                        }
                    }

                    if (visited[redR][redC][blueR][blueC])
                        continue;

                    visited[redR][redC][blueR][blueC] = true;
                    q.offer(new Marble(redR, redC, blueR, blueC));
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input13460.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new char[MAP_MAX][MAP_MAX];
        visited = new boolean[MAP_MAX][MAP_MAX][MAP_MAX][MAP_MAX];
        st = new StringTokenizer(br.readLine());

        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());
        marble = new Marble();
        String input;

        for (int i = 0; i < N; ++i) {
            input = br.readLine();

            for (int j = 0; j < M; ++j) {
                map[i][j] = input.charAt(j);

                if (map[i][j] == RED_MARBLE) {
                    marble.redR = i;
                    marble.redC = j;
                } else if (map[i][j] == BLUE_MARBLE) {
                    marble.blueR = i;
                    marble.blueC = j;
                }
            }
        }

        System.out.println(solve());

        br.close();
    }
}
