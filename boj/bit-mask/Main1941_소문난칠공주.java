/**
 * @Date
 * 2019-08-15
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/1941
 *
 * @문제
 * 백준 1941. 소문난칠공주
 *
 * 총 25명의 여학생들로 이루어진 여학생반은 5*5의 정사각형 격자 형태로 자리가 배치되었고,
 * 얼마 지나지 않아 이다솜과 임도연이라는 두 학생이 두각을 나타내며 다른 학생들을 휘어잡기 시작했다.
 * 곧 모든 여학생이 ‘이다솜파’와 ‘임도연파’의 두 파로 갈라지게 되었으며, 얼마 지나지 않아 ‘임도연파’가 세력을 확장시키며 ‘이다솜파’를 위협하기 시작했다.
 *
 * 위기의식을 느낀 ‘이다솜파’의 학생들은 과감히 현재의 체제를 포기하고, ‘소문난 칠공주’를 결성하는 것이 유일한 생존 수단임을 깨달았다.
 * ‘소문난 칠공주’는 다음과 같은 규칙을 만족해야 한다.
 *
 * 이름이 이름인 만큼, 7명의 여학생들로 구성되어야 한다.
 * 강한 결속력을 위해, 7명의 자리는 서로 가로나 세로로 반드시 인접해 있어야 한다.
 * 화합과 번영을 위해, 반드시 ‘이다솜파’의 학생들로만 구성될 필요는 없다.
 * 그러나 생존을 위해, ‘이다솜파’가 반드시 우위를 점해야 한다.
 * 따라서 7명의 학생 중 ‘이다솜파’의 학생이 적어도 4명 이상은 반드시 포함되어 있어야 한다.
 * 여학생반의 자리 배치도가 주어졌을 때, ‘소문난 칠공주’를 결성할 수 있는 모든 경우의 수를 구하는 프로그램을 작성하시오.
 *
 *
 * @입력값
 * 'S'(이다‘솜’파의 학생을 나타냄) 또는 'Y'(임도‘연’파의 학생을 나타냄)을 값으로 갖는 5*5 행렬이 공백 없이 첫째 줄부터 다섯 줄에 걸쳐 주어진다.
 *
 *
 * @풀이방법
 * 1. dfs를 이용하여 재귀를 돌지만 dfs특성상 +모양은 나올 수 가 없으므로 비트마스크를 이용한다.
 * 2. 비트마스킹을 통해서 이동한 경로를 or연산자를 이용하여 인접한 칸을 방문한다.
 *
 */

import java.io.*;

public class Main1941_소문난칠공주 {
    static final int N = 5;
    static final int[][] DIR = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    static char[][] map;
    static boolean[] visited;
    static int answer;

    static void solve(int depth, int s, int chk) {
        if (depth == 7) {
            if (s >= 4)
                ++answer;
            return;
        }

        for (int i = 0; i < N * N; ++i) {
            if ((chk & (1 << i)) == 0)
                continue;

            int r = i / N;
            int c = i % N;

            for (int[] dir : DIR) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= N)
                    continue;

                int n = nr * N + nc;
                if (visited[chk | (1 << n)])
                    continue;

                visited[chk | (1 << n)] = true;
                if (map[nr][nc] == 'S')
                    solve(depth + 1, s + 1, chk | (1 << n));
                else
                    solve(depth + 1, s, chk | (1 << n));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;

        map = new char[N][N];
        visited = new boolean[1 << (N * N)];

        for (int i = 0; i < N; ++i) {
            input = br.readLine();

            for (int j = 0; j < N; ++j) {
                map[i][j] = input.charAt(j);
            }
        }

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                visited[1 << (i * N + j)] = true;

                if (map[i][j] == 'S')
                    solve(1, 1, 1 << (i * N + j));
                else
                    solve(1, 0, 1 << (i * N + j));
            }
        }

		System.out.println(answer);
        br.close();
    }
}
