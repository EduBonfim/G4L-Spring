package com.jogosparavida.games4life_backend.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jogosparavida.games4life_backend.model.Accessory;
import com.jogosparavida.games4life_backend.model.Console;
import com.jogosparavida.games4life_backend.model.Game;
import com.jogosparavida.games4life_backend.repository.AccessoryRepository;
import com.jogosparavida.games4life_backend.repository.ConsoleRepository;
import com.jogosparavida.games4life_backend.repository.GameRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
        CommandLineRunner initDatabase(
            ConsoleRepository consoleRepository,
            GameRepository gameRepository,
            AccessoryRepository accessoryRepository,
            com.jogosparavida.games4life_backend.repository.RentalRepository rentalRepository,
            com.jogosparavida.games4life_backend.repository.ClientRepository clientRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        
        return args -> {
            // Só popula se o banco estiver vazio
            if (consoleRepository.count() == 0) {
                initConsoles(consoleRepository);
            }
            if (gameRepository.count() == 0) {
                initGames(gameRepository);
            }
            if (accessoryRepository.count() == 0) {
                initAccessories(accessoryRepository);
            }
            // Limpando registros de aluguel inconsistentes (sem client/console)
            if (rentalRepository.count() > 0) {
                var invalid = rentalRepository.findAll()
                    .stream()
                    .filter(r -> r.getClient() == null || r.getConsole() == null)
                    .toList();
                if (!invalid.isEmpty()) {
                    System.out.println("⚠️ Found invalid rentals (missing client or console): " + invalid.size());
                    // Apaga registros inválidos para evitar que `GET /api/rentals` quebre o retorno
                    rentalRepository.deleteAll(invalid);
                    System.out.println("🧹 Invalid rentals removed.");
                }
            }
            

        };
    }

    // POPULANDO O BANCO
    
    private void initConsoles(ConsoleRepository repository) {
        // PlayStation 5
        Console ps5 = new Console();
        ps5.setName("PlayStation 5");
        ps5.setPrice(new BigDecimal("299"));
        ps5.setImagem("/assets/consoles/Ps5.jpeg");
        repository.save(ps5);

        // Xbox Series X
        Console xbox = new Console();
        xbox.setName("Xbox Series X");
        xbox.setPrice(new BigDecimal("279"));
        xbox.setImagem("/assets/consoles/xboxOneX.jpeg");
        repository.save(xbox);

        // Nintendo Switch
        Console switchConsole = new Console();
        switchConsole.setName("Nintendo Switch");
        switchConsole.setPrice(new BigDecimal("199"));
        switchConsole.setImagem("/assets/consoles/SwitchOled.jpeg");
        repository.save(switchConsole);

        // Steam Deck
        Console steam = new Console();
        steam.setName("Steam Deck");
        steam.setPrice(new BigDecimal("400"));
        steam.setImagem("/assets/consoles/SteamDeck.jpg");
        repository.save(steam);

        System.out.println("✅ Consoles populados: " + repository.count());
    }

    private void initGames(GameRepository repository) {
        
        // God of War Ragnarök
        Game gow = new Game();
        gow.setName("God of War Ragnarök");
        gow.setPrice(new BigDecimal("59"));
        gow.setConsole("ps5");
        gow.setImagem("https://images.kabum.com.br/produtos/fotos/sync_mirakl/410629/Jogo-God-Of-War-Ragnar-k-Playstation-5_1719514745_gg.jpg");
        repository.save(gow);

        // Zelda: Tears of the Kingdom
        Game zelda = new Game();
        zelda.setName("Zelda: Tears of the Kingdom");
        zelda.setPrice(new BigDecimal("69"));
        zelda.setConsole("switch");
        zelda.setImagem("https://imgs.search.brave.com/Q8tr4iRYvLSzBL8p8dDNzj0PesFDxE8cR7dIhpYF8PY/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NjE4R1IwV01vUUwu/anBn");
        repository.save(zelda);

        // Spider-Man 2
        Game spiderman = new Game();
        spiderman.setName("Spider-Man 2");
        spiderman.setPrice(new BigDecimal("69"));
        spiderman.setConsole("ps5");
        spiderman.setImagem("https://images.kabum.com.br/produtos/fotos/sync_mirakl/503115/Jogo-Marvels-Spider-Man-2-Standard-Edition-Playstation-5_1724244091_g.jpg");
        repository.save(spiderman);

        // Mario Kart 8 Deluxe
        Game mario = new Game();
        mario.setName("Mario Kart 8 Deluxe");
        mario.setPrice(new BigDecimal("59"));
        mario.setConsole("switch");
        mario.setImagem("https://imgs.search.brave.com/skw4HoUF3pjne1tcDZIg6fnyD9RGyeq01aUqSo7-sy0/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NjFVRm5XNFhld0wu/anBn");
        repository.save(mario);

        // Hollow Knight
        Game hollow = new Game();
        hollow.setName("Hollow Knight");
        hollow.setPrice(new BigDecimal("59"));
        hollow.setConsole("steam");
        hollow.setImagem("https://thunderkeys.com/cdn/shop/files/1_d34353ea-c2cc-456d-abd8-0036d65f8255.png?v=1703066100");
        repository.save(hollow);

        // SubNautica Deep Ocean Bundle
        Game subnautica = new Game();
        subnautica.setName("SubNautica Deep Ocean Bundle");
        subnautica.setPrice(new BigDecimal("59"));
        subnautica.setConsole("steam");
        subnautica.setImagem("https://www.mmoga.com/images/games/_ext/1625419/subnautica-deep-ocean-bundle_large.png");
        repository.save(subnautica);

        // Marvel Guardians Of The Galaxy
        Game guardians = new Game();
        guardians.setName("Marvel Guardians Of The Galaxy");
        guardians.setPrice(new BigDecimal("59"));
        guardians.setConsole("xbox");
        guardians.setImagem("https://lojaarenagames.com.br/wp-content/uploads/2022/02/Marvel_Guardians_Of_The_Galaxy___Xbox_One__Series_X_698612-1.jpg");
        repository.save(guardians);

        // It Takes Two
        Game itTakes = new Game();
        itTakes.setName("It Takes Two");
        itTakes.setPrice(new BigDecimal("59"));
        itTakes.setConsole("xbox");
        itTakes.setImagem("https://images.kabum.com.br/produtos/fotos/sync_mirakl/256128/Jogo-It-Takes-Two-Xbox_1689295716_gg.jpg");
        repository.save(itTakes);

        // Need for Speed Unbound
        Game nfs = new Game();
        nfs.setName("Need for Speed Unbound");
        nfs.setPrice(new BigDecimal("59"));
        nfs.setConsole("xbox");
        nfs.setImagem("https://needgames.com.br/wp-content/uploads/2022/12/need-for-speed-unbound-xbox-series-cover.jpg");
        repository.save(nfs);

        System.out.println("✅ Jogos populados: " + repository.count());
    }

    private void initAccessories(AccessoryRepository repository) {
        // Controle Extra
        Accessory controller = new Accessory();
        controller.setName("Controle Extra");
        controller.setPrice(new BigDecimal("59"));
        controller.setConsole("ps5");
        controller.setImagem("https://img.freepik.com/fotos-premium/controlador-de-jogo-branco-de-proxima-geracao-isolado-em-fundo-de-madeira-luz-natural-foco-seletivo_442713-2770.jpg?semt=ais_incoming&w=740&q=80");
        repository.save(controller);

        // Óculos VR
        Accessory vr = new Accessory();
        vr.setName("Óculos VR");
        vr.setPrice(new BigDecimal("129"));
        vr.setConsole("ps5");
        vr.setImagem("https://images.unsplash.com/photo-1593508512255-86ab42a8e620?q=80");
        repository.save(vr);

        // Headset Gamer
        Accessory headset = new Accessory();
        headset.setName("Headset Pulse Elite");
        headset.setPrice(new BigDecimal("59"));
        headset.setConsole("ps5");
        headset.setImagem("https://www.adrenaline.com.br/wp-content/uploads/2024/03/pulse-elite-pre-venda.jpg");
        repository.save(headset);

        // PlayStation Camera
        Accessory psCamera = new Accessory();
        psCamera.setName("PlayStation Camera");
        psCamera.setPrice(new BigDecimal("59"));
        psCamera.setConsole("ps5");
        psCamera.setImagem("https://imgs.search.brave.com/H5-EBGttIT7NcoczWJL1Q-SkOaPKF69YE5e9IuiUrks/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9pLnl0/aW1nLmNvbS92aV93/ZWJwL2xTRkpvQlda/NWJjL21heHJlc2Rl/ZmF1bHQud2VicA");
        repository.save(psCamera);

        // Kinect
        Accessory kinect = new Accessory();
        kinect.setName("Kinect");
        kinect.setPrice(new BigDecimal("80"));
        kinect.setConsole("xbox");
        kinect.setImagem("https://gamerant.com/wp-content/uploads/2020/07/xbox-series-x-backward-compatibility-kinect.jpg");
        repository.save(kinect);

        // Nintendo Labo
        Accessory labo = new Accessory();
        labo.setName("Nintendo Labo");
        labo.setPrice(new BigDecimal("80"));
        labo.setConsole("switch");
        labo.setImagem("https://assets.b9.com.br/wp-content/uploads/2018/01/nlabo-b9.jpg");
        repository.save(labo);

        // Volante Logitech G920
        Accessory logitech = new Accessory();
        logitech.setName("Volante Logitech G920");
        logitech.setPrice(new BigDecimal("300"));
        logitech.setConsole("xbox");
        logitech.setImagem("https://imgs.search.brave.com/iLJcwPddlMCgchm-ZaApy5_irJZhaIPgjFrpBHdewMA/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9pbWcu/b2x4LmNvbS5ici90/aHVtYnM3MDB4NTAw/LzMxLzMxOTU4NzY5/NDU0MDU1MS53ZWJw");
        repository.save(logitech);

        // Volante Thrustmaster T128
        Accessory thrustmaster = new Accessory();
        thrustmaster.setName("Volante Thrustmaster T128");
        thrustmaster.setPrice(new BigDecimal("200"));
        thrustmaster.setConsole("ps5");
        thrustmaster.setImagem("https://imgs.search.brave.com/FWYOwfhjn8rd_n-8Ykjdr6XEZhy_mmZRc_aQ3wZGXOk/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9zZ2Ft/aW5nLmVzL3N0b3Jh/Z2UvcHJvZHVjdG9z/L3RocnVzdG1hc3Rl/ci10MTI4LndlYnA");
        repository.save(thrustmaster);

        // Base Carregadora
        Accessory base = new Accessory();
        base.setName("Base Carregadora");
        base.setPrice(new BigDecimal("80"));
        base.setConsole("xbox");
        base.setImagem("https://imgs.search.brave.com/506F-472nlgt83JnzOe3zSHpb-04oBvM5OAavB8iKek/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NjFDbG9henhvOEwu/anBn");
        repository.save(base);

        System.out.println("✅ Acessórios populados: " + repository.count());
    }
}
