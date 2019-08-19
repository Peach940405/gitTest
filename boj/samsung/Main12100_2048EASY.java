/**
 * @Date
 * 2019-08-19
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/12100
 *
 * @문제
 * 백준 12100. 2048(Easy)
 *
 * 2048 게임은 4×4 크기의 보드에서 혼자 즐기는 재미있는 게임이다. 이 링크를 누르면 게임을 해볼 수 있다.
 *
 * 이 게임에서 한 번의 이동은 보드 위에 있는 전체 블록을 상하좌우 네 방향 중 하나로 이동시키는 것이다.
 * 이때, 같은 값을 갖는 두 블록이 충돌하면 두 블록은 하나로 합쳐지게 된다.
 * 한 번의 이동에서 이미 합쳐진 블록은 또 다른 블록과 다시 합쳐질 수 없다.
 * (실제 게임에서는 이동을 한 번 할 때마다 블록이 추가되지만, 이 문제에서 블록이 추가되는 경우는 없다)
 *
 * 똑같은 수가 세 개가 있는 경우에는 이동하려고 하는 쪽의 칸이 먼저 합쳐진다.
 * 예를 들어, 위로 이동시키는 경우에는 위쪽에 있는 블록이 먼저 합쳐지게 된다.
 *
 * 이 문제에서 다루는 2048 게임은 보드의 크기가 N×N 이다.
 * 보드의 크기와 보드판의 블록 상태가 주어졌을 때, 최대 5번 이동해서 만들 수 있는 가장 큰 블록의 값을 구하는 프로그램을 작성하시오.
 *
 * @입력값
 * 첫째 줄에 보드의 크기 N (1 ≤ N ≤ 20)이 주어진다.
 * 둘째 줄부터 N개의 줄에는 게임판의 초기 상태가 주어진다.
 * 0은 빈 칸을 나타내며, 이외의 값은 모두 블록을 나타낸다.
 * 블록에 쓰여 있는 수는 2보다 크거나 같고, 1024보다 작거나 같은 2의 제곱꼴이다.
 * 블록은 적어도 하나 주어진다.
 *
 * @풀이방법
 *
 * 1. 재귀를 통해서 각 방향마다 완전탐색을 해야한다.
 * 2. map을 복사해가면서 이동방향을 확인한 후 이동조건에 맞는다면 조건에 맞게 이동시킨다.
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main12100_2048EASY {
    static final int N_MAX = 20, RIGHT = 0, DOWN = 1, LEFT = 2, UP = 3;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int[][] map;
    static int answer, N;

    static void move(int r, int c, int d, int[][] map, boolean[][] visited) {
        int nr = r + DIR[d][0];
        int nc = c + DIR[d][1];

        if (nr < 0 || nr >= N || nc < 0 || nc >= N)
            return;

        if (map[nr][nc] == map[r][c] && !visited[nr][nc]) { // 다음 숫자와 같고, 합쳐진 적이 없는 경우
            map[nr][nc] <<= 1;
            map[r][c] = 0;
            visited[nr][nc] = true;
        } else if (map[nr][nc] == 0) { // 다음 숫자가 0인 경우
            map[nr][nc] = map[r][c];
            map[r][c] = 0;
            move(nr, nc, d, map, visited);
        }
    }

    static int[][] moveUp(int[][] map, boolean[][] visited) {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (map[i][j] > 0)
                    move(i, j, UP, map, visited);
            }
        }

        return map;
    }

    static int[][] moveLeft(int[][] map, boolean[][] visited) {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (map[i][j] > 0)
                    move(i, j, LEFT, map, visited);
            }
        }

        return map;
    }

    static int[][] moveDown(int[][] map, boolean[][] visited) {
        for (int i = N - 1; i >= 0; --i) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] > 0)
                    move(i, j, DOWN, map, visited);
            }
        }

        return map;
    }

    static int[][] moveRight(int[][] map, boolean[][] visited) {
        for (int i = 0; i < N; ++i) {
            for (int j = N - 1; j >= 0; --j) {
                if (map[i][j] > 0)
                    move(i, j, RIGHT, map, visited);
            }
        }

        return map;
    }

    static void arrayCopyy(int[][] tmpMap, int[][] map) {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                tmpMap[i][j] = map[i][j];
            }
        }
    }

    static int[][] move(int d, int[][] map) {
        boolean[][] visited = new boolean[N_MAX][N_MAX];
        int[][] tmpMap = new int[N_MAX][N_MAX];
        arrayCopyy(tmpMap, map);

        switch (d) {
            case RIGHT:
                return moveRight(tmpMap, visited);
            case DOWN:
                return moveDown(tmpMap, visited);
            case LEFT:
                return moveLeft(tmpMap, visited);
            case UP:
                return moveUp(tmpMap, visited);
            default:
                return null;
        }
    }

    static void setAnswer(int[][] map) {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                answer = answer > map[i][j] ? answer : map[i][j];
            }
        }
    }

    static void solve(int depth, int[][] map) {
        if (depth == 5) {
            setAnswer(map);
            return;
        }

        for (int d = 0; d < DIR.length; ++d) {
            solve(depth + 1, move(d, map));
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input12100.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[N_MAX][N_MAX];
        N = new Integer(br.readLine());

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; ++j) {
                map[i][j] = new Integer(st.nextToken());
            }
        }

        solve(0, map);

        System.out.println(answer);
        br.close();
    }
}