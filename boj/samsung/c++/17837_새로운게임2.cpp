#include <cstdio>
#include <cstring>
#include <vector>

using namespace std;

struct Horse {
	int r, c, d;
};

const int MAX_N = 12, MAX_K = 10, BLUE = 2, RED = 1, DIR[][2] = { {0, 1}, {0, -1}, {-1, 0}, {1, 0} };
int answer, N, K, map[MAX_N + 2][MAX_N + 2];
vector<int> mapState[MAX_N + 2][MAX_N + 2];
Horse h[MAX_K + 1];

int getIdx(vector<int> tmp, int n) {
	for (int i = 0; i < tmp.size(); ++i) {
		if (tmp[i] == n) {
			return i;
		}
	}
	return -1;
}

void solve() {
	for (answer = 1; answer <= 1000; ++answer) {
		for (int i = 1; i <= K; ++i) {
			int nr = h[i].r + DIR[h[i].d][0];
			int nc = h[i].c + DIR[h[i].d][1];

			if (map[nr][nc] == BLUE) {
				h[i].d ^= 1;
				nr = h[i].r + DIR[h[i].d][0];
				nc = h[i].c + DIR[h[i].d][1];

				if (map[nr][nc] == BLUE) {
					continue;
				}
			}

			vector<int> &cur = mapState[h[i].r][h[i].c];
			vector<int> &next = mapState[nr][nc];
			int idx = getIdx(cur, i), cnt = 0;
            
			if (map[nr][nc] == RED) {
				for (int j = cur.size() - 1; j >= idx; --j) {
					int n = cur[j];
					next.push_back(n);
					h[n].r = nr;
					h[n].c = nc;
					++cnt;
				}
			}
			else {
				for (int j = idx; j < cur.size(); ++j) {
					int n = cur[j];
					next.push_back(n);
					h[n].r = nr;
					h[n].c = nc;
					++cnt;
				}
			}

			for (int j = 0; j < cnt; ++j) {
				cur.pop_back();
			}

			if (next.size() < 4) {
				continue;
			}

			return;
		}
	}
}

int main() {
	scanf("%d %d", &N, &K);

	for (int i = 0; i < N + 2; ++i) {
		map[0][i] = map[i][0] = map[i][N + 1] = map[N + 1][i] = BLUE;
	}

	for (int i = 1; i <= N; ++i) {
		for (int j = 1; j <= N; ++j) {
			scanf("%d", &map[i][j]);
		}
	}

	for (int i = 1; i <= K; ++i) {
		scanf("%d %d %d", &h[i].r, &h[i].c, &h[i].d);
		--h[i].d;
		mapState[h[i].r][h[i].c].push_back(i);
	}

	solve();
	printf("%d", answer <= 1000 ? answer : -1);

	return 0;
}