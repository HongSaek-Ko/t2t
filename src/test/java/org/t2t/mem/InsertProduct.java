package org.t2t.mem;

import java.sql.*;

public class InsertProduct {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/t2t?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8", "test1", "1234");
        con.setAutoCommit(false);
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO PRD" +
                "(PRDID, USRID, TITLE, CONT, HIT, TRGTSALQTY, CURSALQTY, " +
                "ISLIMIT, SALSTAT, ABTREAS, CATE, PRICE, REGDT, LASTDT) VALUES " +
                "(?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,? ,?, ?, ?)");

        for(int i = 2; i < 30; i++) {
            pstmt.setInt(1, i);
            pstmt.setString(2, "test"+i);  // usrId: test2 ~ test29
            pstmt.setString(3, "title"+i);     // title: title2 ~ title29
            pstmt.setString(4, "cont"+i); // CONT, 내용, cont2 ~ cont29
            pstmt.setInt(5, 0); // hit, 조회수
            pstmt.setInt(6, i+1); // TRGTSALQTY, 목표 판매 수량, 2 ~ 30
            pstmt.setInt(7, i); // CURSALQTY, 현재 판매 수량 1 ~ 29
            pstmt.setString(8, "Y"); // ISLIMIT, 수량 제한 여부 (Y/N)
            pstmt.setString(9, "PRD01"); // SALSTAT, 판매 상태, COCD, 기본값 PRD01(판매중)
            pstmt.setString(10, "tAbRe"); // ABTREAS, 중지 사유, NULL 가능
            pstmt.setString(11, "test"); // CATE, 카테고리, N/N
            pstmt.setInt(12, 1000); // PRICE, 가격, N/N, 1000으로 일괄 설정
            pstmt.setDate(13, new Date(System.currentTimeMillis()));
            pstmt.setDate(14, new Date(System.currentTimeMillis()));

            pstmt.addBatch();
            pstmt.clearParameters();
        }
        pstmt.executeBatch();
        con.commit();
        pstmt.clearBatch();
        pstmt.close();
        con.close();

    }
}
