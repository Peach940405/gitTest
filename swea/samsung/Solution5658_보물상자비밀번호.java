/**
 * @Date
 * 2019-09-01
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRUN9KfZ8DFAUo&categoryId=AWXRUN9KfZ8DFAUo&categoryType=CODE
 *
 * @문제
 * swea 5658. [모의 SW 역량테스트] 보물상자 비밀번호
 *
 * 각 변에 다음과 같이 16진수 숫자(0~F)가 적혀 있는 보물상자가 있다.
 * 보물 상자의 뚜껑은 시계방향으로 돌릴 수 있고, 한 번 돌릴 때마다 숫자가 시계방향으로 한 칸씩 회전한다.
 *
 *
 * 각 변에는 동일한 개수의 숫자가 있고, 시계방향 순으로 높은 자리 숫자에 해당하며 하나의 수를 나타낸다.
 * 예를 들어 [Fig.1]의 수는 1A3, B54, 8F9, D66이고, [Fig.2]의 수는 61A, 3B5, 48F, 9D6이다.
 * 보물상자에는 자물쇠가 걸려있는데, 이 자물쇠의 비밀번호는 보물 상자에 적힌 숫자로 만들 수 있는 모든 수 중,
 * K번째로 큰 수를 10진 수로 만든 수이다.
 * N개의 숫자가 입력으로 주어졌을 때, 보물상자의 비밀 번호를 출력하는 프로그램을 만들어보자.
 * (서로 다른 회전 횟수에서 동일한 수가 중복으로 생성될 수 있다. 크기 순서를 셀 때 같은 수를 중복으로 세지 않도록 주의한다.)
 *
 * @입력값
 * 8 <= N <= 28
 * K
 *
 * @풀이방법
 * 1. 비밀번호를 저장하고 처음과 같아지기 전까지 rotate를 하면서 10진수 수를 저장해둔다.
 * 2. 구해둔 10진수 수들을 정렬하여 k번째 수를 찾는다.
 *
 */

package SWTest;

import java.io.*;
import java.util.*;

public class Solution5658_보물상자비밀번호 {
    static String password;
    static int[] tmpPassword;
    static int N, K, size;
    static int answer;

    static void setAnser() {
        Arrays.sort(tmpPassword);
        answer = 0;
        int k = 0;

        for (int i = N - 1; i >= 0; --i) {
            if (tmpPassword[i] == answer) {
                continue;
            }

            answer = tmpPassword[i];
            if (++k == K)
                break;
        }
    }

    static void rotate() {
        password = new StringBuilder().append(password.charAt(password.length() - 1)).append(password.substring(0, password.length() - 1)).toString();
    }

    static void setPassword() {
        int idx = 0;

        for (int i = 0; i < size; ++i) {
            if (i != 0)
                rotate();

            for (int j = 0; j < 4; ++j) {
                // 16 진수로 변경하여 넣자
                tmpPassword[idx++] = Integer.parseInt(password.substring(size * j, size * (j + 1)), 16);
            }
        }
    }

    static void solve() {
        setPassword();
        setAnser();
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input5658.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = new Integer(br.readLine());

        for (int tc = 1; tc <= T; ++tc) {
            st = new StringTokenizer(br.readLine());

            N = new Integer(st.nextToken());
            K = new Integer(st.nextToken());
            tmpPassword = new int[N];

            password = br.readLine();
            size = password.length() / 4;

            solve();
            System.out.println("#" + tc + " " + answer);
        }

        br.close();
    }
}
