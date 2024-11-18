package org.t2t.t2t;

import java.sql.*;

// 1000명 회원정보 넣기
public class InsertMember {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/t2t?serverTimezone=UTC&characterEncoding=UTF-8","test1","1234");
        con.setAutoCommit(false);
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO MEM " +
                "(USRID, PASSWD, NM, EMAIL, ROLEID, " +
                "MEMSTAT, INTRO, PROFIMG, PROFORGIMG, LOGATMCNT, " +
                "CHGPASSDT, BANKNM, BANKACNT, BANKACNTOWR) VALUES " +
                "(?,?,?,?,?," +
                " ?,?,?,?,?," +
                "?,?,?,?)");

        for(int i = 0 ; i < 1000; i++){
            pstmt.setString(1, "test" + i);
            pstmt.setString(2, "passwd" + i);
            pstmt.setString(3, "T2T"+i);
            pstmt.setString(4, "t2t"+i + "@gmail.com");
            pstmt.setString(5, "MEMBER");
            pstmt.setString(6, "MEM01");
            pstmt.setString(7, "자기소개"+i);
            pstmt.setString(8, "c:\\image\\image.png");
            pstmt.setString(9, "c:\\image\\image.png");
            pstmt.setInt(10, 0);
            pstmt.setDate(11, new Date(System.currentTimeMillis()));
            pstmt.setString(12, "카카오뱅크");
            pstmt.setString(13, "123412341"+i);
            pstmt.setString(14, "account" + i);

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
