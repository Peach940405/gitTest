package algo.tree;
/**
 * @Date
 * 2019-08-17
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/2517
 *
 * @문제
 * 백준 2517. 달리기
 *
 * KOI 장거리 달리기 대회가 진행되어 모든 선수가 반환점을 넘었다.
 * 각 선수의 입장에서 자기보다 앞에 달리고 있는 선수들 중 평소 실력이 자기보다 좋은 선수를 남은 거리 동안 앞지르는 것은 불가능하다.
 * 반대로, 평소 실력이 자기보다 좋지 않은 선수가 앞에 달리고 있으면 남은 거리 동안 앞지르는 것이 가능하다.
 * 이러한 가정 하에서 각 선수는 자신이 앞으로 얻을 수 있는 최선의 등수를 알 수 있다.
 *
 * 각 선수의 평소 실력은 정수로 주어지는데 더 큰 값이 더 좋은 실력을 의미한다.
 * 현재 달리고 있는 선수를 앞에서 부터 표시했을 때 평소 실력이 각각 2, 8, 10, 7, 1, 9, 4, 15라고 하면
 * 각 선수가 얻을 수 있는 최선의 등수는 (같은 순서로) 각각 1, 1, 1, 3, 5, 2, 5, 1이 된다.
 * 예를 들어, 4번째로 달리고 있는 평소 실력이 7인 선수는 그 앞에서 달리고 있는 선수들 중 평소 실력이 2인 선수만 앞지르는 것이 가능하고
 * 평소실력이 8과 10인 선수들은 앞지르는 것이 불가능하므로, 최선의 등수는 3등이 된다.
 *
 * 선수들의 평소 실력을 현재 달리고 있는 순서대로 입력 받아서 각 선수의 최선의 등수를 계산하는 프로그램을 작성하시오.
 *
 *
 * @입력값
 * 첫째 줄에는 선수의 수를 의미하는 정수 N이 주어진다. N은 3 이상 500,000 이하이다.
 * 이후 N개의 줄에는 정수가 한 줄에 하나씩 주어진다.
 * 이 값들은 각 선수들의 평소 실력을 앞에서 달리고 있는 선수부터 제시한 것이다.
 * 각 정수는 1 이상 1,000,000,000 이하이다.
 * 단, 참가한 선수들의 평소 실력은 모두 다르다.
 *
 *
 * @풀이방법
 * 1. 실력이 최대 1,000,000,000 이므로 좌표 압축을 통해서 값을 줄여야한다.
 * 2. merge sort를 활용하여 값을 구한다.
 *
 */

import java.io.*;

public class Main2517_달리기 {
    static Runner[] a, b;
    static int[] ans;
    static int N;

    static class Runner {
        int ab, rank;

        Runner(int ab, int rank) {
            this.ab = ab;
            this.rank = rank;
        }
    }

    static void merge(int start, int mid, int end) {
        int left = start, right = mid + 1, idx = start;

        while (left <= mid && right <= end) {
            if (a[left].ab < a[right].ab) {
                ans[a[right].rank] -= mid - left + 1;
                b[idx++] = a[right++];
            } else {
                b[idx++] = a[left++];
            }
        }

        while (left <= mid)
            b[idx++] = a[left++];

        while (right <= end)
            b[idx++] = a[right++];

        for (left = start; left <= end; ++left)
            a[left] = b[left];
    }

    static void mergeSort(int start, int end) {
        if (start == end)
            return;

        int mid = (start + end) / 2;

        mergeSort(start, mid);
        mergeSort(mid + 1, end);
        merge(start, mid, end);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input2517.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        N = new Integer(br.readLine());
        a = new Runner[N + 1];
        b = new Runner[N + 1];
        ans = new int[N + 1];

        // i로 좌표 압축하기
        for (int i = 1; i <= N; ++i) {
            a[i] = new Runner(new Integer(br.readLine()), i);
            ans[i] = i;
        }

        mergeSort(1, N);

        for (int i = 1; i <= N; ++i)
            sb.append(ans[i]).append("\n");

        System.out.println(sb.toString());
        br.close();
    }
}
