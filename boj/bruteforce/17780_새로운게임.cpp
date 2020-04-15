#include <cstdio>
#include <cstring>
#include <vector>

using namespace std;

struct Horse {
	int r, c, d, flag;
};

const int MAX_N = 12, MAX_K = 10, WHITE = 0, RED = 1, BLUE = 2;
const int DIR[][2] = { {},{ 0, 1 },{ 0, -1 },{ -1, 0 },{ 1, 0 } }, REV[] = { 0, 2, 1, 4, 3 };

int answer, N, K, map[MAX_N + 2][MAX_N + 2];
Horse horses[MAX_K];
vector<int> mapState[MAX_N + 2][MAX_N + 2];

bool chk() {
	for (int i = 0; i < K; ++i) {
		if (mapState[horses[i].r][horses[i].c].size() >= 4) {
			return true;
		}
	}
	return false;
}

void move(Horse horse) {
	int r = horse.r, c = horse.c, nr = r + DIR[horse.d][0], nc = c + DIR[horse.d][1], num = mapState[r][c][0];
	// 흰색일 경우 크 칸으로 이동.
	switch (map[nr][nc]) {
	case WHITE:
		// 다음칸에 말이 있으면 맨밑 끝
		if (mapState[nr][nc].size()) {
			horses[num].flag = false;
		}
		for (int i = 0; i < mapState[r][c].size(); ++i) {
			mapState[nr][nc].push_back(mapState[r][c][i]);
			horses[mapState[r][c][i]].r = nr;
			horses[mapState[r][c][i]].c = nc;
		}
		mapState[r][c].clear();
		break;
	case RED:
		// 다음칸에 말이 있으면 맨밑 끝
		if (mapState[nr][nc].size()) {
			horses[num].flag = false;
		}
		else {
			// 다음칸에 말 없으면 맨위가 바닥이 됨
			// 현재칸에 2개 이상있으면 바꿔주자
			if (mapState[r][c].size() > 1) {
				horses[mapState[r][c][mapState[r][c].size() - 1]].flag = true;
				horses[mapState[r][c][0]].flag = false;
			}
		}

		for (int i = mapState[r][c].size() - 1; i >= 0; --i) {
			mapState[nr][nc].push_back(mapState[r][c][i]);
			horses[mapState[r][c][i]].r = nr;
			horses[mapState[r][c][i]].c = nc;
		}
		mapState[r][c].clear();
		break;
	case BLUE:
		// 이동을 바꾼후
		horses[num].d = REV[horses[num].d];
		nr = horses[num].r + DIR[horses[num].d][0], nc = horses[num].c + DIR[horses[num].d][1];

		// 다음칸이 파란칸이면 이동안함
		if (map[nr][nc] == BLUE) {
			break;
		}
		move(horses[num]);
	}
}

void solve() {
	while (answer++ < 1000) {
		if (chk()) {
			break;
		}
		for (int i = 0; i < K; ++i) {
			// 가장 아래있는 말만 움직일 수 있다.
			if (!horses[i].flag) {
				continue;
			}
			move(horses[i]);
		}
	}
}

int main() {
	scanf("%d %d", &N, &K);

	for (int i = 0; i < N + 2; ++i) {
		for (int j = 0; j < N + 2; ++j) {
			map[i][j] = BLUE;
		}
	}

	for (int i = 1; i <= N; ++i) {
		for (int j = 1; j <= N; ++j) {
			scanf("%d", &map[i][j]);
		}
	}

	int r, c, d;

	for (int i = 0; i < K; ++i) {
		scanf("%d %d %d", &r, &c, &d);
		horses[i] = { r, c, d, true };
		mapState[r][c].push_back(i);
	}

	solve();
	printf("%d", answer < 1000 ? answer - 1 : -1);

	return 0;
}