package br.com.joseonildo.screensound.principal;

import br.com.joseonildo.screensound.model.Artista;
import br.com.joseonildo.screensound.model.Album;
import br.com.joseonildo.screensound.model.Musica;
import br.com.joseonildo.screensound.model.TipoArtista;

import br.com.joseonildo.screensound.repository.ArtistaRepository;
import br.com.joseonildo.screensound.repository.AlbumRepository;
import br.com.joseonildo.screensound.repository.MusicaRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Principal {
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    private final MusicaRepository musicaRepository;
    public Scanner leitura = new Scanner(System.in);
    public String nomeArtista = "";
    public String nomeAlbum = "";
    public String nomeMusica = "";
    Artista artistaSelecionado = null;
    Album albumSelecionado = null;
    boolean erro = false;

    public Principal(ArtistaRepository artistaRepository,
                     AlbumRepository albumRepository,
                     MusicaRepository musicaRepository) {
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
        this.musicaRepository = musicaRepository;
    }

    public void exibeMenu() {
        String opcao = "-1";
        while (!opcao.equalsIgnoreCase("0")
        ) {
            var menu = """
                    \n####### MENU - Screen Sound Músicas #######
                    
                    1 -> Cadastrar Artistas
                    2 -> Cadastrar Albuns
                    3 -> Cadastrar Músicas
                    4 -> Listar Artistas
                    5 -> Listar Albuns
                    6 -> Listar Músicas
                    7 -> Listar Albuns por Artista
                    8 -> Listar Músicas por Artista
                    9 -> Listar Músicas por Album
                    
                    10 -> Pesquisar dados sobre um artista
                    0  -> Sair
                    
                    #############################################
                    """;

            System.out.print(menu);
            System.out.print("Opção: ");
            opcao = leitura.nextLine();
            System.out.println();

            switch (opcao) {
                case "1":
                    cadastrarArtistas();
                    break;
                case "2":
                    cadastrarAlbuns();
                    break;
                case "3":
                    cadastrarMusicas();
                    break;
                case "4":
                    listarArtistas();
                    break;
                case "5":
                    listarAlbuns();
                    break;
                case "6":
                    listarMusicas();
                    break;
                case "7":
                    listarAlbunsPorArtista();
                    break;
                case "8":
                    listarMusicasPorArtista();
                    break;
                case "9":
                    listarMusicasPorAlbum();
                    break;
                case "10":
                    pesquisarDadosDoArtista();
                    break;
                case "0":
                    System.out.println("Encerrando a aplicação!\n");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarArtistas() {
        do {
            if (verificaArtistaExistente(true)) {
                System.out.println("ERRO! Artista " + artistaSelecionado.getNome() + " já cadastrado! Tente outro nome!\n");
            } else {
                if (nomeArtista.isBlank()) {
                    break;
                } else {
                    realizaCadastroArtista(nomeArtista);
                }
            }
        } while (true);
        encerrarLoop();
    }

    private void cadastrarAlbuns() {
        do {
            nomeArtista = "";
            nomeAlbum = "";
            if (artistaSelecionado != null) {
                System.out.println("\nArtista selecionado: " + artistaSelecionado.getNome());
                if (verificaAlbumExistente(true)) {
                    System.out.println("ERRO! Album " + albumSelecionado.getNome() + " já cadastrado! Tente outro nome!\n");
                } else {
                    if (nomeAlbum.isBlank()) {
                        break;
                    } else {
                        realizaCadastroAlbum(nomeAlbum);
                    }
                }
            } else {
                verificaArtistaExistente(false);
                if (nomeArtista.isBlank()) {
                    break;
                }
            }
        } while (true);
        encerrarLoop();
    }

    private void cadastrarMusicas() {
        nomeArtista = "";
        nomeAlbum = "";
        nomeMusica = "Cadastrar";
        do {
            if (artistaSelecionado != null) {
                System.out.println("\nArtista selecionado: " + artistaSelecionado.getNome());
                if (albumSelecionado != null) {
                    System.out.println("Album selecionado: " + albumSelecionado.getNome());
                    realizaCadastroMusica(artistaSelecionado, albumSelecionado);
                } else {
                    if (!verificaAlbumExistente(false)) {
                        if (erro) erro = false;
                        else {
                            System.out.println("ERRO: Album não encontrado, verifique o nome digitado e tente novamente");
                            pausa();
                        }
                    }
                    if (nomeAlbum.isBlank()) {
                        encerrarLoop();
                        break;
                    }
                }
            }else{
                if (!verificaArtistaExistente(false)) {
                    if (erro) erro = false;
                    else {
                        System.out.println("ERRO: Artista não encontrado, verifique o nome digitado e tente novamente");
                        pausa();
                    }
                }
                if (nomeArtista.isBlank()) {
                    encerrarLoop();
                    break;
                }
            }
        }while (!nomeMusica.isBlank());
        encerrarLoop();
    }

    private void listarArtistas() {
        List<Artista> artistas = artistaRepository.findAll();
        System.out.println("Todos os Artistas cadastrados:");
        artistas.forEach(a -> System.out.println(a.getNome()));
        pausa();
    }

    private void listarAlbuns() {
        List<Album> albuns = albumRepository.findAll();
        System.out.println("Todos os Albuns cadastrados:");
        albuns.forEach(a -> System.out.println(
                a.getNome() + " - " + a.getArtista().getNome()
        ));
        pausa();
    }

    private void listarMusicas() {
        List<Musica> musicas = musicaRepository.findAll();
        System.out.println("Todas as musicas cadastradas:");
        musicas.forEach(m -> System.out.println(
                        m.getArtista().getNome() +
                        ": " + m.getAlbum().getNome() +
                        " - " + m.getNome()
        ));
        pausa();
    }

    private void listarAlbunsPorArtista() {
        if (verificaArtistaExistente(true)) {
            AtomicInteger count = new AtomicInteger(1);
            System.out.println("\nTodos os Albuns do Artista " + artistaSelecionado.getNome() + ": ");
            var albuns = albumRepository.buscarAlbunsDoArtista(artistaSelecionado.getNome());
            albuns.forEach(a -> {
                System.out.println(count + " - " + a.getNome());
                count.getAndIncrement();
            });
            pausa();
        } else {
            if (erro) erro = false;
            else {
                System.out.println("ERRO: Artista não encontrado, verifique o nome digitado e tente novamente");
                pausa();
            }
        }
    }

    private void listarMusicasPorArtista() {
        if (verificaArtistaExistente(true)) {
            AtomicInteger count = new AtomicInteger(1);
            System.out.println("\nTodas as Musicas do Artista " + artistaSelecionado.getNome() + ": ");
            var musicas = musicaRepository.buscarMusicasDoArtista(artistaSelecionado.getNome());
            musicas.forEach(m -> {
                System.out.println(count + " - " + m.getNome());
                count.getAndIncrement();
            });
            pausa();
        } else {
            if (erro) erro = false;
            else {
                System.out.println("ERRO: Artista não encontrado, verifique o nome digitado e tente novamente");
                pausa();
            }
        }
    }

    private void listarMusicasPorAlbum() {
        if (verificaArtistaExistente(true)) {
            System.out.println("\nArtista: " + artistaSelecionado.getNome() + "\n*** Albuns *** ");
            var albuns = albumRepository.buscarAlbunsDoArtista(artistaSelecionado.getNome());
            albuns.forEach(a -> {
                System.out.println(a.getNome() + ": ");
                a.getMusicas().forEach(m -> {
                    System.out.println(m.getNome());
                });
                System.out.println();
            });
            pausa();
        } else {
            if (erro) erro = false;
            else {
                System.out.println("ERRO: Artista não encontrado, verifique o nome digitado e tente novamente");
                pausa();
            }
        }
    }

    private void pesquisarDadosDoArtista() {
    }

    public boolean verificaArtistaExistente(boolean apenasVerificar) {
        System.out.println("Digite o nome do Artista ou pressione ENTER para finalizar");
        System.out.print("Nome: ");
        nomeArtista = leitura.nextLine();
        if (nomeArtista.isBlank()) {
            erro = true;
            return false;
        } else {
            Optional<Artista> artista;
            try {
                artista = artistaRepository.findByNomeContainingIgnoreCase(nomeArtista);
            } catch (IncorrectResultSizeDataAccessException e) {
                var artistas = artistaRepository.buscarArtista(nomeArtista);
                System.out.println("\nERRO: Mais de um artista encontrado com esse nome: ");
                artistas.forEach(a -> System.out.println(a.getNome()));
                pausa();
                erro = true;
                return false;
            }
            if (artista.isPresent()) {
                artistaSelecionado = artista.get();
                return true;
            } else {
                if (apenasVerificar) {
                    return  false;
                } else {
                    System.out.println("ERRO: Artista não encontrado! quer cadastrar? (S/N)");
                    System.out.print("Resposta: ");
                    var resposta = leitura.nextLine();
                    if (resposta.toLowerCase().contains("s")) {
                        realizaCadastroArtista(nomeArtista);
                        return true;
                    } else {
                        nomeArtista = "";
                        return false;
                    }
                }
            }
        }
    }

    public boolean verificaAlbumExistente( boolean apenasVerificar) {
        System.out.println("Digite o nome do Album ou pressione ENTER para finalizar");
        System.out.print("Nome: ");
        nomeAlbum = leitura.nextLine();
        if (nomeAlbum.isBlank()) {
            erro = true;
            return false;
        } else {
            Optional<Album> album;
            try {
                album = albumRepository.buscarAlbumDoArtista(artistaSelecionado.getNome(), nomeAlbum);
            } catch (IncorrectResultSizeDataAccessException e) {
                var albuns = albumRepository.buscarAlbunsParecidos(artistaSelecionado.getNome(), nomeAlbum);;
                System.out.println("\nERRO: Mais de um Album encontrado com esse nome: ");
                albuns.forEach(a -> System.out.println(a.getNome()));
                pausa();
                erro = true;
                nomeArtista = "";
                return false;
            }
            if (album.isPresent()) {
                albumSelecionado = album.get();
                return true;
            } else {
                if (apenasVerificar) {
                    return false;
                } else {
                    System.out.println("Album não encontrado, quer cadastrar? (S/N)");
                    System.out.print("Resposta: ");
                    var resposta = leitura.nextLine();
                    if (resposta.toLowerCase().contains("s")) {
                        realizaCadastroAlbum(nomeAlbum);
                        return true;
                    } else {
                        nomeAlbum = "";
                        return false;
                    }
                }
            }
        }
    }

    public void realizaCadastroArtista(String nomeArtista) {
        String tipo;
        System.out.println("\nPor favor informe o tipo do Artista: Solo, Dupla, Trio, Quarteto ou Banda");
        System.out.print("Tipo: ");
        tipo = leitura.nextLine();
        if (tipo.isBlank()) {
            System.out.println("ERRO: Tipo de banda não digitada, cadastro cancelado!\n");
        } else {
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            artistaSelecionado = new Artista(nomeArtista, tipoArtista);
            artistaRepository.save(artistaSelecionado);
            System.out.println("\nArtista: " + artistaSelecionado.getNome() + " -> Cadastrado com sucesso!\n");
        }
    }

    public void realizaCadastroAlbum(String nomeAlbum) {
            albumSelecionado = new Album(nomeAlbum, artistaSelecionado);
            albumRepository.save(albumSelecionado);
            System.out.println("\nAlbum: " + albumSelecionado.getNome() +
                    "\nArtista: " + artistaSelecionado.getNome() +
                    "\nAlbum cadastrado com sucesso!\n");
    }

    public void realizaCadastroMusica(Artista nomeArtista, Album nomeAlbum) {
        System.out.println("Informe o título da música ou pressione ENTER para finalizar");
        System.out.print("Titulo: ");
        nomeMusica = leitura.nextLine();
        if (nomeMusica.isBlank()) {
            return;
        }
        Musica novaMusica = new Musica(nomeMusica, nomeArtista, nomeAlbum);
        musicaRepository.save(novaMusica);
        System.out.println(
                        "\nArtista: "+ artistaSelecionado.getNome() +
                        "\nAlbum: "+ albumSelecionado.getNome() +
                        "\nMusica: " + novaMusica.getNome() +
                        "\nMúsica cadastrada com sucesso!");
    }

    public void encerrarLoop() {
        artistaSelecionado = null;
        albumSelecionado = null;
        nomeArtista = "";
        nomeAlbum = "";
        nomeMusica = "";
    }

    public void pausa() {
        System.out.print("\nPressione ENTER para continuar...");
        leitura.nextLine();
    }
}

