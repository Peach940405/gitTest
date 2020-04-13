#include <cstdio>
#include <cstring>

using namespace std;

struct Data {
	int r, c, s;
};

const int MAX_NM = 51, MAX_K = 6, INF = 987654321;

int answer, N, M, K, A[MAX_NM][MAX_NM], tmpA[MAX_NM][MAX_NM], tmp[MAX_K];
bool visited[MAX_K];
Data data[MAX_K];

int getRes() {
	int min = INF, res;
	
	for (int i = 1; i <= N; ++i) {
		res = 0;
		for (int j = 1; j <= M; ++j) {
			res += tmpA[i][j];
		}
		min = min < res ? min : res;
	}
	return min;
}

void rotate(int r1, int c1, int r2, int c2) {
	int t = tmpA[r1][c1];
	printf("%d %d %d %d\n", r1, c1, r2, c2);

	// 왼쪽
	for (int i = r1; i < r2; ++i) {
		tmpA[i][c1] = tmpA[i + 1][c1];
	}

	// 아래
	for (int i = c1; i < c2; ++i) {
		tmpA[r2][i] = tmpA[r2][i + 1];
	}

	// 오른쪽
	for (int i = r2; i > r1; --i) {
		tmpA[i][c2] = tmpA[i - 1][c2];
	}

	// 위
	for (int i = c2; i > c1; --i) {
		tmpA[r1][i] = tmpA[r1][i - 1];
	}

	tmpA[r1][c1 + 1] = t;
	for (int i = 1; i <= N; ++i) {
		for (int j = 1; j <= M; ++j) {
			printf("%d ", tmpA[i][j]);
		}
		printf("\n");
	}
	printf("\n");
}

void rotate(int k) {
	if (k == K) {
		int res = getRes();
		answer = answer < res ? answer : res;
		return;
	}

	for (int s = data[tmp[k]].s; s > 0; --s) {
		printf("bo=\n");
		rotate(data[tmp[k]].r - s, data[tmp[k]].c - s, data[tmp[k]].r + s, data[tmp[k]].c + s);
	}
	rotate(k + 1);
}

void arrayCopy(int A[][MAX_NM], int B[][MAX_NM]) {
	for (int i = 1; i <= N; ++i) {
		for (int j = 1; j <= M; ++j) {
			A[i][j] = B[i][j];
		}
	}
}

void solve(int depth) {
	if (depth == K) {
		arrayCopy(tmpA, A);
		rotate(0);
		return;
	}

	for (int i = 0; i < K; ++i) {
		if (visited[i]) {
			continue;
		}

		visited[i] = true;
		tmp[depth] = i;
		solve(depth + 1);
		visited[i] = false;
	}
}

int main() {
	scanf("%d %d %d", &N, &M, &K);

	for (int i = 1; i <= N; ++i) {
		for (int j = 1; j <= M; ++j) {
			scanf("%d", &A[i][j]);
		}
	}

	for (int i = 0; i < K; ++i) {
		scanf("%d %d %d", &data[i].r, &data[i].c, &data[i].s);
	}
	
	answer = INF;
	solve(0);
	printf("%d", answer);

	return 0;
}