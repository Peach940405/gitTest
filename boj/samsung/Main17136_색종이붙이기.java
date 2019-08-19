/**
 * @Date
 * 2019-08-19
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/17136
 *
 * @문제
 * 백준 17136. 색종이붙이기
 *
 * 정사각형 모양을 한 다섯 종류의 색종이가 있다. 색종이의 크기는 1×1, 2×2, 3×3, 4×4, 5×5로 총 다섯 종류가 있으며, 각 종류의 색종이는 5개씩 가지고 있다.
 *
 * 색종이를 크기가 10×10인 종이 위에 붙이려고 한다.
 * 종이는 1×1 크기의 칸으로 나누어져 있으며, 각각의 칸에는 0 또는 1이 적혀 있다.
 * 1이 적힌 칸은 모두 색종이로 덮여져야 한다.
 * 색종이를 붙일 때는 종이의 경계 밖으로 나가서는 안되고, 겹쳐도 안 된다. 또, 칸의 경계와 일치하게 붙여야 한다.
 * 0이 적힌 칸에는 색종이가 있으면 안 된다.
 * 종이가 주어졌을 때, 1이 적힌 모든 칸을 붙이는데 필요한 색종이의 최소 개수를 구해보자.
 *
 * @입력값
 * 총 10개의 줄에 종이의 각 칸에 적힌 수가 주어진다.
 *
 * @풀이방법
 * 1. Point 클래스를 만들어 1인 좌표들을 미리 담아둔다.
 * 2. 좌표들을 모두 활용할 때까지 solve 함수를 재귀를 통해 이용한다.
 * 3. 현재 좌표가 사용중이라면 다음단게로 이동하고 아니라면 색종이 5x5 부터 1x1 까지 반복문을 돈다.
 * 4. chk 함수를 통해서 조건을 만족하면 다음 단계로 넘어간다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main17136_색종이붙이기 {
    static final int N = 10, INF = 987654321, PAPER_TYPE = 5, PAPER_MAX = 5;
    static int[][] map;
    static int[] paperCnt;
    static int answer, pointIdx;
    static Point[] points;

    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static void setUse(int type, Point point, boolean use) {
        int flag;

        if (use) {
            paperCnt[type]++;
            flag = 0;
        } else {
            paperCnt[type]--;
            flag = 1;
        }

        for (int r = point.r; r < point.r + type; ++r) {
            for (int c = point.c; c < point.c + type; ++c) {
                map[r][c] = flag;
            }
        }
    }

    static boolean chk(Point point, int type) {
        if (paperCnt[type] == PAPER_MAX) // 색종이를 5개 다 썼을 경우
            return false;

        for (int r = point.r; r < point.r + type; ++r) {
            for (int c = point.c; c < point.c + type; ++c) {
                if (r >= N || c >= N || map[r][c] == 0) // 범위를 초과하거나 해당 위치에 색종이를 덮을 수 없다면
                    return false;
            }
        }

        return true;
    }

    static void solve(int depth, int res) {
        if (answer < res)
            return;

        if (depth == pointIdx) {
            answer = answer < res ? answer : res;
            return;
        }

        Point curPoint = points[depth];
        if (map[curPoint.r][curPoint.c] == 0)
            solve(depth + 1, res); // 현재 위치가 사용중일 경우 다음 위치를 보자.

        for (int t = 5; t >= 1; --t) { // 크기가 5인 색종이부터 보자.
            if (!chk(curPoint, t))
                continue;

            // 색종이를 한개 쓰고 다음 단계 확인
            setUse(t, curPoint, true);
            solve(depth + 1, res + 1);
            setUse(t, curPoint, false);
        }
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input17136.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map = new int[N][N];
        points = new Point[N * N];
        paperCnt = new int[PAPER_TYPE + 1]; // 1부터 하기 위해서 1 더하자.
        int input;

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; ++j) {
                input = new Integer(st.nextToken());
				
                if (input == 0)
                    continue;

                map[i][j] = input;
                points[pointIdx++] = new Point(i, j);
            }
        }

        answer = INF;
        solve(0, 0);
        System.out.println(answer != INF ? answer : -1);

        br.close();
    }
}