/**
 * @Date
 * 2019-08-19
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRQm6qfL0DFAUo
 *
 * @문제
 * swea 5656. 벽돌 깨기
 *
 * 벽돌깨기
 * 구슬을 쏘아 벽돌을 깨트리는 게임을 하려고한다.
 * 구슬은 N번만 쏠 수 있고, 벽돌들의 정보는  W x H 배열로 주어진다.
 * 1. 구슬은 좌우로만 움직일 수 있어서 맨위에 있는 벽돌만 깨트릴 수 있다.
 * 2. 벽돌은 숫자 1~9로 표현되며, 구술이 명중한 벽돌은 상하좌우로 (벽돌에 적힌숫자 -1)만큼 제거된다.
 *
 * @입력값
 * 1 ≤ N ≤ 4
 * 2 ≤ W ≤ 12
 * 2 ≤ H ≤ 15
 * 다음 H 줄에 걸쳐 벽돌들의 정보가 1 줄에 W 개씩 주어진다.
 *
 *
 * @풀이방법
 * 1. solve함수를 통해서 depth가 N이 될때까지 완전탐색을 시작한다.
 * 2. 맨위가 1이상의 숫자일 경우만 터트릴 수 있으니 좌측부터 돌면서 0이아닌 숫자가 나오면 터트리자
 * 3. 폭발처리를 한후 숫자들을 밑으로 내리기 위해서 전체탐색을 하면서 숫자들을 다시 내리도록 하자.
 * 4. 다시 재귀를 통해서 다음 단계로 가게 되고 map에는 원래 배열을 저장하도록 하자.
 *
 */

package SWTest;

import java.io.*;
import java.util.*;

public class Solution5656_벽돌깨기 {
    static final int INF = 987654321, W_MAX = 12, H_MAX = 15, DOWN = 1;
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int[][] map;
    static int N, W, H;
    static int answer;

    static void setAnswer() {
        int res = 0;

        for (int i = 0; i < H; ++i) {
            for (int j = 0; j < W; ++j) {
                if (map[i][j] != 0)
                    res++;
            }
        }

        answer = answer < res ? answer : res;
    }

    static void arrayCopy(int[][] tmpMap, int[][] map) {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                tmpMap[i][j] = map[i][j];
            }
        }
    }

    static void dropWall(int r, int c) {
        int nr = r + DIR[DOWN][0];
        int nc = c + DIR[DOWN][1];

        if (nr < 0 || nr >= H || nc < 0 || nc >= W)
            return;

        if (map[nr][nc] != 0)
            return;

        map[nr][nc] = map[r][c];
        map[r][c] = 0;

        dropWall(nr, nc);
    }

    static void dropWall() {
        for (int i = H - 1; i >= 0; --i) {
            for (int j = 0; j < W; ++j) {
                if (map[i][j] > 0)
                    dropWall(i, j);
            }
        }
    }

    static void removeWall(int r, int c) {
        if (map[r][c] == 0)
            return;

        int weight = map[r][c];
        map[r][c] = 0;

        for (int w = 1; w < weight; w++) {
            for (int[] dir : DIR) {
                int nr = r + dir[0] * w;
                int nc = c + dir[1] * w;

                if (nr < 0 || nr >= H || nc < 0 || nc >= W)
                    continue;

                if (map[nr][nc] == 0)
                    continue;

                removeWall(nr, nc);
            }
        }
    }

    static void solve(int depth) {
        if (depth == N) {
            setAnswer();
            return;
        }

        int[][] tmpMap = new int[H_MAX][W_MAX];
        arrayCopy(tmpMap, map);

        // 왼쪽 부터 떨어 트리자.
        for (int i = 0; i < W; ++i) {
            int r = 0;
            int c = i;

            while (r < H && map[r][c] == 0)
                r++;

            // 벽돌이 없는 경우
            if (r == H)
                continue;

            // 벽돌 있는 경우 제거하자
            removeWall(r, c);
            // 벽돌 떨어트리기
            dropWall();
            // 다음 단계로
            solve(depth + 1);
            // map 복원
            arrayCopy(map, tmpMap);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input5656.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        map = new int[H_MAX][W_MAX];

        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            st = new StringTokenizer(br.readLine());

            N = new Integer(st.nextToken());
            W = new Integer(st.nextToken());
            H = new Integer(st.nextToken());

            for (int i = 0; i < H; ++i) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < W; ++j) {
                    map[i][j] = new Integer(st.nextToken());
                }
            }

            answer = INF;
            solve(0);

            sb.append("#").append(test_case).append(" ").append(answer != INF ? answer : 0).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
