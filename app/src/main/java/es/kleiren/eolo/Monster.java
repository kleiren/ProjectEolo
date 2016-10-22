package es.kleiren.eolo;

/**
 * Created by carlos on 10/22/16.
 */

public class Monster {

    int hp;
    String name;
    int id;

    public Monster(int hp, String name, int id) {
        this.hp = hp;
        this.name = name;
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
