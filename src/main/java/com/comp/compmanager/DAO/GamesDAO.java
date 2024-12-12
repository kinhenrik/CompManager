package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Games;
import com.comp.compmanager.entities.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class GamesDAO {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    public List<Games> getAllGames() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Games> listToReturn = new ArrayList<>();

        //Get all players from Table
        TypedQuery<Games> result = manager.createQuery("FROM Games", Games.class);

        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }
}


