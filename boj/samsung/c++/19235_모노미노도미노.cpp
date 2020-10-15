#include <cstdio>
using namespace std;

const int GREEN = 0, BLUE = 1;
const int DIR[][2] = { {0,  1}, {-1, 0} };
int map[2][6][4];
int score, blockCnt, N;

void moveH(int color, int r, int c, int label) {
	map[color][r][c] = map[color][r - 1][c] = 0;
	while (r < 6) {
		if (map[color][r][c] != 0)
			break;
		++r;
	}
	--r;
	map[color][r][c] = map[color][r - 1][c] = label;
}

void moveW(int color, int r, int c, int label) {
	map[color][r][c] = map[color][r][c + 1] = 0;
	while (r < 6) {
		if (map[color][r][c] != 0 || map[color][r][c + 1] != 0)
			break;
		++r;
	}
	--r;
	map[color][r][c] = map[color][r][c + 1] = label;
}

void move(int color, int r, int c, int label) {
	map[color][r][c] = 0;
	while (r < 6) {
		if (map[color][r][c] != 0)
			break;
		++r;
	}
	--r;
	map[color][r][c] = label;
}

void blockDown(int color, int startR) {
	for (int r = startR; r >= 0; --r) {
		for (int c = 0; c < 4; ++c) {
			if (map[color][r][c] == 0)
				continue;
			int type = 1; // one block
			for (int d = 0; d < 2; ++d) {
				int nr = r + DIR[d][0];
				int nc = c + DIR[d][1];
				if (nr < 0 || nc >= 4)
					continue;
				if (map[color][nr][nc] == map[color][r][c]) {
					if (d == 0)
						type = 2; // w block
					else
						type = 3; // h block
				}
			}
			if (type == 1)
				move(color, r, c, map[color][r][c]);
			else if (type == 2)
				moveW(color, r, c, map[color][r][c]);
			else if (type == 3)
				moveH(color, r, c, map[color][r][c]);
		}
	}
}

void removeLine(int color, int r) {
	for (int c = 0; c < 4; ++c)
		map[color][r][c] = 0;
}

void removeOverBlock(int color) {
	int removeCnt = 0;
	for (int r = 0; r <= 1; ++r) {
		bool flag = false;
		for (int c = 0; c < 4; ++c) {
			if (map[color][r][c] == 0)
				continue;
			flag = true;
			break;
		}
		if (flag)
			++removeCnt;
	}
	if (removeCnt > 0) {
		for (int r = 5; r >= 2; --r) 
			for (int c = 0; c < 4; ++c) 
				map[color][r][c] = map[color][r - removeCnt][c];
		for (int r = 0; r <= 1; ++r)
			for (int c = 0; c < 4; ++c)
				map[color][r][c] = 0;
	}
}

void removeScoreBlock(int color) {
	bool isRemove = false;
	for (int r = 2; r < 6; ++r) {
		int cnt = 0;
		for (int c = 0; c < 4; ++c) {
			if (map[color][r][c] != 0)
				++cnt;
		}
		// 한줄 차있을 경우
		if (cnt == 4) {
			++score;
			isRemove = true;
			removeLine(color, r);
			blockDown(color, r - 1);
		}
	}
	if (isRemove)
		removeScoreBlock(color);
}

void solve(int color, int type, int line, int label) {
	if (type == 1) {
		map[color][0][line] = label;
		move(color, 0, line, label);
	}
	else if ((type == 2 && color == GREEN) || (type == 3 && color == BLUE)) {
		map[color][0][line] = map[color][0][line + 1] = label;
		moveW(color, 0, line, label);
	}
		
	else if ((type == 2 && color == BLUE) || (type == 3 && color == GREEN)) {
		map[color][0][line] = map[color][1][line] = label;
		moveH(color, 1, line, label);
	}
	removeScoreBlock(color);
	removeOverBlock(color);
}

int main() {
	scanf("%d", &N);
	for (int i = 0; i < N; ++i) {
		int t, r, c;
		scanf("%d %d %d", &t, &r, &c);
		solve(GREEN, t, c, i + 1);
		solve(BLUE, t, r, i + 1);
	}
	for (int r = 0; r < 6; ++r) {
		for (int c = 0; c < 4; ++c) {
			for (int color = 0; color < 2; ++color) {
				if (map[color][r][c] == 0)
					continue;
				++blockCnt;
			}
		}
	}
	printf("%d\n%d", score, blockCnt);
	return 0;
}