import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        //fazer uma conexão http e buscar os top 250 filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();


        //fazer uma conexão http e buscar os filmes mais populares
        String url2 = "https://api.mocki.io/v2/549a5d8b/MostPopularMovies";
        URI endereco2 = URI.create(url2);
        HttpRequest request2 = HttpRequest.newBuilder(endereco2).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        String body2 = response2.body();

        // extrair só os dados que interessam
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaMelhoresFilmes = parser.parse(body);
        List<Map<String, String>> listaFilmesPopulares = parser.parse(body2);


        //exibir e manipular os dados
        for (Map<String, String> filme : listaFilmesPopulares) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));
            System.out.println(filme.get("imDbRating"));
            System.out.println();
        }

    }
}
