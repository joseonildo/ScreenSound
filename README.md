# 🎵 Screen Sound Músicas

Sistema de gerenciamento de artistas, álbuns e músicas, desenvolvido em **Java** com foco em interação via **linha de comando (CLI)**. Ideal para estudos de manipulação de dados com repositórios e navegação em menus interativos.

---

## 📋 Funcionalidades

A aplicação oferece um menu interativo com as seguintes opções:

### 📥 Cadastro
- `1` → Cadastrar Artistas
- `2` → Cadastrar Álbuns
- `3` → Cadastrar Músicas

### 📃 Listagem
- `4` → Listar Artistas
- `5` → Listar Álbuns
- `6` → Listar Músicas
- `7` → Listar Álbuns por Artista
- `8` → Listar Músicas por Artista
- `9` → Listar Músicas por Álbum

### 🔍 Pesquisa
- `10` → Pesquisar dados sobre um artista (via integração com `ConsultaGemini`)

### 🚪 Saída
- `0` → Encerrar a aplicação

---

## 🧠 Estrutura da Aplicação

- `Principal`: classe principal que controla o fluxo da aplicação.
- `ArtistaRepository`, `AlbumRepository`, `MusicaRepository`: interfaces responsáveis pelo acesso aos dados.
- `Artista`, `Album`, `Musica`: classes que representam as entidades principais.
- `TipoArtista`: enum para definir o tipo do artista (Solo, Dupla, etc).

---

## 🧩 Recursos Extras

- ✅ Validação de duplicidade para evitar cadastros repetidos.
- ✅ Cadastro sugerido para artistas/álbuns não encontrados.
- ✅ Tratamento de exceções para múltiplos resultados usando `IncorrectResultSizeDataAccessException`.

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven ou Gradle configurado
- Banco de dados configurado (ex: H2, PostgreSQL)
- Spring Boot (presumido pela estrutura com repositórios)

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/screen-sound-musicas.git
   cd screen-sound-musicas
