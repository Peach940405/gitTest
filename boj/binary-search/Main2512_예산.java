/**
 * @Date
 * 2019-08-15
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/2512
 *
 * @문제
 * 백준 2512. 통나무 옮기기
 *
 * 국가의 역할 중 하나는 여러 지방의 예산요청을 심사하여 국가의 예산을 분배하는 것이다.
 * 국가예산의 총액은 미리 정해져 있어서 모든 예산요청을 배정해 주기는 어려울 수도 있다.
 * 그래서 정해진 총액 이하에서 가능한 한 최대의 총 예산을 다음과 같은 방법으로 배정한다.
 *
 * 모든 요청이 배정될 수 있는 경우에는 요청한 금액을 그대로 배정한다.
 * 모든 요청이 배정될 수 없는 경우에는 특정한 정수 상한액을 계산하여 그 이상인 예산요청에는 모두 상한액을 배정한다.
 * 상한액 이하의 예산요청에 대해서는 요청한 금액을 그대로 배정한다.
 * 예를 들어, 전체 국가예산이 485이고 4개 지방의 예산요청이 각각 120, 110, 140, 150이라고 하자.
 * 이 경우, 상한액을 127로 잡으면, 위의 요청들에 대해서 각각 120, 110, 127, 127을 배정하고 그 합이 484로 가능한 최대가 된다.
 *
 * @입력값
 * 첫째 줄에는 지방의 수를 의미하는 정수 N이 주어진다. N은 3 이상 10,000 이하이다.
 * 다음 줄에는 각 지방의 예산요청을 표현하는 N개의 정수가 빈칸을 사이에 두고 주어진다.
 * 이 값들은 모두 1 이상 100,000 이하이다. 그 다음 줄에는 총 예산을 나타내는 정수 M이 주어진다.
 * M은 N 이상 1,000,000,000 이하이다.
 *
 * @풀이방법
 * 1. M이 1,000,000,000 이하 이므로 이분탐색을 이용하여 접근해야한다.
 * 2. 예산들을 배열에 저장해 놓고 최대 예산 값을 정해서 그 값을 기준으로 이분탐색을 시작하자.
 * 3. 예산을 계산해 가면서 계산된 예산값을 M과 비교해가면서 계속탐색한다.
 *
 */

package algo.binarysearch;

import java.io.*;
import java.util.*;

public class Main2512_예산 {
    static final int N_MAX = 10000;
    static int[] budget;
    static int N, M;
    static long answer;

    static long getRes(int tmp) {
        long res = 0;
        for (int i = 0; i < N; i++) {
            res += budget[i] < tmp ? budget[i] : tmp;
        }

        return res;
    }

    static void solve(int start, int end) {
        if (start > end)
            return;

        int mid = (start + end) >> 1;
        long res = getRes(mid);

        // 3)
        if (res <= M) {
            answer = mid;
            solve(mid + 1, end);
        } else {
            solve(start, mid - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input2512.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        budget = new int[N_MAX];
        N = new Integer(br.readLine());
        st = new StringTokenizer(br.readLine());

        int maxBudget = 0;
        // 2)
        for (int i = 0; i < N; i++) {
            budget[i] = new Integer(st.nextToken());
            maxBudget = maxBudget > budget[i] ? maxBudget : budget[i];
        }

        M = new Integer(br.readLine());
        solve(0, maxBudget); // 1)

        System.out.println(answer);
        br.close();
    }
}
