package br.com.joseonildo.screensound;

import br.com.joseonildo.screensound.principal.Principal;
import br.com.joseonildo.screensound.repository.AlbumRepository;
import br.com.joseonildo.screensound.repository.ArtistaRepository;
import br.com.joseonildo.screensound.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreensoundApplication implements CommandLineRunner {
	@Autowired
	private ArtistaRepository artistaRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private MusicaRepository musicaRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreensoundApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(artistaRepository, albumRepository, musicaRepository);
		principal.exibeMenu();
	}
}
