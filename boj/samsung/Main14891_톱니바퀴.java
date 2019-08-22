/**
 * @Date
 * 2019-08-22
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/14891
 *
 * @문제
 * 백준 14891. 톱니바퀴
 *
 * 총 8개의 톱니를 가지고 있는 톱니바퀴 4개가 아래 그림과 같이 일렬로 놓여져 있다.
 * 또, 톱니는 N극 또는 S극 중 하나를 나타내고 있다.
 * 톱니바퀴에는 번호가 매겨져 있는데, 가장 왼쪽 톱니바퀴가 1번, 그 오른쪽은 2번, 그 오른쪽은 3번, 가장 오른쪽 톱니바퀴는 4번이다.
 *
 * 톱니바퀴의 초기 상태와 톱니바퀴를 회전시킨 방법이 주어졌을 때, 최종 톱니바퀴의 상태를 구하는 프로그램을 작성하시오.
 *
 * @입력값
 * 첫째 줄에 1번 톱니바퀴의 상태, 둘째 줄에 2번 톱니바퀴의 상태, 셋째 줄에 3번 톱니바퀴의 상태, 넷째 줄에 4번 톱니바퀴의 상태가 주어진다.
 * 상태는 8개의 정수로 이루어져 있고, 12시방향부터 시계방향 순서대로 주어진다. N극은 0, S극은 1로 나타나있다.
 *
 * 다섯째 줄에는 회전 횟수 K(1 ≤ K ≤ 100)가 주어진다. 다음 K개 줄에는 회전시킨 방법이 순서대로 주어진다.
 * 각 방법은 두 개의 정수로 이루어져 있고, 첫 번째 정수는 회전시킨 톱니바퀴의 번호, 두 번째 정수는 방향이다.
 * 방향이 1인 경우는 시계 방향이고, -1인 경우는 반시계 방향이다.
 *
 * @풀이방법
 * 1. 재귀를 이용하여 오른쪽과 왼쪽 경우 모두 확인한다.
 * 2. 오른쪽의 경우 범위를 초과하지 않고, 회전된 톱니바퀴가 아니고, 현재 톱니바퀴의 오른쪽(2)과 오른쪽 톱니바퀴의 왼쪽(6)이 같은지를 확인한다.
 * 3. 왼쪽의 경우 범위를 초과하지 않고, 회전된 톱니바퀴가 아니고, 현재 톱니바퀴의 왼족(6)과 오른쪽 톱니바퀴의 오른쪽(2)이 같은지를 확인한다.
 * 4. 확인이 끝났다면 rotate를 한다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main14891_톱니바퀴 {
    static final int SIZE = 4, S = 1;
    static Magnet[] magnets;
    static boolean[] visited;
    static int K;

    static class Magnet {
        int[] state;

        Magnet() {
            state = new int[8];
        }
    }

    static int getAnswer() {
        int answer = 0;

        for (int i = 0; i < SIZE; ++i) {
            if (magnets[i].state[0] == S)
                answer += 1 << i;
        }

        return answer;
    }

    static void rotate(int magnetNum, int d) {
        int tmp = 0;

        if (d == 1) {
            tmp = magnets[magnetNum].state[7];

            for (int i = 6; i >= 0; --i)
                magnets[magnetNum].state[i + 1] = magnets[magnetNum].state[i];
            magnets[magnetNum].state[0] = tmp;
        } else {
            tmp = magnets[magnetNum].state[0];

            for (int i = 1; i < 8; ++i)
                magnets[magnetNum].state[i - 1] = magnets[magnetNum].state[i];
            magnets[magnetNum].state[7] = tmp;
        }
    }

    static void solve(int magnetNum, int d) {
        visited[magnetNum] = true;

        // 오른쪽 보자
        if (magnetNum < SIZE - 1 && !visited[magnetNum + 1] && magnets[magnetNum].state[2] != magnets[magnetNum + 1].state[6])
            solve(magnetNum + 1, -1 * d);

        // 왼쪽 보자
        if (magnetNum > 0 && !visited[magnetNum - 1] && magnets[magnetNum].state[6] != magnets[magnetNum - 1].state[2])
            solve(magnetNum - 1, -1 * d);

        rotate(magnetNum, d);

        visited[magnetNum] = false;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input14891.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String input;
        magnets = new Magnet[SIZE];
        visited = new boolean[SIZE];

        for (int i = 0; i < SIZE; ++i) {
            magnets[i] = new Magnet();
            input = br.readLine();

            for (int j = 0; j < 8; ++j) {
                magnets[i].state[j] = input.charAt(j) - '0';
            }
        }

        int magnetNum, d;
        K = new Integer(br.readLine());

        for (int i = 0; i < K; ++i) {
            st = new StringTokenizer(br.readLine());

            magnetNum = new Integer(st.nextToken()) - 1;
            d = new Integer(st.nextToken());

            solve(magnetNum, d);
        }

        System.out.println(getAnswer());
        br.close();
    }
}
