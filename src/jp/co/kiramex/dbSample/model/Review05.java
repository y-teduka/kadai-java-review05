package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet result = null;
        // ドライバのクラスをJava上で読み込む
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DBと接続する
            con = DriverManager.getConnection("jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root", "3hl0rgalT36@iort64");

            // DBとやりとりする窓口（オブジェクト）の作成
            String select = "SELECT * FROM person WHERE id=?";
            pst = con.prepareStatement(select);

            // Select文の実行と結果を格納／代入
            System.out.print("idを数字で入力してください＞");
            int input = keyIn();
            
            pst.setInt(1,input);

            result = pst.executeQuery();

            // 結果を表示する
            if (result.next()) {
                String nam = result.getString("name");
                int ag = result.getInt("age");

                System.out.println(nam);
                System.out.println(ag);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 接続を閉じる
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース接続時にエラーがが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    private static int keyIn() {
        int line =0;
        try {
           BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
           String inputdata= scan.readLine();
            line = Integer.parseInt(inputdata);
            
        } catch (Exception e) {
        }
        return line;
    }
}
