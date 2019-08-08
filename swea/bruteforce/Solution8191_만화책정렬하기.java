/**
 * @Date 2019-08-08
 * @Author 최병길
 *
 * @출처
 * https://www.swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWwtYmX6hvsDFAWU&categoryId=AWwtYmX6hvsDFAWU&categoryType=CODE
 *
 * @문제
 * 8191. 만화책 정렬하기 D5
 *
 * @설명
 * 만화책들이 종류의 순서가 엉망이 되어있다.
 * 순서대로 정렬해서 아래쪽에 꽂기 위해서 책들을 왼쪽에서 오른쪽으로 훑으며
 * 1권이 나오면 아래쪽으로 옮기고, 2권이 나오면 아래쪽으로 옮기고, ..를 원하는 책이 나올 때 마다 순서대로 반복한다.
 * 아직 모든 책이 아래쪽으로 옮겨지지 않았다면 다시 왼쪽 끝으로 와서 다음에 꽂을 책을 찾아 아래쪽 행으로 옮긴다.
 *
 * 예를 들어, 책이 {4, 3, 5, 1, 2, 6}의 순서대로 꽂혀있다면
 * 처음 훑을 때 1, 2권을 두 번재 훑을 때 3권을 세 번째 훑을 때 4, 5, 6권을 아래쪽 행에 꽂아 모든 책을 옮길 수 있다.
 *
 * @입력값
 * 하나의 정수 N (1 <= N <= 200,000)
 * N 개의 정수
 * 
 * @풀이방법
 * 1. 현재 책번호 min을 INF 값으로 두어 비교를 시작한다.
 * 2. N만큼 책 번호를 비교를 시작하는데 min 값과 비교를 시작한다.
 * 3. 현재 책 번호가 min 보다 작다면 그 책을 무조건 이동해야하므로 answer를 1증가 후 이동 시킨다.
 * 4. 현재 책 번호가 min 보다 크지만 현재 책 번호 이전의 번호가 이동 되지 않았다면 현재 책을 이동 시킨다.
 *
 */

import java.io.*;
import java.util.*;

public class Solution8191_만화책정렬하기 {
    static final int INF = 987654321;
    static boolean[] visited;
    static int N, answer;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/swea/input8191.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            N = new Integer(br.readLine());
            st = new StringTokenizer(br.readLine());

            answer = 0;
            visited = new boolean[N + 1];
            int min = INF;

            for (int i = 0; i < N; i++) {
                int bookNum = new Integer(st.nextToken());

                if (bookNum < min) {
                    answer++;
                    min = bookNum;
                } else if (!visited[bookNum - 1])
                    answer++;
                visited[bookNum] = true;

            }

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb.toString());
        br.close();
    }
}
