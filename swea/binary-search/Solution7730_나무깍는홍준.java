/**
 * @Date
 * 2019-08-25
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWq43PI6L64DFARG
 *
 * @문제
 * swea 7730. 나무 깎는 홍준
 *
 * 홍준이는 원목으로 가구를 만드는 장인이다.
 *
 * 이번에 홍준이가 만들 가구는 대형이라 많은 양의 원목을 필요로 한다. 필요한 원목의 총 길이는 M이다.
 *
 * 원목을 얻기 위해서는 나무들을 잘라야 하는데, 나무는 N개가 있으며 길이는 제각각 이다.
 *
 * 홍준이는 자신의 거대한 팔을 휘둘러 나무들을 자르는데, 자르고 나면 너무 지치기 때문에 한 번만 휘두를 수 있다.
 *
 * 먼저 팔을 휘두를 높이 H를 정해야 하며, 나무가 잘릴려면 해당 나무의 높이가 H보다 높아야 한다.
 *
 * 예를 들어, H=18로 설정했고 각 나무의 높이가 15,20,23,18 이라고 했을 때 나무를 자르고 나면 15,18,18,18 이 된다.
 *
 * 얻은 원목의 길이는 2+5=7 이다.
 *
 * 홍준이는 모든 나무들의 높이와 필요한 원목의 길이 M이 주어졌을 때
 *
 * 원하는 원목의 길이를 얻기 위한 최대 설정 높이 H를 구하여라.
 *
 * @입력값
 * 첫 번째 줄에 테스트 케이스의 수 T가 주어진다.
 *
 * 각 테스트 케이스의 첫 번째 줄에는 나무의 수 N(1 ≤ N ≤ 106)과
 *
 * 원하는 원목의 길이 M(1 ≤ M ≤ 2×109) 이 주어진다.
 *
 * 두 번째 줄에는 각 나무의 높이가 주어진다. 높이는 109 이하이고,
 *
 * 모든 나무의 길이의 합이 M이상 임이 보장된다.
 *
 * @풀이방법
 * 1. 이분 탐색을 이용하여 풀이한다.
 * 2. 10^9 부터 탐색하여 최상의 답을 찾는다.
 *
 */

package D5;

import java.io.*;
import java.util.*;

public class Solution7730_나무깍는홍준 {
    static final int N_MAX = 1000000, M_MAX = 1000000000;
    static int[] trees;
    static int N, M, answer;

    static long getHeight(int h) {
        long sum = 0;
        for (int i = 0; i < N; i++)
            if (trees[i] > h)
                sum += trees[i] - h;

        return sum;
    }

    static void solve(int low, int hi) {
        if (low > hi)
            return;

        int mid = (low + hi) / 2;
        long h = getHeight(mid);

        if (h >= M) {
            answer = mid;
            solve(mid + 1, hi);
        } else
            solve(low, mid - 1);
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input7730.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        trees = new int[N_MAX];

        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            st = new StringTokenizer(br.readLine());

            N = new Integer(st.nextToken());
            M = new Integer(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++)
                trees[i] = new Integer(st.nextToken());

            answer = 0;
            solve(0, M_MAX);
            sb.append("#").append(test_case).append(" ").append(answer).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
