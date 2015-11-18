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
import model.business.*;
import util.TrashGen;

/**
 *
 * @author Jefferson Sales
 */
public class AnimalDAO {
    
    private Connection conn;
    
    public void insert(Animal a) throws SQLException{
        
         conn = DatabaseConnection.openConnection();
        
        String sql = "INSERT INTO Animal(animal_description) "
                + "VALUES (?)";
        
        PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        st.setString(1, a.getDescription());
        
        st.executeUpdate();
        
        ResultSet key = st.getGeneratedKeys();
        
        if(key.next()){
            a.setId(key.getInt(1));
        }
        
        key.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn); 
    }
    
    public void remove(int idAnimal) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "DELETE FROM Animal WHERE idAnimal = ?";
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, idAnimal);
                
        statement.executeUpdate();
        statement.close();
            
        DatabaseConnection.closeConnection(conn);
    }
    
    public void update(Animal a) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "UPDATE Animal SET animal_description=? WHERE idAnimal=?";
        
        PreparedStatement st = conn.prepareStatement(sql);
        
        st.setString(1, a.getDescription());
        
        st.setInt(2, a.getId());
        
        st.executeUpdate();
        
        DatabaseConnection.closeConnection(conn);
    }
    
    public Animal retrieveById(int idAnimal) throws SQLException{
        
         conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM Animal WHERE idAnimal=?";
        
        Animal a = new Animal();
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idAnimal);       
        
        ResultSet rs = st.executeQuery();
        
        if(rs.next()){
            a.setId(rs.getInt("idAnimal"));
            a.setDescription(rs.getString("animal_description"));
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return a;
    }
    
    public ArrayList<Animal> retrieveAll() throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        ArrayList<Animal> martinGarrix = new ArrayList<>();
        
        String sql = "SELECT * FROM Animal";
        
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        
        ScoreDAO scoreDao = new ScoreDAO();
        //ImageDAO imageDao = new ImageDAO();
        
        while(rs.next()){
            Animal a = new Animal();

            a.setId(rs.getInt("idAnimal"));
            a.setDescription(rs.getString("animal_description"));
            
            martinGarrix.add(a);
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return martinGarrix;
    }
}
