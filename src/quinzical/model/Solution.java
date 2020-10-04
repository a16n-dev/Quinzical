package quinzical.model;

import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        var output = new ArrayList<int[]>();
        
        int start = newInterval[0];
        int end = newInterval[1];
        int mergeIndex = 0;
        
        for (int i = 0; i < intervals.length; i++) {
            int[] interval = intervals[i];
            
            if (interval[1] < newInterval[0] || interval[0] > newInterval[1]) {
                output.add(interval);

                if (interval[1] < newInterval[0]) {
                    mergeIndex++;
                }
            }
            else {
                start = Math.min(start, interval[0]);
                end = Math.max(end, interval[1]);
            }
        }
        output.add(mergeIndex, new int[]{start, end});
        return output.toArray(new int[output.size()][2]);
    }

    public static void main(String[] args) {
        var test = new Solution();
        var intervals = new int[][] {{1,2},{3,5},{6,7},{8,10},{12,16}};
        
        System.out.println(Arrays.deepToString(test.insert(intervals, new int[]{4,8})));
    }
}