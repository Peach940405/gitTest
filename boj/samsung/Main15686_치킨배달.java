/**
 * @Date
 * 2019-08-17
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/15686
 *
 * @문제
 * 백준 15686. 치킨 배달
 *
 * 도시에 사는 사람들은 치킨을 매우 좋아한다. 따라서, 사람들은 "치킨 거리"라는 말을 주로 사용한다.
 * 치킨 거리는 집과 가장 가까운 치킨집 사이의 거리이다. 즉, 치킨 거리는 집을 기준으로 정해지며, 각각의 집은 치킨 거리를 가지고 있다.
 * 도시의 치킨 거리는 모든 집의 치킨 거리의 합이다.
 * 임의의 두 칸 (r1, c1)과 (r2, c2) 사이의 거리는 |r1-r2| + |c1-c2|로 구한다.
 *
 * 이 도시에 있는 치킨집은 모두 같은 프랜차이즈이다.
 * 프렌차이즈 본사에서는 수익을 증가시키기 위해 일부 치킨집을 폐업시키려고 한다.
 * 오랜 연구 끝에 이 도시에서 가장 수익을 많이 낼 수 있는  치킨집의 개수는 최대 M개라는 사실을 알아내었다.
 *
 * 도시에 있는 치킨집 중에서 최대 M개를 고르고, 나머지 치킨집은 모두 폐업시켜야 한다.
 * 어떻게 고르면, 도시의 치킨 거리가 가장 작게 될지 구하는 프로그램을 작성하시오.
 *
 *
 * @입력값
 * 첫째 줄에 N(2 ≤ N ≤ 50)과 M(1 ≤ M ≤ 13)이 주어진다.
 * 둘째 줄부터 N개의 줄에는 도시의 정보가 주어진다.
 * 도시의 정보는 0, 1, 2로 이루어져 있고, 0은 빈 칸, 1은 집, 2는 치킨집을 의미한다.
 * 집의 개수는 2N개를 넘지 않으며, 적어도 1개는 존재한다.
 * 치킨집의 개수는 M보다 크거나 같고, 13보다 작거나 같다.
 *
 *
 * @풀이방법
 * 1. 조합을 이용하여 치킨 M개를 골라내어 완전탐색을 한다.
 * 2. 모든 집을 돌면서 최소의 거리를 찾아서 치킨 거리를 찾아내어 고른다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main15686_치킨배달 {
    static final int N_MAX = 50, M_MAX = 13, INF = 987654321, CHICKEN = 2, HOUSE = 1;
    static int[][] map;
    static int[] tmp;
    static int answer, N, M, houseIdx, chickenIdx;
    static Point[] house, chicken;

    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static int getAbs(int n) {
        return n > 0 ? n : -1 * n;
    }

    static int getMin(int a, int b) {
        return a < b ? a : b;
    }

    static int getChickenDistance() {
        int chickenDistanct = 0;
        int distance;

        for (int i = 0; i < houseIdx; ++i) {
            distance = INF;

            for (int j = 0; j < M; ++j) {
                distance = getMin(distance, getAbs(house[i].r - chicken[tmp[j]].r) + getAbs(house[i].c - chicken[tmp[j]].c));
            }

            chickenDistanct += distance;
        }

        return chickenDistanct;
    }

    static void solve(int depth, int start) {
        if (depth == M) {
            int distance = getChickenDistance();
            answer = answer < distance ? answer : distance;
            return;
        }

        for (int i = start; i < chickenIdx; ++i) {
            tmp[depth] = i;
            solve(depth + 1, i + 1);
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input15686.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[N_MAX][N_MAX];
        st = new StringTokenizer(br.readLine());

        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());

        house = new Point[N << 1];
        chicken = new Point[M_MAX];
        tmp = new int[M];

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; ++j) {
                int input = new Integer(st.nextToken());
                if (input == 0)
                    continue;

                map[i][j] = input;
                if (input == HOUSE)
                    house[houseIdx++] = new Point(i, j);
                else
                    chicken[chickenIdx++] = new Point(i, j);
            }
        }


        answer = INF;
        solve(0, 0);

        System.out.println(answer);
        br.close();
    }
}