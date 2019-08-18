/**
 * @Date
 * 2019-08-18
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWIeRZV6kBUDFAVH
 *
 * @문제
 * swea 4008. [모의 SW 역량테스트] 숫자 만들기
 *
 * 선표는 게임을 통해 사칙 연산을 공부하고 있다.
 * N개의 숫자가 적혀 있는 게임 판이 있고, +, -, x, / 의 연산자 카드를 숫자 사이에 끼워 넣어 다양한 결과 값을 구해보기로 했다.
 * 수식을 계산할 때 연산자의 우선 순위는 고려하지 않고 왼쪽에서 오른쪽으로 차례대로 계산한다.
 * 예를 들어 1, 2, 3 이 적힌 게임 판에 +와 x를 넣어 1 + 2 * 3을 만들면 1 + 2를 먼저 계산하고 그 뒤에 * 를 계산한다.
 * 즉 1+2*3의 결과는 9이다.
 *
 * [3,5,3,7,9]가 적힌 숫자판과 [‘+’ 2개, ‘-‘ 1개, ‘/’ 1개]의 연산자 카드가 주어진 경우를 생각해보자.
 * 이 경우 최댓값은 3 - 5 / 3 + 7 + 9 = 16, 최솟값은 3 + 5 + 3 / 7 - 9 = -8 이다.
 * 즉 결과는 최댓값과 최솟값의 차이 ( 16 - ( -8 ) ) 로 24 가 답이 된다.
 *
 * @입력값]
 * N ( 3 <= N <= 12)
 * 연산자 갯수
 * N개의 피연산자
 *
 * @풀이방법
 * 1. 재귀를 이용하여 결과 값을 인자로 넘겨 해당 depth 마다 연산을 진행한다.
 * 2. 재귀 조건은 연산자가 있을 때 한다.
 *
 */

package SWTest;

import java.io.*;
import java.util.*;

public class Solution4008_숫자만들기 {
    static final int PLUS = 0, MINUS = 1, MULTI = 2, DIVIDE = 3, N_MAX = 12, INF = 987654321;
    static int[] operand, operator;
    static int N, max, min;

    static int getCal(int n1, int n2, int op) {
        switch (op) {
            case PLUS:
                return n1 + n2;
            case MINUS:
                return n1 - n2;
            case MULTI:
                return n1 * n2;
            case DIVIDE:
                return n1 / n2;
        }
        return 0;
    }

    static void solve(int depth, int res) {
        if (depth == N) {
            min = min < res ? min : res;
            max = max > res ? max : res;
            return;
        }

        for (int i = 0; i < 4; ++i) {
            if (operator[i] <= 0)
                continue;

            operator[i]--;
            solve(depth + 1, getCal(res, operand[depth], i));
            operator[i]++;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input4008.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();
        operand = new int[N_MAX];
        operator = new int[4];

        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; ++test_case) {
            N = new Integer(br.readLine());

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < operator.length; ++i) {
                operator[i] = new Integer(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; ++i) {
                operand[i] = new Integer(st.nextToken());
            }

            min = INF;
            max = -INF;
            solve(1, operand[0]);

            sb.append("#").append(test_case).append(" ").append(max - min).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}