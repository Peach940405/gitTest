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
			// ��ƼĿ ä�����ϴµ� �̹� ä���� ������
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
			// Ȯ�� ����
			if (!chk(k, i, j)) {
				continue;
			}
			// ��ƼĿ�� ä����
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
		// 4ȸ�� ���� ȸ���ϸ鼭 Ȯ��
		for (int r = 0; r < 4; ++r) {
			if (r) {
				rotate(k);
			}
			// ä�� �� ���ٸ�
			if (pos[k].r > N || pos[k].c > M) {
				continue;
			}
			// �� �׸��� ���ʺ��� �������� Ȯ��
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