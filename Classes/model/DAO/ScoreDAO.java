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
public class ScoreDAO {
    
    private Connection conn;
    
    public void insert(Score s) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "INSERT INTO Score(idAnimal, score, idUser)"
                + " VALUES (?, ?, ?)";
        
        PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        st.setInt(1, s.getAnimal().getId());
        st.setDouble(2, s.getScore());
        st.setInt(1, s.getUser().getId());
        
        st.executeUpdate();
        
        ResultSet key = st.getGeneratedKeys();
        
        if(key.next()){
            s.setId(key.getInt(1));
        }
        
        key.close();
        st.close();
        
        
        DatabaseConnection.closeConnection(conn);
    }
    
    public void update(Score s) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "UPDATE Score SET idAnimal=?, score=?, idUser=? WHERE idScore=?";
        
        PreparedStatement st = conn.prepareStatement(sql);
        
        st.setInt(1, s.getAnimal().getId());
        st.setDouble(2, s.getScore());
        st.setInt(3, s.getUser().getId());
        
        st.setInt(4, s.getId());
        
        st.executeUpdate();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
    }

    public Score retrieveById(int idScore) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM Score WHERE idScore=?";
        
        Score s = new Score();
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idScore);       
        
        ResultSet rs = st.executeQuery();
        
        if(rs.next()){
            s.setId(rs.getInt("idScore"));
            s.setAnimal((new AnimalDAO()).retrieveById(rs.getInt("idAnimal")));
            s.setScore(rs.getDouble("score"));
            s.setUser((new UserDAO()).retrieveById(rs.getInt("idUser")));
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return s;
    }

    public ArrayList<Score> retrieveByAnimal(int idAnimal) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM Score WHERE idAnimal=?";
        
        ArrayList<Score> scores = new ArrayList<>();
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idAnimal);       
        
        ResultSet rs = st.executeQuery();
        
        AnimalDAO animalDao = new AnimalDAO();
        UserDAO userDao = new UserDAO();
        
        while(rs.next()){
            Score s = new Score();
            
            s.setId(rs.getInt("idScore"));
            s.setAnimal(animalDao.retrieveById(rs.getInt("idAnimal")));
            s.setScore(rs.getDouble("score"));
            s.setUser(userDao.retrieveById(rs.getInt("idUser")));
            
            scores.add(s);
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return scores;
    }

    public ArrayList<Score> retrieveByUser(int idUser) throws SQLException {
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM Score WHERE idUser=?";
        
        ArrayList<Score> scores = new ArrayList<>();
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idUser);       
        
        ResultSet rs = st.executeQuery();
        
        AnimalDAO animalDao = new AnimalDAO();
        UserDAO userDao = new UserDAO();
        
        while(rs.next()){
            Score s = new Score();
            
            s.setId(rs.getInt("idScore"));
            s.setAnimal(animalDao.retrieveById(rs.getInt("idAnimal")));
            s.setScore(rs.getDouble("score"));
            s.setUser(userDao.retrieveById(rs.getInt("idUser")));
            
            scores.add(s);
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return scores;
    }
}
