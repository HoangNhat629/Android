package com.example.module2_toeic.models;

import java.io.Serializable;

public class TopicModel implements Serializable {
    int id;
    String name;
    String category;
    String color;
    String lastTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public TopicModel(int id, String name, String category, String color, String lastTime) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.color = color;
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "TopicModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
