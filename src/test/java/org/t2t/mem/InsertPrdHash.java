package org.t2t.mem;

import java.sql.*;

public class InsertPrdHash {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/t2t?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8", "test1", "1234");
    con.setAutoCommit(false);
    PreparedStatement pstmt = con.prepareStatement("INSERT INTO PRDHASH" +
            "(PRDHASHID, TAGID, PRDID, REGDT, LASTDT)" +
            "VALUES(?, ?, ?, ?, ?)");

    for(int i = 1; i < 30; i++){
        pstmt.setInt(1, i);
        pstmt.setString(2, "testTag" + i);
        pstmt.setInt(3, i);
        pstmt.setDate(4, new Date(System.currentTimeMillis()));
        pstmt.setDate(5, new Date(System.currentTimeMillis()));

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

