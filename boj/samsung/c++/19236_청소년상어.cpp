#include <cstdio>
#include <algorithm>

using namespace std;

struct Fish {
	int r, c, dir;
	bool isLive;
};

int DIR[9][2] = { {},{ -1, 0 },{ -1, -1 },{ 0, -1 },{ 1, -1 },{ 1, 0 },{ 1, 1 },{ 0, 1 },{ -1, 1 } };
int answer, map[4][4];
Fish fish[17];

void copy(int a[4][4], int b[4][4], Fish c[17], Fish d[17]) {
	for (int i = 0; i < 4; ++i)
		for (int j = 0; j < 4; ++j)
			a[i][j] = b[i][j];
	for (int i = 0; i < 17; ++i)
		c[i] = d[i];
}

void moveFish(int sr, int sc) {
	for (int f = 1; f <= 16; ++f) {
		Fish &cur = fish[f];
		if (!cur.isLive)
			continue;
		for (int d = 0; d < 9; ++d) {
			int nr = cur.r + DIR[cur.dir][0];
			int nc = cur.c + DIR[cur.dir][1];

			if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4 || (nr == sr && nc == sc)) {
				cur.dir = (cur.dir + 1) % 9;
				if (cur.dir == 0)
					++cur.dir;
				continue;
			}
			if (map[nr][nc]) {
				fish[map[nr][nc]].r = cur.r;
				fish[map[nr][nc]].c = cur.c;
			}
			int t = map[cur.r][cur.c];
			map[cur.r][cur.c] = map[nr][nc];
			map[nr][nc] = t;

			cur.r = nr;
			cur.c = nc;
			break;
		}
	}
}


void solve(int r, int c, int d, int res) {
	fish[map[r][c]].isLive = false; // 물고기 죽고
	map[r][c] = 0; // 사라짐

	moveFish(r, c);
	Fish tmpFish[17];
	int tmpMap[4][4];

	while (true) {
		int	nr = r + DIR[d][0];
		int nc = c + DIR[d][1];
		if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4) {
			answer = max(answer, res);
			return;
		}
		if (map[nr][nc] == 0) {
			r = nr;
			c = nc;
			continue;
		}
		copy(tmpMap, map, tmpFish, fish);
		solve(nr, nc, fish[map[nr][nc]].dir, res + map[nr][nc]);
		copy(map, tmpMap, fish, tmpFish);

		r = nr;
		c = nc;
	}
}

int main() {
	int a, b;
	for (int i = 0; i < 4; ++i) {
		for (int j = 0; j < 4; ++j) {
			scanf("%d %d", &a, &b);
			fish[a].r = i;
			fish[a].c = j;
			fish[a].dir = b;
			fish[a].isLive = true;
			map[i][j] = a;
		}
	}

	solve(0, 0, fish[map[0][0]].dir, map[0][0]);
	printf("%d", answer);
	return 0;
}