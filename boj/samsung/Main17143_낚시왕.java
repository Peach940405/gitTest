/**
 * @Date
 * 2019-08-24
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17143
 *
 * @문제
 * 백준 17143. 낚시왕
 *
 * 낚시왕이 상어 낚시를 하는 곳은 크기가 R×C인 격자판으로 나타낼 수 있다.
 * 격자판의 각 칸은 (r, c)로 나타낼 수 있다. r은 행, c는 열이고, (R, C)는 아래 그림에서 가장 오른쪽 아래에 있는 칸이다.
 * 칸에는 상어가 최대 한 마리 들어있을 수 있다. 상어는 크기와 속도를 가지고 있다.
 *
 * 낚시왕은 처음에 1번 열의 한 칸 왼쪽에 있다.
 * 다음은 1초 동안 일어나는 일이며, 아래 적힌 순서대로 일어난다.
 * 낚시왕은 가장 오른쪽 열의 오른쪽 칸에 이동하면 이동을 멈춘다.
 *
 * 1) 낚시왕이 오른쪽으로 한 칸 이동한다.
 * 2) 낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어를 잡는다.
 *    상어를 잡으면 격자판에서 잡은 상어가 사라진다.
 *    상어가 이동한다.
 * 3) 상어는 입력으로 주어진 속도로 이동하고, 속도의 단위는 칸/초이다.
 *    상어가 이동하려고 하는 칸이 격자판의 경계인 경우에는 방향을 반대로 바꿔서 속력을 유지한채로 이동한다.
 *
 * 상어가 이동을 마친 후에 한 칸에 상어가 두 마리 이상 있을 수 있다.
 * 이때는 크기가 가장 큰 상어가 나머지 상어를 모두 잡아먹는다.
 *
 * 낚시왕이 상어 낚시를 하는 격자판의 상태가 주어졌을 때, 낚시왕이 잡은 상어 크기의 합을 구해보자.
 *
 * @입력값
 * 첫째 줄에 격자판의 크기 R, C와 상어의 수 M이 주어진다. (2 ≤ R, C ≤ 100, 0 ≤ M ≤ R×C)
 *
 * 둘째 줄부터 M개의 줄에 상어의 정보가 주어진다.
 * 상어의 정보는 다섯 정수 r, c, s, d, z (1 ≤ r ≤ R, 1 ≤ c ≤ C, 0 ≤ s ≤ 1000, 1 ≤ d ≤ 4, 1 ≤ z ≤ 10000) 로 이루어져 있다.
 * (r, c)는 상어의 위치, s는 속력, d는 이동 방향, z는 크기이다.
 * d가 1인 경우는 위, 2인 경우는 아래, 3인 경우는 오른쪽, 4인 경우는 왼쪽을 의미한다.
 *
 * 두 상어가 같은 크기를 갖는 경우는 없고, 하나의 칸에 둘 이상의 상어가 있는 경우는 없다.
 *
 * @풀이방법
 * 1. 상어의 정보를 입력 받을 때 상어의 스피드를 맵의 크기에 맞게 축소시킨다.
 * 2. 낚시왕이 오른쪽 끝으로 갈때까지 fishing() 과 move() 그리고 init()을 실행한다.
 * 3. fishing()에서는 낚시왕이 서있는 열만 본다.
 * 4. move()에서는 죽은 상어(-1)는 건너 뛰고 나머지를 이동시킨다.
 * 5. init()에서는 다음 상어들을 다시 원래상어로 복사해주고 초기화 시킨다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main17143_낚시왕 {
    static final int UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4, MAX_M = 10000, MAX_MAP = 100;
    static int R, C, M;
    static Shark[] sharks;
    static int[][] map, nextMap;
    static int answer;

    static class Shark {
        int r, c, s, d, z;

        Shark() {
            this(0, 0, 0, 0, 0);
        }

        Shark(int r, int c, int s, int d, int z) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.z = z;
        }
    }

    static void init() {
        for (int i = 1; i <= R; ++i) {
            for (int j = 1; j <= C; ++j) {
                if (nextMap[i][j] == 0)
                    continue;

                map[i][j] = nextMap[i][j];
                nextMap[i][j] = 0;
            }
        }
    }

    static void move() {
        for (int i = 1; i <= M; ++i) {
            if (sharks[i].z == -1) // 죽은 상어일 때
                continue;

            int speed = sharks[i].s;
            int size = sharks[i].z;

            map[sharks[i].r][sharks[i].c] = 0;

            switch (sharks[i].d) {
                case UP:
                    sharks[i].r -= speed;
                    break;
                case DOWN:
                    sharks[i].r += speed;
                    break;
                case RIGHT:
                    sharks[i].c += speed;
                    break;
                case LEFT:
                    sharks[i].c -= speed;
            }

            // 범위를 초과하는 친구들이 있다면
            while (sharks[i].r <= 0 || sharks[i].r > R || sharks[i].c <= 0 || sharks[i].c > C) {
                if (sharks[i].r <= 0) {
                    sharks[i].r = 2 - sharks[i].r;
                    sharks[i].d = DOWN;
                }

                if (sharks[i].r > R) {
                    sharks[i].r = (R << 1) - sharks[i].r;
                    sharks[i].d = UP;
                }

                if (sharks[i].c <= 0) {
                    sharks[i].c = 2 - sharks[i].c;
                    sharks[i].d = RIGHT;
                }

                if (sharks[i].c > C) {
                    sharks[i].c = (C << 1) - sharks[i].c;
                    sharks[i].d = LEFT;
                }
            }

            if (nextMap[sharks[i].r][sharks[i].c] != 0) { // 이미 들어가 있는 상어가 있다면
                if (sharks[nextMap[sharks[i].r][sharks[i].c]].z < size) {
                    sharks[nextMap[sharks[i].r][sharks[i].c]].z = -1;
                    nextMap[sharks[i].r][sharks[i].c] = i;
                } else {
                    sharks[i].z = -1;
                }
            } else {
                nextMap[sharks[i].r][sharks[i].c] = i;
            }
        }
    }

    static void fishing(int location) {
        for (int i = 1; i <= R; ++i) {
            if (map[i][location] == 0)
                continue;

            answer += sharks[map[i][location]].z;
            sharks[map[i][location]].z = -1; // 죽은 상어로 처리
            map[i][location] = 0;
            break;
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input17143.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        sharks = new Shark[MAX_M + 1];
        map = new int[MAX_MAP + 1][MAX_MAP + 1];
        nextMap = new int[MAX_MAP + 1][MAX_MAP + 1];

        for (int i = 1; i <= M; ++i) {
            st = new StringTokenizer(br.readLine());

            sharks[i] = new Shark();
            sharks[i].r = new Integer(st.nextToken());
            sharks[i].c = new Integer(st.nextToken());
            sharks[i].s = new Integer(st.nextToken());
            sharks[i].d = new Integer(st.nextToken());
            sharks[i].z = new Integer(st.nextToken());

            // 속력 축소시키기.
            if (sharks[i].d == UP || sharks[i].d == DOWN) // 위, 아래
                sharks[i].s %= (R - 1) << 1;
            else // 왼쪽, 오른쪽
                sharks[i].s %= (C - 1) << 1;

            map[sharks[i].r][sharks[i].c] = i; // i번째 상어 넣기
        }

        for (int i = 1; i <= C; ++i) { // 오른쪽 끝까지 갈 때 까지
            fishing(i);
            move();
            init();
        }

        System.out.println(answer);
        br.close();
    }
}