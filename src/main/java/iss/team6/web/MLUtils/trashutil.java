package iss.team6.web.MLUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import ai.djl.Device;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Normalize;
import ai.djl.modality.cv.transform.RandomResizedCrop;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;

public class trashutil {
    private static final int INPUT_SIZE = 224;

    private List<String> classNames;

    //use to identify
    Predictor<ai.djl.modality.cv.Image, Classifications> predictor;

    //Not the jpa model import ai.djl.Model;
    private Model model;

    public trashutil() {
        //Load tags into classNames
        this.loadClassNames();
        //Initializing the model works
        this.init();
    }

    private void loadClassNames() {
        BufferedReader reader = null;
        classNames = new ArrayList<>();
        try {
            InputStream in = trashutil.class.getClassLoader().getResourceAsStream("names.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            String name = null;
            while ((name = reader.readLine()) != null) {
                classNames.add(name);
            }
            System.out.println(classNames);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void init() {
        ImageClassificationTranslator translator = ImageClassificationTranslator.builder()
                .addTransform(new RandomResizedCrop(INPUT_SIZE, INPUT_SIZE, 0.6, 1,
                        3. / 4, 4. / 3))
                .addTransform(new ToTensor())
                .addTransform(new Normalize(
                        new float[] {0.5f, 0.5f, 0.5f},
                        new float[] {0.5f, 0.5f, 0.5f}))
                .optApplySoftmax(true)
                //Load all tags in
                .optSynset(classNames)
                //The 5 with the highest probability are finally displayed
                .optTopK(5)
                .build();

        Model model = Model.newInstance("model", Device.cpu());
        try {
            InputStream inputStream = trashutil.class.getClassLoader().getResourceAsStream("model.pt");
            if (inputStream == null) {
                throw new RuntimeException("model.pt can't find");
            }

            model.load(inputStream);
            predictor = model.newPredictor(translator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ai.djl.modality.cv.Image resizeImage(InputStream inputStream) {
        BufferedImage input = null;
        try {
            input = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int iw = input.getWidth(), ih = input.getHeight();
        int w = 224, h = 224;
        double scale = Math.min(1. *  w / iw, 1. * h / ih);
        int nw = (int) (iw * scale), nh = (int) (ih * scale);
        java.awt.Image img;
        boolean needResize = 1. * iw / ih > 1.4 || 1. * ih / iw > 1.4;
        if (needResize) {
            img = input.getScaledInstance(nw, nh, BufferedImage.SCALE_SMOOTH);
        } else {
            img = input.getScaledInstance(INPUT_SIZE, INPUT_SIZE, BufferedImage.SCALE_SMOOTH);
        }
        BufferedImage out = new BufferedImage(INPUT_SIZE, INPUT_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics g = out.getGraphics();
        g.setColor(new Color(128, 128, 128));
        g.fillRect(0, 0, INPUT_SIZE, INPUT_SIZE);
        out.getGraphics().drawImage(img, 0, needResize ? (INPUT_SIZE - nh) / 2 : 0, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            ImageIO.write(out, "jpg", imageOutputStream);
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
            return ImageFactory.getInstance().fromInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("image transfer failed");
        }
    }

    public String predict(InputStream inputStream) {
        ai.djl.modality.cv.Image input = this.resizeImage(inputStream);

        try {
            Classifications output = predictor.predict(input);
            System.out.println(output.best().getClassName());
            return output.best().getClassName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
