package com.fakecorp.DAO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "question" )
public class Question implements Serializable {
    private static  final long serialVersionUID = 1L;

    static public int nbRepQst=4;


    @Id
    @Column(name = "id")
    private int id;

    @Column
    private String enonceQuestion;

    @Column(name="repPossibles")
    @Convert(converter = StringArrayConverter.class)
    private String[]  repPossibles;

    public String[] getRepPossibles() {
        return repPossibles;
    }

    public void setRepPossibles(String[] repPossibles) {
        this.repPossibles = repPossibles;
    }

    @Column
    private int score;

    @Column
    private int bonneRepIndx;

    public int getId() {
        return id;
    }

    public String getEnonceQuestion() {
        return enonceQuestion;
    }

    public void setEnonceQuestion(String enonceQuestion) {
        this.enonceQuestion = enonceQuestion;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBonneRepIndx() {
        return bonneRepIndx;
    }

    public void setBonneRepIndx(int bonneRepIndx) {
        this.bonneRepIndx = bonneRepIndx;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean repJuste(int reponse)
    {
        if (bonneRepIndx == reponse) return true; else return false;
    }


}
