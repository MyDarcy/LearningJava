package com.darcy.main.cleancode_v1_0_3.ArrayString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Author by darcy
 * Date on 17-9-1 上午11:22.
 * Description:
 *
 * 给定字符串找到最长无重复子串的字符串.
 *
 * Given a string, find the length of the longest substring without repeating characters. For
 * example, the longest substring without repeating letters for “abcabcbb” is “abc”, which
 * the length is 3. For “bbbbb” the longest substring is “b”, with the length of 1.
 *
 */
public class P10_LongestSubStringWithRepeatedChar {

  /**
   * 用一个table存储字符的出现情况(这里假定是ASCII码,
   * 所以可以使用大小256的数组,如果是可以出现Unicode,
   * 那么使用set记录字符的出现情况). 这里char直接可以
   * 作为数组的index,所以可以直接设置该index
   * 所表示的char, (char)index 是否出现过。
   *
   * 遍历字符串,如果当前字符出现过, 那么要逐跳的更新table.两次出现相同字符的中间位置处都要设置为未出现，
   * 因为下一次的起始位置其实是当前字符的位置.
   *
   * O(n)的时间复杂度,O(1)的空间复杂度.
   *
   * How can we look up if a character exists in a substring instantaneously? The answer is to
   * use a simple table to store the characters that have appeared. Make sure you communicate
   * with your interviewer if the string can have characters other than ‘a’–‘z’. (ie, Digits?
   * Upper case letter? Does it contain ASCII characters only? Or even unicode character
   * sets?)
   *
   * The next question is to ask yourself what happens when you found a repeated character?
   * For example, if the string is “abcdcedf”, what happens when you reach the second
   * appearance of ‘c’?
   *
   * When you have found a repeated character (let’s say at index j), it means that the current
   * substring (excluding the repeated character of course) is a potential maximum, so update
   * the maximum if necessary. It also means that the repeated character must have appeared\
   * before at an index i, where i is less than j.
   *
   * Since you know that all substrings that start before or at index i would be less than your
   * current maximum, you can safely start to look for the next substring with head which
   * starts exactly at index i + 1.
   *
   * Therefore, you would need two indices to record the head and the tail of the current
   * substring. Since i and j both traverse at most n steps, the worst case would be 2n steps,
   * which the runtime complexity must be O(n).
   * Note that the space complexity is constant O(1), even though we are allocating an array.
   * This is because no matter how long the string is, the size of the array stays the same at
   * 256.
   *
   * @param str
   * @return
   */
  public static int solution(String str) {
    // 存储某个字符的出现情况
    boolean[] exists = new boolean[256];
    int n = str.length();
    int start = 0;
    int maxLength = 0;
    for (int i = 0; i < str.length(); i++) {
      // 出现了重复, 那么前指针就需要移动到当前位置，所经历路径上的字符现在都没有出现过了.
      while (exists[str.charAt(i)]) {
        exists[str.charAt(start)] = false;
        // 退出循环的时候 start == i;
        start++;
      }

      // 每遇到新出现的字符,都可能产生最长距离.
      // 想象一下连续遇到新出现的字符, start都没更新,还是指向第一次新字符处.
      exists[str.charAt(i)] = true;
      maxLength = Math.max(maxLength, i - start + 1);
    }

    return maxLength;
  }

  /**
   *
   * abcddbcam推一下就出来了.
   * 注意charIndexMap映射关系是开始都要初始化为-1;
   *
   * 时间复杂度O(n),空间复杂度O(1)
   * @param str
   * @return
   */
  public static int solution2(String str) {
    int[] charIndexMap = new int[256];
    // 初始化，默认全是0的话会出现问题.
    Arrays.fill(charIndexMap, -1);
    int i = 0; // 第一个未重复出现的字符的index. >=i 就可以判定出现了重复字符.
    int maxLength = 0;
    for (int j = 0; j < str.length(); j++) {
      if (charIndexMap[str.charAt(j)] >= i) { // str.chatAt(j)出现的字符上一次已经出现过.
        // str.charAt(j)处的字符之前已经出现过了,所以起始位置要更新到上一次该字符的下一个位置处.
        i = charIndexMap[str.charAt(j)] + 1;
      }
      charIndexMap[str.charAt(j)] = j;
      maxLength = Math.max(maxLength, j - i + 1);
    }

    return maxLength;
  }

  public static void main(String[] args) {
    String str = "abcabac";
    System.out.println(solution(str));

    System.out.println("******");

    //最长子串出现从第一个字符开始就会出现问题.
    str = "abcd";
    System.out.println(solution(str));
    System.out.println(solution2(str));

    str = "abcdabcd";
    System.out.println(solution(str));
    System.out.println(solution2(str));
  }

}
