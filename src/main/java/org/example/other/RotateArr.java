package org.example.other;

/**
 * 有序数组arr可能经过一次旋转处理，也可能没有，且arr可能存在重复的数
 * 例如有序数组[1,2,3,4,5,6,7]，可以旋转处理成[4,5,6,7,1,2,3]
 * 给定一个可能旋转过的有序数组arr，返回arr中的最小值
 */
public class RotateArr {

    // 二分查找。将最小值那个位置称为断点。如果没有旋转过，则断点在位置0
    // 假设目前想在arr[low...high]范围上找到这个范围上的最小值，以下是具体过程：
    // 1.如果arr[low]<arr[high]，说明没有旋转，返回arr[low]
    // 2.令mid=(low+high)/2：
    //   2.1 如果arr[low]>arr[mid]，说明断点一定在arr[low...mid]上，令high=mid，回到步骤1
    //   2.2 如果arr[mid]>arr[high]，说明断点一定在arr[mid...high]上，令low=mid，回到步骤1
    // 3.如果步骤1和步骤2都没有命中，说明arr[low]>=arr[high]且arr[low]<=arr[mid]且arr[mid]<=arr[high]
    //   即arr[low]==arr[mid]==arr[high]，此时无法根据断点进行判断，但也无需遍历，仍可以采用二分法
    //   生成变量i，初始为i=low，开始向右遍历arr[i++]，会有以下三种情况：
    //   3.1 遍历到某个位置时发现arr[low]>arr[i]，那么arr[i]就是断点
    //   3.2 遍历到某个位置时发现arr[low]<arr[i]，说明arr[i]>arr[mid]，说明断点在arr[i...mid]上，令high=mid，回到步骤1
    //   3.3 如果i==mid都没有出现前面两种情况，说明arr[low...mid]的值全部一样，此时low=mid，退出遍历回到步骤1
    public int getMin(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        while (low < high) {
            if (low == high - 1) {
                break;
            }

            if (arr[low] < arr[high]) { // 情况1
                return arr[low];
            }
            mid = low + (high - low) / 2;
            if (arr[low] > arr[mid]) { // 情况2.1
                low = mid;
                continue;
            }
            if (arr[mid] > arr[high]) { // 情况2.2
                low = mid;
                continue;
            }
            while (low < mid) { // 遍历自然退出来到情况3.3
                if (arr[low] == arr[mid]) {
                    low++;
                } else if (arr[low] < arr[mid]) { // 情况3.1
                    return arr[low];
                } else { // 情况3.2
                    high = mid;
                    break;
                }
            }
        }
        return Math.min(arr[low], arr[high]);
    }
}
