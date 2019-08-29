/**
 * @Date
 * 2019-08-29
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/16235
 *
 * @문제
 * 백준 16235. 나무재테크
 *
 * 나무 재테크란 작은 묘목을 구매해 어느정도 키운 후 팔아서 수익을 얻는 재테크이다.
 * 상도는 나무 재테크로 더 큰 돈을 벌기 위해 M개의 나무를 구매해 땅에 심었다. 같은 1×1 크기의 칸에 여러 개의 나무가 심어져 있을 수도 있다.
 *
 * 이 나무는 사계절을 보내며, 아래와 같은 과정을 반복한다.
 *
 * 봄에는 나무가 자신의 나이만큼 양분을 먹고, 나이가 1 증가한다.
 * 각각의 나무는 나무가 있는 1×1 크기의 칸에 있는 양분만 먹을 수 있다.
 * 하나의 칸에 여러 개의 나무가 있다면, 나이가 어린 나무부터 양분을 먹는다.
 * 만약, 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무는 양분을 먹지 못하고 즉시 죽는다.
 *
 * 여름에는 봄에 죽은 나무가 양분으로 변하게 된다.
 * 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다.
 * 소수점 아래는 버린다.
 *
 * 가을에는 나무가 번식한다. 번식하는 나무는 나이가 5의 배수이어야 하며, 인접한 8개의 칸에 나이가 1인 나무가 생긴다.
 * 어떤 칸 (r, c)와 인접한 칸은 (r-1, c-1), (r-1, c), (r-1, c+1), (r, c-1), (r, c+1), (r+1, c-1), (r+1, c), (r+1, c+1) 이다.
 * 상도의 땅을 벗어나는 칸에는 나무가 생기지 않는다.
 *
 * 겨울에는 S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다.
 * 각 칸에 추가되는 양분의 양은 A[r][c]이고, 입력으로 주어진다.
 *
 * K년이 지난 후 상도의 땅에 살아있는 나무의 개수를 구하는 프로그램을 작성하시오.
 *
 * @입력값
 * 첫째 줄에 N, M, K가 주어진다.
 *
 * 둘째 줄부터 N개의 줄에 A배열의 값이 주어진다. r번째 줄의 c번째 값은 A[r][c]이다.
 *
 * 다음 M개의 줄에는 상도가 심은 나무의 정보를 나타내는 세 정수 x, y, z가 주어진다.
 * 처음 두 개의 정수는 나무의 위치 (x, y)를 의미하고, 마지막 정수는 그 나무의 나이를 의미한다.
 *
 * @풀이방법
 * 1. 살아있는 나무들과 죽은 나무들을 담을 deque를 생성하자.
 * 2. 봄에는 나이와 남아있는 양분을 확인하고 죽지 않는다면 다시 넣어주자.
 * 3. 여름에는 죽은 나무들을 양분으로 바꿔준다.
 * 4. 가을에는 번식할 tmp 배열을 만들어 번식이 가능하다면 tmp 배열에 저장 후 나중에 번식시키자.
 * 5. 겨울에는 양분추가 작업을 한다.
 *
 */

package algo.bookmark.samsung;

import java.io.*;
import java.util.*;

public class Main16235_나무재테크 {
    static final int MAX_N = 10;
    static final int[][] DIR = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    static int[][] nutrient, A;
    static int N, M, K;
    static Deque<Tree> liveTrees, deathTrees;

    static class Tree {
        int r, c, age;

        Tree(int r, int c, int age) {
            this.r = r;
            this.c = c;
            this.age = age;
        }
    }

    static void winter() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                nutrient[i][j] += A[i][j];
            }
        }
    }

    static void fall() {
        int liveTreesSize = liveTrees.size();
        Deque<Tree> tmpTrees = new ArrayDeque<>();
        Tree tree;

        for (int s = 0; s < liveTreesSize; ++s) {
            // 빼낸 다음에
            tree = liveTrees.pollFirst();
            // 바로 다시 넣어주자.
            liveTrees.addLast(tree);

            // 나이가 5의 배수 이어야 번식 가능
            if (tree.age % 5 == 0)
                tmpTrees.offerLast(tree);
        }

        int nr, nc;

        while (!tmpTrees.isEmpty()) {
            tree = tmpTrees.pollLast();

            for (int[] dir : DIR) {
                nr = tree.r + dir[0];
                nc = tree.c + dir[1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= N)
                    continue;

                // last에 나이가 작은 나무들을 넣어주자.
                liveTrees.addLast(new Tree(nr, nc, 1));
            }
        }
    }

    static void summer() {
        Tree tree;

        // 죽은 나무 양분으로 됨
        while (!deathTrees.isEmpty()) {
            tree = deathTrees.pollLast();

            nutrient[tree.r][tree.c] += (tree.age >> 1);
        }
    }

    static void spring() {
        int liveTreesSize = liveTrees.size();

        for (int s = 0; s < liveTreesSize; ++s) {
            Tree tree = liveTrees.pollLast();

            // 땅에 양분이 부족해 자신의 나이만큼 양분을 먹지 못하면 즉시 죽는다.
            if (nutrient[tree.r][tree.c] < tree.age) {
                deathTrees.addLast(tree);
                continue;
            }

            // 나무가 자신의 나이만큼 양분을 먹고
            nutrient[tree.r][tree.c] -= tree.age;
            // 나이가 1 증가한다.
            tree.age++;

            liveTrees.offerFirst(tree);
        }
    }

    static void solve(int depth) {
        if (depth == K)
            return;

        spring();
        summer();
        fall();
        winter();

        solve(depth + 1);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input16235.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        A = new int[MAX_N][MAX_N];
        nutrient = new int[MAX_N][MAX_N];
        liveTrees = new ArrayDeque<>();
        deathTrees = new ArrayDeque<>();

        st = new StringTokenizer(br.readLine());
        N = new Integer(st.nextToken());
        M = new Integer(st.nextToken());
        K = new Integer(st.nextToken());

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; ++j) {
                A[i][j] = new Integer(st.nextToken());
                nutrient[i][j] = 5;
            }
        }

        int r, c, age;

        for (int i = 0; i < M; ++i) {
            st = new StringTokenizer(br.readLine());
            r = new Integer(st.nextToken()) - 1;
            c = new Integer(st.nextToken()) - 1;
            age = new Integer(st.nextToken());

            liveTrees.offerLast(new Tree(r, c, age));
        }

        solve(0);

        System.out.println(liveTrees.size());
        br.close();
    }
}
