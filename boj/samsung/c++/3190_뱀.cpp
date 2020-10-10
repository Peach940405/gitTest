#include <cstdio>
#include <deque>

using namespace std;

struct Snake {
	int r, c, d;
};
const int DIR[][2] = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
const int APPLE = -1, SNAKE = 1, ROUTE = 0;
int answer, N, map[100][100], K, L, change[10001];

void solve() {
	int depth = 0;
	deque<Snake> dQ;
	Snake start = { 0, 0, 0 };
	dQ.push_back(start);
	map[0][0] = SNAKE;

	while (!dQ.empty()) {
		Snake snake = dQ.front();

		if (change[depth] != 0)
			snake.d = (snake.d + change[depth] + 4) % 4;

		int nr = snake.r + DIR[snake.d][0];
		int nc = snake.c + DIR[snake.d][1];

		if (nr < 0 || nr >= N || nc < 0 || nc >= N || map[nr][nc] == SNAKE) {
			answer = depth + 1;
			return;
		}
		Snake next = { nr, nc, snake.d };
		dQ.push_front(next);
		if (map[nr][nc] == ROUTE) {
			Snake tail = dQ.back();
			dQ.pop_back();
			map[tail.r][tail.c] = ROUTE;
		}
		map[nr][nc] = SNAKE;
		++depth;
	}
}

int main() {
	scanf("%d %d", &N, &K);
	for (int i = 0; i < K; ++i) {
		int r, c;
		scanf("%d %d", &r, &c);
		map[r - 1][c - 1] = APPLE;
	}
	scanf("%d", &L);
	for (int i = 0; i < L; ++i) {
		int x;
		char c;
		scanf("%d %c", &x, &c);
		if (c == 'L')
			change[x] = -1;
		else
			change[x] = 1;
	}
	solve();
	printf("%d", answer);
	return 0;
}