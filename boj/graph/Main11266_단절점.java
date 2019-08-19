/**
 * @Date
 * 2019-08-19
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/11266
 *
 * @참고
 * https://www.crocus.co.kr/1165
 *
 * @문제
 * 백준 11266. 단절점
 *
 * 그래프가 주어졌을 때, 단절점을 모두 구해 출력하는 프로그램을 작성하시오.
 *
 * 단절점이란 그 정점을 제거했을 때, 그래프가 두 개 또는 그 이상으로 나누어지는 정점을 말한다.
 * 즉, 제거했을 때 그래프의 connected component의 개수가 증가하는 정점을 말한다.
 *
 * @입력값
 * 첫째 줄에 두 정수 V(1≤V≤10,000), E(1≤E≤100,000)가 주어진다.
 * 이는 그래프가 V개의 정점과 E개의 간선으로 이루어져 있다는 의미이다.
 * 다음 E개의 줄에는 간선에 대한 정보를 나타내는 두 정수 A, B가 주어진다.
 * 이는 A번 정점과 B번 정점이 연결되어 있다는 의미이며, 방향은 양방향이다.
 *
 * 입력으로 주어지는 그래프는 연결 그래프가 아닐 수도 있다.
 *
 * @풀이방법
 *
 * 1. dfs를 이용한다.
 * 2. 만약 dfs에서 탐색된 정점이라면 현재 정점의 방문 순서와 탐색된 정점의 방문 순서중 min 값을 찾는다.
 */

package algo.bookmark.trytty.hard;

import java.io.*;
import java.util.*;

public class Main11266_단절점 {
    static final int V_MAX = 10000;
    static int[] discovered, cut;
    static int num;
    static List<Integer>[] vc;

    static int solve(int here, boolean isRoot) {
        discovered[here] = ++num;

        int ret = num;
        int child = 0;

        for (int p : vc[here]) {
            if (discovered[p] == 0) {
                child++;
                int tmp = solve(p, false);

                /**
                 * 정점 A가 루트가 아니라면 ::
                 * A번 정점에서 자식 노드들이 정점 A를 거치지 않고 정점 A보다 빠른 방문번호를 가진 정점으로 갈 수 없다면 단절점이다.
                 */
                if (!isRoot && tmp >= discovered[here])
                    cut[here] = 1;

                ret = Math.min(ret, tmp);
            } else {
                ret = Math.min(ret, discovered[p]);
            }
        }

        /**
         * 정점 A가 루트 라면 :: 자식 수가 2개 이상이면 단절점이다.
         * */

        if (isRoot && child >= 2)
            cut[here] = 1;

        return ret;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input11266.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        discovered = new int[V_MAX + 1];
        cut = new int[V_MAX + 1];
        vc = new ArrayList[V_MAX + 1];

        st = new StringTokenizer(br.readLine());
        int N = new Integer(st.nextToken());
        int M = new Integer(st.nextToken());

        for (int i = 1; i <= N; ++i) {
            vc[i] = new ArrayList<>();
        }

        int from, to;
        for (int i = 1; i <= M; ++i) {
            st = new StringTokenizer(br.readLine());

            from = new Integer(st.nextToken());
            to = new Integer(st.nextToken());

            vc[from].add(to);
            vc[to].add(from);
        }

        for (int i = 1; i <= N; ++i) {
            if (discovered[i] == 0)
                solve(i, true);
        }

        StringBuilder sb = new StringBuilder();
        int s = 0;
        for (int i = 1; i <= N; ++i)
            s += cut[i];

        sb.append(s).append("\n");

        for (int i = 1; i <= N; ++i) {
            if (cut[i] > 0)
                sb.append(i).append(" ");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
