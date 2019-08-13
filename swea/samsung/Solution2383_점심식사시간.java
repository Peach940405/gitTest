/**
 * @Date
 * 2019-08-13
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5-BEE6AK0DFAVl&categoryId=AV5-BEE6AK0DFAVl&categoryType=CODE
 *
 * @문제
 * swea 2383. [모의 sw 역량테스트] 점심 식사시간
 *
 * N*N 크기의 정사각형 모양의 방에 사람들이 앉아있다.
 * 점심을 먹기 위해서는 아래층으로 내려가야 하는데, 밥을 빨리 먹기 위해서 최대한 빠른시간 내에 내려가야한다.
 * ① 계단 입구까지 이동 시간
 *   - 사람이 현재 위치에서 계단의 입구까지 이동하는데 걸리는 시간으로 다음과 같이 계산한다.
 *   - 이동 시간(분) = | PR - SR | + | PC - SC |
 *   (PR, PC : 사람 P의 세로위치, 가로위치, SR, SC : 계단 입구 S의 세로위치, 가로위치)
 *
 * ② 계단을 내려가는 시간
 *   - 계단을 내려가는 시간은 계단 입구에 도착한 후부터 계단을 완전히 내려갈 때까지의 시간이다.
 *   - 계단 입구에 도착하면, 1분 후 아래칸으로 내려 갈 수 있다.
 *   - 계단 위에는 동시에 최대 3명까지만 올라가 있을 수 있다.
 *   - 이미 계단을 3명이 내려가고 있는 경우, 그 중 한 명이 계단을 완전히 내려갈 때까지 계단 입구에서 대기해야 한다.
 *   - 계단마다 길이 K가 주어지며, 계단에 올라간 후 완전히 내려가는데 K 분이 걸린다.
 *
 * 이때, 모든 사람들이 계단을 내려가 이동이 완료되는 시간이 최소가 되는 경우를 찾고,
 * 그 때의 소요시간을 출력하는 프로그램을 작성하라
 *
 *
 * @입력값
 * 입력의 맨 첫 줄에는 총 테스트 케이스의 개수 T가 주어지고, 그 다음 줄부터 T개의 테스트 케이스가 주어진다.
 * 각 테스트 케이스의 첫 번째 줄에는 방의 한 변의 길이 N이 주어진다. (4 <= N <= 10)
 * 다음 N줄에는 N*N 크기의 지도의 정보가 주어진다.
 * 지도에서 1은 사람을, 2 이상은 계단의 입구를 나타내며 그 값은 계단의 길이를 의미한다.
 *
 *
 * @풀이방법
 * 1. 어느 계단을 가야 최소 시간일 지 모르니까 재귀를 통해 완전탐색을 한다.
 * 2. 우선순위 큐를 이용하여 계단으로 가는 친구를 먼저하고 시간이 짧은 친구를 먼저하자.
 * 3. 1분을 증가시키면서 계단의 인원이 3보다 작으면 down상태로 바꾸어 이동시키자.
 * 4. 그렇지 않다면 wait상태로 바꾸어 계단 이용자 수가 3보다 작아질 때까지 기다리자.
 *
 */

import java.io.*;
import java.util.*;

public class Solution2383_점심식사시간 {
    static final int PERSON_MAX = 10, STAIR_MAX = 2, INF = 987654321, MOVE = -1, WAIT = 0, DOWN = 1;
    static Point[] person, stair;
    static int[] chk;
    static int answer, N, personIdx, stairIdx;

    static class Point {
        int r, c, s; // 위치
        // s는 사람의 경우 계단과의 거리, 계단의 경우 계단의 길이

        Point(int r, int c, int s) {
            this.r = r;
            this.c = c;
            this.s = s;
        }

        @Override
        public String toString() {
            return r + " " + c + " " + s;
        }
    }

    static class Data implements Comparable<Data> {
        int time, stair, status;
        // 시간, 이용준인 계단, 상태
        // 상태: -1 = 계단으로 감, 0 = 3명 이용중이라 대기, 1 = 내려가는 중

        Data(int time, int stair, int status) {
            this.time = time;
            this.stair = stair;
            this.status = status;
        }

        @Override
        public int compareTo(Data o) {
            // 시간이 작고 계단으로 가는 친구 먼저하자.
            if (this.time == o.time)
                return o.status - this.status;

            return this.time - o.time;
        }
    }


    static int getMoveTime(int pr, int pc, int sr, int sc) {
        return Math.abs(pr - sr) + Math.abs(pc - sc);
    }

    static int getRes() {
        // 2)
        Queue<Data> pQ = new PriorityQueue<>();

        for (int i = 0; i < personIdx; ++i) {
            int moveTime = getMoveTime(person[i].r, person[i].c, stair[chk[i]].r, stair[chk[i]].c);
            pQ.offer(new Data(moveTime, chk[i], MOVE));
        }

        int depth = 0;
        int[] stairCnt = new int[STAIR_MAX]; // 해당 계단에 몇명의 사람이 있는지 확인

        while (!pQ.isEmpty()) {
            depth++; // 3)

            while (!pQ.isEmpty()) {
                if (pQ.peek().time != depth)
                    break;

                Data data = pQ.poll();
                if (data.status != DOWN) {
                    // 3)
                    if (stairCnt[data.stair] < 3) {
                        data.time += stair[data.stair].s; // 계단의 길이 더해주기.

                        if (data.status == MOVE) // 도착해서 대기하는 1분 더해주자.
                            data.time++;
                        data.status = DOWN; // 내려가는 상태로 바꾸자.

                        pQ.offer(data);
                        stairCnt[data.stair]++; // 계단 수 증가.
                    } else { // 계단으로 가는 친구도 일단 대기상태로 바꿔노코 시간 증가시키자.
                        // 4)
                        data.time++;
                        data.status = WAIT;
                        pQ.offer(data);
                    }
                } else {
                    stairCnt[data.stair]--;
                }
            }
        }

        return depth;
    }

    static void solve(int depth) {
        if (depth == personIdx) {
            int res = getRes();
            answer = answer < res ? answer : res;
            return;
        }

        // 1)
        chk[depth] = 1;
        solve(depth + 1);
        chk[depth] = 0;
        solve(depth + 1);
    }

    static void init() {
        stairIdx = personIdx = 0;
        answer = INF;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/swea/input2383.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb;

        person = new Point[PERSON_MAX];
        stair = new Point[STAIR_MAX];
        sb = new StringBuilder();

        int T = new Integer(br.readLine());
        for (int test_case = 1; test_case <= T; ++test_case) {
            init();
            N = new Integer(br.readLine());

            for (int i = 0; i < N; ++i) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < N; ++j) {
                    int data = new Integer(st.nextToken());

                    if (data == 0)
                        continue;

                    if (data == 1) {
                        person[personIdx++] = new Point(i, j, 0);
                    } else {
                        stair[stairIdx++] = new Point(i, j, data);
                    }
                }
            }

            chk = new int[personIdx];
            solve(0);

            sb.append("#").append(test_case).append(" ").append(answer).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}