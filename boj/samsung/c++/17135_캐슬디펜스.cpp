#include <cstdio>
#include <queue>
#include <cstring>
#include <cstdlib>

using namespace std;
typedef pair<int, int> pii;

struct Enemy {
	int r, c, d;

	bool operator < (const Enemy& t) const {
		if (d == t.d) {
			return c > t.c;
		}
		return d > t.d;
	}
};

int answer, res, N, M, D, map[15][15], tmpMap[15][15], tmp[3];

int getDist(int r1, int c1, int r2, int c2) {
	return abs(r1 - r2) + abs(c1 - c2);
}

void mapDown() {
	for (int i = N - 1; i > 0; --i) {
		for (int j = 0; j < M; ++j) {
			tmpMap[i][j] = tmpMap[i - 1][j];
		}
	}
	for (int i = 0; i < M; ++i) {
		tmpMap[0][i] = 0;
	}
}

void play(int depth) {
	if (depth == N) {
		answer = answer > res ? answer : res;
		return;
	}
	queue<pii> q;

	for (int s = 0; s < 3; ++s) {
		priority_queue<Enemy> pQ;
		int d;

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				if (tmpMap[i][j] == 0)
					continue;
				d = getDist(i, j, N, tmp[s]);
				if (d > D)
					continue;
				pQ.push({ i, j, d });
			}
		}

		if (pQ.size() == 0) 
			continue;
		q.push({ pQ.top().r, pQ.top().c });
	}

	while (!q.empty()) {
		pii rc = q.front();
		q.pop();
		if (tmpMap[rc.first][rc.second] == 0) 
			continue;
		tmpMap[rc.first][rc.second] = 0;
		++res;
	}
	mapDown();
	play(depth + 1);
}

void arrayCopy(int a[15][15], int b[15][15]) {
	for (int i = 0; i < N; ++i) {
		for (int j = 0; j < M; ++j) {
			a[i][j] = b[i][j];
		}
	}
}

void solve(int depth, int start) {
	if (depth == 3) {
		res = 0;
		arrayCopy(tmpMap, map);
		play(0);
		return;
	}

	for (int i = start; i < M; ++i) {
		tmp[depth] = i;
		solve(depth + 1, i + 1);
	}
}

int main() {
	scanf("%d %d %d", &N, &M, &D);
	for (int i = 0; i < N; ++i) {
		for (int j = 0; j < M; ++j) {
			scanf("%d", &map[i][j]);
		}
	}

	solve(0, 0);
	printf("%d", answer);
	return 0;
}