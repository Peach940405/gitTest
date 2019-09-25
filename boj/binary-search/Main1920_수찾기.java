/**
 * @Date
 * 2019-09-25
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/1920
 *
 * @문제
 * 백준 1920. 수 찾기
 *
 * N개의 정수 A[1], A[2], …, A[N]이 주어져 있을 때, 이 안에 X라는 정수가 존재하는지 알아내라
 * 
 * @입력값
 * 첫째 줄에 자연수 N(1≤N≤100,000)이 주어진다. 다음 줄에는 N개의 정수 A[1], A[2], …, A[N]이 주어진다. 
 * 다음 줄에는 M(1≤M≤100,000)이 주어진다. 다음 줄에는 M개의 수들이 주어지는데, 이 수들이 A안에 존재하는지 알아내면 된다. 
 * 모든 정수들의 범위는 int 로 한다.
 * 
 * @풀이방법
 * 1. A배열을 구한 후 이분탐색을 하기위해서 정렬을 한다.
 * 2. key값을 비교하여 이분탐색을 시작한다.
 *
 */

package algo.binarysearch;

import java.io.*;
import java.util.*;

public class Main1920_수찾기 {
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1920.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = new Integer(br.readLine());
		int[] A = new int[N];
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < N; ++i) {
			A[i] = new Integer(st.nextToken());
		}

		Arrays.sort(A);

		StringBuilder sb = new StringBuilder();
		int answer;
		int start, end, mid;
		int key;
		int M = new Integer(br.readLine());
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < M; ++i) {
			start = 0;
			end = N - 1;
			answer = 0;
			key = new Integer(st.nextToken());

			while (start <= end) {
				mid = (start + end) >> 1;

				if (A[mid] < key) {
					start = mid + 1;
				} else if (A[mid] > key) {
					end = mid - 1;
				} else {
					answer = 1;
					break;
				}

			}

			sb.append(answer).append("\n");
		}

		System.out.println(sb.toString());
		br.close();
	}
}
