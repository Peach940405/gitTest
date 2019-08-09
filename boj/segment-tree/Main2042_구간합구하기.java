/**
 * @Date
 * 2019-08-09
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/2042
 *
 * @문제
 * 백준 2042. 구간 합 구하기
 *
 * 어떤 N개의 수가 주어져 있다. 중간에 수의 변경이 빈번히 일어나고 그 중간에 어떤 부분의 합을 구하려고한다.
 * 1, 2, 3, 4, 5라는 수가 있고, 3 번째 수를 6으로 바꾼 후 2번째 부터 5번재 합을 구하려고 한다면 17을 출력하면된다.
 * 구간합을 구하라.
 *
 *
 * @입력값
 * N개의 데이터 (1 <= N <= 1,000,000)
 * 변경이 일어나는 횟수 M (1 <= M <= 10,000)
 * 구간합을 구하는 횟수 K (1 <= K <= 10,000)
 *
 * @풀이방법
 * 일반 적인 방법으로 구현할 수 없다. 세그먼트 트리를 이용하여 구간합을 저장해두고 활용하자.
 *
 */

import java.io.*;
import java.util.*;

public class Main2042_구간합구하기 {
    static final int UPDATE = 1, SUM = 2;
    static int N, M, K;
    static long[] segTree;
    static int[] data;

    static long sum(int node, int start, int end, int left, int right) {
        if (start > right || end < left)
            return 0;

        if (start >= left && end <= right)
            return segTree[node];

        int mid = (start + end) / 2;
        return sum(node * 2, start, mid, left, right) + sum(node * 2 + 1, mid + 1, end, left, right);

    }

    static void update(int node, int start, int end, int idx, int diff) {
        if (start > idx || end < idx) // 범위에 포함되지 않는다면
            return;

        segTree[node] += diff;

        if (start != end) {
            int mid = (start + end) / 2;
            update(node * 2, start, mid, idx, diff);
            update(node * 2 + 1, mid + 1, end, idx, diff);
        }
    }

    static long initSegTree(int node, int start, int end) {
        if (start == end) {
            return segTree[node] = data[start];
        }

        int mid = (start + end) / 2;
        return segTree[node] = initSegTree(node * 2, start, mid) + initSegTree(node * 2 + 1, mid + 1, end);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());
        K = new Integer(st.nextToken());

        segTree = new long[N << 2];
        data = new int[N];

        for (int i = 0; i < N; ++i)
            data[i] = new Integer(br.readLine());

        initSegTree(1, 0, N - 1);

        M += K;
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int a = new Integer(st.nextToken());
            int b = new Integer(st.nextToken());
            int c = new Integer(st.nextToken());

            switch (a) {
                case UPDATE:
                    update(1, 0, N - 1, b - 1, c - data[b - 1]);
                    data[b - 1] = c;
                    break;
                case SUM:
                    System.out.println(sum(1, 0, N - 1, b - 1, c - 1));
            }
        }
        br.close();
    }
}
