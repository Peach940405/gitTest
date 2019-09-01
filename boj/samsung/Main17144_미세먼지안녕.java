/**
 * @Date
 * 2019-09-01
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17144
 *
 * @문제
 * 백준 17144. 미세먼지안녕
 *
 * 세먼지를 제거하기 위해 구사과는 공기청정기를 설치하려고 한다.
 * 공기청정기의 성능을 테스트하기 위해 구사과는 집을 크기가 R×C인 격자판으로 나타냈고, 1×1 크기의 칸으로 나눴다.
 * 구사과는 뛰어난 코딩 실력을 이용해 각 칸 (r, c)에 있는 미세먼지의 양을 실시간으로 모니터링하는 시스템을 개발했다. (r, c)는 r행 c열을 의미한다.
 *
 * 공기청정기는 항상 왼쪽 열에 설치되어 있고, 크기는 두 행을 차지한다.
 * 공기청정기가 설치되어 있지 않은 칸에는 미세먼지가 있고, (r, c)에 있는 미세먼지의 양은 Ar,c이다.
 *
 * 1초 동안 아래 적힌 일이 순서대로 일어난다.
 *
 * 1. 미세먼지가 확산된다. 확산은 미세먼지가 있는 모든 칸에서 동시에 일어난다.
 *      (r, c)에 있는 미세먼지는 인접한 네 방향으로 확산된다.
 *      인접한 방향에 공기청정기가 있거나, 칸이 없으면 그 방향으로는 확산이 일어나지 않는다.
 *      확산되는 양은 Ar,c/5이고 소수점은 버린다.
 *      (r, c)에 남은 미세먼지의 양은 Ar,c - (Ar,c/5)×(확산된 방향의 개수) 이다.
 * 2. 공기청정기가 작동한다.
 *      공기청정기에서는 바람이 나온다.
 *      위쪽 공기청정기의 바람은 반시계방향으로 순환하고, 아래쪽 공기청정기의 바람은 시계방향으로 순환한다.
 *      바람이 불면 미세먼지가 바람의 방향대로 모두 한 칸씩 이동한다.
 *      공기청정기에서 부는 바람은 미세먼지가 없는 바람이고, 공기청정기로 들어간 미세먼지는 모두 정화된다.
 *
 * 방의 정보가 주어졌을 때, T초가 지난 후 구사과의 방에 남아있는 미세먼지의 양을 구해보자.
 *
 * @입력값
 * 첫째 줄에 R, C, T (6 ≤ R, C ≤ 50, 1 ≤ T ≤ 1,000) 가 주어진다.
 *
 * 둘째 줄부터 R개의 줄에 Ar,c (-1 ≤ Ar,c ≤ 1,000)가 주어진다.
 * 공기청정기가 설치된 곳은 Ar,c가 -1이고, 나머지 값은 미세먼지의 양이다.
 * -1은 2번 위아래로 붙어져 있고, 가장 윗 행, 아랫 행과 두 칸이상 떨어져 있다.
 *
 * @풀이방법
 * 1. 청정기의 위치좌표를 저장해두고 탐색을 시작한다.
 * 2. 먼저 미세먼지의 확산을 칸이 존재하고 청정기 위치가 아닌곳이라면 확산을 시킨다.
 * 3. 공기 청정기 윗부분과 아래부분을 이동시킨다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main17144_미세먼지안녕 {
    static final int MAX_RC = 50, UP = 0, DOWN = 1;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    static int[][] map, tmpMap;
    static Coordinate[] coordinates;
    static int R, C, T;
    static int answer;

    static class Coordinate {
        int r, c;

        Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static void setAnswer() {
        for (int i = 0; i < R; ++i) {
            for (int j = 0; j < C; ++j) {
                if (map[i][j] == 0)
                    continue;

                answer += map[i][j];
            }
        }

        answer += 2;
    }

    static void cleanDown() {
        for (int i = coordinates[DOWN].r + 2; i < R; ++i) {
            map[i - 1][0] = map[i][0];
        }

        for (int i = 1; i < C; ++i) {
            map[R - 1][i - 1] = map[R - 1][i];
        }

        for (int i = R - 2; i >= coordinates[DOWN].r; --i) {
            map[i + 1][C - 1] = map[i][C - 1];
        }

        for (int i = C - 1; i >= 1; --i) {
            map[coordinates[DOWN].r][i + 1] = map[coordinates[DOWN].r][i];
        }
        map[coordinates[DOWN].r][1] = 0;
    }

    static void cleanUp() {
        for (int i = coordinates[UP].r - 2; i >= 0; --i) {
            map[i + 1][0] = map[i][0];
        }

        for (int i = 1; i < C; ++i) {
            map[0][i - 1] = map[0][i];
        }

        for (int i = 1; i <= coordinates[UP].r; ++i) {
            map[i - 1][C - 1] = map[i][C - 1];
        }

        for (int i = C - 1; i >= 1; --i) {
            map[coordinates[UP].r][i + 1] = map[coordinates[UP].r][i];
        }

        map[coordinates[UP].r][1] = 0;
    }

    static void cleanAir() {
        cleanUp();
        cleanDown();
    }

    static void spread(int r, int c) {
        int cnt = 0, dust = map[r][c] / 5;
        int nr, nc;

        for (int[] dir : DIR) {
            nr = r + dir[0];
            nc = c + dir[1];

            // 칸이 없으면 확산이 일어나지 않음
            if (nr < 0 || nr >= R || nc < 0 || nc >= C)
                continue;

            // 공기청정기로는 확신이 일어나지 않음
            if (map[nr][nc] == -1)
                continue;

            cnt++;
            tmpMap[nr][nc] += dust;
        }

        map[r][c] -= map[r][c] / 5 * cnt;
    }

    static void spread() {
        tmpMap = new int[MAX_RC + 1][MAX_RC + 1];

        for (int i = 0; i < R; ++i) {
            for (int j = 0; j < C; ++j) {
                if (map[i][j] == 0 || map[i][j] == -1)
                    continue;

                spread(i, j);
            }
        }

        // 확산된 미세먼지 더해주기
        for (int i = 0; i < R; ++i) {
            for (int j = 0; j < C; ++j) {
                map[i][j] += tmpMap[i][j];
            }
        }
    }

    static void solve(int t) {
        if (t == T) {
            setAnswer();
            return;
        }

        spread();
        cleanAir();

        solve(t + 1);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input17144.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAX_RC + 1][MAX_RC + 1];
        coordinates = new Coordinate[2];

        st = new StringTokenizer(br.readLine());
        R = new Integer(st.nextToken());
        C = new Integer(st.nextToken());
        T = new Integer(st.nextToken());

        int input, idx = 0;

        for (int i = 0; i < R; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < C; ++j) {
                input = new Integer(st.nextToken());

                if (input == 0)
                    continue;

                map[i][j] = input;
                if (input == -1) {
                    coordinates[idx++] = new Coordinate(i, j);
                }
            }
        }

        solve(0);

        System.out.println(answer);
        br.close();
    }
}
