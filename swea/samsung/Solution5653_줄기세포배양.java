/**
 * @Date
 * 2019-09-01
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRJ8EKe48DFAUo
 *
 * @문제
 * swea 5653. [모의 SW 역량테스트] 줄기세포배양
 *
 * 줄기세포 배양 시뮬레이션 프로그램을 만들려고 한다.
 * 줄기세포들을 배양 용기에 도포한 후 일정 시간 동안 배양을 시킨 후
 * 줄기 세포의 개수가 몇 개가 되는지 계산하는 시뮬레이션 프로그램을 만들어야 한다.
 *
 * 하나의 줄기 세포는 가로, 세로 크기가 1인 정사각형 형태로 존재하며 배양 용기는 격자 그리드로 구성되어 있으며
 * 하나의 그리드 셀은 줄기 세포의 크기와 동일한 가로, 세로 크기가 1인 정사각형이다.
 *
 * 각 줄기 세포는 생명력이라는 수치를 가지고 있다.
 * 초기 상태에서 줄기 세포들은 비활성 상태이며 생명력 수치가 X인 줄기 세포의 경우 X시간 동안 비활성 상태이고
 * X시간이 지나는 순간 활성 상태가 된다.
 * 줄기 세포가 활성 상태가 되면 X시간 동안 살아있을 수 있으며 X시간이 지나면 세포는 죽게 된다.
 * 세포가 죽더라도 소멸되는 것은 아니고 죽은 상태로 해당 그리드 셀을 차지하게 된다.
 *
 * 활성화된 줄기 세포는 첫 1시간 동안 상, 하, 좌, 우 네 방향으로 동시에 번식을 한다.
 * 번식된 줄기 세포는 비활성 상태이다.
 * 하나의 그리드 셀에는 하나의 줄기 세포만 존재할 수 있기 때문에 번식하는 방향에 이미 줄기 세포가 존재하는 경우
 * 해당 방향으로 추가적으로 번식하지 않는다.
 * 두 개 이상의 줄기 세포가 하나의 그리드 셀에 동시 번식하려고 하는 경우 생명력 수치가 높은 줄기 세포가 해당 그리드 셀을 혼자서 차지하게 된다.
 *
 * 줄기 세포의 크기에 비해 배양 용기의 크기가 매우 크기 때문에 시뮬레이션에서 배양 용기의 크기는 무한하다고 가정한다.
 *
 * @입력값
 * 세포가 분포된 세로크기 N (1 <= N <= 50), 가로 크기 M ( 1 <= M <= 50), 배양 시간 K (1 <= K <= 300)
 *
 * @풀이방법
 * 1. 세포들을 리스트에 담아두고 사이즈 만큼 for문을 돌린다.
 * 2. 해당 세포의 시간이 0이 되면 해당 세포는 번식과 함께 활성화 상태가 된다.
 * 3. 활성화 시간이 끝나면 -1로 처리하여 죽은 세포로 만든다.
 *
 */

package SWTest;

import java.io.*;
import java.util.*;

public class Solution5653_줄기세포배양 {
    static final int MAX_NM = 50 + 300 + 20;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int[][] map;
    static Queue<Cell> q;
    static List<Cell> cells;
    static int N, M, K;
    static int answer;

    static class Cell {
        int r, c, activePoint, time;

        Cell(int r, int c, int activePoint, int time) {
            this.r = r;
            this.c = c;
            this.activePoint = activePoint;
            this.time = time;
        }
    }

    static void setAnswer() {
        for (int i = 0; i < MAX_NM; ++i) {
            for (int j = 0; j < MAX_NM; ++j) {
                if (map[i][j] > 0)
                    answer++;
            }
        }
    }

    static void propagation() {
        Cell cell, newCell;
        int nr, nc;

        while (!q.isEmpty()) {
            cell = q.poll();

            for (int[] dir : DIR) {
                nr = cell.r + dir[0];
                nc = cell.c + dir[1];

                if (map[nr][nc] == 0) {
                    newCell = new Cell(nr, nc, cell.activePoint, cell.activePoint + 1);

                    map[nr][nc] = newCell.activePoint;
                    cells.add(newCell);
                }
            }
        }
    }

    static void solve(int k) {
        if (k == K) {
            setAnswer();
            return;
        }

        Cell cell;

        for (int i = 0; i < cells.size(); ++i) {
            cell = cells.get(i);

            if (--cell.time == 0) {
                // 활성화 상태가 된다.
                // 번식시키기 위해 q에 넣자.
                q.offer(cell);
            }

            // 활성화 되면 X(active point) 시간동안 살아 남을 수 있다.
            if (cell.time - 1 == -cell.activePoint) {
                // 세포는 죽는다.
                map[cell.r][cell.c] = -1;
                // 제거 시키자.
                cells.remove(i--);
            }
        }

        // 번식 시작
        propagation();
        solve(k + 1);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input5653.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = new Integer(br.readLine());

        for (int tc = 1; tc <= T; ++tc) {
            st = new StringTokenizer(br.readLine());
            N = new Integer(st.nextToken());
            M = new Integer(st.nextToken());
            K = new Integer(st.nextToken());

            map = new int[MAX_NM][MAX_NM];

            int idx = (K >> 1) + 2;
            q = new LinkedList<>();
            cells = new ArrayList<>();
            int input;
            Cell cell;

            for (int i = 0; i < N; ++i) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < M; ++j) {
                    input = new Integer(st.nextToken());

                    if (input == 0)
                        continue;

                    map[i + idx][j + idx] = input;
                    cell = new Cell(i + idx, j + idx, input, input + 1);
                    cells.add(cell);
                }
            }

            answer = 0;
            solve(0);

            System.out.println("#" + tc + " " + answer);
        }

        br.close();
    }
}
