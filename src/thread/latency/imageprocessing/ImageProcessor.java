package thread.latency.imageprocessing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessor {
    public static final String SRC_FILE = "./resources/many-flowers.jpg";
    public static final String DST_FILE = "./out/many-flowers.jpg";

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedImage originalImage = ImageIO.read(new File(SRC_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        long start = System.currentTimeMillis();
        //singleThreadedImageProcessing(originalImage, resultImage);
        multiThreadedImageProcessing(originalImage, resultImage);
        long end = System.currentTimeMillis();
        System.out.println("time taken to process image = " + (end - start));
        File processedImage = new File(DST_FILE);
        ImageIO.write(resultImage, "jpg", processedImage);
    }

    public static void multiThreadedImageProcessing(BufferedImage orig, BufferedImage dest) throws InterruptedException {
        int numThreads = 2;
        final int height = orig.getHeight()/numThreads;
        List<Thread> threadList = new ArrayList<>();
        for(int i=0;i<numThreads;++i) {
            final int bottom = i*height;
            threadList.add(new Thread(() -> {
                recolorImage(orig, dest, 0, bottom, orig.getWidth(), height);
            }));
        }

        for(Thread t : threadList) {
            t.start();
        }

        for(Thread t : threadList) {
            t.join();
        }
    }
    public static void singleThreadedImageProcessing(BufferedImage orig, BufferedImage dest) {
        recolorImage(orig, dest, 0, 0, orig.getWidth(), orig.getHeight());
    }
    public static void recolorImage(BufferedImage orig, BufferedImage dest, int left, int bottom, int w, int h) {
        for(int i=left;i<left+w;++i) {
            for(int j=bottom;j<bottom+h;++j) {
                recolorPixel(orig, dest, i,j);
            }
        }
    }
    public static void recolorPixel(BufferedImage orig, BufferedImage dest, int x , int y) {
        int rgb = orig.getRGB(x, y);
        int r = getRed(rgb);
        int b = getBlue(rgb);
        int g = getGreen(rgb);

        int newr = r, newb = b, newg = g, newrgb = rgb;
        if(isShadeOfGrey(r, g, b)) {
            newr = Math.min(255, r+10);
            newg = Math.max(0, g-80);
            newb = Math.max(0, b-20);
            newrgb = createRGBFromColors(newr, newg, newb);
        }

        dest.setRGB(x,y, newrgb);
    }

    public static boolean isShadeOfGrey(int r, int g, int b) {
        return Math.abs(r-g) < 30 && Math.abs(g-b) < 30 && Math.abs(r-b) < 30;
    }

    public static int createRGBFromColors(int r, int g, int b) {
        int rgb = 0;
        rgb |= (r << 16);
        rgb |= (g << 8);
        rgb |= b;
        rgb |= 0xFF000000; //add max opacity;
        return rgb;
    }
    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }

}
