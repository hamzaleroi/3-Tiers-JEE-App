package com.fakecorp.DAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public interface QuestionRepository {
    public static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("QuestionReponse");
    public void addQuestion(int id,String enonceQuestion,String [] repPossibles,int score,int bonneRepIndx);
    public void addQuestion(int id,Question question);
    public List<Question> getQuestions();
}
