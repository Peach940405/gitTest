/**
 * @Date
 * 2019-08-30
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17281
 *
 * @문제
 * 백준 17281. 야구
 *
 *  9명으로 이루어진 두 팀이 공격과 수비를 번갈아 하는 게임이다.
 *  하나의 이닝은 공격과 수비로 이루어져 있고, 총 N이닝동안 게임을 진행해야 한다.
 *  한 이닝에 3아웃이 발생하면 이닝이 종료되고, 두 팀이 공격과 수비를 서로 바꾼다.
 *
 * 두 팀은 경기가 시작하기 전까지 타순(타자가 타석에 서는 순서)을 정해야 하고, 경기 중에는 타순을 변경할 수 없다.
 * 9번 타자까지 공을 쳤는데 3아웃이 발생하지 않은 상태면 이닝은 끝나지 않고, 1번 타자가 다시 타석에 선다.
 * 타순은 이닝이 변경되어도 순서를 유지해야 한다.
 * 예를 들어, 2이닝에 6번 타자가 마지막 타자였다면, 3이닝은 7번 타자부터 타석에 선다.
 *
 * 공격은 투수가 던진 공을 타석에 있는 타자가 치는 것이다.
 * 공격 팀의 선수가 1루, 2루, 3루를 거쳐서 홈에 도착하면 1점을 득점한다.
 * 타자가 홈에 도착하지 못하고 1루, 2루, 3루 중 하나에 머물러있을 수 있다.
 * 루에 있는 선수를 주자라고 한다. 이닝이 시작될 때는 주자는 없다.
 *
 * 타자가 공을 쳐서 얻을 수 있는 결과는 안타, 2루타, 3루타, 홈런, 아웃 중 하나이다.
 * 각각이 발생했을 때, 벌어지는 일은 다음과 같다.
 *
 *  안타: 타자와 모든 주자가 한 루씩 진루한다.
 *  2루타: 타자와 모든 주자가 두 루씩 진루한다.
 *  3루타: 타자와 모든 주자가 세 루씩 진루한다.
 *  홈런: 타자와 모든 주자가 홈까지 진루한다.
 *  아웃: 모든 주자는 진루하지 못하고, 공격 팀에 아웃이 하나 증가한다.
 *
 * 한 야구팀의 감독 아인타는 타순을 정하려고 한다.
 * 아인타 팀의 선수는 총 9명이 있고, 1번부터 9번까지 번호가 매겨져 있다.
 * 아인타는 자신이 가장 좋아하는 선수인 1번 선수를 4번 타자로 미리 결정했다.
 * 이제 다른 선수의 타순을 모두 결정해야 한다. 아인타는 각 선수가 각 이닝에서 어떤 결과를 얻는지 미리 알고 있다.
 * 가장 많은 득점을 하는 타순을 찾고, 그 때의 득점을 구해보자.
 *
 * @입력값
 * 첫째 줄에 이닝 수 N(2 ≤ N ≤ 50)이 주어진다.
 * 둘째 줄부터 N개의 줄에는 각 선수가 각 이닝에서 얻는 결과가 1번 이닝부터 N번 이닝까지 순서대로 주어진다.
 * 이닝에서 얻는 결과는 9개의 정수가 공백으로 구분되어져 있다. 각 결과가 의미하는 정수는 다음과 같다.
 *
 * 안타: 1
 * 2루타: 2
 * 3루타: 3
 * 홈런: 4
 * 아웃: 0
 *
 * @풀이방법
 * 1. 순열을 이용하여 모든 경우의 수를 탐색해야한다.
 * 2. 야구 규칙에 맞게 play함수를 구현한다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main17281_야구 {
    static final int HITTER = 9, HITS = 1, DOUBLE = 2, TRIPLE = 3, HOMERUN = 4, OUT = 0;
    static int[][] round;
    static int[] line;
    static boolean[] visited;
    static int N;
    static int answer;

    static void play() {
        int score = 0, inning = 0, outCnt = 0, idx = 0, player;
        boolean[] base = new boolean[3];

        while (inning != N) {
            player = line[idx];
            idx = (idx + 1) % 9;

            switch (round[inning][player]) {
                case OUT:
                    outCnt++;

                    if (outCnt == 3) {
                        inning++;
                        outCnt = 0;

                        for (int i = 0; i < base.length; ++i)
                            base[i] = false;
                    }
                    break;
                case HITS: // 안타
                    if (base[2]) {
                        score++;
                        base[2] = false;
                    }
                    if (base[1]) {
                        base[2] = true;
                        base[1] = false;
                    }
                    if (base[0]) {
                        base[1] = true;
                        base[0] = false;
                    }
                    base[0] = true;
                    break;
                case DOUBLE: // 2루타
                    if (base[2]) {
                        score++;
                        base[2] = false;
                    }
                    if (base[1]) {
                        score++;
                        base[1] = false;
                    }
                    if (base[0]) {
                        base[2] = true;
                        base[0] = false;
                    }
                    base[1] = true;
                    break;
                case TRIPLE: // 3루타
                    for (int b = 0; b < 3; ++b) {
                        if (base[b]) {
                            base[b] = false;
                            score++;
                        }
                    }
                    base[2] = true;
                    break;
                case HOMERUN:
                    for (int b = 0; b < 3; ++b) {
                        if (base[b]) {
                            base[b] = false;
                            score++;
                        }
                    }
                    score++;
                    break;
            }
        }
        answer = answer < score ? score : answer;
    }

    static void solve(int depth) {
        if (depth == HITTER) {
            play();
            return;
        }

        if (depth == 3) {
            line[depth] = 0;
            solve(depth + 1);
        } else {
            for (int i = 1; i < HITTER; ++i) {
                if (visited[i])
                    continue;

                visited[i] = true;
                line[depth] = i;
                solve(depth + 1);
                visited[i] = false;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input17281.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        round = new int[N][HITTER];
        line = new int[HITTER];
        visited = new boolean[HITTER];

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < HITTER; ++j)
                round[i][j] = Integer.parseInt(st.nextToken());
        }

        solve(0);

        bw.write(String.valueOf(answer));
        bw.close();
        br.close();
    }
}