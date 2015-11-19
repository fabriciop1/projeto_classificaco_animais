/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import model.business.*;
import util.TrashGen;

/**
 *
 * @author Jefferson Sales
 */
public class ImageDAO {
    
    private Connection conn;
    
    public void insert(Image i) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "INSERT INTO Image(idAnimal, animal_image) "
                + "VALUES (?, ?)";
        
        PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        st.setInt(1, i.getAnimal().getId());
        st.setBlob(2, i.getImage());
        
        st.executeUpdate();
        
        ResultSet key = st.getGeneratedKeys();
        
        if(key.next()){
            i.setId(key.getInt(1));
        }
        
        DatabaseConnection.closeConnection(conn);
    }
    
    public void update(Image i) throws SQLException {
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "UPDATE Image SET idAnimal=?, animal_image=? WHERE idImage=?";
        
        PreparedStatement st = conn.prepareStatement(sql);
        
        st.setInt(1, i.getAnimal().getId());
        st.setBlob(2, i.getImage());
        
        st.setInt(3, i.getId());
        
        st.executeUpdate();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
    }

    public void remove(int idImage) throws SQLException {
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "DELETE FROM Image WHERE idImage=?";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idImage);
        
        st.executeUpdate();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
    }

    public Image retrieveById(int idImage) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM Image WHERE idImage=?";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idImage);
        
        ResultSet rs = st.executeQuery();
        
        Image i = new Image();
        
        if(rs.next()){
            i.setId(idImage);
            i.setAnimal((new AnimalDAO()).retrieveById(rs.getInt("idAnimal")));
            i.setImage(rs.getBlob("animal_image"));
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return i;
    }

    public Image retrieveByAnimal(int idAnimal) throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM Image WHERE idAnimal=?";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idAnimal);
        
        ResultSet rs = st.executeQuery();
        
        Image i = new Image();
        
        if(rs.next()){
            i.setId(rs.getInt("idImage"));
            i.setAnimal((new AnimalDAO()).retrieveById(idAnimal));
            i.setImage(rs.getBlob("animal_image"));
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return i;
    }

    public ArrayList<Image> retrieveAll() throws SQLException{
        
        conn = DatabaseConnection.openConnection();
        
        String sql = "SELECT * FROM Image";
        
        ArrayList<Image> images = new ArrayList<>();
        
        PreparedStatement st = conn.prepareStatement(sql);
        
        ResultSet rs = st.executeQuery();
        
        while(rs.next()){
            Image i = new Image();
            
            i.setId(rs.getInt("idImage"));
            i.setAnimal((new AnimalDAO()).retrieveById(rs.getInt("idAnimal")));
            i.setImage(rs.getBlob("animal_image"));
            
            images.add(i);
        }
        
        rs.close();
        st.close();
        
        DatabaseConnection.closeConnection(conn);
        
        return images;
    }

    public static BufferedImage convertBlobToImage(Blob blob) throws IOException, SQLException{
        
        return ImageIO.read(blob.getBinaryStream(1, blob.length()));
    }
    
    public static Blob convertImageToBlob(BufferedImage image, String imageFormat, Connection conn) throws SQLException, IOException{
        
        Blob blob = conn.createBlob();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        ImageIO.write(image, imageFormat, baos);
        baos.flush();
        
        blob.setBytes(1, baos.toByteArray());
        
        return blob;
    }
    
    public static BufferedImage loadImage(String filePath) throws IOException{
        
        File file = new File(filePath);
        
        return ImageIO.read(file);
    }
    
    public static void saveImage(BufferedImage image, String imageFormat, String filePath) throws IOException{
        
        File file = new File(filePath);
        
        if(!file.exists()){
            file.createNewFile();
        }
        
        ImageIO.write(image, imageFormat, file);
    }
    
    public static void main(String[] arg){
        Connection connection = null;
        
              try{    
                              connection = DatabaseConnection.openConnection();
                              
            BufferedImage buff = loadImage("C:\\Users\\Fabricio\\Documents\\NetBeansProjects\\NotasImagensAnimais\\images\\20151112_153059.jpg");
            
            Image i = new Image();
            i.setAnimal((new AnimalDAO()).retrieveById(2));
            i.setImage(convertImageToBlob(buff,"jpg",connection));
            
            (new ImageDAO()).insert(i);
            
              }catch(Exception e ){
                  
              }finally{
                  DatabaseConnection.closeConnection(connection);
              }
            
            
            
            
    }
    
}
