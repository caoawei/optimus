package com.colt.common;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

/**
 * @author caoawei
 * Created on 2017/12/18.
 */
public class ColtUtil {

    /**
     * 是否是奇异矩阵.
     * 奇异矩阵的特征:
     *   1.矩阵是方阵
     *   2.矩阵不是满秩
     * 非奇异矩阵
     *   1.矩阵是方阵
     *   2.矩阵是满秩(矩阵对应的行列式值不为0)
     * @param matrix2D
     * @return 是否是奇异矩阵
     */
    public static boolean isSingular(DoubleMatrix2D matrix2D) {
        int row = matrix2D.rows();
        int col = matrix2D.columns();
        if(row != col) return false;
        int rank = Algebra.DEFAULT.rank(matrix2D);
        return rank < row;
    }

    /**
     * 非奇异矩阵验证
     * @param matrix2D 矩阵
     * @return 是否是非奇异矩阵
     */
    public static boolean isNoneSingular(DoubleMatrix2D matrix2D) {
        return !isSingular(matrix2D);
    }
}
