/**
 * @Date
 * 2019-08-26
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/15684
 *
 * @문제
 * 백준 15684. 사다리조작
 *
 * 사다리 게임은 N개의 세로선과 M개의 가로선으로 이루어져 있다.
 * 인접한 세로선 사이에는 가로선을 놓을 수 있는데, 각각의 세로선마다 가로선을 놓을 수 있는 위치의 개수는 H이고, 모든 세로선이 같은 위치를 갖는다.
 *
 * 초록선은 세로선을 나타내고, 초록선과 점선이 교차하는 점은 가로선을 놓을 수 있는 점이다.
 * 가로선은 인접한 두 세로선을 연결해야 한다.
 * 단, 두 가로선이 연속하거나 서로 접하면 안 된다. 또, 가로선은 점선 위에 있어야 한다.
 *
 * 가로선은 위의 그림과 같이 인접한 두 세로선을 연결해야 하고, 가로선을 놓을 수 있는 위치를 연결해야 한다.
 *
 * 사다리 게임은 각각의 세로선마다 게임을 진행하고, 세로선의 가장 위에서부터 아래 방향으로 내려가야 한다.
 * 이때, 가로선을 만나면 가로선을 이용해 옆 세로선으로 이동한 다음, 이동한 세로선에서 아래 방향으로 이동해야 한다.
 *
 * 사다리에 가로선을 추가해서, 사다리 게임의 결과를 조작하려고 한다. 이때, i번 세로선의 결과가 i번이 나와야 한다.
 * 그렇게 하기 위해서 추가해야 하는 가로선 개수의 최솟값을 구하는 프로그램을 작성하시오.
 *
 * @입력값
 * 첫째 줄에 세로선의 개수 N, 가로선의 개수 M, 세로선마다 가로선을 놓을 수 있는 위치의 개수 H가 주어진다.
 * (2 ≤ N ≤ 10, 1 ≤ H ≤ 30, 0 ≤ M ≤ (N-1)×H)
 *
 * 둘째 줄부터 M개의 줄에는 가로선의 정보가 한 줄에 하나씩 주어진다.
 *
 * 가로선의 정보는 두 정수 a과 b로 나타낸다. (1 ≤ a ≤ H, 1 ≤ b ≤ N-1) b번 세로선과 b+1번 세로선을 a번 점선 위치에서 연결했다는 의미이다.
 *
 * 가장 위에 있는 점선의 번호는 1번이고, 아래로 내려갈 때마다 1이 증가한다.
 * 세로선은 가장 왼쪽에 있는 것의 번호가 1번이고, 오른쪽으로 갈 때마다 1이 증가한다.
 *
 * 입력으로 주어지는 가로선이 서로 연속하는 경우는 없다.
 *
 * @풀이방법
 * 1. 정답이 최대 3이므로 완전탐색을 돌린다.
 * 2. 사다리가 양옆으로나 현재 위치에 존재 하지 않는다면 사다리를 설치하고 재귀를 돌린다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main15684_사다리조작 {
    static final int MAX_N = 10, MAX_H = 30, LADDER = 1, INF = 987654321;
    static int[][] map;
    static int N, M, H;
    static int answer;

    static boolean chk() {
        int idx;

        for (int c = 1; c <= N; ++c) {
            idx = c;

            for (int r = 1; r <= H; ++r) {
                if (map[r][idx] == 1)
                    ++idx;
                else if (map[r][idx - 1] == 1)
                    --idx;
            }

            if (idx != c)
                return false;
        }

        return true;
    }

    static void solve(int r, int c, int res) {
        if (res >= answer)
            return;

        if (chk()) {
            answer = res;
            return;
        }

        // 정답은 최대 3
        if (res == 3)
            return;

        for (int i = r; i <= H; ++i) {
            for (int j = c; j < N; ++j) {
                if (map[i][j - 1] == LADDER || map[i][j] == LADDER || map[i][j + 1] == LADDER)
                    continue;

                map[i][j] = LADDER;
                solve(i, j, res + 1);
                map[i][j] = 0;
            }

            c = 1;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input15684.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAX_H + 1][MAX_N + 1];

        st = new StringTokenizer(br.readLine());
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());
        H = new Integer(st.nextToken());

        for (int i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            map[new Integer(st.nextToken())][new Integer(st.nextToken())] = LADDER;
        }

        answer = INF;
        solve(1, 1, 0);

        System.out.println(answer == INF ? -1 : answer);
        br.close();
    }
}
