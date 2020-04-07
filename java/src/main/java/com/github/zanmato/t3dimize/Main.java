package com.github.zanmato.t3dimize;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

public class Main {
  final static double IMAGE_SIZE = 600.0;

  final static double CUBE_LENGTH = 36.0;
  final static double LETTER_WEIGHT = 12.0;

  final static Color bkgColor = Color.RED;
  final static Color fgColor = Color.WHITE;

  final static String topFaceFile = "./top-face.png";
  final static String leftFaceFile = "./left-face.png";
  final static String rightFaceFile = "./right-face.png";
  final static String blenderUVFile = "./uv.png";

  public static void main(String[] args) throws IOException {
    TopFace topFace = new TopFace();
    topFace.drawImage();
    topFace.saveImage();
    topFace.showTransformedImage();

    LeftFace leftFace = new LeftFace();
    leftFace.drawImage();
    leftFace.saveImage();
    leftFace.showTransformedImage();

    RightFace rightFace = new RightFace();
    rightFace.drawImage();
    rightFace.saveImage();
    rightFace.showTransformedImage();

    BlenderUV blenderUV = new BlenderUV(topFace.image, leftFace.image, rightFace.image);
    blenderUV.drawImage();
    blenderUV.saveImage();
  }

  static class TopFace extends JPanel {
    TopFace() {
      setPreferredSize(new Dimension((int) IMAGE_SIZE * 2, (int) IMAGE_SIZE * 2));
    }

    BufferedImage image = new BufferedImage((int) IMAGE_SIZE, (int) IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);

    Rectangle2D tBar = new Rectangle2D.Double(-LETTER_WEIGHT / 2.0, -LETTER_WEIGHT / 2.0, LETTER_WEIGHT, CUBE_LENGTH / 2.0 + LETTER_WEIGHT / 2.0);
    Rectangle2D tPole = new Rectangle2D.Double(-LETTER_WEIGHT / 2.0, 0.0, LETTER_WEIGHT, CUBE_LENGTH / 4.0 + LETTER_WEIGHT / 4.0);
    Rectangle2D iPole = new Rectangle2D.Double(0.0, 0.0, CUBE_LENGTH / 2.0 - LETTER_WEIGHT * 3.0 / 2.0, LETTER_WEIGHT);

    public void drawImage() {
      Graphics2D graphics = image.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setColor(bkgColor);
      graphics.fillRect(0, 0, (int) IMAGE_SIZE, (int) IMAGE_SIZE);
      graphics.setColor(fgColor);

      AffineTransform transformBase = AffineTransform.getScaleInstance(IMAGE_SIZE / CUBE_LENGTH, IMAGE_SIZE / CUBE_LENGTH);
      graphics.transform(transformBase);
      AffineTransform oldTransform = null;

      oldTransform = graphics.getTransform();
      AffineTransform transformTBar = AffineTransform.getTranslateInstance(CUBE_LENGTH / 2.0, CUBE_LENGTH / 2.0);
      graphics.transform(transformTBar);
      graphics.fill(tBar);
      graphics.setTransform(oldTransform);

      oldTransform = graphics.getTransform();
      AffineTransform transformTPole = AffineTransform.getTranslateInstance(CUBE_LENGTH / 2.0, CUBE_LENGTH * 3.0 / 4.0 - LETTER_WEIGHT / 4.0);
      transformTPole.shear(1.0, 0.0);
      graphics.transform(transformTPole);
      graphics.fill(tPole);
      graphics.setTransform(oldTransform);

      oldTransform = graphics.getTransform();
      AffineTransform transformIPole = AffineTransform.getTranslateInstance(CUBE_LENGTH / 2.0 + LETTER_WEIGHT * 3.0 / 2.0, CUBE_LENGTH * 3.0 / 4.0 - LETTER_WEIGHT * 5.0 / 4.0);
      transformIPole.shear(0.0, 1.0);
      graphics.transform(transformIPole);
      graphics.fill(iPole);
      graphics.setTransform(oldTransform);
    }

    public void saveImage() throws IOException {
      ImageIO.write(image, "PNG", new File(topFaceFile));
    }

    public void showTransformedImage() {
      final JPanel pane = this;

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          JFrame frame = new JFrame("Top Face");
          frame.setContentPane(pane);
          frame.pack();
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        }
      });
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D graphics = (Graphics2D) g;

      AffineTransform transform = new AffineTransform();
      transform.translate(IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0);
      transform.scale(1.0, 1.0 / sqrt(3.0));
      transform.rotate(PI / 4.0, IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0);

      graphics.setTransform(transform);
      g.drawImage(image, 0, 0, null);
    }
  }

  static class LeftFace extends JPanel {
    LeftFace() {
      setPreferredSize(new Dimension((int) IMAGE_SIZE * 2, (int) IMAGE_SIZE * 2));
    }

    BufferedImage image = new BufferedImage((int) IMAGE_SIZE, (int) IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);

    Point2D tBarP1 = new Point2D.Double(-LETTER_WEIGHT / 2.0, 0.0);
    Point2D tBarP2 = new Point2D.Double(LETTER_WEIGHT / 2.0, 0.0);
    Point2D tBarP3 = new Point2D.Double(-LETTER_WEIGHT / 2.0, LETTER_WEIGHT);
    Rectangle2D tPole = new Rectangle2D.Double(0.0, 0.0, LETTER_WEIGHT, CUBE_LENGTH / 2.0 + LETTER_WEIGHT / 2.0);

    public void drawImage() {
      Graphics2D graphics = image.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setColor(bkgColor);
      graphics.fillRect(0, 0, (int) IMAGE_SIZE, (int) IMAGE_SIZE);
      graphics.setColor(fgColor);

      AffineTransform transformBase = AffineTransform.getScaleInstance(IMAGE_SIZE / CUBE_LENGTH, IMAGE_SIZE / CUBE_LENGTH);
      graphics.transform(transformBase);
      AffineTransform oldTransform = null;

      oldTransform = graphics.getTransform();
      AffineTransform transformTBar = AffineTransform.getTranslateInstance(CUBE_LENGTH / 2.0, 0.0);
      graphics.transform(transformTBar);
      Path2D tBar = new Path2D.Double();
      tBar.moveTo(tBarP1.getX(), tBarP1.getY());
      tBar.lineTo(tBarP2.getX(), tBarP2.getY());
      tBar.lineTo(tBarP3.getX(), tBarP3.getY());
      tBar.closePath();
      graphics.fill(tBar);
      graphics.setTransform(oldTransform);

      oldTransform = graphics.getTransform();
      AffineTransform transformTPole = AffineTransform.getTranslateInstance(CUBE_LENGTH * 3.0 / 4.0 - LETTER_WEIGHT / 4.0, 0.0);
      graphics.transform(transformTPole);
      graphics.fill(tPole);
      graphics.setTransform(oldTransform);
    }

    public void saveImage() throws IOException {
      ImageIO.write(image, "PNG", new File(leftFaceFile));
    }

    public void showTransformedImage() {
      final JPanel pane = this;

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          JFrame frame = new JFrame("Left Face");
          frame.setContentPane(pane);
          frame.pack();
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        }
      });
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D graphics = (Graphics2D) g;

      AffineTransform transform = new AffineTransform();
      transform.translate(IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0);
      transform.scale(sqrt(3.0) / 2.0, 1.0);
      transform.shear(0, 1.0 / sqrt(3.0));

      graphics.setTransform(transform);
      g.drawImage(image, 0, 0, null);
    }
  }

  static class RightFace extends JPanel {
    RightFace() {
      setPreferredSize(new Dimension((int) IMAGE_SIZE * 2, (int) IMAGE_SIZE * 2));
    }

    BufferedImage image = new BufferedImage((int) IMAGE_SIZE, (int) IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);

    Rectangle2D iPole = new Rectangle2D.Double(0.0, 0.0, LETTER_WEIGHT, CUBE_LENGTH / 2.0 + LETTER_WEIGHT / 2.0);

    public void drawImage() {
      Graphics2D graphics = image.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setColor(bkgColor);
      graphics.fillRect(0, 0, (int) IMAGE_SIZE, (int) IMAGE_SIZE);
      graphics.setColor(fgColor);

      AffineTransform transformBase = AffineTransform.getScaleInstance(IMAGE_SIZE / CUBE_LENGTH, IMAGE_SIZE / CUBE_LENGTH);
      graphics.transform(transformBase);
      AffineTransform oldTransform = null;

      oldTransform = graphics.getTransform();
      AffineTransform transformIPole = AffineTransform.getTranslateInstance(-CUBE_LENGTH / 4.0 + LETTER_WEIGHT * 7.0 / 4.0, 0.0);
      graphics.transform(transformIPole);
      graphics.fill(iPole);
      graphics.setTransform(oldTransform);
    }

    public void saveImage() throws IOException {
      ImageIO.write(image, "PNG", new File(rightFaceFile));
    }

    public void showTransformedImage() {
      final JPanel pane = this;

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          JFrame frame = new JFrame("Right Face");
          frame.setContentPane(pane);
          frame.pack();
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        }
      });
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D graphics = (Graphics2D) g;

      AffineTransform transform = new AffineTransform();
      transform.translate(IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0);
      transform.scale(sqrt(3.0) / 2.0, 1.0);
      transform.shear(0, -1.0 / sqrt(3.0));

      graphics.setTransform(transform);
      g.drawImage(image, 0, 0, null);
    }
  }

  static class BlenderUV {
    BlenderUV(BufferedImage top, BufferedImage left, BufferedImage right) {
      this.top = top;
      this.left = left;
      this.right = right;
    }

    BufferedImage top, left, right;

    BufferedImage image = new BufferedImage((int) IMAGE_SIZE * 4, (int) IMAGE_SIZE * 4, BufferedImage.TYPE_INT_RGB);

    public void drawImage() {
      Graphics2D graphics = image.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setColor(Color.RED);
      graphics.fillRect(0, 0, (int) IMAGE_SIZE * 4, (int) IMAGE_SIZE * 4);
      graphics.setColor(fgColor);

      graphics.rotate(PI / 2.0, IMAGE_SIZE * 2, IMAGE_SIZE * 2);

      AffineTransform transformTop = new AffineTransform();
      transformTop.translate(IMAGE_SIZE, IMAGE_SIZE / 2.0);
      transformTop.rotate(PI / 2.0, IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0);
      graphics.drawRenderedImage(top, transformTop);

      AffineTransform transformBottom = new AffineTransform();
      transformBottom.translate(IMAGE_SIZE, IMAGE_SIZE * 2.5);
      graphics.drawRenderedImage(top, transformBottom);

      AffineTransform transformLeft = new AffineTransform();
      transformLeft.translate(0.0, IMAGE_SIZE * 1.5);
      graphics.drawRenderedImage(left, transformLeft);

      AffineTransform transformRight = new AffineTransform();
      transformRight.translate(IMAGE_SIZE * 1.0, IMAGE_SIZE * 1.5);
      graphics.drawRenderedImage(right, transformRight);

      AffineTransform transformRightBack = new AffineTransform();
      transformRightBack.translate(IMAGE_SIZE * 2.0, IMAGE_SIZE * 1.5);
      transformRightBack.rotate(PI, IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0);
      graphics.drawRenderedImage(right, transformRightBack);

      AffineTransform transformLeftBack = new AffineTransform();
      transformLeftBack.translate(IMAGE_SIZE * 3.0, IMAGE_SIZE * 1.5);
      transformLeftBack.rotate(PI, IMAGE_SIZE / 2.0, IMAGE_SIZE / 2.0);
      graphics.drawRenderedImage(left, transformLeftBack);
    }

    public void saveImage() throws IOException {
      ImageIO.write(image, "PNG", new File(blenderUVFile));
    }
  }
}
