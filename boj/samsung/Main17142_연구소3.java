/**
 * @Date
 * 2019-08-28
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17142
 *
 * @문제
 * 백준 17142. 연구소3
 *
 * 인체에 치명적인 바이러스를 연구하던 연구소에 승원이가 침입했고, 바이러스를 유출하려고 한다.
 * 바이러스는 활성 상태와 비활성 상태가 있다.
 * 가장 처음에 모든 바이러스는 비활성 상태이고, 활성 상태인 바이러스는 상하좌우로 인접한 모든 빈 칸으로 동시에 복제되며, 1초가 걸린다.
 * 승원이는 연구소의 바이러스 M개를 활성 상태로 변경하려고 한다.
 *
 * 연구소는 크기가 N×N인 정사각형으로 나타낼 수 있으며, 정사각형은 1×1 크기의 정사각형으로 나누어져 있다.
 * 연구소는 빈 칸, 벽, 바이러스로 이루어져 있으며, 벽은 칸 하나를 가득 차지한다.
 * 활성 바이러스가 비활성 바이러스가 있는 칸으로 가면 비활성 바이러스가 활성으로 변한다.
 *
 * 예를 들어, 아래와 같이 연구소가 생긴 경우를 살펴보자.
 * 0은 빈 칸, 1은 벽, 2는 바이러스의 위치이다.
 *
 * 연구소의 상태가 주어졌을 때, 모든 빈 칸에 바이러스를 퍼뜨리는 최소 시간을 구해보자.
 *
 * @입력값
 * 첫째 줄에 연구소의 크기 N(4 ≤ N ≤ 50), 놓을 수 있는 바이러스의 개수 M(1 ≤ M ≤ 10)이 주어진다.
 *
 * 둘째 줄부터 N개의 줄에 연구소의 상태가 주어진다. 0은 빈 칸, 1은 벽, 2는 바이러스를 놓을 수 있는 위치이다.
 * 2의 개수는 M보다 크거나 같고, 10보다 작거나 같은 자연수이다.
 *
 * @풀이방법
 * 1. 조합을 이용하여 M개의 경우를 골라야한다.
 * 2. dist 배열을 -1로 초기화 하고 map이 길(0)이거나 바이러스(2)이면 갈 수 있도록 하자.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main17142_연구소3 {
    static final int MAX_N = 50, MAX_M = 10, INF = 987654321;
    static final int ROUTE = 0, WALL = 1, VIRUS = 2;
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static int[][] map;
    static Virus[] virus;
    static int[] tmp;
    static int N, M, virusCnt, routeCnt;
    static int answer;

    static class Virus {
        int r, c;

        Virus(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static void spread() {
        int[][] dist = new int[N][N];
        Queue<Virus> q = new LinkedList<>();

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                dist[i][j] = -1;
            }
        }

        for (int i = 0; i < M; ++i) {
            q.offer(virus[tmp[i]]);
            dist[virus[tmp[i]].r][virus[tmp[i]].c] = 0;
        }

        int infect = 0, res = 0;
        int nr, nc;

        while (!q.isEmpty()) {
            if (res >= answer)
                return;

            Virus virus = q.poll();

            for (int[] dir : DIR) {
                nr = virus.r + dir[0];
                nc = virus.c + dir[1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= N)
                    continue;

                // 갈 곳이 벽이거나 길이 아니면 갈 수 없다.
                if (map[nr][nc] == WALL || dist[nr][nc] != -1)
                    continue;

                dist[nr][nc] = dist[virus.r][virus.c] + 1;

                if (map[nr][nc] == ROUTE) {
                    infect++;
                    res = dist[nr][nc];
                }

                q.offer(new Virus(nr, nc));
            }
        }

        if (infect == routeCnt && answer > res)
            answer = res;
    }

    static void solve(int depth, int start) {
        if (depth == M) {
            spread();
            return;
        }

        for (int i = start; i < virusCnt; ++i) {
            tmp[depth] = i;
            solve(depth + 1, i + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input17142.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[MAX_N][MAX_N];
        virus = new Virus[MAX_M];
        tmp = new int[MAX_M];

        st = new StringTokenizer(br.readLine());
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; ++j) {
                map[i][j] = new Integer(st.nextToken());

                if (map[i][j] == ROUTE)
                    routeCnt++;
                else if (map[i][j] == VIRUS)
                    virus[virusCnt++] = new Virus(i, j);
            }
        }

        answer = INF;
        solve(0, 0);

        System.out.println(answer == INF ? -1 : answer);
        br.close();
    }
}
