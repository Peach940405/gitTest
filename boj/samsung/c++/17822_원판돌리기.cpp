#include <cstdio>
using namespace std;

const int DIR[][2] = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
const int DEL = -1;
int N, M, T;
int map[50][50];

void solve(int x, int d, int k) {
	// 회전시키기
	int rotate = x - 1;
	if (d == 1) {
		k = -k;
	}
	while (rotate < N) {
		int tmp[50] = { 0, };
		for (int i = 0; i < M; ++i) {
			tmp[(i + k + M) % M] = map[rotate][i];
		}
		for (int i = 0; i < M; ++i) {
			map[rotate][i] = tmp[i];
		}
		rotate += x;
	}
	bool isRemove = false;
	bool visited[50][50] = { false, };

	for (int r = 0; r < N; ++r) {
		for (int c = 0; c < M; ++c) {
			for (int d = 0; d < 4; ++d) {
				int nr = r + DIR[d][0];
				int nc = (c + DIR[d][1] + M) % M;
				if (nr < 0 || nr >= N)
					continue;
				if (map[r][c] == DEL || map[nr][nc] == DEL || map[r][c] != map[nr][nc])
					continue;
				isRemove = true;
				visited[r][c] = true;
				visited[nr][nc] = true;
			}
		}
	}
	// 제거된게 있는 경우
	if (isRemove) {
		for (int r = 0; r < N; ++r) {
			for (int c = 0; c < M; ++c) {
				if (!visited[r][c])
					continue;
				map[r][c] = DEL;
			}
		}
	}
	// 없는경우
	else {
		int sum = 0, cnt = 0;
		for (int r = 0; r < N; ++r) {
			for (int c = 0; c < M; ++c) {
				if (map[r][c] == DEL)
					continue;
				sum += map[r][c];
				++cnt;
			}
		}
		for (int r = 0; r < N; ++r) {
			for (int c = 0; c < M; ++c) {
				if (map[r][c] == DEL)
					continue;
				if (map[r][c] * cnt > sum)
					--map[r][c];
				else if (map[r][c] * cnt < sum)
					++map[r][c];
			}
		}
	}
}

int main() {
	scanf("%d %d %d", &N, &M, &T);
	for (int r = 0; r < N; ++r) {
		for (int c = 0; c < M; ++c) {
			scanf("%d", &map[r][c]);
		}
	}
	for (int i = 0; i < T; ++i) {
		int x, d, k;
		scanf("%d %d %d", &x, &d, &k);
		solve(x, d, k);
	}
	int answer = 0;
	for (int r = 0; r < N; ++r) {
		for (int c = 0; c < M; ++c) {
			if (map[r][c] == DEL)
				continue;
			answer += map[r][c];
		}
	}
	printf("%d\n", answer);
	return 0;
}