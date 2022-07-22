import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    static void imprimeListaEscolhida(List<Map<String, String>> lista, String path) throws Exception {
        StickerMaker gerador = new StickerMaker();
        for (Map<String, String> filme : lista) {
            try {
                String image = "";
                if (filme.get("image").split("\\._V1").length>1) {
                    image = filme.get("image").split("\\._V1")[0] + ".jpg";
                } else image = filme.get("image");
                String title = filme.get("title").replaceAll(":", " -");
                String rating = filme.get("imDbRating");
                InputStream inputStream = new URL(image).openStream();
                gerador.cria(inputStream, title, rating, path);

                System.out.println(title);
                System.out.println(image);
                System.out.println(rating);
                System.out.println(filme.get("rank"));
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    static String buscaListaNaApi(String url) throws Exception {
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);

        //fazer uma conexão http e buscar os top 250 filmes
        String urlTopFilmes = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        String bodyTopFilmes = buscaListaNaApi(urlTopFilmes);


        //fazer uma conexão http e buscar os filmes mais populares
        String urlFilmesPopulares = "https://api.mocki.io/v2/549a5d8b/MostPopularMovies";
        String bodyFilmesPopulares = buscaListaNaApi(urlFilmesPopulares);

        //fazer uma conexão http e buscar as top 250 series
        String urlTopSeries = "https://api.mocki.io/v2/549a5d8b/Top250TVs";
        String bodyTopSeries = buscaListaNaApi(urlTopSeries);

        //fazer uma conexão http e buscar as series mais populares
        String urlSeriesPopulares = "https://api.mocki.io/v2/549a5d8b/MostPopularTVs";
        String bodySeriesPopulares = buscaListaNaApi(urlSeriesPopulares);

        // extrair só os dados que interessam
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaMelhoresFilmes = parser.parse(bodyTopFilmes);
        List<Map<String, String>> listaFilmesPopulares = parser.parse(bodyFilmesPopulares);
        List<Map<String, String>> listaMelhoresSeries = parser.parse(bodyTopSeries);
        List<Map<String, String>> listaSeriesPopulares = parser.parse(bodySeriesPopulares);



        //escolher a lista desejada
        try {
                System.out.println("Selecione a opção desejada: ");
                System.out.println("1: Lista com os Top 250 Filmes");
                System.out.println("2: Lista com os filmes mais populares");
                System.out.println("3: Lista com as Top 250 Séries");
                System.out.println("4: Lista com séries mais populares");
                int escolha;

                escolha = in.nextInt();

                if (escolha == 1) {
                    imprimeListaEscolhida(listaMelhoresFilmes, "Top250Filmes");
                } else if (escolha == 2) {
                    imprimeListaEscolhida(listaFilmesPopulares, "FilmesPopulares");
                } else if (escolha == 3) {
                    imprimeListaEscolhida(listaMelhoresSeries, "TopSéries");
                } else if (escolha == 4) {
                        imprimeListaEscolhida(listaSeriesPopulares, "SeriesPopulares");
                }
                else System.out.println("Opção Inválida.");


        }
        catch (InputMismatchException e) {
            System.out.println("Opção Inválida.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
