/**
 * @Date
 * 2020-01-04
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/2589
 *
 * @문제
 * 백준 2589. 보물섬
 *
 * 보물섬 지도를 발견한 후크 선장은 보물을 찾아나섰다.
 * 보물섬 지도는 아래 그림과 같이 직사각형 모양이며 여러 칸으로 나뉘어져 있다.
 * 각 칸은 육지(L)나 바다(W)로 표시되어 있다.
 * 이 지도에서 이동은 상하좌우로 이웃한 육지로만 가능하며, 한 칸 이동하는데 한 시간이 걸린다.
 * 보물은 서로 간에 최단 거리로 이동하는데 있어 가장 긴 시간이 걸리는 육지 두 곳에 나뉘어 묻혀있다.
 * 육지를 나타내는 두 곳 사이를 최단 거리로 이동하려면 같은 곳을 두 번 이상 지나가거나, 멀리 돌아가서는 안 된다.
 *
 *
 * @입력값
 * 세로의 길이 <= 50, 가로의 길이 <= 50
 * L과 W로 표시된 보물지도
 *
 *
 * @풀이방법
 * 1. 50*50의 크기이므로 완전탐색을 이용한다.
 * 2. 지도의 값이 L이라면 탐색을 시작한다.
 * 3. 해당 위치의 좌표부터 범위안에 들고, 방문하지 않았고, 다음 지도의 값이 L인 값을 만나면 q에 넣어준다.
 * 4. q가 비어질때까지 탐색 후 최댁 값을 저장
 *
 */

#include <iostream>
#include <stdio.h>
#include <queue>
#include <cstring>

using namespace std;

const int MAX_NM = 50;
const int DIR[4][2] = {{0,  1}, {1,  0}, {0,  -1}, {-1, 0}};

struct Point {
    int r, c;
};

int map[MAX_NM][MAX_NM];
bool visited[MAX_NM][MAX_NM];
int N, M;
int answer;

void search(int r, int c) {
    memset(visited, false, sizeof(visited));

    queue<Point> q;
    int res = -1;
    int nr, nc;
    int qSize;

    q.push({r, c});
    visited[r][c] = true;

    while (!q.empty()) {
        ++res;
        qSize = q.size();

        for (int s = 0; s < qSize; ++s) {
            r = q.front().r;
            c = q.front().c;
            q.pop();

            for (int d = 0; d < 4; ++d) {
                nr = r + DIR[d][0];
                nc = c + DIR[d][1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                    continue;
                }

                if (visited[nr][nc]) {
                    continue;
                }

                if (map[nr][nc] == 0) {
                    continue;
                }

                visited[nr][nc] = true;
                q.push({nr, nc});
            }
        }
    }

    answer = answer > res ? answer : res;
}

void solve() {
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            if (map[i][j] == 1) {
                search(i, j);
            }
        }
    }
}

int main() {
    scanf("%d%d", &N, &M);
    char input[MAX_NM];

    for (int i = 0; i < N; ++i) {
        scanf("%s", input);

        for (int j = 0; j < M; ++j) {
            if (input[j] == 'L') {
                map[i][j] = 1;
            }
        }
    }

    solve();
    printf("%d", answer);

    return 0;
}