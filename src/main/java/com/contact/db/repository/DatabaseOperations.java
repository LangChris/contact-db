package com.contact.db.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.contact.db.model.ColumnType;

@Repository
public class DatabaseOperations {

	@PersistenceContext
    private EntityManager entityManager;
	
    @Transactional
    public boolean addNewColumn(String schema, String tableName, String columnName, ColumnType columnType, Integer columnLength) {
    	try {
    		columnName = columnName
    				.trim()
    				.toLowerCase()
    				.replaceAll(" ", "_");
    		String query = "ALTER TABLE " + schema + "." + tableName + " ADD COLUMN " + columnName + " " + columnType.name() + (columnLength != null ? "(" + columnLength + ")" : "");
            entityManager.createNativeQuery(query).executeUpdate();
            return true;
    	} catch(Exception e) {
    		System.out.println("Error Adding New Column: " + e.getMessage());
    	}
    	return false;
    }
    
    
    
    public List<Object> getContacts() {
    	try {
    		String query = "select * from contactdb.contact";
            List<Object> results = entityManager.createNativeQuery(query).getResultList();

            return results;
    	} catch(Exception e) {
    		System.out.println("Error Retrieving Contacts: " + e.getMessage());
    	}
    	return null;
    }
}
