#include <cstdio>
#include <string.h>

using namespace std;

struct Pos {
	int r, c;
};

const int MAX_NM = 40, MAX_K = 100, MAX_RC = 10;

int N, M, K, map[MAX_NM][MAX_NM], sticker[MAX_K][MAX_RC][MAX_RC], tmp[MAX_RC][MAX_RC];
Pos pos[MAX_K];

int answer() {
	int answer = 0;
	for (int i = 0; i < N; ++i) {
		for (int j = 0; j < M; ++j) {
			if (map[i][j]) {
				++answer;
			}
		}
	}
	return answer;
}

bool chk(int k, int r, int c) {
	for (int i = r; i < r + pos[k].r; ++i) {
		for (int j = c; j < c + pos[k].c; ++j) {
			if (sticker[k][i - r][j - c] == 0) {
				continue;
			}
			// 스티커 채워야하는데 이미 채워져 있으면
			if (map[i][j]) {
				return false;
			}
		}
	}

	return true;
}

bool chk(int k) {
	for (int i = 0; i <= N - pos[k].r; ++i) {
		for (int j = 0; j <= M - pos[k].c; ++j) {
			// 확인 시작
			if (!chk(k, i, j)) {
				continue;
			}
			// 스티커를 채우자
			for (int r = i; r < i + pos[k].r; ++r) {
				for (int c = j; c < j + pos[k].c; ++c) {
					if (sticker[k][r - i][c - j] == 0) {
						continue;
					}
					map[r][c] = sticker[k][r - i][c - j];
				}
			}
			return true;
		}
	}
	return false;
}

void rotate(int k) {
	memset(tmp, 0, sizeof(tmp));
	int r = pos[k].c;
	int c = pos[k].r;

	for (int i = 0; i < r; ++i) {
		for (int j = 0; j < c; ++j) {
			tmp[i][j] = sticker[k][c - 1 - j][i];
		}
	}

	pos[k].r = r;
	pos[k].c = c;
	for (int i = 0; i < r; ++i) {
		for (int j = 0; j < c; ++j) {
			sticker[k][i][j] = tmp[i][j];
		}
	}
}

void solve() {
	for (int k = 0; k < K; ++k) {
		// 4회전 동안 회전하면서 확인
		for (int r = 0; r < 4; ++r) {
			if (r) {
				rotate(k);
			}
			// 채울 수 없다면
			if (pos[k].r > N || pos[k].c > M) {
				continue;
			}
			// 위 그리고 왼쪽부터 가능한지 확인
			if (chk(k)) {
				break;
			}
		}
	}
}

int main() {
	scanf("%d %d %d", &N, &M, &K);
	
	for (int k = 0; k < K; ++k) {
		scanf("%d %d", &pos[k].r, &pos[k].c);

		for (int i = 0; i < pos[k].r; ++i) {
			for (int j = 0; j < pos[k].c; ++j) {
				scanf("%d", &sticker[k][i][j]);
			}
		}
	}

	solve();

	printf("%d", answer());
	return 0;
}