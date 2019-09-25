/**
 * @Date
 * 2019-09-25
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/1654
 *
 * @문제
 * 백준 1654. 랜선 자르기
 *
 * 집에서 시간을 보내던 오영식은 박성원의 부름을 받고 급히 달려왔다. 
 * 박성원이 캠프 때 쓸 N개의 랜선을 만들어야 하는데 너무 바빠서 영식이에게 도움을 청했다.
 * 이미 오영식은 자체적으로 K개의 랜선을 가지고 있다. 그러나 K개의 랜선은 길이가 제각각이다.
 *  박성원은 랜선을 모두 N개의 같은 길이의 랜선으로 만들고 싶었기 때문에 K개의 랜선을 잘라서 만들어야 한다. 
 *  예를 들어 300cm 짜리 랜선에서 140cm 짜리 랜선을 두 개 잘라내면 20cm 은 버려야 한다. (이미 자른 랜선은 붙일 수 없다.)
 * 편의를 위해 랜선을 자르거나 만들 때 손실되는 길이는 없다고 가정하며, 기존의 K개의 랜선으로 N개의 랜선을 만들 수 없는 경우는 없다고 가정하자. 
 * 그리고 자를 때는 항상 센티미터 단위로 정수길이만큼 자른다고 가정하자. N개보다 많이 만드는 것도 N개를 만드는 것에 포함된다. 
 * 이때 만들 수 있는 최대 랜선의 길이를 구하는 프로그램을 작성하시오.
 * 
 * @입력값
 * 첫째 줄에는 오영식이 이미 가지고 있는 랜선의 개수 K, 그리고 필요한 랜선의 개수 N이 입력된다.
 *  K는 1이상 10,000이하의 정수이고, N은 1이상 1,000,000이하의 정수이다. 
 *  그리고 항상 K ≦ N 이다. 그 후 K줄에 걸쳐 이미 가지고 있는 각 랜선의 길이가 센티미터 단위의 정수로 입력된다. 
 *  랜선의 길이는 231-1보다 작거나 같은 자연수이다.
 * 
 * @풀이방법
 * 1. 이분탐색을 이용한다.
 * 2. 길이 max 값을 구하여 max 부터 자르기 시작한다.
 * 3. 길이 n이 N 보다 크거나 같으면 정답이다.
 * 4. 최대의 길이를 찾을 떄까지 반복하자.
 *
 */

package algo.binarysearch;

import java.io.*;
import java.util.*;

public class Main1654_랜선자르기 {
	static int K, N;
	static int[] ia;
	static long answer;

	static long getLen(long len) {
		int sum = 0;

		for (int i = 0; i < K; ++i) {
			sum += ia[i] / len;
		}

		return sum;
	}

	static void solve(long start, long end) {
		if (start > end)
			return;

		long mid = (start + end) >> 1;
		long n = getLen(mid);

		if (n >= N) {
			answer = mid;
			solve(mid + 1, end);
		} else {
			solve(start, mid - 1);
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1654.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		K = new Integer(st.nextToken());
		N = new Integer(st.nextToken());
		ia = new int[K];
		int max = 0;

		for (int i = 0; i < K; ++i) {
			ia[i] = new Integer(br.readLine());
			max = max > ia[i] ? max : ia[i];
		}

		solve(1, max);

		System.out.println(answer);
		br.close();
	}
}
