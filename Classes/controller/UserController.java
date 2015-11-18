/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.DAO.UserDAO;
import model.business.User;

/**
 *
 * @author Alexandre
 */
public class UserController {
    
    private User user;
    private UserDAO userDAO;
    
    private UserController() {}
    
    private static class UsuarioLogadoHolder { 
        private final static UserController instance = new UserController();
    }

    public static UserController getInstance() {
            return UsuarioLogadoHolder.instance;
    }
    
    public boolean login(String login, char[] password) throws SQLException{
        
        User atual = new User();
        
        userDAO = new UserDAO();
        atual = userDAO.retrieveByLogin(login);
        
        if(atual != null){
            
            if(verifyPassword(atual.getPassword(), password)){
                getInstance().setUser(atual);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Senha incorreta", null, JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuário incorreto", null, JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    public boolean verifyPassword(String passwordStr, char[] passwordChar){
        
        char[] temp = passwordStr.toCharArray();
        
        if(passwordStr.length() == passwordChar.length){
            for(int i = 0; i < passwordChar.length; i++){
                if(temp[i] != passwordChar[i]){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean logout(){
        getInstance().setUser(null);
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
}
