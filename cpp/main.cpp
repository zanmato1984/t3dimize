#include <iostream>
#include <cstring>
#include <math.h>

#include "include.h"

GLuint faceTextures[3];
GLubyte *topFaceImage, *leftFaceImage, *rightFaceImage;
float rotateX = -45;
float rotateY = 35;

int mouseX;
int mouseY;

void init(void) {
    glClearColor(0.0, 0.0, 0.0, 0.0);
    glEnable(GL_DEPTH_TEST);
    // The following two lines enable semi transparent
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    glGenTextures(3, faceTextures);

    int width, height;
    bool hasAlpha;
    bool success = loadPngImage("../top-face.png", width, height, hasAlpha, &topFaceImage);
    if (!success) {
        std::cout << "Unable to load png file" << std::endl;
        return;
    }
    std::cout << "Image loaded " << width << " " << height << " alpha " << hasAlpha << std::endl;
    glBindTexture(GL_TEXTURE_2D, faceTextures[0]);
    glTexImage2D(GL_TEXTURE_2D, 0, hasAlpha ? 4 : 3, width,
                 height, 0, hasAlpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE,
                 topFaceImage);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    success = loadPngImage("../left-face.png", width, height, hasAlpha, &leftFaceImage);
    if (!success) {
        std::cout << "Unable to load png file" << std::endl;
        return;
    }
    std::cout << "Image loaded " << width << " " << height << " alpha " << hasAlpha << std::endl;
    glBindTexture(GL_TEXTURE_2D, faceTextures[1]);
    glTexImage2D(GL_TEXTURE_2D, 0, hasAlpha ? 4 : 3, width,
                 height, 0, hasAlpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE,
                 leftFaceImage);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    success = loadPngImage("../right-face.png", width, height, hasAlpha, &rightFaceImage);
    if (!success) {
        std::cout << "Unable to load png file" << std::endl;
        return;
    }
    std::cout << "Image loaded " << width << " " << height << " alpha " << hasAlpha << std::endl;
    glBindTexture(GL_TEXTURE_2D, faceTextures[2]);
    glTexImage2D(GL_TEXTURE_2D, 0, hasAlpha ? 4 : 3, width,
                 height, 0, hasAlpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE,
                 rightFaceImage);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
    glShadeModel(GL_FLAT);
}

void display(void) {
    glLoadIdentity();
    glTranslatef(0.0, 0.0, 0.0);
    glRotatef(rotateY, 1, 0, 0);
    glRotatef(rotateX, 0, 1, 0);

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glActiveTexture(GL_TEXTURE0);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, faceTextures[0]);
    glBegin(GL_QUADS);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(-1.0, 1.0, 1.0);
    glTexCoord2f(0.0, 1.0);
    glVertex3f(-1.0, 1.0, -1.0);
    glTexCoord2f(1.0, 1.0);
    glVertex3f(1.0, 1.0, -1.0);
    glTexCoord2f(1.0, 0.0);
    glVertex3f(1.0, 1.0, 1.0);
    glEnd();

    glActiveTexture(GL_TEXTURE0);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, faceTextures[1]);
    glBegin(GL_QUADS);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(-1.0, -1.0, 1.0);
    glTexCoord2f(0.0, 1.0);
    glVertex3f(-1.0, 1.0, 1.0);
    glTexCoord2f(1.0, 1.0);
    glVertex3f(1.0, 1.0, 1.0);
    glTexCoord2f(1.0, 0.0);
    glVertex3f(1.0, -1.0, 1.0);
    glEnd();

    glActiveTexture(GL_TEXTURE0);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, faceTextures[2]);
    glBegin(GL_QUADS);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(1.0, -1.0, 1.0);
    glTexCoord2f(0.0, 1.0);
    glVertex3f(1.0, 1.0, 1.0);
    glTexCoord2f(1.0, 1.0);
    glVertex3f(1.0, 1.0, -1.0);
    glTexCoord2f(1.0, 0.0);
    glVertex3f(1.0, -1.0, -1.0);
    glEnd();

    glActiveTexture(GL_TEXTURE0);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, faceTextures[0]);
    glBegin(GL_QUADS);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(-1.0, -1.0, 1.0);
    glTexCoord2f(0.0, 1.0);
    glVertex3f(1.0, -1.0, 1.0);
    glTexCoord2f(1.0, 1.0);
    glVertex3f(1.0, -1.0, -1.0);
    glTexCoord2f(1.0, 0.0);
    glVertex3f(-1.0, -1.0, -1.0);
    glEnd();

    glActiveTexture(GL_TEXTURE0);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, faceTextures[1]);
    glBegin(GL_QUADS);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(-1.0, 1.0, 1.0);
    glTexCoord2f(0.0, 1.0);
    glVertex3f(-1.0, -1.0, 1.0);
    glTexCoord2f(1.0, 1.0);
    glVertex3f(-1.0, -1.0, -1.0);
    glTexCoord2f(1.0, 0.0);
    glVertex3f(-1.0, 1.0, -1.0);
    glEnd();

    glActiveTexture(GL_TEXTURE0);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, faceTextures[2]);
    glBegin(GL_QUADS);
    glTexCoord2f(0.0, 0.0);
    glVertex3f(-1.0, 1.0, -1.0);
    glTexCoord2f(0.0, 1.0);
    glVertex3f(-1.0, -1.0, -1.0);
    glTexCoord2f(1.0, 1.0);
    glVertex3f(1.0, -1.0, -1.0);
    glTexCoord2f(1.0, 0.0);
    glVertex3f(1.0, 1.0, -1.0);
    glEnd();

    glutSwapBuffers();
}

void reshape(int w, int h) {
    glViewport(0, 0, fmin(w, h), fmin(w, h));
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-4.0, 4.0, -4.0, 4.0, -4.0, 4.0);
    glMatrixMode(GL_MODELVIEW);
}

void mousePassive(int x, int y){
    mouseX = x;
    mouseY = y;
}

void mouseMotion(int x, int y){
    const float SPEED = 2;

    rotateX += (mouseX-x)/SPEED;
    rotateY += (mouseY-y)/SPEED;
    mousePassive(x, y);
    glutPostRedisplay();
}

int
main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGB | GLUT_DEPTH);
    glutCreateWindow("TiDB Cube");
    glutMotionFunc(mouseMotion);
    glutPassiveMotionFunc(mousePassive);
    init();
    glutReshapeFunc(reshape);
    glutDisplayFunc(display);
    std::cout << "Use mouse drag to rotate." << std::endl;
    glutMainLoop();
    return 0;
}