/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.business.User;
import util.TrashGen;

/**
 *
 * @author Jefferson Sales
 */
public class UserDAO {
    
    private Connection conn;
    
    public void insert(User user) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "INSERT INTO User(user_name, user_login, user_password) "
                + "VALUES (?,?,?)";
        
        PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        st.setString(1, user.getName());
        st.setString(2,user.getLogin());
        st.setString(3,user.getPassword());
        
        st.executeUpdate();
        
        ResultSet key = st.getGeneratedKeys();
        
        if(key.next()){
            user.setId(key.getInt(1));
        }
        
        key.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);   
    }
    
    public void update(User user) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "UPDATE User SET user_name=?, user_login=?, user_password=? WHERE idUser=?";
        
        PreparedStatement st = conn.prepareStatement(sql);
        
        st.setString(1, user.getName());
        st.setString(2, user.getLogin());
        st.setString(3, user.getPassword());
        
        st.setInt(4, user.getId());
        
        st.executeUpdate();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
    }
    
    public User retrieveById(int idUser) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM User WHERE idUser=?";
        
        User user = new User();
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idUser);       
        
        ResultSet rs = st.executeQuery();
        
        if(rs.next()){
            user.setId(rs.getInt("idUser"));
            user.setName(rs.getString("user_name"));
            user.setLogin(rs.getString("user_login"));
            user.setPassword(rs.getString("user_password"));
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return user;
    }

    public User retrieveByLogin(String login) throws SQLException{
        conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM User WHERE user_login=?";
        
        User user = null;
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, login);       
        
        ResultSet rs = st.executeQuery();
                
        if(rs.next()) {
                user = new User();
                
                user.setId(rs.getInt("idUser"));
                user.setName(rs.getString("user_name"));
                user.setLogin(rs.getString("user_login"));
                user.setPassword(rs.getString("user_password"));
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return user;
    }    
    
    public ArrayList<User> retrieveAll() throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        ArrayList<User> users = new ArrayList<>();
        
        String sql = "SELECT * FROM User";
        
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            User u = new User();
            
            u.setId(rs.getInt("idUser"));
            u.setName(rs.getString("user_name"));
            u.setLogin(rs.getString("user_login"));
            u.setPassword(rs.getString("user_password"));
            
            users.add(u);
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return users;
    }

    public void remove(int idUser) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "DELETE FROM User WHERE idUser = ?";
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idUser);
                
        statement.executeUpdate();
        statement.close();
            
        DatabaseConnection.closeConnection(conn);
    }

}
