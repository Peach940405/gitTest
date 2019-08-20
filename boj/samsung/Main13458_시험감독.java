/**
 * @Date
 * 2019-08-20
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/13458
 *
 * @문제
 * 백준 13458. 시험감독
 *
 * 총 N개의 시험장이 있고, 각각의 시험장마다 응시자들이 있다.
 * i번 시험장에 있는 응시자의 수는 Ai명이다.
 *
 * 감독관은 총감독관과 부감독관으로 두 종류가 있다.
 * 총감독관은 한 방에서 감시할 수 있는 응시자의 수가 B명이고, 부감독관은 한 방에서 감시할 수 있는 응시자의 수가 C명이다.
 *
 * 각각의 시험장에 총감독관은 오직 1명만 있어야 하고, 부감독관은 여러 명 있어도 된다.
 *
 * 각 시험장마다 응시생들을 모두 감시해야 한다.
 * 이때, 필요한 감독관 수의 최솟값을 구하는 프로그램을 작성하시오.
 *
 * @입력값
 * 첫째 줄에 시험장의 개수 N(1 ≤ N ≤ 1,000,000)이 주어진다.
 *
 * 둘째 줄에는 각 시험장에 있는 응시자의 수 Ai (1 ≤ Ai ≤ 1,000,000)가 주어진다.
 *
 * 셋째 줄에는 B와 C가 주어진다. (1 ≤ B, C ≤ 1,000,000)
 *
 * @풀이방법
 * 1. 부감독 수를 나눈 수를 구한다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main13458_시험감독 {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input13458.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = new Integer(br.readLine());
        int[] room = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; ++i) {
            room[i] = new Integer(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int mainSupervisor = new Integer(st.nextToken());
        int subSupervisor = new Integer(st.nextToken());
        long answer = 0;

        for (int i = 0; i < N; ++i) {
            answer++;
            room[i] -= mainSupervisor;

            if (room[i] > 0)
                answer += Math.ceil(1.0 * room[i] / subSupervisor);
        }

        System.out.println(answer);
        br.close();
    }
}
