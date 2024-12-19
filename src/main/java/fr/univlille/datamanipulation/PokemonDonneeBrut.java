package fr.univlille.datamanipulation;

import com.opencsv.bean.CsvBindByName;
import fr.univlille.modele.AttributeType;

import java.util.Arrays;
import java.util.List;

public class PokemonDonneeBrut extends FormatDonneebrut{

    public final static int NB_ATTRIBUTES = 13;

    @CsvBindByName(column = "name")
    public String name;

    @CsvBindByName(column = "attack")
    public int attack;

    @CsvBindByName(column = "base_egg_steps")
    public int base_egg_steps;

    @CsvBindByName(column = "capture_rate")
    public double capture_rate;

    @CsvBindByName(column = "defense")
    public int defense;

    @CsvBindByName(column = "experience_growth")
    public int experience_growth;

    @CsvBindByName(column = "hp")
    public int hp;

    @CsvBindByName(column = "sp_attack")
    public int sp_attack;

    @CsvBindByName(column = "sp_defense")
    public int sp_defense;

    @CsvBindByName(column = "type1")
    public String type1;

    @CsvBindByName(column = "type2")
    public String type2;

    @CsvBindByName(column = "speed")
    public double speed;

    @CsvBindByName(column = "is_legendary")
    public boolean is_legendary;

    public static List<String> columnList = Arrays.asList(
            "name",
            "attack",
            "base_egg_steps",
            "capture_rate",
            "defense",
            "experience_growth",
            "hp",
            "sp_attack",
            "sp_defense",
            "type1",
            "type2",
            "speed",
            "is_legendary");


    public static List<AttributeType> attributeTypeList = Arrays.asList(
            AttributeType.STR,
            AttributeType.NUM,
            AttributeType.NUM,
            AttributeType.NUM,
            AttributeType.NUM,
            AttributeType.NUM,
            AttributeType.NUM,
            AttributeType.NUM,
            AttributeType.NUM,
            AttributeType.STR,
            AttributeType.STR,
            AttributeType.NUM,
            AttributeType.BOOL);

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getBase_egg_steps() {
        return base_egg_steps;
    }

    public double getCapture_rate() {
        return capture_rate;
    }

    public int getDefense() {
        return defense;
    }

    public int getExperience_growth() {
        return experience_growth;
    }

    public int getHp() {
        return hp;
    }

    public int getSp_attack() {
        return sp_attack;
    }

    public int getSp_defense() {
        return sp_defense;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean Is_legendary() {
        return is_legendary;
    }

    @Override
    public String toString() {
        return "FormatDonneeBrut{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", base_egg_steps=" + base_egg_steps +
                ", capture_rate=" + capture_rate +
                ", defense=" + defense +
                ", experience_growth=" + experience_growth +
                ", hp=" + hp +
                ", sp_attack=" + sp_attack +
                ", sp_defense=" + sp_defense +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", speed=" + speed +
                ", is_legendary=" + is_legendary +
                '}';
    }

    public static Object[][] getValues(List<? extends FormatDonneebrut> list) {
        Object[][] result = new Object[list.size()][NB_ATTRIBUTES];
        for (int idxList = 0; idxList < list.size(); idxList++) {
            PokemonDonneeBrut tmp = (PokemonDonneeBrut) list.get(idxList);
            result[idxList][0] = tmp.getName();
            result[idxList][1] = tmp.getAttack();
            result[idxList][2] = tmp.getBase_egg_steps();
            result[idxList][3] = tmp.getCapture_rate();
            result[idxList][4] = tmp.getDefense();
            result[idxList][5] = tmp.getExperience_growth();
            result[idxList][6] = tmp.getHp();
            result[idxList][7] = tmp.getSp_attack();
            result[idxList][8] = tmp.getSp_defense();
            result[idxList][9] = tmp.getType1();
            if(tmp.getType2()!= null){
                result[idxList][10] = tmp.getType2();
            }
            result[idxList][11] = tmp.getSpeed();
            result[idxList][12] = tmp.Is_legendary();
        }
        return result;
    }

    public static void displayData(List<PokemonDonneeBrut> donnees) {
        for (PokemonDonneeBrut tmp: donnees) {
            System.out.println(tmp);
        }
    }
}
