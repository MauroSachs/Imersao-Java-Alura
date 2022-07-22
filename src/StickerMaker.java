import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class StickerMaker {

    public void cria(InputStream inputStream, String nomeArquivo, String rating, String path) throws Exception {


        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + altura/3;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // configurar a fonte
        var fonte = new Font("Comic Sans MS", Font.PLAIN, largura/4);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        // escrever uma frase na nova imagem
        graphics.drawString(rating, largura/2 -largura/6, altura + altura/4);

        // escrever a nova imagem em um arquivo
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
        ImageIO.write(novaImagem, "png", new File(path, nomeArquivo + ".png"));

    }

}
