package fr.univlille.datamanipulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonDonneeBrutTest {

    private PokemonDonneeBrut pokemon;

    @BeforeEach
    public void setUp() {
        pokemon = new PokemonDonneeBrut();
        pokemon.name = "pikachu";
        pokemon.attack = 100;
        pokemon.base_egg_steps = 300;
        pokemon.capture_rate = 100;
        pokemon.defense = 200;
        pokemon.experience_growth = 200;
        pokemon.hp = 150;
        pokemon.sp_attack = 150;
        pokemon.sp_defense = 150;
        pokemon.type1 = "fly";
        pokemon.type2 = "ground";
        pokemon.speed = 400;
        pokemon.is_legendary = false;

    }

    @Test
    public void testToString() {
        String expectedString = "FormatDonneeBrut{name='pikachu', attack=100, base_egg_steps=300, capture_rate=100.0, defense=200, experience_growth=200, hp=150, sp_attack=150, sp_defense=150, type1='fly', type2='ground', speed=400.0, is_legendary=false}";
        assertEquals(expectedString, pokemon.toString());
    }

    @Test
    public void testGetValues() {
        List<PokemonDonneeBrut> pokemonList = new ArrayList<>();
        pokemonList.add(pokemon);

        Object[][] expectedValues = {
                {"pikachu", 100, 300, 100.0, 200, 200, 150, 150, 150, "fly", "ground", 400.0, false}
        };

        Object[][] actualValues = PokemonDonneeBrut.getValues(pokemonList);
        assertArrayEquals(expectedValues, actualValues);
    }
}
