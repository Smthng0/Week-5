package dream.factory.learning.postgreSql;

import com.univocity.parsers.annotations.Parsed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Minion {
    @Parsed
    private String title;

    @Parsed(field = "mana_cost")
    private int manaCost;

    @Parsed
    private int attack;

    @Parsed
    private int health;

    @Parsed
    private String abilities;

    public List<String> getAbilityList(){
        if(abilities == null){
            return new ArrayList<>();
        }

        String[] split = abilities.split(",");

        return Arrays.asList(split);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }
}
