/**
 * @Date 2020-01-04
 *
 * @Author 최병길
 *
 * @출처
 * https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWT-lPB6dHUDFAVT&categoryId=AWT-lPB6dHUDFAVT&categoryType=CODE
 *
 * @문제
 * swea 5215. 햄버거 다이어트 D3
 *
 * 평소 햄버거를 좋아하던 민기는 최근 부쩍 늘어난 살 때문에 걱정이 많다.
 * 그렇다고 햄버거를 포기할 수 없었던 민기는 햄버거의 맛은 최대한 유지하면서 정해진 칼로리를 넘지 않는 햄버거를 주문하여 먹으려고 한다.
 * 민기가 주로 이용하는 햄버거 가게에서는 고객이 원하는 조합으로 햄버거를 만들어서 준다.
 * 하지만 재료는 미리 만들어서 준비해놓기 때문에 조합에 들어가는 재료를 잘라서 조합해주지는 않고,
 * 재료를 선택하면 준비해놓은 재료를 그대로 사용하여 조합해준다.
 * 민기는 이 가게에서 자신이 먹었던 햄버거의 재료에 대한 맛을 자신의 오랜 경험을 통해 점수를 매겨놓았다.
 * 민기의 햄버거 재료에 대한 점수와 가게에서 제공하는 재료에 대한 칼로리가 주어졌을 때,
 * 민기가 좋아하는 햄버거를 먹으면서도 다이어트에 성공할 수 있도록 정해진 칼로리 이하의 조합 중에서
 * 민기가 가장 선호하는 햄버거를 조합해주는 프로그램을 만들어보자.
 *
 * (단 여러 재료를 조합하였을 햄버거의 선호도는 조합된 재료들의 맛에 대한 점수의 합으로 결정되고, 같은 재료를 여러 번 사용할 수 없으며,
 * 햄버거의 조합의 제한은 칼로리를 제외하고는 없다.)

 * @입력값
 * 재료의 수, 제한 칼로리를 나타내는 N, L(1 ≤ N ≤ 20, 1 ≤ L ≤ 10^4)
 * 재료에 대한 민기의 맛에 대한 점수와 칼로리를 나타내는 Ti, Ki(1 ≤ Ti ≤ 10^3, 1 ≤ Ki ≤ 10^3)
 * 
 * @풀이방법
 * 1. 햄버거의 정보를 처음부터 완전탐색을 한다.
 * 2. 매개변수로 주어지는 칼로리가 L보다 커지면 탐색을 종료한다.
 * 3. 끝가지 왔을 때 최대의 점수를 저장해준다.
 *
 */

#include <iostream>
#include <stdio.h>

using namespace std;

const int MAX_N = 20;

struct Hamburger {
    int score, cal;
};

Hamburger hamburger[MAX_N];
int N, L;
int answer;

void solve(int depth, int cal, int score) {
    if (cal > L) {
        return;
    }

    if (depth == N) {
        answer = answer > score ? answer : score;
        return;
    }

    solve(depth + 1, cal + hamburger[depth].cal, score + hamburger[depth].score);
    solve(depth + 1, cal, score);
}

int main() {
    int T;
    scanf("%d", &T);

    for (int tc = 1; tc <= T; ++tc) {
        scanf("%d%d", &N, &L);

        for (int i = 0; i < N; ++i) {
            scanf("%d%d", &hamburger[i].score, &hamburger[i].cal);
        }

        answer = 0;
        solve(0, 0, 0);

        printf("#%d %d\n", tc, answer);
    }

    return 0;
}