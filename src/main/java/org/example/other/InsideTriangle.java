package org.example.other;

/**
 * 二维坐标系，所有值都是double类型。一个三角形由3个点表示
 * 给定3个点代表的三角形，再给定一个点(x,y)，判断(x,y)是否在三角形中
 */
public class InsideTriangle {

    // 如果点O在三角形ABC中，那么从三角形一点出发，逆时针走过所有边的过程中，点O始终都在走过边的左侧
    // 关键在于找到逆时针的顺序，并判断一个点是否在一条边的左侧
    public boolean isInside(double x1, double y1,
                            double x2, double y2,
                            double x3, double y3,
                            double x, double y) {
        // 如果三角形的点不是逆时针输入，改变一下顺序
        if (crossProduct(x3 - x1, y3 - y1, x2 - x1, y2 - y1) >= 0) {
            double tmpX = x2;
            double tmpY = y2;
            x2 = x3;
            y2 = y3;
            x3 = tmpX;
            y3 = tmpY;
        }
        if (crossProduct(x2 - x1, y2 - y1, x - x1, y - y1) < 0) {
            return false;
        }
        if (crossProduct(x3 - x2, y3 - y2, x - x2, y - y2) < 0) {
            return false;
        }
        if (crossProduct(x1 - x3, y1 - y3, x - x3, y - y3) < 0) {
            return false;
        }
        return true;
    }


    // 向量积的方式判断方向
    private double crossProduct(double x1, double y1, double x2, double y2) {
        return x1 * y2 - x2 * y1;
    }

}
