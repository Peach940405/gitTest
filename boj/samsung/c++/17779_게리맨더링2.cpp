#include <cstdio>
#include <algorithm>
using namespace std;

int N, sum;
int map[21][21];

int solve(int x, int y, int d1, int d2) {
	int tmp[21][21] = { 0, };
	tmp[x][y] = 5;
	// (x, y), (x + 1, y - 1), ..., (x + d1, y - d1)
	// (x + d2, y + d2), (x + d2 + 1, y + d2 - 1), ..., (x + d2 + d1, y + d2 - d1)
	for (int i = 1; i <= d1; ++i) {
		tmp[x + i][y - i] = 5;
		tmp[x + d2 + i][y + d2 - i] = 5;
	}
	// (x, y), (x + 1, y + 1), ..., (x + d2, y + d2)
	// (x + d1, y - d1), (x + d1 + 1, y - d1 + 1), ... (x + d1 + d2, y - d1 + d2)
	for (int i = 1; i <= d2; ++i) {
		tmp[x + i][y + i] = 5;
		tmp[x + d1 + i][y - d1 + i] = 5;
	}
	int cnt[6] = { 0, };
	// 1번선거구
	for (int r = 1; r < x + d1; ++r) {
		for (int c = 1; c <= y; ++c) {
			if (tmp[r][c] == 5)
				break;
			cnt[1] += map[r][c];
		}
	}
	// 2번선거구
	for (int r = 1; r <= x + d2; ++r) {
		for (int c = N; c > y; --c) {
			if (tmp[r][c] == 5)
				break;
			cnt[2] += map[r][c];
		}
	}
	// 3번선거구
	for (int r = x + d1; r <= N; ++r) {
		for (int c = 1; c < y - d1 + d2; ++c) {
			if (tmp[r][c] == 5)
				break;
			cnt[3] += map[r][c];
		}
	}
	// 4번선거구
	for (int r = x + d2 + 1; r <= N; ++r) {
		for (int c = N; c >= y - d1 + d2; --c) {
			if (tmp[r][c] == 5)
				break;
			cnt[4] += map[r][c];
		}
	}
	cnt[5] = sum;
	for (int i = 1; i <= 4; ++i)
		cnt[5] -= cnt[i];
	int maxVal = max(cnt[1], max(cnt[2], max(cnt[3], max(cnt[4], cnt[5]))));
	int minVal = min(cnt[1], min(cnt[2], min(cnt[3], min(cnt[4], cnt[5]))));
	
	return maxVal - minVal;
}

int main() {
	scanf("%d", &N);
	for (int x = 1; x <= N; ++x) {
		for (int y = 1; y <= N; ++y) {
			scanf("%d", &map[x][y]);
			sum += map[x][y];
		}
	}
	int answer = 0x7fffffff;
	for (int x = 1; x <= N; ++x) {
		for (int y = 1; y <= N; ++y) {
			for (int d1 = 1; d1 <= N; ++d1) {
				for (int d2 = 1; d2 <= N; ++d2) {
					if (x + d1 + d2 > N)	continue;
					if (1 > y - d1)			continue;
					if (y + d2 > N)			continue;
					answer = min(answer, solve(x, y, d1, d2));
				}
			}
		}
	}
	printf("%d", answer);
	return 0;
}