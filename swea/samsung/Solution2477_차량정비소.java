/**
 * @Date
 * 2019-08-09
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV6c6bgaIuoDFAXy&categoryId=AV6c6bgaIuoDFAXy&categoryType=CODE
 *
 * @문제
 * 2477. [모의 SW 역량테스트] 차량 정비소
 *
 * 고객이 차량 정비소에 지갑을 두고 가서 찾아주려하지만, 방문 고객이 너무 많아 전화하여 확인하기가 불가능하다.
 * 다행히 고객이 작성한 고객 만족도 설문지가 있으며, 여기에는 이용했던 접수 창구와 정비 창구 번호가 적혀있다.
 * 차량 정비소에는 N개의 접수 창구와 M개의 정비 창구가 있다.
 * 접수 창구는 1 부터 N까지 번호가 붙어있다. 정비 창구도 1부터 M까지 번호가 붙어있다.
 * 접수 창구 i에서 고객 한명의 고장을 접수하는 데 걸리는 시간은 ai이다.
 * 정비 창구 i에서 고객 한명의 차량을 정비하는 데 걸리는 시간은 bi이다.
 * 지금까지 방문한 고객은 K명이다.
 * 고객은 도착하는데로 1부터 번호를 부여받으며 도착시간이 순서대로 주어진다.
 * 고장 접수를 완료하면 정비 창구로 이동한다.
 * 빈 정비 창구가 비어 있다면  해당 창구에서 이용가능하지만, 빈 정비 창구가 없는 경우 빈 창구가 생길 때까지 대기한다.
 * 접수 창구의 우선순위는 고객 번호가 낮은 고객이 먼저 접수, 빈 창구번호가 낮은 곳
 * 정비 창구의 우선순위는 먼저 기다리는 고객, 이용했던 접수 창구 번호가 낮은 고객, 빈 정비 창구 번호가 낮은 곳
 * 고객의 이동시간은 0이며 지갑을 분실한 같은 접수창고와 정비창구를 이용한 고객들의 합을 구하라.
 *
 *
 * @입력값
 * 접수 창구의 개수 N, 정비 창구의 개수 M (1 <= N, M <= 9)
 * 차량 정비소를 방문한 고객의 수 K (1 <= K <= 1000)
 * 지갑을 두고 간 고객이 이용한 접수 창구 A, 정비 창구 B (1 <= A <= N, 1 <= B <= M)
 *
 * @풀이방법
 * 1) 접수 창구에 도착한 고객들이 있다면 접수창구 대기 큐에 넣어주자.
 * 2) 접수 창구를 대기 중인 고객들이 있고 비어있는 접수 창구가 있다면 고객을 해당 창구에 이동시키자. A창구를 방문한 고객이라면 확인해두자.
 * 3) 정비 창구를 대기 중이 고객들이 있고 비어있는 정비 창구가 있다면 고객을 해당 창구에 이동시키자. A창구를 방문했으며 B창구를 방문한 고객이라면 답을 더해주자.
 * 4) 1초를 경과시킨다.
 * 5) 1초 후 접수 창구 이용이 끝난 고객이 있다면 해당 고객을 정비창구 대기큐로 이동시킨다.
 * 6) 1초 후 정비 창구 이용이 끝난 고객이 있다면 depth를 1증가시켜주자.
 *
 */
import java.io.*;
import java.util.*;

public class Solution2477_차량정비소 {
    static final int COUNTER_MAX = 9, PERSON_MAX = 1000, RECEPTION = 0, REPAIR = 1;
    static int[] reception, repair, t;
    static boolean[] visited;
    static Counter[] receptionCounter, repairCounter;
    static int answer, N, M, K, A, B;

    static class Counter {
        int uNum, time;
        boolean flag;

        Counter(int uNum, int time, boolean flag) {
            this.uNum = uNum;
            this.time = time;
            this.flag = flag;
        }
    }

    static void solve() {
        Queue<Integer> receptionQ = new LinkedList<>(), repairQ = new LinkedList<>();
        int depth = 0;
        int idx = 1;

        while (depth != K) {
            // 접수 창구 대기 큐에 넣자. // 1)
            for (int i = idx; i <= K; ++i) {
                if (t[i] == 0) {
                    t[i]--;
                    receptionQ.offer(i); // i번 사람을 receptionQ에 넣어주자.
                } else if (t[i] > 0) {
                    idx = i; // 다음 번에 여기 부터 보면 된다.
                    break;
                }
            }

            // 접수 창구 이용시키자. // 2)
            for (int i = 1; i <= N; i++) {
                // 접수 창구를 대기 중인 사람이 없다면
                if (receptionQ.isEmpty())
                    break;

                // 이미 사용중인 접수 창구라면 넘어가자.
                if (receptionCounter[i].flag)
                    continue;

                // 대기 중인 사람을 접수창구에 넣자.
                int uNum = receptionQ.poll();
                if (i == A)
                    visited[uNum] = true;
                receptionCounter[i].uNum = uNum;
                receptionCounter[i].flag = true;
            }

            // 정비 창구 이용 시키자. // 3)
            for (int i = 1; i <= M; i++) {
                if (repairQ.isEmpty())
                    break;

                // 이미 사용중인 정비 창구라면 넘어가자.
                if (repairCounter[i].flag)
                    continue;

                // 대기 중인 사람을 정비 창구에 넣자.
                int uNum = repairQ.poll();
                if (i == B && visited[uNum])
                    answer += uNum;
                repairCounter[i].uNum = uNum;
                repairCounter[i].flag = true;
            }

            // 1초 경과 시키기 // 4)
            for (int i = 1; i <= N; i++) {
                if (!receptionCounter[i].flag)
                    continue;

                // 이용시간이 끝났다면 // 5)
                if (--receptionCounter[i].time == 0) {
                    // 원래 이용 가능 시간으로 복구시키자.
                    receptionCounter[i].time = reception[i];
                    receptionCounter[i].flag = false;
                    // 정비 창구를 이용할 수 있도록 하자.
                    repairQ.offer(receptionCounter[i].uNum);
                }
            }

            for (int i = 1; i <= M; i++) {
                if (!repairCounter[i].flag)
                    continue;

                // 6)
                if (--repairCounter[i].time == 0) {
                    repairCounter[i].time = repair[i];
                    repairCounter[i].flag = false;
                    // 이용이 끝난 인원 만큼 depth를 증가시키자.
                    depth++;
                }
            }

            for (int i = idx; i <= K; i++)
                t[i]--;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        reception = new int[COUNTER_MAX + 1];
        repair = new int[COUNTER_MAX + 1];
        t = new int[PERSON_MAX + 1];
        receptionCounter = new Counter[COUNTER_MAX + 1];
        repairCounter = new Counter[COUNTER_MAX + 1];
        visited = new boolean[PERSON_MAX + 1];

        int T = new Integer(br.readLine());
        for (int tc = 1; tc <= T; ++tc) {
            st = new StringTokenizer(br.readLine());
            N = new Integer(st.nextToken());
            M = new Integer(st.nextToken());
            K = new Integer(st.nextToken());
            A = new Integer(st.nextToken());
            B = new Integer(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; ++i) {
                reception[i] = new Integer(st.nextToken());
                receptionCounter[i] = new Counter(0, reception[i], false);
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= M; ++i) {
                repair[i] = new Integer(st.nextToken());
                repairCounter[i] = new Counter(0, repair[i], false);
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= K; ++i) {
                t[i] = new Integer(st.nextToken());
                visited[i] = false; // 초기화 하기
            }

            answer = 0;
            solve();
            answer = answer != 0 ? answer : -1;

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
