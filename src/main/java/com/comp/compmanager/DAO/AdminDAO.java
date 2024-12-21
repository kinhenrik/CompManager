package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Admin;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class AdminDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //CREATE
    public void addAdmin (Admin admin) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(admin);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (manager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }

    //GET
    public Admin getAdminByID (int admin_id) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Admin adminToReturn = manager.find(Admin.class, admin_id);
        manager.close();
        return adminToReturn;
    }

    public List<Admin> getAllAdmins () {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Admin> listToReturn = new ArrayList<>();

        TypedQuery<Admin> result = manager.createQuery("FROM Admin", Admin.class);

        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    //UPDATE
    public void updateAdmin (Admin adminToUpdate) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (manager.contains(adminToUpdate)) {
                manager.persist(adminToUpdate);
            } else {
                Admin updatedAdmin = manager.merge(adminToUpdate);
                System.out.println("Admin with ID=" + updatedAdmin.getId() + " has been updated.");
            }
            manager.merge(adminToUpdate);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (manager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }

    //DELETE
    public static void deleteAdmin(Admin admin) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (!manager.contains(admin)) {
                admin = manager.merge(admin);
            }
            manager.remove(admin);
            transaction.commit();
            System.out.println("Admin with ID=" + admin.getId() + " has been removed from database.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (manager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }

}
