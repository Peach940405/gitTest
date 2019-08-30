/**
 * @Date
 * 2019-08-30
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17140
 *
 * @문제
 * 백준 17140. 이차원 배열과 연산
 *
 *  크기가 3×3인 배열 A가 있다. 1초가 지날때마다 배열에 연산이 적용된다.
 *
 *  R 연산: 배열 A의 모든 행에 대해서 정렬을 수행한다. 행의 개수 ≥ 열의 개수인 경우에 적용된다.
 *  C 연산: 배열 A의 모든 열에 대해서 정렬을 수행한다. 행의 개수 < 열의 개수인 경우에 적용된다.
 *
 * 한 행 또는 열에 있는 수를 정렬하려면, 각각의 수가 몇 번 나왔는지 알아야 한다.
 * 그 다음, 수의 등장 횟수가 커지는 순으로, 그러한 것이 여러가지면 수가 커지는 순으로 정렬한다.
 * 그 다음에는 배열 A에 정렬된 결과를 다시 넣어야 한다. 정렬된 결과를 배열에 넣을 때는, 수와 등장 횟수를 모두 넣으며, 순서는 수가 먼저이다.
 *
 * 예를 들어, [3, 1, 1]에는 3이 1번, 1가 2번 등장한다.
 * 따라서, 정렬된 결과는 [3, 1, 1, 2]가 된다. 다시 이 배열에는 3이 1번, 1이 2번, 2가 1번 등장한다. 다시 정렬하면 [2, 1, 3, 1, 1, 2]가 된다.
 *
 * 정렬된 결과를 배열에 다시 넣으면 행 또는 열의 크기가 커질 수 있다.
 * R 연산이 적용된 경우에는 행의 크기가 가장 큰 행을 기준으로 모든 행의 크기가 커지고, C 연산이 적용된 경우에는 열의 크기가 가장 큰 열을 기준으로 모든 열의 크기가 커진다. 행 또는 열의 크기가 커진 곳에는 0이 채워진다. 수를 정렬할 때 0은 무시해야 한다. 예를 들어, [3, 2, 0, 0]을 정렬한 결과는 [3, 2]를 정렬한 결과와 같다.
 *
 * 행 또는 열의 크기가 100을 넘어가는 경우에는 처음 100개를 제외한 나머지는 버린다.
 *
 * 배열 A에 들어있는 수와 r, c, k가 주어졌을 때, A[r][c]에 들어있는 값이 k가 되기 위한 최소 시간을 구해보자.
 *
 * @입력값
 * 첫째 줄에 r, c, k가 주어진다. (1 ≤ r, c, k ≤ 100)
 *
 * 둘째 줄부터 3개의 줄에 배열 A에 들어있는 수가 주어진다. 배열 A에 들어있는 수는 100보다 작거나 같은 자연수이다.
 *
 * @풀이방법
 * 1. 우선순위 큐를 이용하여 문제의 규칙대로 정렬 규칙을 세워 활용한다.
 * 2. idx가 100이 되면(max 가 100) 다시 처음으로 돌아간다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main17140_이차원배열과연산 {
    static final int MAX_RC = 100;
    static int[][] map;
    static int r, c, k, row, col;
    static int answer;

    static class Data implements Comparable<Data> {
        int num, cnt;

        Data(int num, int cnt) {
            this.num = num;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Data o) {
            if (this.cnt == o.cnt)
                return this.num - o.num;

            return this.cnt - o.cnt;
        }
    }

    static void calcC() {
        int rowCnt = 0, idx;
        int[] counts;
        Queue<Data> q = new PriorityQueue<>();

        for (int i = 0; i < col; ++i) {
            counts = new int[MAX_RC + 1];

            for (int j = 0; j < row; ++j) {
                counts[map[j][i]]++;
                map[j][i] = 0;
            }

            for (int j = 1; j < MAX_RC + 1; ++j) {
                if (counts[j] == 0)
                    continue;

                q.offer(new Data(j, counts[j]));
            }

            idx = 0;

            while (!q.isEmpty()) {
                map[idx++][i] = q.peek().num;
                map[idx++][i] = q.poll().cnt;

                if (idx == 100) {
                    rowCnt = 100;
                    q.clear();
                    break;
                }
            }

            rowCnt = rowCnt > idx ? rowCnt : idx;
        }

        row = rowCnt;
    }

    static void calcR() {
        int colCnt = 0, idx;
        int[] counts;
        Queue<Data> q = new PriorityQueue<>();

        for (int i = 0; i < row; ++i) {
            counts = new int[MAX_RC + 1];

            for (int j = 0; j < col; ++j) {
                counts[map[i][j]]++;
                map[i][j] = 0;
            }

            for (int j = 1; j < MAX_RC + 1; ++j) {
                if (counts[j] == 0)
                    continue;

                q.offer(new Data(j, counts[j]));
            }

            idx = 0;

            while (!q.isEmpty()) {
                map[i][idx++] = q.peek().num;
                map[i][idx++] = q.poll().cnt;

                if (idx == 100) {
                    colCnt = 100;
                    q.clear();
                    break;
                }
            }

            colCnt = colCnt > idx ? colCnt : idx;
        }

        col = colCnt;
    }

    static void solve(int depth) {
        if (depth > MAX_RC) {
            answer = -1;
            return;
        }

        if (map[r][c] == k) {
            answer = depth;
            return;
        }

        if (row >= col)
            calcR();
        else
            calcC();

        solve(depth + 1);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input17140.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAX_RC + 1][MAX_RC + 1];

        st = new StringTokenizer(br.readLine());
        r = new Integer(st.nextToken()) - 1;
        c = new Integer(st.nextToken()) - 1;
        k = new Integer(st.nextToken());

        for (int i = 0; i < 3; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 3; ++j) {
                map[i][j] = new Integer(st.nextToken());
            }
        }

        row = col = 3;
        solve(0);

        System.out.println(answer);
        br.close();
    }
}
