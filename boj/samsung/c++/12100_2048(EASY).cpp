#include <cstdio>
#include <queue>

using namespace std;

const int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
int answer, N, map[20][20];

void setAns() {
	for (int i = 0; i < N; ++i) 
		for (int j = 0; j < N; ++j) 
			answer = answer > map[i][j] ? answer : map[i][j];
}

void arrCopy(int a[20][20], int b[20][20]) {
	for (int i = 0; i < 20; ++i)
		for (int j = 0; j < 20; ++j)
			a[i][j] = b[i][j];
}

void move(int d) {
	queue<int> q;
	switch (d) {
	case LEFT:
		for (int r = 0; r < N; ++r) {
			for (int c = 0; c < N; ++c) {
				if (map[r][c] == 0)
					continue;
				q.push(map[r][c]);
				map[r][c] = 0;
			}
			int idx = 0;
			while (!q.empty()) {
				int cur = q.front();
				q.pop();
				if (map[r][idx] == 0)
					map[r][idx] = cur;
				else if (map[r][idx] == cur)
					map[r][idx++] <<= 1;
				else
					map[r][++idx] = cur;
			}
		}
		break;
	case RIGHT:
		for (int r = 0; r < N; ++r) {
			for (int c = N - 1; c >= 0; --c) {
				if (map[r][c] == 0)
					continue;
				q.push(map[r][c]);
				map[r][c] = 0;
			}
			int idx = N - 1;
			while (!q.empty()) {
				int cur = q.front();
				q.pop();
				if (map[r][idx] == 0)
					map[r][idx] = cur;
				else if (map[r][idx] == cur)
					map[r][idx--] <<= 1;
				else
					map[r][--idx] = cur;
			}
		}
		break;
	case UP:
		for (int c = 0; c < N; ++c) {
			for (int r = 0; r < N; ++r) {
				if (map[r][c] == 0)
					continue;
				q.push(map[r][c]);
				map[r][c] = 0;
			}
			int idx = 0;
			while (!q.empty()) {
				int cur = q.front();
				q.pop();
				if (map[idx][c] == 0)
					map[idx][c] = cur;
				else if (map[idx][c] == cur)
					map[idx++][c] <<= 1;
				else
					map[++idx][c] = cur;
			}
		}
		break;
	case DOWN:
		for (int c = 0; c < N; ++c) {
			for (int r = N - 1; r >= 0; --r) {
				if (map[r][c] == 0)
					continue;
				q.push(map[r][c]);
				map[r][c] = 0;
			}
			int idx = N - 1;
			while (!q.empty()) {
				int cur = q.front();
				q.pop();
				if (map[idx][c] == 0)
					map[idx][c] = cur;
				else if (map[idx][c] == cur)
					map[idx--][c] <<= 1;
				else
					map[--idx][c] = cur;
			}
		}
	}
}

void solve(int depth) {
	if (depth == 5) {
		setAns();
		return;
	}
	int tmp[20][20];
	arrCopy(tmp, map);
	for (int i = 0; i < 4; ++i) {
		move(i);	// 모든방향 탐색
		solve(depth + 1);
		arrCopy(map, tmp); // 원상태 복원
	}
}

int main() {
	scanf("%d", &N);
	for (int i = 0; i < N; ++i) {
		for (int j = 0; j < N; ++j) {
			scanf("%d", &map[i][j]);
		}
	}
	solve(0);
	printf("%d", answer);
	return 0;
}