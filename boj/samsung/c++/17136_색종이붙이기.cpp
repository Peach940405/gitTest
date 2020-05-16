#include <cstdio>
#include <string.h>

using namespace std;

struct Pos {
	int r, c;
};

const int INF = 9876654321;
int answer = INF, idx, map[10][10], paperCnt[6] = {0,};
Pos pos[10 * 10];

void set(int size, Pos pos, int flag) {
	if (flag) {
		--paperCnt[size];
	}
	else {
		++paperCnt[size];
	}
	
	for (int i = pos.r; i < pos.r + size; ++i) {
		for (int j = pos.c; j < pos.c + size; ++j) {
			map[i][j] = flag;
		}
	}
}

bool chk(int size, Pos pos) {
	if (paperCnt[size] == 5) {
		return false;
	}
	for (int i = pos.r; i < pos.r + size; ++i) {
		for (int j = pos.c; j < pos.c + size; ++j) {
			if (i >= 10 || j >= 10 || map[i][j] == 0) {
				return false;
			}
		}
	}
	return true;
}

void solve(int depth, int res) {
	if (answer < res) {
		return;
	}
	if (depth == idx) {
		answer = answer < res ? answer : res;
		return;
	}
	Pos curPos = pos[depth];
	if (map[curPos.r][curPos.c] == 0) {
		solve(depth + 1, res);
	}

	for (int size = 5; size >= 1; --size) {
		if (!chk(size, curPos)) {
			continue;
		}
		
		set(size, curPos, 0);
		solve(depth + 1, res + 1);
		set(size, curPos, 1);
	}
}

int main() {
	for (int i = 0; i < 10; ++i) {
		for (int j = 0; j < 10; ++j) {
			scanf("%d", &map[i][j]);
			if (map[i][j]) {
				pos[idx++] = { i, j };
			}
		}
	}

	solve(0, 0);

	printf("%d", answer == INF ? -1 : answer);
	return 0;
}