/**
 * @Date
 * 2019-08-09
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/14889
 *
 * @문제
 * 백준 14889. 스타트와 링크
 *
 * 스타트링크에 다니는 사람들이 모여서 축구를 해보려고한다. 총 N명이고 N은 짝수이다.
 * N/2명으로 이루어진 스타트 팀과 링크 팀으로 사람들을 나눠야 한다.
 * 번호를 1부터 N까지로 배정했고 능력치를 조사했다. 능력치 Sij는 i번 사람과 j번 사림이 같은 팀에 속해 있을 때 팀에 더해지는 능력이다.
 * 팀의 능력치는 모든 쌍의 능력치 Sij의 합이다. Sij와 Sji는 다를 수 있으며 i와 j가 같은 팀에 속해 있을 때, 팀에 더해지는 능력치는 Sij와 Sji이다.
 * 축구를 재미있게 하기 위해서 능력치 차이를 최소화 하려고할 때 최솟값을 구후자.
 *
 * @입력값
 * 사람 수 4 <= N <= 20
 * N * N 의 능력치
 *
 * @풀이방법
 * 1) 팀을 구별하기 위해서 재귀를 통해 팀을 구별하는 완전탐색을 하자.
 * 2) 팀이 반으로 나뉘었다면, 답을 구하기 시작한다.
 * 3) i번 사람과 j번 사람의 팀이 같다면 각 팀의 능력치를 증가시킨다.
 * 4) 팀 능력치 합산이 끝났다면 현재의 answer와 비교하여 최솟값으로 갱신시킨다.
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main14889_스타트와링크 {
    static final int INF = 987654321, N_MAX = 20;
    static int[][] map;
    static boolean[] team;
    static int answer, N;

    static int getAbs(int a, int b) {
        return a - b >= 0 ? a - b : -1 * (a - b);
    }

    static void getAnswer() {
        int sA = 0, sB = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (team[i] && team[j]) // 3)
                    sA += map[i][j];
                else if (!team[i] && !team[j]) // 3)
                    sB += map[i][j];
            }
        }

        int cmp = getAbs(sA, sB);
        answer = answer < cmp ? answer : cmp; // 4)
    }

    static void solve(int depth, int idx) {
        if (depth == N)
            return;

        if (idx == N / 2) { // 2)
            getAnswer();
            return;
        }

        team[depth] = true;
        solve(depth + 1, idx + 1);
        team[depth] = false;
        solve(depth + 1, idx);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input14889.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[N_MAX][N_MAX];
        N = new Integer(br.readLine());
        team = new boolean[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                map[i][j] = new Integer(st.nextToken());
            }
        }

        answer = INF;
        solve(0, 0); // 1)

        System.out.println(answer);
        br.close();
    }
}
