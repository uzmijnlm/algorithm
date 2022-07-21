package org.example.other;

/**
 * 在二维坐标系中，所有的值都是double类型，那么一个矩形可以由4个点来代表
 * (x1,y1)为最左的点，(x2,y2)为最上的点，(x3,y3)为最下的点，(x4,y4)为最右的点
 * 给定4个点代表的矩形，再给定一个点(x,y)，判断该点是否在矩形中
 */
public class InsideRectangle {

    // 难点在于矩形的边不一定平行于x轴或y轴
    // 利用高中知识进行坐标变换即可
    public boolean isInside(double x1, double y1,
                            double x2, double y2,
                            double x3, double y3,
                            double x4, double y4,
                            double x, double y) {
        if (y1 == y2) {
            return isInside(x1, y1, x4, y4, x, y);
        }

        // 坐标变换
        double l = Math.abs(y4 - y3);
        double k = Math.abs(x4 - x3);
        double s = Math.sqrt(l * l + k * k);
        double sin = l / s;
        double cos = k / s;
        double x1R = cos * x1 + sin * y1;
        double y1R = -x1 * sin + y1 * cos;
        double x4R = cos * x4 + sin * y4;
        double y4R = -x4 * sin + y4 * cos;
        double xR = cos * x + sin * y;
        double yR = -x * sin + y * cos;

        return isInside(x1R, y1R, x4R, y4R, xR, yR);
    }

    // 针对平行于x轴和y轴的情况
    private boolean isInside(double x1, double y1,
                             double x4, double y4,
                             double x, double y) {
        if (x <= x1) {
            return false;
        }
        if (x >= x4) {
            return false;
        }
        if (y >= y1) {
            return false;
        }
        if (y <= y4) {
            return false;
        }
        return true;
    }
}
