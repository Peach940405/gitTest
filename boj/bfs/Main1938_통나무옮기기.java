/**
 * @Date
 * 2019-08-11
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/1938
 *
 * @문제
 * 백준 1938. 통나무 옮기기
 *
 * 길이 3인 통나무 BBB를 밀거나 회전시켜 EEE의 위치로 옮기는 작업을 하고, 위치는 임의로 주어진다.
 * 동서남북 이동가능하고 회전가능하지만 벽(1)은 갈 수 없을 때 최소의 이동 및 회전 동작을 찾아라
 *
 *
 * @입력값
 * 평지의 한변의 길이 N (4 <= N <= 50)
 * 0, 1, B, E로 이루어진 평지데이터
 *
 *
 * @풀이방법
 * 1. BBB와 EEE의 좌표를 미리 저장하여 초기의 모양이 가로인지 세로인지 파악하자.
 * 2. bfs를 이용하여 최선의 동작을 파악하는데, 가운데 통나무 만을 확인하자.
 * 3. 먼저 동서남북 이동할 수 있는지를 확인하는데 동서남북에서 이동이 가능할 때 나머지 2개도 이동가능한지 확인한다.
 * 4. 회전을 확인하는데 8방향을 확인하면서 세로일때는 위,아래 가로일 때는 좌우는 확인하지 않도록 하자.
 * 5. EEE에 도착했을 때 answer에 답을 넣어 출력해주자.
 *
 */


import java.io.*;
import java.util.*;

public class Main1938_통나무옮기기 {
    static final int N_MAX = 50, P_MAX = 3, B = 2, E = 3, INF = 987654321, ROW = 0, HI = 1, CENTER = 1;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 동, 남, 서, 북
    static final int[][] TURN_DIR = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    static final int[][][] CHECK = {
            {{0, -1}, {0, 1}}, // 가로
            {{-1, 0}, {1, 0}}, // 세로
    };

    static int[][] map;
    static Point[] log, exit;
    static int N, answer, logIdx, exitIdx;

    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static boolean isTurn(int r, int c, int shape) {
        for (int d = 0; d < TURN_DIR.length; d++) {
            if (shape == ROW && (d == 2 || d == 6)) // 가로 모양
                continue;
            if (shape == HI && (d == 0 || d == 4)) // 세로 모양
                continue;

            int nr = r + TURN_DIR[d][0];
            int nc = c + TURN_DIR[d][1];

            if (nr < 0 || nr >= N || nc < 0 || nc >= N)
                return false;

            if (map[nr][nc] == 1)
                return false;
        }

        return true;
    }

    static boolean checkShape(Point[] pa) {
        if (pa[0].r == pa[1].r && pa[1].r == pa[2].r) // 가로 모드
            return true;

        return false;
    }

    static void solve() {
        Queue<int[]> q = new LinkedList<>();
        boolean[][][] visited = new boolean[N][N][2];
        int depth = 0;

        // 2)
        if (checkShape(log)) {
            q.offer(new int[]{log[CENTER].r, log[CENTER].c, ROW});
            visited[log[CENTER].r][log[CENTER].c][ROW] = true;
        } else {
            q.offer(new int[]{log[CENTER].r, log[CENTER].c, HI});
            visited[log[CENTER].r][log[CENTER].c][HI] = true;
        }

        int exitShape = HI;
        if (checkShape(exit))
            exitShape = ROW;

        while (!q.isEmpty()) {
            int qSize = q.size();
            depth++;

            for (int s = 0; s < qSize; ++s) {
                int r = q.peek()[0];
                int c = q.peek()[1];
                int shape = q.poll()[2];

                // 5)
                if (r == exit[CENTER].r && c == exit[CENTER].c && shape == exitShape) {
                    answer = depth - 1;
                    return;
                }

                // 3)
                // 동서남북 이동 체크
                for (int d = 0; d < DIR.length; d++) {
                    int nr = r + DIR[d][0];
                    int nc = c + DIR[d][1];

                    if (nr < 0 || nr >= N || nc < 0 || nc >= N)
                        continue;

                    if (map[nr][nc] == 1 || visited[nr][nc][shape])
                        continue;

                    int flagCnt = 0;
                    // 3)
                    for (int[] isPossible : CHECK[shape]) { // 각 방향에서 나머지 2개의 통나무가 움직일 수 있는지 확인
                        int cnr = nr + isPossible[0];
                        int cnc = nc + isPossible[1];

                        if (cnr < 0 || cnr >= N || cnc < 0 || cnc >= N)
                            continue;

                        if (map[cnr][cnc] == 1) // 길이아니면 갈 수 없다.
                            continue;

                        flagCnt++;
                    }

                    if (flagCnt == 2) { // 나머지 2개도 이동할 수 있다면
                        visited[nr][nc][shape] = true;
                        q.offer(new int[]{nr, nc, shape});
                    }
                }

                // 4)
                // 회전 가능한지 체크
                if (shape == ROW) { // 통나무가 가로 모양
                    if (!visited[r][c][HI] && isTurn(r, c, ROW)) { // 회전할 수 있다면
                        // 세로로 회전시키자.
                        visited[r][c][HI] = true;
                        q.offer(new int[]{r, c, HI});
                    }
                } else { // 통나무가 세로 모양
                    if (!visited[r][c][ROW] && isTurn(r, c, HI)) { // 회전할 수 있다면
                        // 가로로 회전시키자.
                        visited[r][c][ROW] = true;
                        q.offer(new int[]{r, c, ROW});
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[N_MAX][N_MAX];
        log = new Point[P_MAX];
        exit = new Point[P_MAX];
        N = new Integer(br.readLine());

        for (int i = 0; i < N; ++i) {
            String s = br.readLine();

            // 1)
            for (int j = 0; j < N; ++j) {
                switch (s.charAt(j)) {
                    case '1':
                        map[i][j] = 1;
                        break;
                    case 'B':
                        map[i][j] = B;
                        log[logIdx++] = new Point(i, j);
                        break;
                    case 'E':
                        map[i][j] = E;
                        exit[exitIdx++] = new Point(i, j);
                }
            }
        }

        answer = INF;
        solve();
        answer = answer != INF ? answer : 0;

        System.out.println(answer);
        br.close();
    }
}
