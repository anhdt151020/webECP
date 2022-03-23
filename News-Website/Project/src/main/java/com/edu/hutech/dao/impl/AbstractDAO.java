package com.edu.hutech.dao.impl;

import com.edu.hutech.dao.GenericDAO;
import com.edu.hutech.mapper.RowMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//Build hàm chung query data
public class AbstractDAO<T> implements GenericDAO<T> {


    @Override
    public List<T> query(String sql, RowMapper rowMapper, Object... param) {
        List<T> results = new ArrayList<>();
        Connection conn = getConnection(); //Hàm getConnection Database
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if(conn != null) {
            try {
                statement = conn.prepareStatement(sql);
                setParam(statement, param); //Hàm setParameter
                resultSet = statement.executeQuery(); //execute

                //convert từ type resulthSet > type model & add vào results
                while (resultSet.next()) {
                    results.add( (T) rowMapper.mapRow(resultSet) );
                }

                return results;
            } catch (SQLException e) {
                return null;
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (resultSet != null) {
                        resultSet.close();
                    }
                } catch (SQLException e) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public Long insert(String sql, Object... param) {
        Connection conn = null;
        PreparedStatement statement = null;
        //resultSet để get data từ db, id để hứng value của resultSet
        ResultSet resultSet = null;
        Long id = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); //để tạo cơ chế rollBack() khi query lỗi
            statement = conn.prepareStatement(sql,statement.RETURN_GENERATED_KEYS); //return ra có kèm index trong db
            setParam(statement,param);
            statement.executeUpdate();

            /*ko thể get thẳng từ statement.getGeneratedKeys() gán vào cho id mà phải qua resultSet(dạng table) */
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }

            conn.commit();

        } catch (SQLException e) {
            if(conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public void update(String sql, Object... param) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql);
            setParam(statement,param);
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int count(String sql, Object... param) {
        Connection conn = getConnection(); //Hàm getConnection Database
        PreparedStatement statement = null;
        //resultSet để get "select count.." từ db, count để hứng value của resultSet
        ResultSet resultSet = null;
        int count = -1;
        if(conn != null) {
            try {
                statement = conn.prepareStatement(sql);
                setParam(statement, param); //Hàm setParameter
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }

                return count;
            } catch (SQLException e) {
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (resultSet != null) {
                        resultSet.close();
                    }
                } catch (SQLException e) {
                }
            }
        }
        return count;
    }


    ResourceBundle resourceBundle = ResourceBundle.getBundle("db"); //get data từ db.properties
    //Nếu để private thì lớp con extends sẽ ko gọi dc hàm
    public Connection getConnection() {
        try {
            Class.forName(resourceBundle.getString("driverName"));
            String url = resourceBundle.getString("url");
            String user = resourceBundle.getString("user");
            String pass = resourceBundle.getString("password");

            return DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
    }
    private void setParam(PreparedStatement statement, Object... param) {
        //nếu ko có param thì param[]=null(hay param.length=null) -> Đkien for sai-> out
        //có param thì check datatype là kiểu nào thì set kiểu
        try {
            for (int i = 0; i < param.length; i++) {
                int index = i+1;
                if(param[i] instanceof Long) {
                    statement.setLong(index,(Long) param[i]);
                }
                else if(param[i] instanceof String) {
                    statement.setString(index,(String) param[i]);
                }
                else if(param[i] instanceof Integer) {
                    statement.setInt(index,(Integer) param[i]);
                }
                else if(param[i] instanceof Timestamp) {
                    statement.setTimestamp(index,(Timestamp) param[i]);
                }
                /*else if(param[i] == null) {
                    statement.setNull(index,Types.TIMESTAMP);
                }*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
