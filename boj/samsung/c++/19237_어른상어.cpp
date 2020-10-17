#include <cstdio>
using namespace std;

struct Shark {
	int r, c, d;
	bool isDie;
	int priority[4][4];
};

const int DIR[][2] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
int answer, N, M, k, map[20][20][3];
Shark shark[401];

void solve() {
	int t = 0;
	int sharkCnt = M;
	while (t++ < 1000) {
		// 원래 맵 백업
		int tmpMap[20][20][3] = { 0, };
		for (int r = 0; r < N; ++r) {
			for (int c = 0; c < N; ++c) {
				tmpMap[r][c][0] = map[r][c][0];
				tmpMap[r][c][1] = map[r][c][1];
				tmpMap[r][c][2] = map[r][c][2];
				if (tmpMap[r][c][2] > 0) 
					--tmpMap[r][c][2];			
			}
		}
		// 상어 이동
		for (int s = 1; s <= M; ++s) {
			if (shark[s].isDie)
				continue;
			int cr = shark[s].r;
			int cc = shark[s].c;
			int cd = shark[s].d;
			bool isMove = false;

			for (int d = 0; d < 4; ++d) {
				int nd = shark[s].priority[cd][d];
				int nr = cr + DIR[nd][0];
				int nc = cc + DIR[nd][1];
				if (nr < 0 || nr >= N || nc < 0 || nc >= N)
					continue;
				// 냄새 있으면 못감
				if (map[nr][nc][2] != 0)
					continue;
				isMove = true;
				tmpMap[cr][cc][0] = 0;
				if (tmpMap[nr][nc][0] == 0) {
					tmpMap[nr][nc][0] = s;
					tmpMap[nr][nc][1] = s;
					tmpMap[nr][nc][2] = k;
					shark[s].r = nr;
					shark[s].c = nc;
					shark[s].d = nd;
				}
				else {
					shark[s].isDie = true;
					--sharkCnt;
				}
				break;
			}
			if (isMove)
				continue;
			for (int d = 0; d < 4; ++d) {
				int nd = shark[s].priority[cd][d];
				int nr = cr + DIR[nd][0];
				int nc = cc + DIR[nd][1];
				if (nr < 0 || nr >= N || nc < 0 || nc >= N)
					continue;
				// 자기 냄새아니면 못감
				if (map[nr][nc][1] != s)
					continue;
				tmpMap[cr][cc][0] = 0;
				if (tmpMap[nr][nc][0] == 0) {
					tmpMap[nr][nc][0] = s;
					tmpMap[nr][nc][1] = s;
					tmpMap[nr][nc][2] = k;
					shark[s].r = nr;
					shark[s].c = nc;
					shark[s].d = nd;
				}
				else {
					shark[s].isDie = true;
					--sharkCnt;
				}
				break;
			}
		}
		// 상어가 이동 후 1번 상어만 남으면 나가기
		if (sharkCnt == 1) {
			answer = t;
			break;
		}
		for (int r = 0; r < N; ++r)
			for (int c = 0; c < N; ++c)
				for (int k = 0; k < 3; ++k)
					map[r][c][k] = tmpMap[r][c][k];
	}
}

int main() {
	scanf("%d %d %d", &N, &M, &k);
	for (int r = 0; r < N; ++r) {
		for (int c = 0; c < N; ++c) {
			scanf("%d", &map[r][c][0]);
			if (map[r][c][0] == 0)
				continue;
			int n = map[r][c][0];
			map[r][c][1] = n;
			map[r][c][2] = k;
			shark[n].r = r;
			shark[n].c = c;
		}
	}
	for (int s = 1; s <= M; ++s) {
		scanf("%d", &shark[s].d);
		--shark[s].d;
	}
	for (int s = 1; s <= M; ++s) {
		for (int k = 0; k < 4; ++k) {
			for (int d = 0; d < 4; ++d) {
				scanf("%d", &shark[s].priority[k][d]);
				--shark[s].priority[k][d];
			}
		}
	}
	answer = -1;
	solve();
	printf("%d", answer);
	return 0;
}