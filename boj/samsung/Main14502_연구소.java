
/**
 * @Date
 * 2019-08-08
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/14502
 *
 * @문제
 * 백준 14502. 연구소
 *
 * 바이러스가 유출되었지만 아직 퍼지지 않았고 벽을 세우려고 한다.
 * 연구소는 NxM 크기의 직사각형 형태로 나타낼 수 있다.
 * 일부 칸은 바이러스가 존재하며, 이 바이러스는 상하좌우로 인접한 빈 칸으로 모두 퍼저나갈 수 있다.
 * 새로 세울 수 있는 벽의 개수는 3개이며, 꼭 3개를 세워야한다.
 * 벽 3개를 세운 뒤, 바이러스가 퍼질 수 없는 곳을 안전 영역이라고 한다.
 * 안전역역 크기의 최댓값을 구하는 프로그램을 작성하라.
 *
 * @입력값
 * 세로크기 N과 가로크기 M (3 <= N, M <= 8)
 * 0은 빈칸, 1은 벽, 2는 바이러스가 있는 위치.
 *
 * @풀이방법
 * 1) 벽을 3개 세워야 하므로 안전영역인 빈칸의 좌표를 미리 저장해 두어 3중 for문을 돌면서 벽을 세울 좌표를 알아내자.
 * 2) 벽을 3개 세운 후에 dfs를 이용하여 탐색을 하면서 퍼질 수 있는 곳에 virus 표시(2)를 하자.
 * 3) 바이러스가 모두 퍼진 후에 남아 있는 안전영역의 수를 구한다.
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main14502_연구소 {
    static final int SIZE_MAX = 8, VIRUS = 2, WALL = 1, ROUTE = 0;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int N, M, answer, cnt;
    static int[][] map;
    static List<Point> virus, safety;

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // 2)
    static void dfs(int x, int y) {
        for (int[] dir : DIR) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx < 0 || nx >= N || ny < 0 || ny >= M)
                continue;

            if (map[nx][ny] == VIRUS || map[nx][ny] == WALL)
                continue;

            map[nx][ny] = VIRUS;
            cnt++;
            dfs(nx, ny);
        }
    }

    // 2)
    static void dfs() {
        for (int i = 0; i < virus.size(); i++) {
            dfs(virus.get(i).x, virus.get(i).y);
        }
    }

    static void arrayCopy(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++)
                a[i][j] = b[i][j];
        }
    }

    static void solve() {
        int[][] tmp = new int[SIZE_MAX][SIZE_MAX];
        cnt = 0;

        arrayCopy(tmp, map); // 배열 움직여야 하므로
        dfs();

        // 3)
        cnt = safety.size() - cnt - 3; // 원래 안전영역의 수 - 감염된 안전영역의 수 - 벽으로 설정한 영역
        answer = answer > cnt ? answer : cnt;

        arrayCopy(map, tmp); // 배열 원상 복구
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input14502.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        map = new int[SIZE_MAX][SIZE_MAX];
        virus = new ArrayList<>();
        safety = new ArrayList<>();
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                int data = new Integer(st.nextToken());

                // 1)
                if (data == ROUTE) {
                    safety.add(new Point(i, j));
                    continue;
                }

                map[i][j] = data;
                if (data == VIRUS) {
                    virus.add(new Point(i, j));
                }
            }
        }

        // 1)
        int s = safety.size();
        for (int i = 0; i < s - 2; i++) {
            for (int j = i + 1; j < s - 1; j++) {
                for (int k = j + 1; k < s; k++) {
                    map[safety.get(i).x][safety.get(i).y] = WALL;
                    map[safety.get(j).x][safety.get(j).y] = WALL;
                    map[safety.get(k).x][safety.get(k).y] = WALL;
                    solve();
                    map[safety.get(i).x][safety.get(i).y] = ROUTE;
                    map[safety.get(j).x][safety.get(j).y] = ROUTE;
                    map[safety.get(k).x][safety.get(k).y] = ROUTE;
                }
            }
        }

        System.out.println(answer);
        br.close();
    }
}
