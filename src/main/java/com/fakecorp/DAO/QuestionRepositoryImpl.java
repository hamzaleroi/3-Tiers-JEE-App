package com.fakecorp.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Vector;

public class QuestionRepositoryImpl implements QuestionRepository {



    @Override
    public void addQuestion(int id, String enonceQuestion, String[] repPossibles, int score, int bonneRepIndx) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction et = null;

        try{
            et = em.getTransaction();
            et.begin();
            Question question = new Question();
            question.setId(id);
            question.setEnonceQuestion(enonceQuestion);
            question.setScore(score);
            question.setRepPossibles(repPossibles);
            question.setBonneRepIndx(bonneRepIndx);
            em.persist(question);
            et.commit();
        }
        catch (Exception e){
            if(et != null){
                et.rollback();
            }

        }
        finally {
            em.close();
        }
    }





    @Override
    public void addQuestion(int id,Question q) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        EntityTransaction et = null;

        try{
            et = em.getTransaction();
            et.begin();
            q.setId(id);
            em.persist(q);
            et.commit();
        }
        catch (Exception e){
            if(et != null){
                et.rollback();
            }
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    @Override
    public  List<Question> getQuestions() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String strQuery = "SELECT  c FROM Question c WHERE c.id IS NOT NULL";

        // Issue the query and get a matching Question
        TypedQuery<Question> tq = em.createQuery(strQuery, Question.class);
        List<Question> questions;
        try {
            // Get matching customer object and output
            questions = tq.getResultList();
            return questions;
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            em.close();
        }

    }

}
